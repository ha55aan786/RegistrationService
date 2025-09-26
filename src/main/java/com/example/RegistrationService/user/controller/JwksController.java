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
                "2e16ad86-e49b-4fe2-b8f4-e1570fccd266",
                "wPJqyU08GpgBcwrkTxMkMIAyP7_XyhRf-NGX14136LEJYPcN9XzIVi5kTG5ZHoYkjXJR5s8ez706PMbm-MBdRdQv04Y7WqH35ku8WoX0XSq4gRsGWbTrOBB17DHDoXNEJZtueaiONtq4tsEjGouwUg0kqPGOBu2e66A3OmWVo0zlCVwWfVP736iQXd3MVLlTfA65ssiAx_Pin1MNQDJapIcq4oE_cA7JveYCVEzhuPBd1RzwbC-t8_Q6DlwZq1Xb0K4HSLFb2lI8HtXdwHu7gNMu57ezKur8yc-g5sy0apNDc00IWf-Z17OEWMG6tnArG9_XWCNTpsGhOOJp_xuxZQ"
        );
        return new JwkSet(List.of(key));
    }
}

