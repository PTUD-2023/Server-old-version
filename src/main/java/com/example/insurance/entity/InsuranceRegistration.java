package com.example.insurance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "Registration")
@Data
public class InsuranceRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "apply_date")
    private Date applyDate;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "note")
    private String note;
    @ManyToOne
    @JoinColumn(name = "user_account_id", referencedColumnName = "id")
    private UserAccount userAccountId;
    @Column(name = "applicant_type")
    private String applicantType;
    @OneToOne
    @JoinColumn(name = "insured_person_id",referencedColumnName = "id")
    private InsuredPerson insuredPerson;
    @ManyToOne
    @JoinColumn(name = "insurance_plan_id",referencedColumnName = "id")
    private InsurancePlan insurancePlan;
    @Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Timestamp updatedAt;
    @Column(name = "status", columnDefinition = "varhar(20) default 'not_activated'")
    private String status;
}
