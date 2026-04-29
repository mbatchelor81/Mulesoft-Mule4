package com.example.mulescopedemo.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class AddEmployeeService {

    private static final Logger log = LoggerFactory.getLogger(AddEmployeeService.class);

    private final JdbcTemplate jdbcTemplate;
    private final AddEmployeeService self;

    public AddEmployeeService(JdbcTemplate jdbcTemplate, @Lazy AddEmployeeService self) {
        this.jdbcTemplate = jdbcTemplate;
        this.self = self;
    }

    public void addEmployeeToAllTables(Map<String, Object> employee) {
        CompletableFuture<Void> master = self.insertAsync("emp_masterv2", employee);
        CompletableFuture<Void> fin = self.insertAsync("emp_fin_master", employee);
        CompletableFuture<Void> appr = self.insertAsync("emp_appr_master", employee);
        CompletableFuture<Void> backup = self.insertAsync("emp_backup", employee);

        CompletableFuture.allOf(master, fin, appr, backup).join();
        log.info("Employee inserted into all 4 tables in parallel");
    }

    @Async
    public CompletableFuture<Void> insertAsync(String tableName, Map<String, Object> employee) {
        log.info("Inserting into {}", tableName);
        jdbcTemplate.update(
            "INSERT INTO " + tableName + " (emp_id, emp_name, emp_status) VALUES (?, ?, ?)",
            employee.get("emp_id"),
            employee.get("emp_name"),
            employee.get("emp_status")
        );
        return CompletableFuture.completedFuture(null);
    }
}
