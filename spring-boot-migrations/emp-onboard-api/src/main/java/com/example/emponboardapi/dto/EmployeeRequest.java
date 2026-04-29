package com.example.emponboardapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record EmployeeRequest(
    @NotBlank(message = "Employee name is required")
    @JsonProperty("emp-name")
    String empName,

    @NotBlank(message = "Status is required")
    String status
) {}
