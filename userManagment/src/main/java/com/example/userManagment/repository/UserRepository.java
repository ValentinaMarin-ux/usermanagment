package com.example.userManagment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userManagment.models.User;   


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    

    Optional<User> findByEmail(String email);

    

    Boolean existsByEmail(String email);



}   
