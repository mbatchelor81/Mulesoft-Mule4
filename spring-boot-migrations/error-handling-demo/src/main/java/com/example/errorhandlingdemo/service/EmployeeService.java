package com.example.errorhandlingdemo.service;

import com.example.errorhandlingdemo.exception.EmployeeNotFoundException;
import com.example.errorhandlingdemo.model.Employee;
import com.example.errorhandlingdemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee findByEmpId(Integer empId) {
        return repository.findById(empId)
            .orElseThrow(() -> new EmployeeNotFoundException(empId));
    }
}
