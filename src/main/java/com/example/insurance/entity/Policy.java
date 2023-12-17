package com.example.insurance.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "policy")
@Data
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "policy_name")
    private String policyName;
    private Float coverage;
    @Column(name = "premium_amount")
    private Float premiumAmount;

}

