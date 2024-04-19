package com.codex.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codex.app.model.UserEntity;


public interface UserEntityRepository extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity> findByUsername(String username);
    
}
