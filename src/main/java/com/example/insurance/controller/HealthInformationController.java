package com.example.insurance.controller;

import com.example.insurance.dto.HealthInformationDTO;
import com.example.insurance.entity.HealthInformation;
import com.example.insurance.exception.CustomException;
import com.example.insurance.service.HealthInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        try {
            Optional<HealthInformation> healthInformation = healthInformationService.findHealthInformationById(id);
            if (healthInformation.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Thông tin sức khỏe không tồn tại!");
            }
            HealthInformationDTO dto = healthInformationService.mapHealthInformationToDTO(healthInformation.get());
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> AddInformationHealth(@RequestBody HealthInformationDTO dto)
    {
        try {
            HealthInformation healthInformation = healthInformationService.mapDTOToHealthInformation(dto);
            healthInformationService.addHealthInformation(healthInformation);
            return ResponseEntity.status(HttpStatus.CREATED).body("Thêm thông tin sức khỏe thành công!");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> UpdateInformationHealth(@PathVariable Long id, @RequestBody HealthInformationDTO dto)
    {
        try {
            healthInformationService.updateHealthInformation(id, dto);
            return ResponseEntity.status(HttpStatus.OK).body("Cập nhật thông tin sức khỏe thành công!");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }
}
