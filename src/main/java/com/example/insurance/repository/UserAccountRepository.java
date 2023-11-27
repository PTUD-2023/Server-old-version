package com.example.insurance.repository;

import com.example.insurance.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount,Long> {
    Optional<UserAccount> findByEmail(String email);
}
