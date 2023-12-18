package com.example.insurance.repository;

import com.example.insurance.entity.InsuranceRegistration;
import org.springframework.data.repository.CrudRepository;


public interface InsuranceRegistrationRepository extends CrudRepository<InsuranceRegistration, Long> {

}

