package com.example.insurance.service;

import com.example.insurance.dto.InsurancePolicyDTO;
import com.example.insurance.entity.InsurancePlan;
import com.example.insurance.entity.InsurancePolicy;
import com.example.insurance.entity.RegistrationForm;
import com.example.insurance.entity.UserAccount;
import com.example.insurance.exception.CustomException;
import com.example.insurance.repository.InsurancePolicyRepository;
import com.example.insurance.repository.RegistrationFormRepository;
import com.example.insurance.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InsurancePolicyService {
    private final InsurancePolicyRepository insurancePolicyRepository;
    private final UserAccountRepository userAccountRepository;
    private final RegistrationFormRepository registrationFormRepository;

    @Autowired
    public InsurancePolicyService(InsurancePolicyRepository insurancePolicyRepository,
                                  UserAccountRepository userAccountRepository,
                                  RegistrationFormRepository registrationFormRepository)
    {
        this.insurancePolicyRepository = insurancePolicyRepository;
        this.userAccountRepository = userAccountRepository;
        this.registrationFormRepository = registrationFormRepository;
    }

    public Iterable<InsurancePolicy> findAllInsurancePolicy()
    {
        return insurancePolicyRepository.findAll();
    }

    public Optional<InsurancePolicy> findInsurancePolicyById(Long id)
    {
        return insurancePolicyRepository.findById(id);
    }

    public List<InsurancePolicy> findAllInsurancePolicyByUserId(Long id)
    {
        Optional<UserAccount> userAccount = userAccountRepository.findById(id);
        if (userAccount.isEmpty())
        {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "Not Fount", "User account does not exist!");
        }
        Iterable<RegistrationForm> registrationForms = registrationFormRepository.findAllRegistrationFormByUserId(id);
        List<InsurancePolicy> insurancePolicies = new ArrayList<>();
        for(RegistrationForm form : registrationForms)
        {
            InsurancePolicy policy = insurancePolicyRepository.findInsurancePolicyByRegistrationFormId(form.getId());
            insurancePolicies.add(policy);
        }
        return insurancePolicies;
    }

    public void createInsurancePolicy(InsurancePolicy insurancePolicy)
    {
        insurancePolicyRepository.save(insurancePolicy);
    }

    public void updateInsurancePolicy(Long id, InsurancePolicyDTO dto)
    {
        Optional<InsurancePolicy> insurancePolicy = insurancePolicyRepository.findById(id);
        if (insurancePolicy.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Insurance policy does not exist!");
        }

        InsurancePolicy policy = mapDTOToInsurancePolicy(dto);
        policy.setId(id);
        policy.setCreatedAt(insurancePolicy.get().getCreatedAt());
        policy.setUpdatedAt(new Date());

        insurancePolicyRepository.save(policy);
    }

    public void removeInsurancePolicy(Long id)
    {
        Optional<InsurancePolicy> insurancePolicy = insurancePolicyRepository.findById(id);
        if (insurancePolicy.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Insurance policy does not exist!");
        }

        insurancePolicyRepository.delete(insurancePolicy.get());
    }

    public InsurancePolicy mapDTOToInsurancePolicy(InsurancePolicyDTO dto)
    {
        InsurancePolicy policy = new InsurancePolicy();

        Optional<RegistrationForm> registrationForm = registrationFormRepository.findById(dto.getRegistrationFormId());
        if (registrationForm.isEmpty())
        {
            {
                throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Registration form does not exist!");
            }
        }

        policy.setApplyDate(dto.getApplyDate());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setRegistrationForm(registrationForm.get());
        policy.setStatus(dto.getStatus());

        return policy;
    }

    public InsurancePolicyDTO mapInsurancePolicyToDTO(InsurancePolicy policy)
    {
        InsurancePolicyDTO dto = new InsurancePolicyDTO();

        dto.setApplyDate(policy.getApplyDate());
        dto.setStartDate(policy.getStartDate());
        dto.setEndDate(policy.getEndDate());
        dto.setRegistrationFormId(policy.getRegistrationForm().getId());
        dto.setStatus(policy.getStatus());

        return dto;
    }
}
