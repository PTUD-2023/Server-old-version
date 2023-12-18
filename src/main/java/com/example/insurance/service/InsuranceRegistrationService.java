package com.example.insurance.service;

import com.example.insurance.entity.InsuranceRegistration;
import com.example.insurance.exception.CustomException;
import com.example.insurance.repository.InsuranceRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class InsuranceRegistrationService {

    @Autowired
    private InsuranceRegistrationRepository registrationRepository;

    public void registerInsurance(InsuranceRegistration registration) {
        if (registration.getName() == null || registration.getName().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "NameRequired", "Name cannot be empty");
        }

        if (registration.getEmail() == null || registration.getEmail().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "EmailRequired", "Email cannot be empty");
        }
        if (registration.getInsuranceType() == null || registration.getInsuranceType().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "InsuranceTypeRequired", "Insurance type cannot be empty");
        }
        if (registration.getDuration() == null || registration.getDuration().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "DurationRequired", "Duration cannot be empty");
        }
        registrationRepository.save(registration);
    }

}

