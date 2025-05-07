package com.example.userManagment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class representing a Role in the user management system.
 * A role defines a set of permissions and can be assigned to multiple users.
 */
@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    
    /**
     * Unique identifier for the role
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;
    
    /**
     * Name of the role (e.g., "ADMIN", "USER")
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Description of the role's purpose and responsibilities
     */
    @Column(name = "description")
    private String description;

    /**
     * Timestamp when the role was created
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the role was last updated
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

  
    /**
     * List of users who have been assigned this role
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;

    public Role(String name) {
        this.name = name;
    }
    
}