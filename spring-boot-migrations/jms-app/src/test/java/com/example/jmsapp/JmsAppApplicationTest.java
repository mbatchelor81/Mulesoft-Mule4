package com.example.jmsapp;

import com.example.jmsapp.service.QueueListenerService;
import com.example.jmsapp.service.TopicListenerService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JmsAppApplicationTest {

    @Test
    void servicesCanBeInstantiated() {
        assertThat(new QueueListenerService()).isNotNull();
        assertThat(new TopicListenerService()).isNotNull();
    }
}
