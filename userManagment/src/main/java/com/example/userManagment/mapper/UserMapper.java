package com.example.userManagment.mapper;

import com.example.userManagment.dto.*;
import com.example.userManagment.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Crear un nuevo usuario desde CreateUserDTO
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role.id", source = "roleId")
    User toEntity(CreateUserDTO createUserDTO);

    // Actualizar un usuario existente (IMPORTANTE: esto no crea un nuevo objeto)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role.id", source = "roleId")
    User updateToEntity(UpdateUserDto updateUserDto);

    // Conversión de entidad a DTOs de visualización/edición
    GetUserDTO toGetUserDTO(User user);
    UpdateUserDto toUpdateUserDto(User user);
    UserDTO toUserDTO(User user);
}
