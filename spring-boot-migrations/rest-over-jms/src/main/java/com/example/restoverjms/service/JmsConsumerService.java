package com.example.restoverjms.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class JmsConsumerService {

    private static final Logger log = LoggerFactory.getLogger(JmsConsumerService.class);

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();
    private final WebClient webClient;

    public JmsConsumerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @JmsListener(destination = "Q.FSD.EMP")
    public void onMessage(String payload) {
        log.info("Received from Q.FSD.EMP: {}", payload);
        try {
            JsonNode jsonNode = jsonMapper.readTree(payload);
            String xmlPayload = xmlMapper.writeValueAsString(jsonNode);
            log.info("Transformed to XML: {}", xmlPayload);

            String response = webClient.post()
                .uri("http://localhost:8096/onboard/update-salary")
                .header("Content-Type", "application/xml")
                .bodyValue(xmlPayload)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            log.info("Update salary response: {}", response);
        } catch (Exception e) {
            log.error("Error processing JMS message: {}", e.getMessage());
        }
    }
}
