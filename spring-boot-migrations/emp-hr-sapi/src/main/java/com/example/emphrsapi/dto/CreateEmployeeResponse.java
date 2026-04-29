package com.example.emphrsapi.dto;

public record CreateEmployeeResponse(
    int code,
    String message,
    String description,
    String datetime,
    String transactionId
) {}
