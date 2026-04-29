package com.example.restoverjms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
public class DeleteEmployeeController {

    private static final Logger log = LoggerFactory.getLogger(DeleteEmployeeController.class);

    private final JdbcTemplate jdbcTemplate;
    private final WebClient webClient;

    public DeleteEmployeeController(JdbcTemplate jdbcTemplate, WebClient.Builder webClientBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        this.webClient = webClientBuilder.build();
    }

    @DeleteMapping("/onboard/delete-employee/{empid}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Integer empid) {
        int affectedRows = jdbcTemplate.update(
            "UPDATE emp_master SET emp_status='D' WHERE emp_id=?", empid);

        if (affectedRows > 0) {
            log.info("Marked employee {} as deleted in emp_master, calling downstream", empid);
            try {
                webClient.delete()
                    .uri("http://localhost:8096/emp-sapi/delete-employee/{empid}", empid)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            } catch (Exception e) {
                log.error("Error calling downstream delete: {}", e.getMessage());
            }
            return ResponseEntity.ok(Map.of("Message", "Employee deleted"));
        }
        return ResponseEntity.ok(Map.of("Message", "Employee Not Found"));
    }

    @DeleteMapping("/emp-sapi/delete-employee/{empid}")
    public ResponseEntity<Map<String, String>> deleteFromEmpTable(@PathVariable Integer empid) {
        int rows = jdbcTemplate.update("DELETE FROM emp WHERE emp_id=?", empid);
        log.info("Deleted {} row(s) from emp table for emp_id={}", rows, empid);
        return ResponseEntity.ok(Map.of("Message", "Deleted from emp table", "rows", String.valueOf(rows)));
    }
}
