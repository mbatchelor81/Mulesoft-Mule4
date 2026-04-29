package com.example.consumejsonrestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.consumejsonrestservice", "com.example.common.security"})
public class ConsumeJsonRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumeJsonRestServiceApplication.class, args);
    }
}
