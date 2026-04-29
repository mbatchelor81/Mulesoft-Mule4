package com.example.jmspushoperations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication(scanBasePackages = {"com.example.jmspushoperations", "com.example.common.security"})
@EnableJms
public class JmsPushOperationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmsPushOperationsApplication.class, args);
    }
}
