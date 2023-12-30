package com.example.insurance.service;

import com.example.insurance.dto.RegistrationFormDTO;
import com.example.insurance.entity.InsurancePlan;
import com.example.insurance.entity.InsuredPerson;
import com.example.insurance.entity.RegistrationForm;
import com.example.insurance.entity.UserAccount;
import com.example.insurance.exception.CustomException;
import com.example.insurance.repository.InsurancePlanRepository;
import com.example.insurance.repository.InsuredPersonRepository;
import com.example.insurance.repository.RegistrationFormRepository;
import com.example.insurance.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationFormService {
    private final RegistrationFormRepository registrationFormRepository;
    private final UserAccountRepository userAccountRepository;
    private final InsurancePlanRepository insurancePlanRepository;
    private final InsuredPersonRepository insuredPersonRepository;

    @Autowired
    public RegistrationFormService(RegistrationFormRepository registrationFormRepository,
                                   UserAccountRepository userAccountRepository,
                                   InsurancePlanRepository insurancePlanRepository,
                                   InsuredPersonRepository insuredPersonRepository)
    {
        this.registrationFormRepository = registrationFormRepository;
        this.userAccountRepository = userAccountRepository;
        this.insurancePlanRepository = insurancePlanRepository;
        this.insuredPersonRepository = insuredPersonRepository;
    }

    public Iterable<RegistrationForm> getAllRegistrationForm()
    {
        return registrationFormRepository.findAll();
    }

    public Optional<RegistrationForm> getRegistrationFormById(Long formId)
    {
        return registrationFormRepository.findById(formId);
    }

    public void createRegistrationForm(RegistrationForm registrationForm)
    {
        registrationFormRepository.save(registrationForm);
    }

    public void approveRegistrationForm(Long formId)
    {
        Optional<RegistrationForm> registrationForm = registrationFormRepository.findById(formId);
        if(registrationForm.isPresent())
        {
            registrationForm.get().setStatus("Approved");
            registrationFormRepository.save(registrationForm.get());
        }
        else
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Registration form does not exist!");

    }

    public void cancelRegistrationForm(Long formId)
    {
        Optional<RegistrationForm> registrationForm = registrationFormRepository.findById(formId);
        if(registrationForm.isPresent())
        {
            registrationForm.get().setStatus("Cancelled");
            registrationFormRepository.save(registrationForm.get());
        }
        else
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Registration form does not exist!");

    }

    public void removeRegistrationForm(Long formId)
    {
        Optional<RegistrationForm> registrationForm = registrationFormRepository.findById(formId);
        if(registrationForm.isPresent())
        {
            registrationFormRepository.delete(registrationForm.get());
        }
        else
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Registration form does not exist!");

    }

    public RegistrationForm mapDTOToRegistrationForm(RegistrationFormDTO registrationFormDTO)
    {

        RegistrationForm registrationForm = new RegistrationForm();

        Optional<UserAccount> userAccount = userAccountRepository.findById(registrationFormDTO.getUserAccountId());
        if(userAccount.isEmpty())
        {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Account does not exist!");
        }
        Optional<InsurancePlan> insurancePlan = insurancePlanRepository.findById(registrationFormDTO.getInsurancePlanId());
        if(insurancePlan.isEmpty())
        {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Insurance plan does not exist!");
        }
        Optional<InsuredPerson> insuredPerson = insuredPersonRepository.findById(registrationFormDTO.getInsuredPersonId());
        if(insuredPerson.isEmpty())
        {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "NotFound","Insured person does not exist!");
        }

        registrationForm.setApplyDate(registrationFormDTO.getApplyDate());
        registrationForm.setStartDate(registrationFormDTO.getStartDate());
        registrationForm.setEndDate(registrationFormDTO.getEndDate());
        registrationForm.setNote(registrationFormDTO.getNote());
        registrationForm.setApplicantType(registrationFormDTO.getApplicantType());
        registrationForm.setStatus(registrationFormDTO.getStatus());
        registrationForm.setUserAccount(userAccount.get());
        registrationForm.setInsurancePlan(insurancePlan.get());
        registrationForm.setInsuredPerson(insuredPerson.get());

        return registrationForm;
    }

    public RegistrationFormDTO mapRegistrationFormToDTO(RegistrationForm registrationForm)
    {

        RegistrationFormDTO registrationFormDTO = new RegistrationFormDTO();

        registrationFormDTO.setApplyDate(registrationForm.getApplyDate());
        registrationFormDTO.setStartDate(registrationForm.getStartDate());
        registrationFormDTO.setEndDate(registrationForm.getEndDate());
        registrationFormDTO.setNote(registrationForm.getNote());
        registrationFormDTO.setApplicantType(registrationForm.getApplicantType());
        registrationFormDTO.setStatus(registrationForm.getStatus());

        if (registrationForm.getUserAccount() != null) {
            registrationFormDTO.setUserAccountId(registrationForm.getUserAccount().getId());
        }
        if (registrationForm.getInsurancePlan() != null) {
            registrationFormDTO.setInsurancePlanId(registrationForm.getInsurancePlan().getId());
        }
        if (registrationForm.getInsuredPerson() != null) {
            registrationFormDTO.setInsuredPersonId(registrationForm.getInsuredPerson().getId());
        }

        return registrationFormDTO;
    }
}
