package com.example.errorhandlingdemo.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(Integer empId) {
        super("Employee not found with ID: " + empId);
    }
}
