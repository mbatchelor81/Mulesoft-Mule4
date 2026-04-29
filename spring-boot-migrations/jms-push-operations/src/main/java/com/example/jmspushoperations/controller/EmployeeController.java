package com.example.jmspushoperations.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final JmsTemplate jmsTemplate;

    public EmployeeController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("/add-employee")
    public ResponseEntity<Map<String, String>> addEmployee(@RequestBody String payload) {
        log.info("Sending employee payload to JMS queue Q.FSD.EMP");
        jmsTemplate.convertAndSend("Q.FSD.EMP", payload);
        return ResponseEntity.ok(Map.of("message", "Employee data sent to queue Q.FSD.EMP"));
    }
}
