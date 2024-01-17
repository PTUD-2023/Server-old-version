package com.example.insurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = {"createdAt","updatedAt","status"})
public class HealthInformationDTO {
    private Long id;
    private String generalHealthCondition;
    private String medicalHistory;
    private String medicinesAndTreatment;
    private String familyHistory;
    private String lifestyleAndRiskFactors;
}
