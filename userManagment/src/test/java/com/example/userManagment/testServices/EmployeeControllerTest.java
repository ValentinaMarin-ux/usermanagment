package com.example.userManagment.testServices;

import com.example.userManagment.controllers.EmployeeController;
import com.example.userManagment.dto.CreateEmployeeDTO;
import com.example.userManagment.dto.EmployeeDTO;
import com.example.userManagment.dto.GetEmployeeDTO;
import com.example.userManagment.dto.UpdateEmployeeDTO;
import com.example.userManagment.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private GetEmployeeDTO getEmployeeDTO;
    private CreateEmployeeDTO createEmployeeDTO;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        getEmployeeDTO = new GetEmployeeDTO();
        getEmployeeDTO.setId(1);
        getEmployeeDTO.setFirstName("John");
        getEmployeeDTO.setLastName("Doe");
        getEmployeeDTO.setEmail("john.doe@example.com");
        getEmployeeDTO.setAddress("123 Main St");
        getEmployeeDTO.setContractId(1001);
        getEmployeeDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        getEmployeeDTO.setAge(34);
        getEmployeeDTO.setStatus(true);

        createEmployeeDTO = new CreateEmployeeDTO();
        createEmployeeDTO.setFirstName("John");
        createEmployeeDTO.setLastName("Doe");
        createEmployeeDTO.setEmail("john.doe@example.com");
        createEmployeeDTO.setAddress("123 Main St");
        createEmployeeDTO.setContractId(1001);
        createEmployeeDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        createEmployeeDTO.setStatus(true);

        employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setAddress("123 Main St");
        employeeDTO.setContractId(1001);
        employeeDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employeeDTO.setAge(34);
        employeeDTO.setStatus(true);
    }

    @Test
    void getAllEmployees_ShouldReturnList() throws Exception {
        List<GetEmployeeDTO> list = Arrays.asList(getEmployeeDTO);
        when(employeeService.findAll()).thenReturn(list);

        mockMvc.perform(get("/employee/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() throws Exception {
        when(employeeService.findById(1)).thenReturn(getEmployeeDTO);

        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() throws Exception {
        when(employeeService.createEmployee(any(CreateEmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEmployeeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.age").value(34));
    }

    @Test
    void updateEmployee_ShouldReturnNoContent() throws Exception {
        UpdateEmployeeDTO updateEmployeeDTO = new UpdateEmployeeDTO();
        updateEmployeeDTO.setFirstName("Johnny");

        mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEmployeeDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEmployee_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/employee/delete/1"))
                .andExpect(status().isNoContent());
    }
}
