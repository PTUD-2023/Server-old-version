package com.example.insurance.service;

import com.example.insurance.entity.InsuredPerson;
import com.example.insurance.repository.InsuredPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InsuredPersonService {
    private final InsuredPersonRepository insuredPersonRepository;

    @Autowired
    public InsuredPersonService(InsuredPersonRepository insuredPersonRepository)
    {
        this.insuredPersonRepository = insuredPersonRepository;
    }

    public Optional<InsuredPerson> findInsuredPersonById (Long insuredPersonId)
    {
        return insuredPersonRepository.findById(insuredPersonId);
    }
}
