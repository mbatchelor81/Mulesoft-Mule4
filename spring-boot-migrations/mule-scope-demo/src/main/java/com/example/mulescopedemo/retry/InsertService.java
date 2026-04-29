package com.example.mulescopedemo.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InsertService {

    private static final Logger log = LoggerFactory.getLogger(InsertService.class);

    private final JdbcTemplate jdbcTemplate;

    public InsertService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public int insertEmployee(Map<String, Object> employee) {
        log.info("Attempting insert for employee: {}", employee);
        return jdbcTemplate.update(
            "INSERT INTO emp (emp_id, emp_name, emp_status) VALUES (?, ?, ?)",
            employee.get("emp_id"),
            employee.get("emp_name"),
            employee.get("emp_status")
        );
    }
}
