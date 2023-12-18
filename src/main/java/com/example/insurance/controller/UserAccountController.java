package com.example.insurance.controller;

import com.example.insurance.common.MapEntityToDTO;
import com.example.insurance.dto.UserAccountDTO;
import com.example.insurance.entity.UserAccount;
import com.example.insurance.service.JwtService;
import com.example.insurance.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final JwtService jwtService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, JwtService jwtService) {
        this.userAccountService = userAccountService;
        this.jwtService = jwtService;
    }

    @GetMapping("/infor")
    public ResponseEntity<UserAccountDTO> getUserInfor(@RequestHeader(name = "Authorization") String token)
    {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                String jwtToken = token.substring(7);
                String email = jwtService.extractUsername(jwtToken);
                Optional<UserAccount> userAccount = userAccountService.getUserByEmail(email);
                if(userAccount.isPresent())
                    return ResponseEntity.status(HttpStatus.OK).body(MapEntityToDTO.mapUserAccountToDTO(userAccount.get()));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
