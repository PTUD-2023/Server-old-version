package com.example.insurance.repository;

import com.example.insurance.entity.InsuranceApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InsuranceApplicationRepository extends CrudRepository<InsuranceApplication,Long> {

}