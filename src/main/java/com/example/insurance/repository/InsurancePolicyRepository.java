package com.example.insurance.repository;

import com.example.insurance.entity.InsurancePolicy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InsurancePolicyRepository extends CrudRepository<InsurancePolicy,Long> {
    @Query("SELECT ip FROM InsurancePolicy ip WHERE ip.registrationForm.id = :formId")
    InsurancePolicy findInsurancePolicyByRegistrationFormId(@Param("formId") Long id);
}
