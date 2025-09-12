package com.example.RegistrationService.utility;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;

public class KeyGeneratorUtil {

    public static void main(String[] args) throws Exception {
        // Generate a 2048-bit RSA key pair with a Key ID (kid)
        RSAKey rsaJWK = new RSAKeyGenerator(2048)
                .keyID("your-unique-key")  // give each key a unique ID for JWKS
                .generate();

        // Extract the private key in JWK (JSON Web Key) format
        System.out.println("Private JWK:\n" + rsaJWK.toJSONString());

        // Extract only the public key (to expose in JWKS)
        System.out.println("\nPublic JWK (for JWKS endpoint):\n" + rsaJWK.toPublicJWK().toJSONString());
    }
}

