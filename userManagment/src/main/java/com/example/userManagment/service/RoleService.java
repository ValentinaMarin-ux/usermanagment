package com.example.userManagment.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userManagment.dto.RoleDTO;
import com.example.userManagment.models.Role;
import com.example.userManagment.repository.RoleRepository;


@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleDTO findById(Integer id) {
        return roleRepository.findById(id).map(role -> getRoleDTO(role)).orElse(null);
    }

    public List<RoleDTO>  findALL() {
        return roleRepository.findAll().stream().map(role -> getRoleDTO(role)).collect(Collectors.toList());
    }
    
    public RoleDTO createRoleDTO(Role role) {
         return getRoleDTO(roleRepository.save(role));
    }

    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            roleRepository.delete(role);
        }
    }



    public void updateRole(Integer id, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole != null) {
            existingRole.setName(roleDTO.getName());
            existingRole.setDescription(roleDTO.getDescription());
            roleRepository.save(existingRole);
        }
    }
        






    public RoleDTO getRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());
        return roleDTO;
    }

}
