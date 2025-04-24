package com.example.userManagment.service;

import com.example.userManagment.dto.UserDTO;
import com.example.userManagment.dto.RoleDTO;
import com.example.userManagment.models.User;
import com.example.userManagment.models.Role;
import com.example.userManagment.repository.UserRepository;
import com.example.userManagment.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Busca un usuario por ID y lo convierte a DTO.
     */
    public UserDTO findById(Integer id) {
        return userRepository.findById(id)
                .map(this::getUserDTO)
                .orElse(null);
    }

    /**
     * Obtiene todos los usuarios y los convierte a DTO.
     */
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::getUserDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo usuario y devuelve su DTO.
     */
    public UserDTO createUserDTO(User user) {
        user = userRepository.save(user);
        return getUserDTO(user);
    }

    /**
     * Elimina un usuario por ID.
     */
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    /**
     * Actualiza un usuario existente con los datos del DTO.
     */
    public void updateUserDTO(Integer id, UserDTO userDTO) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setFirstName(userDTO.getFirstName());
            existing.setLastName(userDTO.getLastName());
            existing.setAge(userDTO.getAge());
            existing.setBirthDate(userDTO.getBirthDate());
            existing.setEmail(userDTO.getEmail());
            existing.setAddress(userDTO.getAddress());
            existing.setStatus(userDTO.getStatus());
     
            if (userDTO.getRole() != null) {
                Role role = roleRepository.findById(userDTO.getRole().getId()).orElse(null);
                existing.setRole(role);
            }
            userRepository.save(existing);
        }
    }

    /**
     * Convierte una entidad User a UserDTO.
     */
    public UserDTO getUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAge(user.getAge());
        dto.setBirthDate(user.getBirthDate());
        dto.setStatus(user.getStatus());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        if (user.getRole() != null) {
            RoleDTO r = new RoleDTO();
            r.setId(user.getRole().getId());
            r.setName(user.getRole().getName());
            r.setDescription(user.getRole().getDescription());
            dto.setRole(r);
        }
        return dto;
    }
}
