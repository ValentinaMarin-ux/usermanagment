package com.example.userManagment.testControllers;

import com.example.userManagment.dto.CreateEmployeeDTO;

import lombok.Data;

import java.time.LocalDate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateEmployeeDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCalculateCorrectAge() {
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setBirthDate(LocalDate.now().minusYears(25));
        assertEquals(25, dto.getAge());
    }

    @Test
    void shouldFailValidationWhenFieldsAreMissing() {
        CreateEmployeeDTO dto = new CreateEmployeeDTO(); // campos vacíos

        Set<ConstraintViolation<CreateEmployeeDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(7, violations.size()); // Esperamos 7 errores (sin contar birthDate nulo que lanza 2 errores: NotNull y @Past)
    }

    @Test
    void shouldPassValidationWhenAllFieldsAreCorrect() {
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setContractId(1);
        dto.setJobTitle("Developer");
        dto.setFirstName("Valentina");
        dto.setLastName("Florez");
        dto.setEmail("valentina@example.com");
        dto.setAddress("Calle 123");
        dto.setBirthDate(LocalDate.now().minusYears(30));
        dto.setStatus(true);

        Set<ConstraintViolation<CreateEmployeeDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
        assertEquals(30, dto.getAge());
    }

    @Test
    void shouldFailIfBirthDateIsInTheFuture() {
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setContractId(1);
        dto.setJobTitle("QA");
        dto.setFirstName("Ana");
        dto.setLastName("Marin");
        dto.setEmail("ana@example.com");
        dto.setAddress("Carrera 456");
        dto.setBirthDate(LocalDate.now().plusDays(1)); // Fecha futura
        dto.setStatus(false);

        Set<ConstraintViolation<CreateEmployeeDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("birthDate")));
    }
}
