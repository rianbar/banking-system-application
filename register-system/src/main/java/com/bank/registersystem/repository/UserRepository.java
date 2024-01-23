package com.bank.registersystem.repository;

import com.bank.registersystem.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByIdentity(String id);
    Optional<UserModel> findByEmail(String email);
}
