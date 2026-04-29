package com.example.errorhandlingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication(scanBasePackages = {"com.example.errorhandlingdemo", "com.example.common.security"})
@EnableJms
public class ErrorHandlingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErrorHandlingDemoApplication.class, args);
    }
}
