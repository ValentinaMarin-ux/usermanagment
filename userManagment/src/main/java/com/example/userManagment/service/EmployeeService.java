package com.example.userManagment.service;

import com.example.userManagment.dto.*;
import com.example.userManagment.mapper.EmployeeMapper;
import com.example.userManagment.models.Employee;
import com.example.userManagment.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Buscar un empleado por ID
    public GetEmployeeDTO findById(Integer id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toGetEmployeeDTO)
                .orElse(null); // Devuelve null si no se encuentra
    }

    // Obtener todos los empleados
    public List<GetEmployeeDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toGetEmployeeDTO)
                .collect(Collectors.toList());
    }

    // Crear un nuevo empleado
    public EmployeeDTO createEmployee(CreateEmployeeDTO createEmployeeDTO) {
        // Convertir el DTO a entidad
        Employee employee = employeeMapper.toEntity(createEmployeeDTO);
    
        // Asignar la edad calculada en el DTO
        employee.setAge(createEmployeeDTO.getAge());
    
        // Guardar el empleado y devolver el DTO
        Employee savedEmployee = employeeRepository.save(employee);
        
        // Devolver el DTO correspondiente
        return employeeMapper.toEmployeeDTO(savedEmployee);
    }
    
    
    // Eliminar un empleado por ID
    public void deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employeeRepository.delete(employee);
        }
    }

    // Actualizar un empleado
    public void updateEmployee(Integer id, UpdateEmployeeDTO updateEmployeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee != null) {
            // Mapeamos el DTO a entidad y asignamos el ID del empleado existente
            Employee updatedEmployee = employeeMapper.updateToEntity(updateEmployeeDTO);
            updatedEmployee.setId(id);
            employeeRepository.save(updatedEmployee); // Guardamos el empleado actualizado
        }
    }


    public boolean employeeExists(Integer id) {
        return employeeRepository.existsById(id);
    }


    public List<GetEmployeeAndContractDTO> findEmployeeAndContract() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toGetEmployeeAndContractDTO)
                .collect(Collectors.toList());
    }
}
