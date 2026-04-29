package com.example.errorhandlingdemo.controller;

import com.example.errorhandlingdemo.model.Employee;
import com.example.errorhandlingdemo.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/fetch-employee")
    public ResponseEntity<Employee> fetchEmployee(@RequestParam Integer empid) {
        return ResponseEntity.ok(employeeService.findByEmpId(empid));
    }
}
