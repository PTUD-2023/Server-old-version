package com.example.insurance.service;

import com.example.insurance.entity.InsurancePlan;
import com.example.insurance.repository.InsurancePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InsurancePlanService {
    private final InsurancePlanRepository insurancePlanRepository;

    @Autowired
    public InsurancePlanService(InsurancePlanRepository insurancePlanRepository)
    {
        this.insurancePlanRepository = insurancePlanRepository;
    }

    public Optional<InsurancePlan> findInsurancePlanById (Long insurancePlanId)
    {
        return insurancePlanRepository.findById(insurancePlanId);
    }
}
