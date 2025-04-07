package com.example.userManagment.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

/**
 * Entity class representing a Permission in the user management system.
 * Permissions define specific actions or access rights that can be assigned to roles.
 */
@Entity
@Table(name = "permission")
@Data
public class Permission {
    
    /**
     * Unique identifier for the permission
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Integer id;
    
    /**
     * Name of the permission (e.g., "READ", "WRITE", "DELETE")
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Description of what this permission allows
     */
    @Column(name = "description")
    private String description;

    /**
     * The resource or entity this permission applies to
     */
    @Column(name = "resource", nullable = false)
    private String resource;

    /**
     * Timestamp when the permission was created
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the permission was last updated
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    /**
     * Role to which this permission belongs
     */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
