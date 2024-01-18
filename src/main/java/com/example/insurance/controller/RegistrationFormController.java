package com.example.insurance.controller;

import com.example.insurance.common.CustomErrorResponse;
import com.example.insurance.common.CustomSuccessResponse;
import com.example.insurance.common.MapEntityToDTO;
import com.example.insurance.entity.RegistrationForm;
import com.example.insurance.exception.CustomException;
import com.example.insurance.service.RegistrationFormService;
import com.example.insurance.dto.RegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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

    @GetMapping("/get")
    public ResponseEntity<?> getAllRegistrationForm() {
        return ResponseEntity.status(HttpStatus.OK).body(registrationFormService.getAllRegistrationFormNotPaid());
    }

    @GetMapping("/get/{formId}")
    public ResponseEntity<?> getRegistrationFormById(@PathVariable Long formId) {
        RegistrationFormDTO registrationFormDTO = registrationFormService.getRegistrationFormDTOById(formId);
        if(registrationFormDTO == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),"RegistrationFormNotFound","Không thể tìm thấy đơn đăng kí",new Date()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(registrationFormDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRegistrationForm(@RequestBody RegistrationFormDTO registrationFormDTO) {
        registrationFormService.createRegistrationForm(registrationFormDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Gửi đơn đăng kí thành công!!");
    }


    @PutMapping("/approve-form/{formId}")
    public ResponseEntity<?> approveRegistrationForm(@PathVariable Long formId) {
        RegistrationFormDTO registrationForm = registrationFormService.getRegistrationFormDTOById(formId);
        if(registrationForm == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),"RegistrationFormNotFound","Không thể tìm thấy đơn đăng kí",new Date()));
        }
        registrationFormService.updateStatusById(formId, "Approved");
        return ResponseEntity.status(HttpStatus.OK).body("Đơn đăng kí đã được duyệt thành công!!");
    }

    @PutMapping("/refuse-form/{formId}")
    public ResponseEntity<?> cancelRegistrationForm(@PathVariable Long formId) {
        RegistrationFormDTO registrationForm = registrationFormService.getRegistrationFormDTOById(formId);
        if(registrationForm == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),"RegistrationFormNotFound","Không thể tìm thấy đơn đăng kí",new Date()));
        }
        registrationFormService.updateStatusById(formId, "Refused");
        return ResponseEntity.status(HttpStatus.OK).body("Đơn đăng kí đã bị từ chối!!");
    }

    @DeleteMapping("/delete/{formId}")
    public ResponseEntity<?> removeRegistrationForm(@PathVariable Long formId) {
        RegistrationForm registrationForm = registrationFormService.getRegistrationFormById(formId);
        if (registrationForm == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),"RegistrationFormNotFound","Không thể tìm thấy đơn đăng kí",new Date()));
        }
        if (registrationForm.getStatus().equals("Paid"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),"BadRequest","Đơn đăng kí đã thanh toán không thể bị xóa",new Date()));
        }
        registrationFormService.removeRegistrationForm(formId);
        return ResponseEntity.status(HttpStatus.OK).body("Xóa đơn đăng kí thành công!!");
    }
}