package com.example.empsapiv1.controller;

import com.example.empsapiv1.model.Employee;
import com.example.empsapiv1.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emp-sapi")
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/add-employee")
    @PreAuthorize("hasAuthority('SCOPE_Employee.ReadWrite')")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee saved = repository.save(employee);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/fetch-employees")
    @PreAuthorize("hasAuthority('SCOPE_Employee.ReadWrite')")
    public ResponseEntity<List<Employee>> fetchEmployees() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/fetch-employee/{empid}")
    @PreAuthorize("hasAuthority('SCOPE_Employee.ReadWrite')")
    public ResponseEntity<Object> fetchEmployee(@PathVariable Integer empid) {
        return repository.findById(empid)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(404)
                .body(Map.of("message", "Employee Not Found")));
    }

    @PutMapping("/update-employee")
    @PreAuthorize("hasAuthority('SCOPE_Employee.ReadWrite')")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee saved = repository.save(employee);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/delete-employee/{empid}")
    @PreAuthorize("hasAuthority('SCOPE_Employee.ReadWrite')")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Integer empid) {
        if (repository.existsById(empid)) {
            repository.deleteById(empid);
            return ResponseEntity.ok(Map.of("message", "Employee deleted successfully"));
        }
        return ResponseEntity.status(404)
            .body(Map.of("message", "Employee Not Found"));
    }
}
