package com.example.insurance.repository;

import com.example.insurance.entity.InsurancePolicy;
import com.example.insurance.entity.RegistrationForm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsurancePolicyRepository extends CrudRepository<InsurancePolicy,Long> {
    List<InsurancePolicy> findAllByRegistrationForm_UserAccount_Id(Long id);
    InsurancePolicy findByRegistrationForm_Id(Long id);
}
