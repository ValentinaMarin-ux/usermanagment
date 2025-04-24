package com.example.userManagment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.userManagment.dto.RoleDTO;
import com.example.userManagment.models.Role;

import com.example.userManagment.service.RoleService;

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
    public RoleDTO createRoleDTO(@RequestBody Role role) {
        return roleService.createRoleDTO(role);
    }
     
    @DeleteMapping("/delete/{id}")
    public void deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
    }

    @PutMapping("/update/{id}")
    public void updateRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        roleService.updateRole(id, roleDTO);
    }

    

}
