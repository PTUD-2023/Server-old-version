package com.example.insurance.controller;

import com.example.insurance.dto.HealthInformationDTO;
import com.example.insurance.dto.InsurancePlanDTO;
import com.example.insurance.dto.InsurancePolicyDTO;
import com.example.insurance.entity.HealthInformation;
import com.example.insurance.entity.InsurancePlan;
import com.example.insurance.entity.InsurancePolicy;
import com.example.insurance.exception.CustomException;
import com.example.insurance.service.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/insurance-plan")
public class InsurancePlanController {
    private final InsurancePlanService insurancePlanService;

    @Autowired
    public InsurancePlanController(InsurancePlanService insurancePlanService)
    {
        this.insurancePlanService = insurancePlanService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAllInsurancePolicy()
    {
        try {
            Iterable<InsurancePlan> insurancePlans = insurancePlanService.findAllInsurancePlan();
            List<InsurancePlanDTO> dtos = new ArrayList<>();
            for (InsurancePlan plan : insurancePlans)
            {
                dtos.add(insurancePlanService.mapInsurancePlanToDTO(plan));
            }
            return ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findInsurancePlanById(@PathVariable Long id)
    {
        try {
            Optional<InsurancePlan> insurancePlan = insurancePlanService.findInsurancePlanById(id);
            if (insurancePlan.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insurance plan does not exist!");
            }
            InsurancePlanDTO dto = insurancePlanService.mapInsurancePlanToDTO(insurancePlan.get());
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createInsurancePlan(@RequestBody InsurancePlanDTO dto)
    {
        try {
            InsurancePlan insurancePlan = insurancePlanService.mapDTOToInsurancePlan(dto);
            insurancePlanService.createInsurancePlan(insurancePlan);
            return ResponseEntity.status(HttpStatus.CREATED).body("Create insurance plan successful!");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInsurancePlan(@PathVariable Long id, @RequestBody InsurancePlanDTO dto)
    {
        try {
            insurancePlanService.updateInsurancePlan(id, dto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeInsurancePlan(@PathVariable Long id)
    {
        try {
            insurancePlanService.removeInsurancePlan(id);
            return ResponseEntity.status(HttpStatus.OK).body("Remove insurance plan successful!");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

}
