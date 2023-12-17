package com.example.insurance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "insurance_application")
@Data
public class InsuranceApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_account_id",referencedColumnName = "id")
    private UserAccount userAccount;
    @ManyToOne
    @JoinColumn(name = "policy_id",referencedColumnName = "id")
    private Policy policy;
    @Column(name = "heal_information")
    private String healInformation;
    @Column(name = "apply_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applyDate;
    private String status;
}
