package com.example.insurance.dto;

import com.example.insurance.entity.ClaimRequest;
import com.example.insurance.entity.InsurancePayment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(value = {"registrationForm","createdAt","updatedAt","status"})
public class InsurancePolicyDTO {
    private Long id;
    private Date applyDate;
    private Date startDate;
    private Date endDate;
    private List<InsurancePayment> paymentLists;
    private List<ClaimRequest> claimRequests;
}
