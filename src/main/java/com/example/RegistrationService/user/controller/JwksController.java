package com.example.RegistrationService.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JwksController {

    record JwkKey(String kty, String e, String kid, String n) {}
    record JwkSet(List<JwkKey> keys) {}

    @GetMapping("/.well-known/jwks.json")
    public JwkSet returnJwks() {
        JwkKey key = new JwkKey(
                "RSA",
                "AQAB",
                "A7f2kL9xQ1wZ3rT0",
                "sxQvZKVae0YbH_pqiA3z06JB8rg-UJwgbkziz77brpPq-3kBlU68Hwdhj_xA-heCvNBvqki0A2vTdMfvOeQZWh952cTgekrDD1PyBRu90zhJ9IFF5XN9LMJl3AXfRibBbx44CtCjqjguSUhYbJGB3IPGT5Ln9EYQg52HY-smuRiUeFmgdCBm1Y_20tIjy7cu3_YM3vx2YzsaFiIVd9uXmGYi950j-w1660WsUzi9NbnOouaprQCdpjsx5YoVi3bNKNwiZd581LBdNuxm7Hvu0Q4ULtG1aH7nZgl7EZ2WdmsOylo0DLNKMWvgfWPHx-Rs5gN9Si8D6OcAqCo9SAcC0Q"
        );
        return new JwkSet(List.of(key));
    }
}

