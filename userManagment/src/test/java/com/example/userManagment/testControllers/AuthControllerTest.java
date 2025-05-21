package com.example.userManagment.testControllers;

import com.example.userManagment.controllers.AuthController;
import com.example.userManagment.dto.JwtResponse;
import com.example.userManagment.dto.LoginRequest;
import com.example.userManagment.security.JwtTokenProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginRequest loginRequest;
    private Authentication authentication;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        jwtToken = "mocked-jwt-token";

        authentication = mock(Authentication.class);
    }

    @Test
    void authenticateUser_ShouldReturnJwtResponse_WhenCredentialsAreValid() throws Exception {
        // Simular autenticación exitosa
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Simular token generado
        when(tokenProvider.generateToken(authentication)).thenReturn(jwtToken);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(jwtToken))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}

