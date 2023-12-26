package com.example.insurance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class HealthInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "general_health_condition",nullable = false, columnDefinition = "text")
    private String generalHealthCondition;

    @Column(name = "medical_history",nullable = false, columnDefinition = "text")
    private String medicalHistory;

    @Column(name = "medicines_and_treatment",nullable = false, columnDefinition = "text")
    private String medicinesAndTreatment;

    @Column(name = "family_history",nullable = false, columnDefinition = "text")
    private String familyHistory;

    @Column(name = "lifestyle_and_risk_factors",nullable = false, columnDefinition = "text")
    private String lifestyleAndRiskFactors;

    @Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Timestamp updatedAt;
}
