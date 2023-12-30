package com.example.insurance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsurancePlanDTO {
    private String planName;
    private String duration;
    private Integer accidentInsurance;
    private Integer hospitalization;
    private Integer surgery;
    private Integer beforeAdmission;
    private Integer afterDischarge;
    private Integer takeCareAtHome;
    private Integer hospitalizationAllowance;
    private Integer emergencyTransport;
    private Integer funeralAllowance;
    private String status;
}
