package com.example.mulescopedemo.foreach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeEnricherService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeEnricherService.class);

    private final WebClient webClient;

    public EmployeeEnricherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public List<Map<String, Object>> enrichEmployees(List<Integer> employeeIds) {
        List<Map<String, Object>> results = new ArrayList<>();

        for (Integer id : employeeIds) {
            try {
                String response = webClient.get()
                    .uri("http://dummy.restapiexample.com/api/v1/employee/{id}", id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
                results.add(Map.of("empId", id, "data", response != null ? response : "null"));
            } catch (Exception e) {
                log.warn("Error enriching employee {}: {} (on-error-continue)", id, e.getMessage());
                results.add(Map.of("empId", id, "error", e.getMessage() != null ? e.getMessage() : e.getClass().getName()));
            }
        }

        return results;
    }
}
