package com.example.RegistrationService.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.*;
import java.nio.file.*;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.*;

public class GenerateJwks {

    public static String createJWKS() throws Exception {
        // 1. Read public key from PEM
        String pem = Files.readString(Paths.get("public_key.pem"))
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(pem);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // 2. Create JWK (with a key ID – kid)
        RSAKey jwk = new RSAKey.Builder((RSAPublicKey) publicKey)
                .keyID(UUID.randomUUID().toString()) // give each key a unique kid
                .build();

        // 3. Wrap into JWKS (could hold multiple keys)
        JWKSet jwkSet = new JWKSet(jwk);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(jwkSet.toJSONObject());
    }
}
