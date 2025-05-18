package com.example.userManagment.testControllers;

import com.example.userManagment.controllers.UserController;
import com.example.userManagment.dto.CreateUserDTO;
import com.example.userManagment.dto.GetRoleDTO;
import com.example.userManagment.dto.GetUserDTO;
import com.example.userManagment.dto.UpdateUserDto;
import com.example.userManagment.dto.UserDTO;
import com.example.userManagment.models.Role;
import com.example.userManagment.models.User;
import com.example.userManagment.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateUserDTO createUserDTO;
    private GetUserDTO getUserDTO;
    private UserDTO userDTO;
    private UpdateUserDto updateUserDto;
    private Role role;
    private User user;

    @BeforeEach
    void setUp() {
        // Simular rol
        role = new Role();
        role.setId(1);
        role.setName("User");
        role.setDescription("Default role");

        // Datos de creación
        createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("test@example.com");
        createUserDTO.setPassword("password123");
        createUserDTO.setRoleId(1);

        // Datos para GetUserDTO
        getUserDTO = new GetUserDTO();
        getUserDTO.setId(1);
        getUserDTO.setEmail("test@example.com");
        getUserDTO.setPassword("password123");
        GetRoleDTO getRoleDTO = new GetRoleDTO();
        getRoleDTO.setName("User");
        getRoleDTO.setDescription("Default role");
        getUserDTO.setRole(getRoleDTO);

        // DTO completo
        userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");
        userDTO.setRole(role);

        // Actualización
        updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail("updated@example.com");
        updateUserDto.setPassword("newpass123");
        updateUserDto.setRoleId(1);

        // Modelo
        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(role);
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        when(userService.findById(1)).thenReturn(getUserDTO);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role.name").value("User"));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(getUserDTO));

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        when(userService.createUser(any(CreateUserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role.name").value("User"));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        when(userService.findById(1)).thenReturn(getUserDTO);
        doNothing().when(userService).deleteUser(1);

        mockMvc.perform(delete("/user/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateUser_ShouldReturnNoContent() throws Exception {
        when(userService.findById(1)).thenReturn(getUserDTO);
        doNothing().when(userService).updateUser(eq(1), any(UpdateUserDto.class));

        mockMvc.perform(put("/user/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void changePassword_ShouldReturnOk_WhenSuccessful() throws Exception {
        when(userService.changePassword(1, "oldPass", "newPass")).thenReturn(true);

        mockMvc.perform(post("/user/1/change-password")
                .param("oldPassword", "oldPass")
                .param("newPassword", "newPass"))
                .andExpect(status().isOk());
    }

    @Test
    void changePassword_ShouldReturnBadRequest_WhenFailed() throws Exception {
        when(userService.changePassword(1, "wrongOldPass", "newPass")).thenReturn(false);

        mockMvc.perform(post("/user/1/change-password")
                .param("oldPassword", "wrongOldPass")
                .param("newPassword", "newPass"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserByEmail_ShouldReturnUser_WhenFound() throws Exception {
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userService.findById(1)).thenReturn(getUserDTO);

        mockMvc.perform(get("/user/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getUserByEmail_ShouldReturnNotFound_WhenMissing() throws Exception {
        when(userService.getUserByEmail("missing@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/email/missing@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserById_ShouldReturnNotFound_WhenMissing() throws Exception {
        when(userService.findById(999)).thenReturn(null);

        mockMvc.perform(get("/user/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        when(userService.findById(999)).thenReturn(null);

        mockMvc.perform(delete("/user/delete/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        when(userService.findById(999)).thenReturn(null);

        mockMvc.perform(put("/user/update/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isNotFound());
    }
}
