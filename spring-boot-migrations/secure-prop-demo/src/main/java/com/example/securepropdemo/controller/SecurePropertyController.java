package com.example.securepropdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/secure-prop")
public class SecurePropertyController {

    private static final Logger log = LoggerFactory.getLogger(SecurePropertyController.class);

    @Value("${db.password:NOT_SET}")
    private String dbPassword;

    @GetMapping
    public ResponseEntity<Map<String, String>> readSecureProperty() {
        String masked = dbPassword.length() > 2
            ? dbPassword.substring(0, 2) + "****"
            : "****";
        log.info("DB password (masked): {}", masked);
        return ResponseEntity.ok(Map.of(
            "db.password", masked,
            "source", "Azure Key Vault"
        ));
    }
}
