package com.example.RegistrationService.user.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    private String firstName;
    private String lastName;
    private String password;

    private boolean isAdmin = false;

}
/*
* entity:
* dto:
* registration controller, login controller
* registration service, login service
*
*
* */