package com.example.insurance.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "insurance_plan")
@Data
public class InsurancePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "plan_name")
    private String planName;
    private String duration;
    @Column(name = "accident_insurance")
    private Integer accidentInsurance;
    private Integer hospitalization;
    private Integer surgery;
    @Column(name = "before_admission")
    private Integer beforeAdmission;
    @Column(name = "after_discharge")
    private Integer afterDischarge;
    @Column(name = "take_care_at_home")
    private Integer takeCareAtHome;
    @Column(name = "hospitalization_allowance")
    private Integer hospitalizationAllowance;
    @Column(name = "emergency_transport")
    private Integer emergencyTransport;
    @Column(name = "funeral_allowance")
    private Integer funeralAllowance;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private String status;
}
