package com.example.RegistrationService.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isActive;
}
