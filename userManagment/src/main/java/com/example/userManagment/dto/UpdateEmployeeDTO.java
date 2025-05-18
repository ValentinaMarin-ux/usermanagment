package com.example.userManagment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateEmployeeDTO {

    private Integer contractId; // Puede cambiarlo si quiere

    private String jobTitle;

    private String firstName;

    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    private String address;


    @PastOrPresent(message = "Date of Birth must be in the past or present")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    private Boolean status;
}
