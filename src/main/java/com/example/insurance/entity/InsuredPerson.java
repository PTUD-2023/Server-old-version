package com.example.insurance.entity;

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
@Table(name = "insured_person")
@Data
@JsonIdentityInfo(scope = InsuredPerson.class, generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class InsuredPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    private String gender;

    private String address;

    @Column(name = "CMND")
    private String IDCard;

    @OneToOne
    @JoinColumn(name = "health_infor_id", referencedColumnName = "id")
    private HealthInformation healthInformation;

    @Column(name = "relationship_with_buyers")
    private String relationshipWithBuyers;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private String status;
}
