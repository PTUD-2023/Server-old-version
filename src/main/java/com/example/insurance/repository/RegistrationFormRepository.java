package com.example.insurance.repository;

import com.example.insurance.entity.RegistrationForm;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RegistrationFormRepository extends CrudRepository<RegistrationForm, Long> {
    List<RegistrationForm> findAllByUserAccount_Id(Long id);
    @Modifying
    @Transactional
    @Query("UPDATE RegistrationForm rf SET rf.status = :status WHERE rf.id = :id")
    void updateStatusById(@Param("id") Long id, String status);
}
