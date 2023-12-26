package com.example.insurance.controller;

import com.example.insurance.common.MapEntityToDTO;
import com.example.insurance.entity.RegistrationForm;
import com.example.insurance.service.RegistrationFormService;
import com.example.insurance.dto.RegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/registration-form")
public class RegistrationFormController {
    private final RegistrationFormService registrationFormService;
    @Autowired
    public RegistrationFormController(RegistrationFormService registrationFormService) {
        this.registrationFormService = registrationFormService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllRegistrationForm() {
        try {
            Iterable<RegistrationForm> registrationForms = registrationFormService.getAllRegistrationForm();
            List<RegistrationFormDTO> registrationFormDTOs = new ArrayList<>();
            for (RegistrationForm registrationForm : registrationForms) {
                registrationFormDTOs.add(registrationFormService.mapRegistrationFormToDTO(registrationForm));
            }
            return ResponseEntity.status(HttpStatus.OK).body(registrationFormDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{formId}")
    public ResponseEntity<?> getRegistrationFormById(@PathVariable Long formId) {
        try {
            Optional<RegistrationForm> registrationForm = registrationFormService.getRegistrationFormById(formId);
            if(registrationForm.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration form does not exist!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(registrationFormService.mapRegistrationFormToDTO(registrationForm.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create-form")
    public ResponseEntity<?> createRegistrationForm(@RequestBody RegistrationFormDTO registrationFormDTO) {
        try {
            RegistrationForm registrationForm = registrationFormService.mapDTOToRegistrationForm(registrationFormDTO);
            registrationFormService.createRegistrationForm(registrationForm);
            return ResponseEntity.status(HttpStatus.CREATED).body("Send Registration Successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/approve-form/{formId}")
    public ResponseEntity<?> approveRegistrationForm(@PathVariable Long formId) {
        try {
            registrationFormService.approveRegistrationForm(formId);
            return ResponseEntity.status(HttpStatus.OK).body("Registration form approved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/cancel-form/{formId}")
    public ResponseEntity<?> cancelRegistrationForm(@PathVariable Long formId) {
        try {
            registrationFormService.cancelRegistrationForm(formId);
            return ResponseEntity.status(HttpStatus.OK).body("Registration form cancelled successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{formId}")
    public ResponseEntity<?> removeRegistrationForm(@PathVariable Long formId) {
        try {
            registrationFormService.removeRegistrationForm(formId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}