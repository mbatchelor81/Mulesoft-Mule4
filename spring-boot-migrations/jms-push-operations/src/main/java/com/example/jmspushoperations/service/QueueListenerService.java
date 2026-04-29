package com.example.jmspushoperations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class QueueListenerService {

    private static final Logger log = LoggerFactory.getLogger(QueueListenerService.class);

    @JmsListener(destination = "Q.FSD.EMP")
    public void onMessage(String payload) {
        log.info("Received from Q.FSD.EMP: {}", payload);
    }
}
