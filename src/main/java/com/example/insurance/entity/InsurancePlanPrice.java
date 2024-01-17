package com.example.insurance.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "insurance_plan_price")
@ToString
@JsonIdentityInfo(scope = InsurancePlanPrice.class, generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonIgnoreProperties(value= {"insurancePlan"})
public class InsurancePlanPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "min_age")
    private int minAge;

    @Column(name = "max_age")
    private int maxAge;

    @Column(name = "price")
    private int price;

    @Column(name = "rate")
    private float rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_plan_id", referencedColumnName = "id")
    private InsurancePlan insurancePlan;

    @Column(name = "effective_date")
    private Date effectiveDate;

    @Column(name = "status")
    private String status;


}
