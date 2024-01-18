package com.example.insurance.controller;

import com.example.insurance.common.CustomErrorResponse;
import com.example.insurance.dto.HealthInformationDTO;
import com.example.insurance.entity.HealthInformation;
import com.example.insurance.exception.CustomException;
import com.example.insurance.service.HealthInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("v1/health-information")
public class HealthInformationController {
    private final HealthInformationService healthInformationService;

    @Autowired
    public HealthInformationController(HealthInformationService healthInformationService)
    {
        this.healthInformationService = healthInformationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetHealthInformationById(@PathVariable Long id)
    {
        HealthInformationDTO healthInformationDTO = healthInformationService.findHealthInformationDTOById(id);
        if (healthInformationDTO == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),"HealthInformationNotFound","Không thể tìm thấy thông tin sức khỏe!",new Date()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(healthInformationDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<?> AddInformationHealth(@RequestBody HealthInformationDTO dto)
    {
        healthInformationService.addHealthInformation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Thêm thông tin sức khỏe thành công!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> UpdateInformationHealth(@PathVariable Long id, @RequestBody HealthInformationDTO dto)
    {
        healthInformationService.updateHealthInformation(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body("Cập nhật thông tin sức khỏe thành công!");
    }
}
