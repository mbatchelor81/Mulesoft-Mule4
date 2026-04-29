package com.example.errorhandlingdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsPublishService {

    private static final Logger log = LoggerFactory.getLogger(JmsPublishService.class);

    private final JmsTemplate jmsTemplate;

    public JmsPublishService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public String publishWithContinue(String destination, String payload) {
        try {
            jmsTemplate.convertAndSend(destination, payload);
            log.info("Successfully published to {}", destination);
            return "Published";
        } catch (Exception e) {
            log.warn("JMS publish failed (on-error-continue): {}", e.getMessage());
            return "Publish failed, continuing: " + e.getMessage();
        }
    }
}
