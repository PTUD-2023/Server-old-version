package com.example.insurance.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class InsurancePolicyDTO {
    private Date applyDate;
    private Date startDate;
    private Date endDate;
    private Long registrationFormId;
    private String status;
}
