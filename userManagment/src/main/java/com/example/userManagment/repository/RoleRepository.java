package com.example.userManagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userManagment.models.Role;   


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    


}   
