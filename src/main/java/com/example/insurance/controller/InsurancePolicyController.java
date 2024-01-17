package com.example.insurance.controller;

import com.example.insurance.common.CustomErrorResponse;
import com.example.insurance.dto.HealthInformationDTO;
import com.example.insurance.dto.InsurancePolicyDTO;
import com.example.insurance.dto.UserAccountDTO;
import com.example.insurance.entity.HealthInformation;
import com.example.insurance.entity.InsurancePolicy;
import com.example.insurance.entity.UserAccount;
import com.example.insurance.exception.CustomException;
import com.example.insurance.service.InsurancePolicyService;
import com.example.insurance.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/insurance-policy")
public class InsurancePolicyController {
    private final InsurancePolicyService insurancePolicyService;
    private final UserAccountService userAccountService;

    @Autowired
    public InsurancePolicyController(InsurancePolicyService insurancePolicyService,
                                     UserAccountService userAccountService)
    {
        this.insurancePolicyService = insurancePolicyService;
        this.userAccountService = userAccountService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> findAllInsurancePolicy()
    {
        return ResponseEntity.status(HttpStatus.OK).body(insurancePolicyService.findAllInsurancePolicy());
    }

    @GetMapping("/get/{policyId}")
    public ResponseEntity<?> findInsurancePolicyById(@PathVariable Long policyId)
    {
        InsurancePolicyDTO insurancePolicyDTO = insurancePolicyService.findInsurancePolicyById(policyId);
        if (insurancePolicyDTO == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),"InsurancePolicyNotFound","Chính sách hiểm không tồn tại!",new Date()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(insurancePolicyDTO);
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<?> findAllInsurancePolicyByUserId(@PathVariable Long userId)
    {
        Optional<UserAccount> userAccount = userAccountService.getUserById(userId);
        if (userAccount.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),"UserAccountNotFound","Tài khoản không tồn tại!",new Date()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(insurancePolicyService.findAllInsurancePolicyByUserId(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createInsurancePolicy(@RequestBody InsurancePolicyDTO dto)
    {
        insurancePolicyService.createInsurancePolicy(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Thêm chính sách bảo hiểm thành công!");
    }

    @PutMapping("/update/{policyId}")
    public ResponseEntity<?> updateInsurancePolicy(@PathVariable Long policyId, @RequestBody InsurancePolicyDTO dto)
    {
        InsurancePolicyDTO insurancePolicyDTO = insurancePolicyService.findInsurancePolicyById(policyId);
        if (insurancePolicyDTO == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),"InsurancePolicyNotFound","Chính sách hiểm không tồn tại!",new Date()));
        }
        insurancePolicyService.updateInsurancePolicy(policyId, dto);
        return ResponseEntity.status(HttpStatus.OK).body("Cập nhật chính sách bảo hiểm thành công!");
    }

    @PostMapping("/remove/{policyId}")
    public ResponseEntity<?> removeInsurancePolicy(@PathVariable Long policyId)
    {
        insurancePolicyService.removeInsurancePolicy(policyId);
        return ResponseEntity.status(HttpStatus.OK).body("Xóa chính sách bảo hiểm thành công!");
    }
}
