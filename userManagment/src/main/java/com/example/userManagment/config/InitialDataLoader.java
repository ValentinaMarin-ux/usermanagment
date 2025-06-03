package com.example.userManagment.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.userManagment.models.Role;
import com.example.userManagment.models.User;
import com.example.userManagment.repository.RoleRepository;
import com.example.userManagment.repository.UserRepository;

@Component
public class InitialDataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Role rhEmplployeeRole = roleRepository.findByName("ROLE_EMPLOYEE_MODULE")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_EMPLOYEE_MODULE")));

        Role rhSchedulesRole = roleRepository.findByName("ROLE_SCHEDULES_MODULE")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_SCHEDULES_MODULE")));

        Role rhPayrollRole = roleRepository.findByName("ROLE_PAYROLL_MODULE")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_PAYROLL_MODULE")));

        Role rhVacationsRole = roleRepository.findByName("ROLE_VACATIONS_MODULE")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_VACATIONS_MODULE")));

        Role rhGoalRole = roleRepository.findByName("ROLE_GOALS_MODULE")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_GOALS_MODULE")));

        // Crear usuario para ROLE_EMPLOYEE_MODULE
        if (!userRepository.existsByEmail("employee@example.com")) {
            User user = new User();
            user.setEmail("employee@example.com");
            user.setPassword(passwordEncoder.encode("employee123"));

            user.setRole(rhEmplployeeRole);

            // Asignar el conjunto de roles correctamente
            userRepository.save(user); // Guardar solo una vez
        }

        // Crear usuario para ROLE_SCHEDULES_MODULE
        if (!userRepository.existsByEmail("schedules@example.com")) {
            User user = new User();
            user.setEmail("schedules@example.com");
            user.setPassword(passwordEncoder.encode("schedules123"));
            user.setRole(rhSchedulesRole);
            userRepository.save(user);
        }

        // Crear usuario para ROLE_GOALS_MODULE
        if (!userRepository.existsByEmail("goals@example.com")) {
            User user = new User();
            user.setEmail("goals@example.com");
            user.setPassword(passwordEncoder.encode("goals123"));
            user.setRole(rhGoalRole);
            userRepository.save(user);
        }

        // Crear usuario para ROLE_PAYROLL_MODULE
        if (!userRepository.existsByEmail("payroll@example.com")) {
            User user = new User();
            user.setEmail("payroll@example.com");
            user.setPassword(passwordEncoder.encode("payroll123"));
            user.setRole(rhPayrollRole);
            userRepository.save(user);
        }

        // Crear usuario para ROLE_VACATIONS_MODULE
        if (!userRepository.existsByEmail("vacations@example.com")) {
            User user = new User();
            user.setEmail("vacations@example.com");
            user.setPassword(passwordEncoder.encode("vacations123"));
            user.setRole(rhVacationsRole);
            userRepository.save(user);
        }

    }
}
