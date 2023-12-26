package com.example.insurance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
public class InsuredPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(name = "email",length = 50)
    private String email;
    @Column(name = "phone",length = 20)
    private String phone;
    @Column(name = "birthday", nullable = false)
    private Date birthDay;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false,columnDefinition = "text")
    private String address;
    @Column(name = "CMND",length = 9)
    private String CMND;
    @Column(name = "relationship_with_buyers",columnDefinition = "text",nullable = false)
    private String relationshipWithBuyers;
    @OneToOne
    @JoinColumn(name = "health_infor_id",referencedColumnName = "id")
    private HealthInformation healthInformationId;
    @Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Timestamp updatedAt;
    @Column(name = "status", columnDefinition = "varhar(20) default 'uninsured'")
    private String status;
}
