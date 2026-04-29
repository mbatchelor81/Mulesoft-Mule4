package com.example.emponboardapi.controller;

import com.example.emponboardapi.dto.EmployeeRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/emp-onboard-api/v1")
public class EmployeeOnboardController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeOnboardController.class);

    @PostMapping("/add-employee")
    public ResponseEntity<Map<String, Object>> addEmployee(@Valid @RequestBody EmployeeRequest request) {
        log.info("Add employee: {}", request.empName());
        return ResponseEntity.ok(Map.of("status", 200, "message", "Success"));
    }

    @GetMapping("/fetch-employee/{empId}")
    public ResponseEntity<Map<String, Object>> fetchEmployee(@PathVariable Integer empId) {
        log.info("Fetch employee: {}", empId);
        return ResponseEntity.ok(Map.of("emp-name", "Gaurav", "status", "A"));
    }

    @PutMapping("/update-employee")
    public ResponseEntity<Map<String, Object>> updateEmployee(@RequestBody Map<String, Object> request) {
        log.info("Update employee: {}", request);
        return ResponseEntity.ok(Map.of("status", 201, "message", "Success"));
    }

    @DeleteMapping("/delete-employee/{employeeID}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable Integer employeeID) {
        log.info("Delete employee: {}", employeeID);
        return ResponseEntity.ok(Map.of("status", 200, "message", "Success"));
    }
}
