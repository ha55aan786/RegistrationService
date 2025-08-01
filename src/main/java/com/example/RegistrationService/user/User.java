package com.example.RegistrationService.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

}
