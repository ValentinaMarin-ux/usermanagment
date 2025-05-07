package com.example.userManagment.controllers;

import com.example.userManagment.dto.*;
import com.example.userManagment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Obtener un empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<GetEmployeeDTO> getEmployeeById(@PathVariable Integer id) {
        GetEmployeeDTO employee = employeeService.findById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    // Obtener todos los empleados
    @GetMapping("/all")
    public ResponseEntity<List<GetEmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    // Crear un nuevo empleado
    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody CreateEmployeeDTO createEmployeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(createEmployeeDTO);
        return ResponseEntity.status(201).body(createdEmployee);
    }

    // Eliminar un empleado por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        GetEmployeeDTO employee = employeeService.findById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar un empleado por ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateEmployee(@Valid @PathVariable Integer id,
            @Valid @RequestBody UpdateEmployeeDTO updateEmployeeDTO) {
        GetEmployeeDTO employee = employeeService.findById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        employeeService.updateEmployee(id, updateEmployeeDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkIfEmployeeExists(@PathVariable Integer id) {
        try {
            boolean exists = employeeService.employeeExists(id);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá en la consola la causa
            return ResponseEntity.status(500).body(false);
        }
    }

}
