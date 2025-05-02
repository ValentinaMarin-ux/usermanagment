package com.example.userManagment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userManagment.dto.CreateRoleDTO;
import com.example.userManagment.dto.RoleDTO;
import com.example.userManagment.dto.UpdateRoleDTO;
import com.example.userManagment.mapper.RoleMapper;
import com.example.userManagment.models.Role;
import com.example.userManagment.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleRepository roleRepository;

    public RoleDTO findById(Integer id) {
        return roleRepository.findById(id).map(roleMapper::toRoleDTO).orElse(null);
    }

    public List<RoleDTO> findALL() {
        return roleRepository.findAll()
                             .stream()
                             .map(roleMapper::toRoleDTO)
                             .collect(Collectors.toList());
    }

    public RoleDTO createRole(CreateRoleDTO createRoleDTO) {
        return roleMapper.toRoleDTO(
                roleRepository.save(roleMapper.toEntity(createRoleDTO))
        );
    }

    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            roleRepository.delete(role);
        }
    }

    public void updateRole(Integer id, UpdateRoleDTO updateRoleDTO) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole != null) {
            Role updatedRole = roleMapper.updateToEntity(updateRoleDTO);
            updatedRole.setId(id);
            roleRepository.save(updatedRole);
        }
    }
}

     








