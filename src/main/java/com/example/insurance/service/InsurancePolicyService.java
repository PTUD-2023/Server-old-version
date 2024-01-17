package com.example.insurance.service;

import com.example.insurance.common.MapEntityToDTO;
import com.example.insurance.dto.InsurancePolicyDTO;
import com.example.insurance.entity.InsurancePolicy;
import com.example.insurance.repository.InsurancePolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InsurancePolicyService {
    private final InsurancePolicyRepository insurancePolicyRepository;


    @Autowired
    public InsurancePolicyService(InsurancePolicyRepository insurancePolicyRepository)
    {
        this.insurancePolicyRepository = insurancePolicyRepository;
    }

    public List<InsurancePolicyDTO> findAllInsurancePolicy()
    {
        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        List<InsurancePolicy> insurancePolicies = (List<InsurancePolicy>) insurancePolicyRepository.findAll();
        return mapEntityToDTO.mapInsurancePolicyToDTOList(insurancePolicies);
    }

    public InsurancePolicyDTO findInsurancePolicyById(Long id)
    {
        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        Optional<InsurancePolicy> insurancePolicy = insurancePolicyRepository.findById(id);
        return insurancePolicy.map(mapEntityToDTO::mapInsurancePolicyToDTO).orElse(null);
    }

    public List<InsurancePolicyDTO> findAllInsurancePolicyByUserId(Long id)
    {
        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        List<InsurancePolicy> insurancePolicies = insurancePolicyRepository.findAllByRegistrationForm_UserAccount_Id(id);
        return mapEntityToDTO.mapInsurancePolicyToDTOList(insurancePolicies);
    }

    public void createInsurancePolicy(InsurancePolicyDTO dto)
    {
        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        InsurancePolicy insurancePolicy = mapEntityToDTO.mapDTOToInsurancePolicy(dto);
        insurancePolicyRepository.save(insurancePolicy);
    }

    public void updateInsurancePolicy(Long id, InsurancePolicyDTO dto)
    {
        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        Optional<InsurancePolicy> insurancePolicy = insurancePolicyRepository.findById(id);
        InsurancePolicy policy = mapEntityToDTO.mapDTOToInsurancePolicy(dto);
        policy.setId(id);
        policy.setCreatedAt(insurancePolicy.get().getCreatedAt());
        policy.setUpdatedAt(new Date());
        insurancePolicyRepository.save(policy);
    }

    public void removeInsurancePolicy(Long id)
    {
        insurancePolicyRepository.deleteById(id);;
    }
}
