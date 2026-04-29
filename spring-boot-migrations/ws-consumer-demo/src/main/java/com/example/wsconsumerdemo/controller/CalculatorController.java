package com.example.wsconsumerdemo.controller;

import com.example.wsconsumerdemo.dto.AdditionRequest;
import com.example.wsconsumerdemo.service.CalculatorClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CalculatorController {

    private final CalculatorClient calculatorClient;

    public CalculatorController(CalculatorClient calculatorClient) {
        this.calculatorClient = calculatorClient;
    }

    @PostMapping("/addition")
    public ResponseEntity<Map<String, Object>> add(@RequestBody AdditionRequest request) {
        int result = calculatorClient.add(request.firstNo(), request.secondNo());
        return ResponseEntity.ok(Map.of(
            "firstNo", request.firstNo(),
            "secondNo", request.secondNo(),
            "result", result
        ));
    }
}
