package com.example.consumejsonrestservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FinanceController {

    private static final Logger log = LoggerFactory.getLogger(FinanceController.class);

    @PostMapping("/finance/add-details")
    public ResponseEntity<Map<String, String>> addFinanceDetails(@RequestBody Map<String, Object> payload) {
        log.info("Finance service received: {}", payload);
        return ResponseEntity.ok(Map.of("message", "Finance details recorded"));
    }
}
