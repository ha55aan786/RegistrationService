package com.example.RegistrationService;

import com.example.RegistrationService.utility.JwtUtility;

public class JwtTest {
    public static void main(String[] args) {
        String token = JwtUtility.generateToken("USERNAME");
        System.out.println("Generated JWT: " + token);
    }
}
