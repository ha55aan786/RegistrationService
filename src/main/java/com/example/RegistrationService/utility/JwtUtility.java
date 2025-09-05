package com.example.RegistrationService.utility;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtility {

    // Secret key for signing (should be kept safe, ideally in env variable or config server)
    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generate JWT token
    public static String generateToken(String username) {
        long expirationTimeMs = 1000 * 60 * 60; // 1 hour

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(secretKey)
                .compact();
    }
}