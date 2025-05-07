package com.example.userManagment.security;

// Importaciones necesarias para el filtro JWT
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Filtro que se ejecuta una vez por cada solicitud para validar el token JWT
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Proveedor de servicios para manejar tokens JWT (generación, validación, etc.)
    @Autowired
    private JwtTokenProvider tokenProvider;

    // Servicio personalizado para cargar los detalles del usuario
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Método principal que se ejecuta en cada solicitud
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Obtiene el token JWT del encabezado de la solicitud
            String jwt = getJwtFromRequest(request);

            // Verifica si el token existe y es válido
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                // Extrae el nombre de usuario del token
                String email = tokenProvider.getUsernameFromJWT(jwt); // Cambiar 'username' a 'email'
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                // Crea un token de autenticación con los detalles del usuario
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // Agrega detalles adicionales de la solicitud web
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            // Registra cualquier error durante el proceso de autenticación
            logger.error("No se pudo establecer la autenticación del usuario en el contexto de seguridad", ex);
        }

        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para extraer el token JWT del encabezado de la solicitud
    private String getJwtFromRequest(HttpServletRequest request) {
        // Obtiene el valor del encabezado "Authorization"
        String bearerToken = request.getHeader("Authorization");
        // Verifica si el token existe y comienza con "Bearer "
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // Retorna el token sin el prefijo "Bearer "
            return bearerToken.substring(7);
        }
        return null;
    }
}