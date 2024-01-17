package com.example.insurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"createdAt","updatedAt","status"})
public class InsuredPersonDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Date birthday;
    private String gender;
    private String address;
    private String IDCard;
    private HealthInformationDTO healthInformation;
    private String relationshipWithBuyers;
//    public InsurancePlanDTO(Long id)
//    {
//        this.id = id;
//    }
}
