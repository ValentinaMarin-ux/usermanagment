package com.example.userManagment.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetEmployeeDTO {

    private Integer id; // ID del empleado en la base de datos

    private Integer contractId;

    private String jobTitle;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private LocalDate birthDate;

    private Integer age;

    private Boolean status;
}
