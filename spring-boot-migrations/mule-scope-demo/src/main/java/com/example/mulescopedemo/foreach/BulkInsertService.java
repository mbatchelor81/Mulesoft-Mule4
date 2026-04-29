package com.example.mulescopedemo.foreach;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BulkInsertService {

    private static final Logger log = LoggerFactory.getLogger(BulkInsertService.class);

    private final JdbcTemplate jdbcTemplate;

    public BulkInsertService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int bulkInsert(List<Map<String, Object>> employees) {
        int totalInserted = 0;
        List<List<Map<String, Object>>> partitions = Lists.partition(employees, 2);

        for (List<Map<String, Object>> batch : partitions) {
            log.info("Inserting batch of {} employees", batch.size());
            int[] results = jdbcTemplate.batchUpdate(
                "INSERT INTO emp (emp_id, emp_name, emp_status) VALUES (?, ?, ?)",
                batch.stream()
                    .map(emp -> new Object[]{
                        emp.get("emp_id"),
                        emp.get("emp_name"),
                        emp.get("emp_status")
                    })
                    .toList()
            );
            for (int r : results) {
                totalInserted += r;
            }
        }

        log.info("Total rows inserted: {}", totalInserted);
        return totalInserted;
    }
}
