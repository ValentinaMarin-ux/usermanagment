package com.example.userManagment.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity class representing a User in the user management system.
 * This class stores all personal and authentication information for system users.
 */
@Entity
@Table(name = "user")
@Data
public class User {
    
    /**
     * Unique identifier for the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    
    /**
     * Role assigned to this user, defining their permissions and access levels
     */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

   
 

    /**
     * User's email address, used as unique identifier for authentication
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * User's encrypted password
     */
    @Column(name = "password")
    private String password;

   

    /**
     * Timestamp when the user record was created
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the user record was last updated
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;


    
}
