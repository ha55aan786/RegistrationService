package com.example.RegistrationService.utility;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class PublicKeyGenerator {

    public static void main(String[] args) throws Exception {
        // Load private key (PEM file)
        String privateKeyPem = new String(Files.readAllBytes(Paths.get("private_key.pem")));
        privateKeyPem = privateKeyPem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", ""); // remove newlines and spaces

        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPem);

        // Convert to PrivateKey object
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // Extract public key from private key
        if (privateKey instanceof RSAPrivateCrtKey) {
            RSAPrivateCrtKey rsaPrivate = (RSAPrivateCrtKey) privateKey;

            RSAPublicKeySpec publicKeySpec =
                    new RSAPublicKeySpec(rsaPrivate.getModulus(), rsaPrivate.getPublicExponent());

            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            // Print in PEM format
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            System.out.println("-----BEGIN PUBLIC KEY-----");
            System.out.println(publicKeyBase64);
            System.out.println("-----END PUBLIC KEY-----");
        } else {
            System.out.println("Not an RSA private key with CRT params");
        }
    }
}
