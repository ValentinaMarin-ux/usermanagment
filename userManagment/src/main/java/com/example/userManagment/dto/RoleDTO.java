package com.example.userManagment.dto;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Data;

@Data
public class RoleDTO {

    private Integer id;
    private String name;
    private String description;


    
}
