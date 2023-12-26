package com.example.insurance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class InsurancePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "plan_name", columnDefinition = "text")
    private String planName;

    @Column(name = "duration", length = 20, columnDefinition = "varchar(20) default '1 năm'")
    private String duration;

    @Column(name = "accident_insurance")
    private int accidentInsurance;

    @Column(name = "hospitalization")
    private int hospitalization;

    @Column(name = "surgery")
    private int surgery;

    @Column(name = "before_admission")
    private int beforeAdmission;

    @Column(name = "after_discharge")
    private int afterDischarge;

    @Column(name = "take_care_at_home")
    private int takeCareAtHome;

    @Column(name = "hospitalization_allowance")
    private int hospitalizationAllowance;

    @Column(name = "emergency_transport")
    private int emergencyTransport;

    @Column(name = "funeral_allowance")
    private int funeralAllowance;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Timestamp updatedAt;

    @Column(name = "status", length = 20, nullable = false, columnDefinition = "varchar(20) default 'activated'")
    private String status;
}
