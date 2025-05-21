package com.example.userManagment.mapper;

import com.example.userManagment.dto.*;
import com.example.userManagment.models.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // Crear un nuevo empleado desde CreateEmployeeDTO
    @Mapping(target = "id", ignore = true) // Ignorar el ID, ya que será generado
    @Mapping(target = "age", ignore = true) // No necesitamos asignar edad (se calcula automáticamente)
    @Mapping(target = "createdAt", ignore = true) // Si tienes un campo de fecha de creación
    @Mapping(target = "updatedAt", ignore = true) // Si tienes un campo de fecha de actualización
    Employee toEntity(CreateEmployeeDTO createEmployeeDTO);

    // Actualizar un empleado existente (IMPORTANTE: esto no crea un nuevo objeto)
    @Mapping(target = "id", ignore = true) // No necesitamos cambiar el ID
    @Mapping(target = "createdAt", ignore = true) // Ignorar fecha de creación
    @Mapping(target = "updatedAt", ignore = true) // Ignorar fecha de actualización
    @Mapping(target = "age", ignore = true) // Ignorar campo de edad (se calcula)
    Employee updateToEntity(UpdateEmployeeDTO updateEmployeeDTO);

    // Conversión de entidad a DTOs de visualización
   
    GetEmployeeDTO toGetEmployeeDTO(Employee employee);

    // Conversión de entidad a DTO para la actualización
    UpdateEmployeeDTO toUpdateEmployeeDTO(Employee employee);

    // Conversión de entidad a DTO básico
    EmployeeDTO toEmployeeDTO(Employee employee);

    // Conversión de entidad a DTO para la creación (sin edad calculada)
    CreateEmployeeDTO toCreateEmployeeDTO(Employee employee);

    @Mapping(target = "employeeId", source = "id")
    GetEmployeeAndContractDTO toGetEmployeeAndContractDTO(Employee employee);
}
