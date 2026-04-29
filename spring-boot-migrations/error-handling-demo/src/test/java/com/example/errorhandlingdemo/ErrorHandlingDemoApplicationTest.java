package com.example.errorhandlingdemo;

import com.example.errorhandlingdemo.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ErrorHandlingDemoApplicationTest {

    @Test
    void employeeNotFoundExceptionContainsEmpId() {
        EmployeeNotFoundException ex = new EmployeeNotFoundException(42);
        assertThat(ex.getMessage()).contains("42");
    }

    @Test
    void employeeNotFoundExceptionWithMessage() {
        EmployeeNotFoundException ex = new EmployeeNotFoundException("custom message");
        assertThat(ex.getMessage()).isEqualTo("custom message");
    }
}
