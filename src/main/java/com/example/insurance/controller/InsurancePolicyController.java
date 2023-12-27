package com.example.insurance.controller;

import com.example.insurance.dto.HealthInformationDTO;
import com.example.insurance.dto.InsurancePolicyDTO;
import com.example.insurance.entity.HealthInformation;
import com.example.insurance.entity.InsurancePolicy;
import com.example.insurance.exception.CustomException;
import com.example.insurance.service.InsurancePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/insurance-policy")
public class InsurancePolicyController {
    private final InsurancePolicyService insurancePolicyService;

    @Autowired
    public InsurancePolicyController(InsurancePolicyService insurancePolicyService)
    {
        this.insurancePolicyService = insurancePolicyService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAllInsurancePolicy()
    {
        try {
            Iterable<InsurancePolicy> insurancePolicies = insurancePolicyService.findAllInsurancePolicy();
            List<InsurancePolicyDTO> dtos = new ArrayList<>();
            for (InsurancePolicy policy : insurancePolicies)
            {
                dtos.add(insurancePolicyService.mapInsurancePolicyToDTO(policy));
            }
            return ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }

    }

    @GetMapping("/{policyId}")
    public ResponseEntity<?> findInsurancePolicyById(@PathVariable Long policyId)
    {
        try {
            Optional<InsurancePolicy> insurancePolicy = insurancePolicyService.findInsurancePolicyById(policyId);
            if (insurancePolicy.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insurance policy does not exist!");
            }
            InsurancePolicyDTO dto = insurancePolicyService.mapInsurancePolicyToDTO(insurancePolicy.get());
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findAllInsurancePolicyByUserId(@PathVariable Long userId)
    {
        try {
            Iterable<InsurancePolicy> insurancePolicies = insurancePolicyService.findAllInsurancePolicyByUserId(userId);
            List<InsurancePolicyDTO> dtos = new ArrayList<>();
            for (InsurancePolicy policy : insurancePolicies)
            {
                dtos.add(insurancePolicyService.mapInsurancePolicyToDTO(policy));
            }
            return ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }

    }

    @PostMapping("/create")
    public ResponseEntity<?> createInsurancePolicy(@RequestBody InsurancePolicyDTO dto)
    {
        try {
            InsurancePolicy insurancePolicy = insurancePolicyService.mapDTOToInsurancePolicy(dto);
            insurancePolicyService.createInsurancePolicy(insurancePolicy);
            return ResponseEntity.status(HttpStatus.CREATED).body("Create insurance policy successful!");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInsurancePolicy(@PathVariable Long id, @RequestBody InsurancePolicyDTO dto)
    {
        try {
            insurancePolicyService.updateInsurancePolicy(id, dto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeInsurancePolicy(@PathVariable Long id)
    {
        try {
            insurancePolicyService.removeInsurancePolicy(id);
            return ResponseEntity.status(HttpStatus.OK).body("Remove insurance policy successful!");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }
}
