package com.example.emphrsapi.controller;

import com.example.emphrsapi.dto.CreateEmployeeRequest;
import com.example.emphrsapi.dto.CreateEmployeeResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/emp-hr-sapi/v1")
public class EmployeeHrController {

    private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

    @PostMapping("/add-employee")
    public ResponseEntity<CreateEmployeeResponse> addEmployee(
            @Valid @RequestBody CreateEmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
            .body(new CreateEmployeeResponse(
                501, "Not Implemented", "Stub - implementation pending",
                LocalDateTime.now().format(DT_FORMAT), UUID.randomUUID().toString()));
    }

    @GetMapping("/fetch-employee/{empId}")
    public ResponseEntity<Map<String, Object>> fetchEmployee(@PathVariable Integer empId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
            .body(Map.of("code", 501, "message", "Not Implemented"));
    }

    @PutMapping("/update-employee")
    public ResponseEntity<Map<String, Object>> updateEmployee(
            @Valid @RequestBody CreateEmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
            .body(Map.of("code", 501, "message", "Not Implemented"));
    }

    @DeleteMapping("/delete-employee/{empId}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable Integer empId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
            .body(Map.of("code", 501, "message", "Not Implemented"));
    }
}
