package com.example.insurance.service;

import com.example.insurance.common.MapEntityToDTO;
import com.example.insurance.dto.HealthInformationDTO;
import com.example.insurance.entity.HealthInformation;
import com.example.insurance.entity.RegistrationForm;
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

    public HealthInformationDTO findHealthInformationDTOById(Long id)
    {
        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        Optional<HealthInformation> healthInformation = healthInformationRepository.findById(id);
        return healthInformation.map(mapEntityToDTO::mapHealthInformationToDTO).orElse(null);
    }

    public void addHealthInformation(HealthInformationDTO healthInformationDTO)
    {
        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        HealthInformation healthInformation = mapEntityToDTO.mapDTOToHealthInformation(healthInformationDTO);
        healthInformationRepository.save(healthInformation);
    }

    public void updateHealthInformation(Long id, HealthInformationDTO healthInformationDTO)
    {
        Optional<HealthInformation> optionalHealthInformation = healthInformationRepository.findById(id);
        if (optionalHealthInformation.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Thông tin sức khỏe không tồn tại!");
        }

        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        HealthInformation healthInformation = mapEntityToDTO.mapDTOToHealthInformation(healthInformationDTO);
        healthInformation.setId(id);
        healthInformation.setCreatedAt(optionalHealthInformation.get().getCreatedAt());
        healthInformation.setUpdatedAt(new Date());

        healthInformationRepository.save(healthInformation);
    }

}
