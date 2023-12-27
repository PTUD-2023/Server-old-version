package com.example.insurance.repository;

import com.example.insurance.entity.RegistrationForm;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RegistrationFormRepository extends CrudRepository<RegistrationForm, Long> {
    @Query("SELECT rf FROM RegistrationForm rf WHERE rf.userAccount.id = :userId")
    Iterable<RegistrationForm> findAllRegistrationFormByUserId(@Param("userId") Long id);
}
