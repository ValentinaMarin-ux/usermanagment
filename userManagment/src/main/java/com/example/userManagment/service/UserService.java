package com.example.userManagment.service;
import com.example.userManagment.dto.*;
import com.example.userManagment.mapper.UserMapper;
import com.example.userManagment.models.User;
import com.example.userManagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public GetUserDTO findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toGetUserDTO)
                .orElse(null);
    }

    public List<GetUserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toGetUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        
        return userMapper.toUserDTO(
            userRepository.save(userMapper.toEntity(createUserDTO))
        );
    }
        
    
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public void updateUser(Integer id, UpdateUserDto updateUserDto) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            User updatedUser = userMapper.updateToEntity(updateUserDto);
            updatedUser.setId(id);
            userRepository.save(updatedUser);
            
        }
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
