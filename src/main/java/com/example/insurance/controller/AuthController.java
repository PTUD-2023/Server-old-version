package com.example.insurance.controller;

import com.example.insurance.common.CustomErrorResponse;
import com.example.insurance.common.CustomSuccessResponse;
import com.example.insurance.dto.SignUpRequest;
import com.example.insurance.dto.SignUpResponse;
import com.example.insurance.dto.TokenRefreshRequest;
import com.example.insurance.entity.RefreshToken;
import com.example.insurance.entity.UserAccount;
import com.example.insurance.exception.CustomException;
import com.example.insurance.service.JwtService;
import com.example.insurance.service.RefreshTokenService;
import com.example.insurance.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/v1/authenticate")
public class AuthController {
    private final UserAccountService userAccountService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(UserAccountService userAccountService, JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.userAccountService = userAccountService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpNewAccount(@RequestBody UserAccount userAccount)
    {
        UserAccount newAccount = userAccountService.signUpNewAccount(userAccount);
        if(newAccount.getId() > 0)
        {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomSuccessResponse("Account registration has email " + userAccount.getEmail() + " successful","AccountIsRegistered"));
        }
        else{
            return ResponseEntity.badRequest().body( new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), "RegistrationFailed","Account registration failed",new Date()));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<SignUpResponse> signIn(@RequestBody SignUpRequest signUpRequest)
    {
        if(signUpRequest.getEmail().isEmpty() || signUpRequest.getPassword().isEmpty())
        {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(),"LackOfEmailOrPassword","Missing Email or password");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(signUpRequest.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(new SignUpResponse(jwtService.generateToken(signUpRequest.getEmail()),refreshToken.getToken(),"Success","Login is successfully")) ;
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
                        new CustomException(HttpStatus.BAD_REQUEST.value(),"RefreshTokenIsInexist","Refresh token does not exist")
                );

    }
}
