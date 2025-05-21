package com.example.userManagment.testControllers;

import com.example.userManagment.controllers.RoleController;
import com.example.userManagment.dto.CreateRoleDTO;
import com.example.userManagment.dto.RoleDTO;
import com.example.userManagment.dto.UpdateRoleDTO;
import com.example.userManagment.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    private RoleDTO testRoleDTO;
    private CreateRoleDTO createRoleDTO;
    private UpdateRoleDTO updateRoleDTO;

    @BeforeEach
    void setUp() {
        // Datos de prueba
        testRoleDTO = new RoleDTO();
        testRoleDTO.setName("Admin");
        testRoleDTO.setDescription("Administrator role");

        createRoleDTO = new CreateRoleDTO();
        createRoleDTO.setName("Admin");
        createRoleDTO.setDescription("Administrator role");

        updateRoleDTO = new UpdateRoleDTO();
        updateRoleDTO.setName("Updated Admin");
        updateRoleDTO.setDescription("Updated description");
    }

    @Test
    void getRoleById_ShouldReturnRole() throws Exception {
        when(roleService.findById(1)).thenReturn(testRoleDTO);

        mockMvc.perform(get("/role/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Admin"))
                .andExpect(jsonPath("$.description").value("Administrator role"));
    }

    @Test
    void getAllRoles_ShouldReturnListOfRoles() throws Exception {
        List<RoleDTO> roles = Arrays.asList(testRoleDTO);
        when(roleService.findALL()).thenReturn(roles);

        mockMvc.perform(get("/role/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Admin"))
                .andExpect(jsonPath("$[0].description").value("Administrator role"));
    }

    @Test
    void createRole_ShouldReturnCreatedRole() throws Exception {
        when(roleService.createRole(any(CreateRoleDTO.class))).thenReturn(testRoleDTO);

        mockMvc.perform(post("/role/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRoleDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Admin"))
                .andExpect(jsonPath("$.description").value("Administrator role"));
    }

    @Test
    void updateRole_ShouldReturnOk() throws Exception {
        mockMvc.perform(put("/role/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRoleDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRole_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/role/delete/1"))
                .andExpect(status().isOk());
    }
}
