package com.example.consumejsonrestservice.controller;

import com.example.consumejsonrestservice.model.EmployeeMaster;
import com.example.consumejsonrestservice.service.OnboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OnboardController {

    private final OnboardService onboardService;

    public OnboardController(OnboardService onboardService) {
        this.onboardService = onboardService;
    }

    @PostMapping("/onboard/create-employee")
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody EmployeeMaster employee) {
        return ResponseEntity.ok(onboardService.createEmployee(employee));
    }
}
