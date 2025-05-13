package com.example.userManagment.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.userManagment.dto.CreateUserDTO;
import com.example.userManagment.dto.GetUserDTO;
import com.example.userManagment.dto.UpdateUserDto;
import com.example.userManagment.dto.UserDTO;
import com.example.userManagment.models.User;
import com.example.userManagment.service.UserService;
import java.util.Optional;  // ✅ Esta es la correcta

import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable Integer id) {
        GetUserDTO user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        UserDTO createdUser = userService.createUser(createUserDTO);
        return ResponseEntity.status(201).body(createdUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        GetUserDTO user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@Valid @PathVariable Integer id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        GetUserDTO user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.updateUser(id, updateUserDto);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable Integer id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        boolean changed = userService.changePassword(id, oldPassword, newPassword);
        if (changed) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<GetUserDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(userService.findById(user.get().getId()));
        }
        return ResponseEntity.notFound().build();
    }
}
