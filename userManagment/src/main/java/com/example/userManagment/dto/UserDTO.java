package com.example.userManagment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

// UserDTO.java
@Data
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
    private LocalDate birthDate;
    private Boolean status;   
    private String email;
    private String address;
    private RoleDTO role; 
}

