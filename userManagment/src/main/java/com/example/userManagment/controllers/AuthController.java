package com.example.userManagment.controllers;

// Importaciones necesarias para el controlador de autenticación
import com.example.userManagment.dto.JwtResponse;
import com.example.userManagment.dto.LoginRequest;
import com.example.userManagment.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// Define esta clase como un controlador REST
@RestController
// Define la ruta base para todos los endpoints en este controlador
@RequestMapping("/api/auth")
public class AuthController {

    // Inyecta el gestor de autenticación
    @Autowired
    private AuthenticationManager authenticationManager;

    // Inyecta el proveedor de tokens JWT
    @Autowired
    private JwtTokenProvider tokenProvider;

    // Define el endpoint de login
    @PostMapping("/login")
public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    System.out.println("Iniciando autenticación para el usuario: " + loginRequest.getEmail());
    
    // Autentica al usuario con las credenciales proporcionadas
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()));
    
    // Establece la autenticación en el contexto de seguridad
    SecurityContextHolder.getContext().setAuthentication(authentication);

    System.out.println("Autenticación exitosa, generando token...");

    // Genera un token JWT para el usuario autenticado
    String jwt = tokenProvider.generateToken(authentication);

    System.out.println("Token generado: " + jwt);

    // Retorna el token JWT junto con información básica del usuario
    return ResponseEntity.ok(new JwtResponse(jwt, loginRequest.getEmail()));
}

}
