package com.example.testflows.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PrivateFlowService {

    private static final Logger log = LoggerFactory.getLogger(PrivateFlowService.class);

    public String execute() {
        log.info("Private flow executing synchronously");
        return "Private flow result";
    }
}
