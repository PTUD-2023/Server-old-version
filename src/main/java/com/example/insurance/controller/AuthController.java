package com.example.insurance.controller;

import com.example.insurance.common.CustomErrorResponse;
import com.example.insurance.common.CustomSuccessResponse;
import com.example.insurance.component.Base64Encoding;
import com.example.insurance.dto.ConfirmCodeRequest;
import com.example.insurance.dto.SignUpRequest;
import com.example.insurance.dto.SignUpResponse;
import com.example.insurance.dto.TokenRefreshRequest;
import com.example.insurance.entity.ConfirmCode;
import com.example.insurance.entity.RefreshToken;
import com.example.insurance.entity.UserAccount;
import com.example.insurance.exception.CustomException;
import com.example.insurance.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/v1/authenticate")
public class AuthController {
    private final UserAccountService userAccountService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final ConfirmCodeService confirmCodeService;
    private final MyEmailService myEmailService;
    private final Environment env;

    @Autowired
    public AuthController(UserAccountService userAccountService, JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, ConfirmCodeService confirmCodeService, MyEmailService myEmailService, Environment env) {
        this.userAccountService = userAccountService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.confirmCodeService = confirmCodeService;
        this.myEmailService = myEmailService;
        this.env = env;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpNewAccount(@RequestBody UserAccount userAccount)
    {
        UserAccount newAccount = userAccountService.signUpNewAccount(userAccount);
        if(newAccount.getId() > 0)
        {
            sendConfirmCodeEmail(userAccount);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomSuccessResponse("Đăng ký tài khoản có email " + userAccount.getEmail() + " thành công!","AccountIsRegistered"));
        }
        else{
            return ResponseEntity.badRequest().body( new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), "RegistrationFailed","Đăng kí tài khoản thất bại!",new Date()));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignUpRequest signUpRequest)
    {
        if(signUpRequest.getEmail().isEmpty() || signUpRequest.getPassword().isEmpty())
        {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(),"LackOfEmailOrPassword","Missing Email or password");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));
        Object principal = authentication.getPrincipal();
        if(userAccountService.getUserByEmail(((UserDetails) principal).getUsername()).get().getStatus().equals("not_activated"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), "UnverifiedAccount","Tài khoản chưa được kích hoạt!",new Date()));
        }
        else if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(signUpRequest.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(new SignUpResponse(jwtService.generateToken(signUpRequest.getEmail()),refreshToken.getToken(),"Success","Đăng nhập thành công!")) ;
        } else {
            throw new UsernameNotFoundException("invalid user with email = " + signUpRequest.getEmail() + " request !");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest tokenRefreshRequest)
    {
        String refreshToken = tokenRefreshRequest.getRefreshToken();
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserAccount)
                .map(userAccount -> {
                    String token = jwtService.generateToken(userAccount.getEmail());
                    return ResponseEntity.ok().body(new SignUpResponse(token,refreshToken));
                })
                .orElseThrow(()->
                        new CustomException(HttpStatus.BAD_REQUEST.value(),"RefreshTokenIsInexist","Refresh token không tồn tại!")
                );

    }

    private void sendConfirmCodeEmail(UserAccount userAccount)
    {
        ConfirmCode confirmCode=  confirmCodeService.createConfirmCode(userAccount.getEmail());
        String confirmationUrl =env.getProperty("client.URL")
                +"/confirm/"+ Base64Encoding.encodeStringToBase64(userAccount.getEmail());

        String to = userAccount.getEmail();
        String subject =confirmCode.getCode()+ " là mã xác nhận của bạn";
        StringBuilder body = new StringBuilder();
        String htmlContent = readEmailTemplate();
        htmlContent = htmlContent.replace("${lastName}",userAccount.getLastName());
        htmlContent = htmlContent.replace("${email}",userAccount.getEmail());
        htmlContent = htmlContent.replace("${code}",confirmCode.getCode());
        htmlContent = htmlContent.replace("${confirmUrl}",confirmationUrl);
        body.append(htmlContent);

        new Thread(()->{
            try
            {
                myEmailService.sendSimpleMessage(to,subject,body.toString());
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    @PostMapping("/confirm")
    public ResponseEntity<CustomSuccessResponse> confirmSignUpAccount(@RequestBody ConfirmCodeRequest confirmCodeRequest)
    {
        Optional<ConfirmCode> confirmCode = confirmCodeService.findByCode(confirmCodeRequest.getCode());
        if(confirmCode.isPresent())
        {
            if(confirmCodeService.isExpiredCode(confirmCode.get()))
            {
                throw new CustomException(HttpStatus.BAD_REQUEST.value(),"ConfirmCodeIsExpired","Verification code expired");
            }
            else{
                String email = confirmCode.get().getUserAccount().getEmail();
                String emailRequest = Base64Encoding.decodeBase64ToString(confirmCodeRequest.getEmail());
                if(emailRequest.equals(email))
                {
                    userAccountService.updateStatusByEmail(email,"activated");
                    confirmCodeService.deleteByCode(confirmCodeRequest.getCode());
                    return ResponseEntity.ok().body(new CustomSuccessResponse("Account confirmed successfully"));
                }
                else{
                    throw new CustomException(HttpStatus.BAD_REQUEST.value(), "ConfirmCodeIsIncorrect","Verification code is wrong");
                }
            }
        }
        else{
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "ConfirmCodeIsIncorrect","Verification code is wrong");
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<CustomSuccessResponse> resendConfirmCode(@RequestParam("email") String email)
    {
        String emailRequest = Base64Encoding.decodeBase64ToString(email);
        Optional<UserAccount> userAccount = userAccountService.getUserByEmail(emailRequest);
        if(userAccount.isEmpty())
        {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "InexistAccount","Account does not exist");
        }
        if(userAccount.get().getStatus().equals("activated"))
        {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "VerifiedAccount","Account has email authentication");
        }
        confirmCodeService.deleteAllByUserAccount(userAccount.get());
        sendConfirmCodeEmail(userAccount.get());
        return ResponseEntity.ok().body(new CustomSuccessResponse("Resend confirmation code successfully"));
    }

    private String readEmailTemplate() {
        Resource resource = new ClassPathResource("static/emailTemplate.html");
        try {
            byte[] byteData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            return new String(byteData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Xử lý lỗi
            System.out.println(e.getMessage());
            return ""; // Hoặc trả về một giá trị mặc định
        }
    }

}
