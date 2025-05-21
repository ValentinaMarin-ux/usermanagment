package com.example.userManagment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.userManagment.dto.CreateRoleDTO;
import com.example.userManagment.dto.GetRoleDTO;
import com.example.userManagment.dto.RoleDTO;
import com.example.userManagment.dto.UpdateRoleDTO;
import com.example.userManagment.models.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role toEntity(CreateRoleDTO roleDTO);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role updateToEntity(UpdateRoleDTO roleDTO);

    CreateRoleDTO toDTO(Role role);
    GetRoleDTO toGetRoleDTO(Role role);
    UpdateRoleDTO toUpdateRoleDTO(Role role);
    RoleDTO toRoleDTO(Role role);
}