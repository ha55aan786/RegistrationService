package com.example.RegistrationService.dto;

import lombok.Data;

@Data
public class EmailSendDTO {
    private String to;
    private String CC;
    private String subject;
    private String body;
}
