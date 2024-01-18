package com.example.insurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@JsonIgnoreProperties(value = {"password","status","timeUpdated","forms","claimRequests"})
public class UserAccountDTO {
    private Long id;
    private String email;
    private String lastName;
    private String firstName;
    private String phone;
    private Date birthday;
    private String gender;
    private String avatar;
    private String CMND;
    private String address;
    private Date timeCreated;
    private String role;

    public UserAccountDTO(Long id)
    {
        this.id = id;
    }
}
