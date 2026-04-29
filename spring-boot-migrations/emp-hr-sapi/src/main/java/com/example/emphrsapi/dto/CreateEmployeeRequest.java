package com.example.emphrsapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateEmployeeRequest(
    @NotNull(message = "Employee ID is required")
    Integer empID,

    @NotNull(message = "Employee name is required")
    @Size(min = 1, max = 100, message = "Employee name must be between 1 and 100 characters")
    String empName,

    @Size(max = 255, message = "Address must not exceed 255 characters")
    String empAddress,

    @Size(max = 1, message = "Status must be a single character")
    String empStatus
) {}
