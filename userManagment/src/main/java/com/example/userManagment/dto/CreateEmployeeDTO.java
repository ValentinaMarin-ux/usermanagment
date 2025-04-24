package com.example.userManagment.dto;

import lombok.Data;

@Data
public class CreateEmployeeDTO {
    private Integer contractId;
    private String name;
    private String lastName;
    private Integer age;
    private String birthDate;
    private String email;
    private String address;
    private String password;
    private String status;
    private RoleDTO role;
    
}
