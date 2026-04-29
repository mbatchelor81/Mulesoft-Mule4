package com.example.testflows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TestFlowsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestFlowsApplication.class, args);
    }
}
