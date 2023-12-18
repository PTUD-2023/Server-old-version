package com.example.insurance.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity(name = "Registration")
@Data
public class InsuranceRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String insuranceType;
    private String duration;
}
