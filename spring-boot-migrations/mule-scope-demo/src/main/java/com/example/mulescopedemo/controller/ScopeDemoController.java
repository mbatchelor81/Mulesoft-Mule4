package com.example.mulescopedemo.controller;

import com.example.mulescopedemo.foreach.BulkInsertService;
import com.example.mulescopedemo.foreach.EmployeeEnricherService;
import com.example.mulescopedemo.parallel.AddEmployeeService;
import com.example.mulescopedemo.retry.InsertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scope-demo")
public class ScopeDemoController {

    private static final Logger log = LoggerFactory.getLogger(ScopeDemoController.class);

    private final InsertService insertService;
    private final AddEmployeeService addEmployeeService;
    private final EmployeeEnricherService enricherService;
    private final BulkInsertService bulkInsertService;
    private final JobLauncher jobLauncher;
    private final Job salaryIncrementBatch;

    public ScopeDemoController(InsertService insertService,
                                AddEmployeeService addEmployeeService,
                                EmployeeEnricherService enricherService,
                                BulkInsertService bulkInsertService,
                                JobLauncher jobLauncher,
                                Job salaryIncrementBatch) {
        this.insertService = insertService;
        this.addEmployeeService = addEmployeeService;
        this.enricherService = enricherService;
        this.bulkInsertService = bulkInsertService;
        this.jobLauncher = jobLauncher;
        this.salaryIncrementBatch = salaryIncrementBatch;
    }

    @PostMapping("/retry-insert")
    public ResponseEntity<Map<String, Object>> retryInsert(@RequestBody Map<String, Object> employee) {
        int rows = insertService.insertEmployee(employee);
        return ResponseEntity.ok(Map.of("message", "Inserted with retry", "rows", rows));
    }

    @PostMapping("/batch-salary-increment")
    public ResponseEntity<Map<String, String>> runBatch() throws Exception {
        JobParameters params = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();
        jobLauncher.run(salaryIncrementBatch, params);
        return ResponseEntity.ok(Map.of("message", "Batch job salaryIncrementBatch launched"));
    }

    @PostMapping("/parallel-insert")
    public ResponseEntity<Map<String, String>> parallelInsert(@RequestBody Map<String, Object> employee) {
        addEmployeeService.addEmployeeToAllTables(employee);
        return ResponseEntity.ok(Map.of("message", "Employee inserted into 4 tables in parallel"));
    }

    @PostMapping("/enrich-employees")
    public ResponseEntity<List<Map<String, Object>>> enrichEmployees(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(enricherService.enrichEmployees(ids));
    }

    @PostMapping("/bulk-insert")
    public ResponseEntity<Map<String, Object>> bulkInsert(@RequestBody List<Map<String, Object>> employees) {
        int total = bulkInsertService.bulkInsert(employees);
        return ResponseEntity.ok(Map.of("message", "Bulk insert complete", "totalInserted", total));
    }
}
