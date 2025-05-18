package com.example.userManagment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.userManagment.dto.CreateRoleDTO;
import com.example.userManagment.dto.RoleDTO;
import com.example.userManagment.dto.UpdateRoleDTO;
import com.example.userManagment.models.Role;

import com.example.userManagment.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @GetMapping("/{id}")
    public RoleDTO getRoleById(@PathVariable Integer id) {
        return roleService.findById(id);
    }

    @GetMapping("/all")
    public List<RoleDTO> getAllRoles() {
        return roleService.findALL();
    }

    @PostMapping("/add")
    public RoleDTO createRole( @Valid @RequestBody CreateRoleDTO role) {
        return roleService.createRole(role);
    }
     
    @DeleteMapping("/delete/{id}")
    public void deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
    }

    @PutMapping("/update/{id}")
    public void updateRole(@Valid @PathVariable Integer id, @Valid@RequestBody UpdateRoleDTO roleDTO) {
        roleService.updateRole(id, roleDTO);
    }

    

}
