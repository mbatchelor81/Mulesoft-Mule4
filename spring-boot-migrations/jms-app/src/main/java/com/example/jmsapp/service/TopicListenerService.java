package com.example.jmsapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class TopicListenerService {

    private static final Logger log = LoggerFactory.getLogger(TopicListenerService.class);

    @JmsListener(destination = "T.FSD.EMP", containerFactory = "topicListenerFactory")
    public void onTopicMessage(String payload) {
        log.info("Received message from topic T.FSD.EMP: {}", payload);
    }
}
