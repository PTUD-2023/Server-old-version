package com.example.insurance.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegistrationFormDTO {
    private Date applyDate;
    private Date startDate;
    private Date endDate;
    private String note;
    private Long userAccountId;
    private String applicantType;
    private Long insuredPersonId;
    private Long insurancePlanId;
    private String status;
}
