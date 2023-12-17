package com.example.insurance.controller;

import com.example.insurance.entity.InsuranceApplication;
import com.example.insurance.service.InsuranceApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/application")
public class InsuranceApplicationController {
    private final InsuranceApplicationService insuranceApplicationService;

    public InsuranceApplicationController(InsuranceApplicationService insuranceApplicationService)
    {
        this.insuranceApplicationService = insuranceApplicationService;
    }

    @GetMapping("/all-applications")
    public ResponseEntity<?> getAllApplication()
    {
        try
        {
            Iterable<InsuranceApplication> insuranceApplications = insuranceApplicationService.getAllInsuranceApplications();
            return ResponseEntity.status(HttpStatus.OK).body(insuranceApplications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long applicationId)
    {
        try
        {
            Optional<InsuranceApplication> insuranceApplication= insuranceApplicationService.getInsuranceApplicationById(applicationId);
            return ResponseEntity.status(HttpStatus.OK).body(insuranceApplication);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/approve-application/{applicationId}")
    public ResponseEntity<?> approveInsuranceApplication(@PathVariable Long applicationId)
    {
        try
        {
            insuranceApplicationService.approveInsuranceApplication(applicationId);
            return ResponseEntity.status(HttpStatus.OK).body("Application approved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/cancel-application/{applicationId}")
    public ResponseEntity<?> cancelInsuranceApplication(@PathVariable Long applicationId)
    {
        try
        {
            insuranceApplicationService.cancelInsuranceApplication(applicationId);
            return ResponseEntity.status(HttpStatus.OK).body("Application cancelled successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<?> removeInsuranceApplication(@PathVariable Long applicationId)
    {
        try
        {
            insuranceApplicationService.removeInsuranceApplication(applicationId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
