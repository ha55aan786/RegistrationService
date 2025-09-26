package com.example.RegistrationService.utility;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;

@Component
public class JwtUtility {

    @Value("${jwt.key-id}")
    private static String keyId;

    public static String generateToken(String username) throws Exception {
        // 1. Read private key from PEM
        String pem = Files.readString(Paths.get("private_key.pem"))
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(pem);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // 2. Create JWT claims
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("registration-service")
                .expirationTime(new Date(System.currentTimeMillis() + 3600_000)) // 1 hr
                .build();

        // 3. Sign with RS256
        JWSSigner signer = new RSASSASigner(privateKey);

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256)
                        .keyID(keyId) // match kid in JWKS!
                        .build(),
                claims
        );

        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
}