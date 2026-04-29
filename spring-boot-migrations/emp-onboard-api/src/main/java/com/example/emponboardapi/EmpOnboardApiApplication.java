package com.example.emponboardapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.emponboardapi", "com.example.common.security"})
public class EmpOnboardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpOnboardApiApplication.class, args);
    }
}
