package com.example.userManagment.security;
import com.example.userManagment.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// Esta clase actúa como un adaptador entre nuestro modelo de Usuario y el modelo de usuario que Spring Security necesita
// Implementa UserDetails que es la interfaz principal de Spring Security para representar usuarios
public class UserPrincipal implements UserDetails {
    // Atributos que almacenan la información básica del usuario
    private Integer id;
    private String email;
    private String password;
    // Colección de permisos/roles del usuario
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor que inicializa todos los campos necesarios
    public UserPrincipal(Integer id,  String email, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Método estático que convierte nuestro modelo User en UserPrincipal
    // Este método es crucial para la integración con Spring Security
    public static UserPrincipal create(User user) {
        // Convierte los roles del usuario en GrantedAuthorities que Spring Security
        // puede entender
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().getName()));

        // Crea y retorna una nueva instancia de UserPrincipal
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    // Getter para el ID del usuario
    public Integer getId() {
        return id;
    }

    // Implementación de UserDetails: Retorna los roles/autoridades del usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Implementación de UserDetails: Retorna la contraseña del usuario
    @Override
    public String getPassword() {
        return password;
    }

    

    // Implementación de UserDetails: Indica si la cuenta no ha expirado
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Implementación de UserDetails: Indica si la cuenta no está bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Implementación de UserDetails: Indica si las credenciales no han expirado
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Implementación de UserDetails: Indica si el usuario está habilitado
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getter para el email del usuario
    public String getEmail() {
        return email;
    }

    @Override
public String getUsername() {
    return email;
}
}