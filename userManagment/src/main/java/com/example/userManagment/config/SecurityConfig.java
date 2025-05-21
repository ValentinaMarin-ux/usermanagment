package com.example.userManagment.config;

// Importaciones necesarias para la configuración de seguridad
import com.example.userManagment.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Marca esta clase como una clase de configuración de Spring
@Configuration
// Habilita la seguridad web de Spring Security
@EnableWebSecurity
// Habilita la seguridad a nivel de método (para usar @PreAuthorize)
@EnableMethodSecurity
public class SecurityConfig {

    // Define un bean para el filtro de autenticación JWT
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    // Define el codificador de contraseñas que se usará en la aplicación
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura el gestor de autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Configura la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilita CSRF ya que usamos tokens JWT
                .csrf(csrf -> csrf.disable())
                // Configura la gestión de sesiones como STATELESS (sin estado)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura las reglas de autorización para las peticiones HTTP
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/api-docs/**", "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/user/add").permitAll() 
                       
                        
 // 👈 Agregado
                        .anyRequest().authenticated())
                // Agrega nuestro filtro JWT antes del filtro de autenticación por usuario y
                // contraseña
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}