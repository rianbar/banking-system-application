package com.bank.registersystem.repository;

import com.bank.registersystem.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByIdentity(String id);
    UserDetails findByName(String name);
    Optional<UserModel> findByEmail(String email);
}
