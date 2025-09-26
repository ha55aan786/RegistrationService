package com.example.RegistrationService;

import com.example.RegistrationService.utility.JwtUtility;

public class JwtTest {
    public static void main(String[] args) throws Exception {
        String token = JwtUtility.generateToken("USERNAME");
        System.out.println("Generated JWT: " + token);
    }
}
