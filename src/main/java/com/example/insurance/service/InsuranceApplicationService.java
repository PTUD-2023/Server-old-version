package com.example.insurance.service;

import com.example.insurance.exception.CustomException;

import com.example.insurance.entity.InsuranceApplication;
import com.example.insurance.repository.InsuranceApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class InsuranceApplicationService {
    private final InsuranceApplicationRepository insuranceApplicationRepository;

    @Autowired
    public InsuranceApplicationService(InsuranceApplicationRepository insuranceApplicationRepository) {
        this.insuranceApplicationRepository = insuranceApplicationRepository;
    }

    public Iterable<InsuranceApplication> getAllInsuranceApplications() {
        return insuranceApplicationRepository.findAll();
    }

    public Optional<InsuranceApplication> getInsuranceApplicationById(Long applicationId) {
        return insuranceApplicationRepository.findById(applicationId);
    }

    public void approveInsuranceApplication(Long applicationId) {
        Optional<InsuranceApplication> insuranceApplication = insuranceApplicationRepository.findById(applicationId);
        if(insuranceApplication.isPresent())
        {
            insuranceApplication.get().setStatus("Approved");
            insuranceApplicationRepository.save(insuranceApplication.get());
        }
        else
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "NotFound","Application not found");

    }

    public void cancelInsuranceApplication(Long applicationId) {
        Optional<InsuranceApplication> insuranceApplication = insuranceApplicationRepository.findById(applicationId);
        if(insuranceApplication.isPresent())
        {
            insuranceApplication.get().setStatus("Cancelled");
            insuranceApplicationRepository.save(insuranceApplication.get());
        }
        else
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "NotFound","Application not found");

    }

    public void removeInsuranceApplication(Long applicationId)
    {
        Optional<InsuranceApplication> insuranceApplication = insuranceApplicationRepository.findById(applicationId);
        if(insuranceApplication.isPresent())
        {
            insuranceApplicationRepository.delete(insuranceApplication.get());
        }
        else
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "NotFound","Application not found");

    }
}
