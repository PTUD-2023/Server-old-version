package com.example.insurance.repository;

import com.example.insurance.entity.InsuredPerson;
import org.springframework.data.repository.CrudRepository;

public interface InsuredPersonRepository extends CrudRepository<InsuredPerson,Long> {

}
