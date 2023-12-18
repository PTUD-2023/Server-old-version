package com.example.insurance.controller;

import com.example.insurance.entity.InsuranceRegistration;
import com.example.insurance.service.InsuranceRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceRegistrationController {

    @Autowired
    private InsuranceRegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerInsurance(@RequestBody InsuranceRegistration registration) {
        try {
           registrationService.registerInsurance(registration);
            return new ResponseEntity<>("Send Registration Successful", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed. Please check your input.", HttpStatus.BAD_REQUEST);
        }
    }
}

