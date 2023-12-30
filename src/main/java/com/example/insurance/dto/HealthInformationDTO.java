package com.example.insurance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthInformationDTO {
    private String generalHealthCondition;
    private String medicalHistory;
    private String medicinesAndTreatment;
    private String familyHistory;
    private String lifestyleAndRiskFactors;
}
