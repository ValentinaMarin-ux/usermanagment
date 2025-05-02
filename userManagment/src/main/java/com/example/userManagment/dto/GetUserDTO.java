package com.example.userManagment.dto;

import lombok.Data;

@Data
public class GetUserDTO {
    private Integer id;
    private String email;
    private String password;    
    private GetRoleDTO role;
    
}
