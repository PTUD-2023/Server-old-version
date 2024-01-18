package com.example.insurance.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "registration_form")
@Data
@ToString
@JsonIdentityInfo(scope = RegistrationForm.class, generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class RegistrationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apply_date")
    @Temporal(TemporalType.DATE)
    private Date applyDate;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String note;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id",referencedColumnName = "id")
    @ToString.Exclude
    private UserAccount userAccount;

    @Column(name = "applicant_type")
    private String applicantType;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "insured_person_id", referencedColumnName = "id")
    private InsuredPerson insuredPerson;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "insurance_plan_id", referencedColumnName = "id")
    @ToString.Exclude
    private InsurancePlan insurancePlan;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private String status;
}
