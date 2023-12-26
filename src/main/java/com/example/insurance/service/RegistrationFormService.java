package com.example.insurance.service;

import com.example.insurance.dto.RegistrationFormDTO;
import com.example.insurance.entity.InsurancePlan;
import com.example.insurance.entity.InsuredPerson;
import com.example.insurance.entity.RegistrationForm;
import com.example.insurance.entity.UserAccount;
import com.example.insurance.exception.CustomException;
import com.example.insurance.repository.RegistrationFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationFormService {
    private final RegistrationFormRepository registrationFormRepository;
    private final UserAccountService userAccountService;
    private final InsurancePlanService insurancePlanService;
    private final InsuredPersonService insuredPersonService;

    @Autowired
    public RegistrationFormService(RegistrationFormRepository registrationFormRepository,
                                   UserAccountService userAccountService,
                                   InsurancePlanService insurancePlanService,
                                   InsuredPersonService insuredPersonService)
    {
        this.registrationFormRepository = registrationFormRepository;
        this.userAccountService = userAccountService;
        this.insurancePlanService = insurancePlanService;
        this.insuredPersonService = insuredPersonService;
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
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "NotFound","Registration form does not exist!");

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
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "NotFound","Registration form does not exist!");

    }

    public void removeRegistrationForm(Long formId)
    {
        Optional<RegistrationForm> registrationForm = registrationFormRepository.findById(formId);
        if(registrationForm.isPresent())
        {
            registrationFormRepository.delete(registrationForm.get());
        }
        else
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "NotFound","Registration form does not exist!");

    }

    public RegistrationForm mapDTOToRegistrationForm(RegistrationFormDTO registrationFormDTO)
    {

        RegistrationForm registrationForm = new RegistrationForm();

        Optional<UserAccount> userAccount = userAccountService.getUserById(registrationFormDTO.getUserAccountId());
        if(userAccount.isEmpty())
        {
            throw new UsernameNotFoundException("Account does not exist!");
        }
        Optional<InsurancePlan> insurancePlan = insurancePlanService.findInsurancePlanById(registrationFormDTO.getInsurancePlanId());
        if(insurancePlan.isEmpty())
        {
            throw new UsernameNotFoundException("Insurance plan does not exist!");
        }
        Optional<InsuredPerson> insuredPerson = insuredPersonService.findInsuredPersonById(registrationFormDTO.getInsuredPersonId());
        if(insuredPerson.isEmpty())
        {
            throw new UsernameNotFoundException("Insured person does not exist!");
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
