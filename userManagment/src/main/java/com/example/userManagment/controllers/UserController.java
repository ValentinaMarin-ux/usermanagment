package com.example.userManagment.controllers;

import com.example.userManagment.dto.UserDTO;
import com.example.userManagment.models.User;
import com.example.userManagment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /api/users
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    // POST /api/users
    @PostMapping
    public UserDTO createUserDTO(@RequestBody User user) {
        return userService.createUserDTO(user);
    }

    // PUT /api/users/{id}
    @PutMapping("/{id}")
    public void updateUserDTO(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        userService.updateUserDTO(id, userDTO);
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
