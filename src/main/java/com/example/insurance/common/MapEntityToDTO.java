package com.example.insurance.common;

import com.example.insurance.dto.*;
import com.example.insurance.entity.*;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.List;

public class MapEntityToDTO {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Getter
    private static final MapEntityToDTO instance = new MapEntityToDTO();

    private MapEntityToDTO() {
        // private constructor thực thi singleton pattern
    }

    public UserAccountDTO mapUserAccountToDTO(UserAccount userAccount) {
        return objectMapper.convertValue(userAccount, UserAccountDTO.class);
    }

    public InsurancePlanDTO mapInsurancePlanToDTO(InsurancePlan insurancePlan) {
        return objectMapper.convertValue(insurancePlan, InsurancePlanDTO.class);
    }

    public List<InsurancePlanDTO> mapInsurancePlanListToDTOList(List<InsurancePlan> insurancePlanList) {
        JavaType targetType = objectMapper.getTypeFactory().constructCollectionType(List.class, InsurancePlanDTO.class);
        return objectMapper.convertValue(insurancePlanList, targetType);
    }

    public List<InsurancePlanPriceDTO> mapInsurancePlanPriceListToDTOList(List<InsurancePlanPrice> insurancePlanPriceList) {
        JavaType targetType = objectMapper.getTypeFactory().constructCollectionType(List.class, InsurancePlanPriceDTO.class);
        return objectMapper.convertValue(insurancePlanPriceList, targetType);
    }

    public RegistrationFormDTO mapRegistrationFormToDTO(RegistrationForm registrationForm) {
        return objectMapper.convertValue(registrationForm, RegistrationFormDTO.class);
    }

    public List<RegistrationFormDTO> mapRegistrationFormToDTOList(List<RegistrationForm> registrationFormList) {
        JavaType targetType = objectMapper.getTypeFactory().constructCollectionType(List.class, RegistrationFormDTO.class);
        return objectMapper.convertValue(registrationFormList, targetType);
    }

    public RegistrationForm mapDTOToRegistrationForm(RegistrationFormDTO dto) {
        return objectMapper.convertValue(dto, RegistrationForm.class);
    }

    public InsurancePolicyDTO mapInsurancePolicyToDTO(InsurancePolicy insurancePolicy) {
        return objectMapper.convertValue(insurancePolicy, InsurancePolicyDTO.class);
    }

    public List<InsurancePolicyDTO> mapInsurancePolicyToDTOList(List<InsurancePolicy> insurancePolicyList) {
        JavaType targetType = objectMapper.getTypeFactory().constructCollectionType(List.class, InsurancePolicyDTO.class);
        return objectMapper.convertValue(insurancePolicyList, targetType);
    }

    public InsurancePolicy mapDTOToInsurancePolicy(InsurancePolicyDTO dto) {
        return objectMapper.convertValue(dto, InsurancePolicy.class);
    }

    public HealthInformation mapDTOToHealthInformation(HealthInformationDTO dto) {
        return objectMapper.convertValue(dto, HealthInformation.class);
    }

    public HealthInformationDTO mapHealthInformationToDTO(HealthInformation healthInformation) {
        return objectMapper.convertValue(healthInformation, HealthInformationDTO.class);
    }
}
