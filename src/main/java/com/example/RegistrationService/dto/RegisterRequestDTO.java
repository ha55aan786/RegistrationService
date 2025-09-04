package com.example.RegistrationService.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;

}
