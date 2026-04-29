package com.example.restoverjms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateSalaryController {

    private static final Logger log = LoggerFactory.getLogger(UpdateSalaryController.class);

    private final JdbcTemplate jdbcTemplate;

    public UpdateSalaryController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping(value = "/onboard/update-salary", consumes = {"application/xml", "application/json"})
    public ResponseEntity<Map<String, Object>> updateSalary(@RequestBody Map<String, Object> payload) {
        log.info("Received salary update: {}", payload);
        Object empIdObj = payload.get("emp_id");
        Object empSalObj = payload.get("emp_sal");

        if (empIdObj != null && empSalObj != null) {
            int rows = jdbcTemplate.update(
                "UPDATE emp_master SET emp_sal=? WHERE emp_id=?",
                Double.parseDouble(empSalObj.toString()),
                Integer.parseInt(empIdObj.toString())
            );
            return ResponseEntity.ok(Map.of("message", "Updated", "rowsAffected", rows));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Missing emp_id or emp_sal"));
    }
}
