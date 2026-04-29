package com.example.jmsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class JmsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmsAppApplication.class, args);
    }
}
