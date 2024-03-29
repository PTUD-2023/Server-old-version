package com.example.insurance.service;

import com.example.insurance.common.MapEntityToDTO;
import com.example.insurance.dto.RegistrationFormDTO;
import com.example.insurance.entity.RegistrationForm;
import com.example.insurance.repository.RegistrationFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationFormService {
    private final RegistrationFormRepository registrationFormRepository;

    @Autowired
    public RegistrationFormService(RegistrationFormRepository registrationFormRepository) {
        this.registrationFormRepository = registrationFormRepository;
    }

    @Transactional
    public List<RegistrationFormDTO> getAllRegistrationFormNotPaid() {
        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        List<RegistrationForm> registrationFormList = registrationFormRepository.findAllByStatusNot("Paid");
//        return registrationFormList;
        return mapEntityToDTO.mapRegistrationFormToDTOList(registrationFormList);
    }

    public RegistrationForm getRegistrationFormById(Long formId)
    {
        return registrationFormRepository.findById(formId).get();
    }

    @Transactional
    public RegistrationFormDTO getRegistrationFormDTOById(Long formId) {
        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        Optional<RegistrationForm> registrationForm = registrationFormRepository.findById(formId);
        return registrationForm.map(mapEntityToDTO::mapRegistrationFormToDTO).orElse(null);
    }

    @Transactional
    public void createRegistrationForm(RegistrationFormDTO registrationFormDTO) {
        MapEntityToDTO mapEntityToDTO = MapEntityToDTO.getInstance();
        RegistrationForm registrationForm = mapEntityToDTO.mapDTOToRegistrationForm(registrationFormDTO);
        registrationFormRepository.save(registrationForm);
    }

    @Transactional
    public void updateStatusById(Long formId, String status) {
        registrationFormRepository.updateStatusById(formId, status);
    }

    @Transactional
    public void removeRegistrationForm(Long formId) {
        registrationFormRepository.deleteById(formId);
    }
}

