package com.example.RegistrationService.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotNull
    private String username;
    @NotNull
    @Size(min=8)
    private String password;

}
