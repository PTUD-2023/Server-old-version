package com.example.insurance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "health_information")
@Data
@JsonIdentityInfo(scope = HealthInformation.class, generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class HealthInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "general_health_condition")
    private String generalHealthCondition;

    @Column(name = "medical_history")
    private String medicalHistory;

    @Column(name = "medicines_and_treatment")
    private String medicinesAndTreatment;

    @Column(name = "family_history")
    private String familyHistory;

    @Column(name = "lifestyle_and_risk_factors")
    private String lifestyleAndRiskFactors;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
