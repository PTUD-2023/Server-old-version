package com.example.insurance.common;

import com.example.insurance.dto.UserAccountDTO;
import com.example.insurance.entity.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapEntityToDTO {
    public static UserAccountDTO mapUserAccountToDTO(UserAccount userAccount)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(userAccount,UserAccountDTO.class);

    }
}
