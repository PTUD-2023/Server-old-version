package com.example.insurance.service;

import com.example.insurance.dto.HealthInformationDTO;
import com.example.insurance.entity.HealthInformation;
import com.example.insurance.exception.CustomException;
import com.example.insurance.repository.HealthInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class HealthInformationService {
    private final HealthInformationRepository healthInformationRepository;

    @Autowired
    public HealthInformationService(HealthInformationRepository healthInformationRepository)
    {
        this.healthInformationRepository = healthInformationRepository;
    }

    public Optional<HealthInformation> findHealthInformationById(Long id)
    {
        return healthInformationRepository.findById(id);
    }

    public void addHealthInformation(HealthInformation healthInformation)
    {
        healthInformationRepository.save(healthInformation);
    }

    public void updateHealthInformation(Long id, HealthInformationDTO dto)
    {
        Optional<HealthInformation> optionalHealthInformation = healthInformationRepository.findById(id);
        if (optionalHealthInformation.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Health information does not exist!");
        }

        HealthInformation healthInformation = mapDTOToHealthInformation(dto);
        healthInformation.setId(id);
        healthInformation.setCreatedAt(optionalHealthInformation.get().getCreatedAt());
        healthInformation.setUpdatedAt(new Date());

        healthInformationRepository.save(healthInformation);
    }

    public HealthInformation mapDTOToHealthInformation(HealthInformationDTO dto)
    {
        HealthInformation healthInformation = new HealthInformation();

        healthInformation.setGeneralHealthCondition(dto.getGeneralHealthCondition());
        healthInformation.setMedicalHistory(dto.getMedicalHistory());
        healthInformation.setMedicinesAndTreatment(dto.getMedicinesAndTreatment());
        healthInformation.setFamilyHistory(dto.getFamilyHistory());
        healthInformation.setLifestyleAndRiskFactors(dto.getLifestyleAndRiskFactors());

        return  healthInformation;
    }

    public HealthInformationDTO mapHealthInformationToDTO(HealthInformation healthInformation)
    {
        HealthInformationDTO dto = new HealthInformationDTO();

        dto.setGeneralHealthCondition(healthInformation.getGeneralHealthCondition());
        dto.setMedicalHistory(healthInformation.getMedicalHistory());
        dto.setMedicinesAndTreatment(healthInformation.getMedicinesAndTreatment());
        dto.setFamilyHistory(healthInformation.getFamilyHistory());
        dto.setLifestyleAndRiskFactors(healthInformation.getLifestyleAndRiskFactors());

        return dto;
    }
}
