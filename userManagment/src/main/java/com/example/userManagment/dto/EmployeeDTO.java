package com.example.userManagment.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeDTO {

    @NotNull(message = "Contract ID is required")
    private Integer contractId;

    @NotBlank(message = "Job Title is required")
    private String jobTitle;

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

  
    @NotNull(message = "Date of Birth is required")
    @PastOrPresent(message = "Date of Birth must be in the past or present")
    @JsonFormat(pattern = "yyyy-MM-dd") // Para que cuando envíen JSON, el formato siga igual
    private LocalDate dateOfBirth;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    @NotNull(message = "Status is required")
    private Boolean status;

}
