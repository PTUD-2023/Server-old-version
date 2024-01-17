package com.example.insurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(value = {"createdAt","updatedAt","status"})
public class RegistrationFormDTO {
    private Long id;
    private Date applyDate;
    private Date startDate;
    private Date endDate;
    private String note;
    private UserAccountDTO userAccount;
    private String applicantType;
    private InsuredPersonDTO insuredPerson;
    private InsurancePlanDTO insurancePlan;
}
