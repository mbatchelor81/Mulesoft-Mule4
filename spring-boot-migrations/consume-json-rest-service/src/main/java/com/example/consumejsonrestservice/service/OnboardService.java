package com.example.consumejsonrestservice.service;

import com.example.consumejsonrestservice.model.EmployeeMaster;
import com.example.consumejsonrestservice.repository.EmployeeMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class OnboardService {

    private static final Logger log = LoggerFactory.getLogger(OnboardService.class);

    private final EmployeeMasterRepository repository;
    private final WebClient webClient;

    public OnboardService(EmployeeMasterRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClient = webClientBuilder.build();
    }

    @Transactional
    public Map<String, Object> createEmployee(EmployeeMaster employee) {
        EmployeeMaster saved = repository.save(employee);
        log.info("Employee saved with ID: {}", saved.getEmpId());

        Map<String, Object> financePayload = Map.of(
            "emp_id", saved.getEmpId(),
            "emp_name", saved.getEmpName(),
            "emp_sal", saved.getEmpSal() != null ? saved.getEmpSal() : 0.0
        );

        try {
            String financeResponse = webClient.post()
                .uri("http://localhost:8094/finance/add-details")
                .bodyValue(financePayload)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            log.info("Finance service response: {}", financeResponse);
        } catch (Exception e) {
            log.error("Error calling finance service: {}", e.getMessage());
        }

        return Map.of("message", "Employee onboarded", "empId", saved.getEmpId());
    }
}
