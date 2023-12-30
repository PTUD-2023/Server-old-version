package com.example.insurance.service;

import com.example.insurance.dto.InsurancePlanDTO;
import com.example.insurance.dto.InsurancePolicyDTO;
import com.example.insurance.entity.InsurancePlan;
import com.example.insurance.entity.InsurancePolicy;
import com.example.insurance.exception.CustomException;
import com.example.insurance.repository.InsurancePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class InsurancePlanService {
    private final InsurancePlanRepository insurancePlanRepository;

    @Autowired
    public InsurancePlanService(InsurancePlanRepository insurancePlanRepository)
    {
        this.insurancePlanRepository = insurancePlanRepository;
    }

    public Iterable<InsurancePlan> findAllInsurancePlan()
    {
        return insurancePlanRepository.findAll();
    }

    public Optional<InsurancePlan> findInsurancePlanById (Long insurancePlanId)
    {
        return insurancePlanRepository.findById(insurancePlanId);
    }

    public void createInsurancePlan(InsurancePlan insurancePlan)
    {
        insurancePlanRepository.save(insurancePlan);
    }

    public void updateInsurancePlan(Long id, InsurancePlanDTO dto)
    {
        Optional<InsurancePlan> insurancePlan = insurancePlanRepository.findById(id);
        if (insurancePlan.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Insurance policy does not exist!");
        }

        InsurancePlan plan = mapDTOToInsurancePlan(dto);
        plan.setId(id);
        plan.setCreatedAt(insurancePlan.get().getCreatedAt());
        plan.setUpdatedAt(new Date());

        insurancePlanRepository.save(plan);
    }

    public void removeInsurancePlan(Long id)
    {
        Optional<InsurancePlan> insurancePlan = insurancePlanRepository.findById(id);
        if (insurancePlan.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Insurance policy does not exist!");
        }

        insurancePlanRepository.delete(insurancePlan.get());
    }

    public InsurancePlan mapDTOToInsurancePlan(InsurancePlanDTO dto)
    {
        InsurancePlan plan = new InsurancePlan();

        plan.setPlanName(dto.getPlanName());
        plan.setDuration(dto.getDuration());
        plan.setAccidentInsurance(dto.getAccidentInsurance());
        plan.setHospitalization(dto.getHospitalization());
        plan.setSurgery(dto.getSurgery());
        plan.setBeforeAdmission(dto.getBeforeAdmission());
        plan.setAfterDischarge(dto.getAfterDischarge());
        plan.setTakeCareAtHome(dto.getTakeCareAtHome());
        plan.setHospitalizationAllowance(dto.getHospitalizationAllowance());
        plan.setEmergencyTransport(dto.getEmergencyTransport());
        plan.setFuneralAllowance(dto.getFuneralAllowance());
        plan.setStatus(dto.getStatus());

        return plan;
    }

    public InsurancePlanDTO mapInsurancePlanToDTO(InsurancePlan plan)
    {
        InsurancePlanDTO dto = new InsurancePlanDTO();

        dto.setPlanName(plan.getPlanName());
        dto.setDuration(plan.getDuration());
        dto.setAccidentInsurance(plan.getAccidentInsurance());
        dto.setHospitalization(plan.getHospitalization());
        dto.setSurgery(plan.getSurgery());
        dto.setBeforeAdmission(plan.getBeforeAdmission());
        dto.setAfterDischarge(plan.getAfterDischarge());
        dto.setTakeCareAtHome(plan.getTakeCareAtHome());
        dto.setHospitalizationAllowance(plan.getHospitalizationAllowance());
        dto.setEmergencyTransport(plan.getEmergencyTransport());
        dto.setFuneralAllowance(plan.getFuneralAllowance());
        dto.setStatus(plan.getStatus());

        return dto;
    }
}
