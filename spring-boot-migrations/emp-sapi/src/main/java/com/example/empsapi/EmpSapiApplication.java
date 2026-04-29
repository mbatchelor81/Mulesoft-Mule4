package com.example.empsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.empsapi", "com.example.common.security"})
public class EmpSapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpSapiApplication.class, args);
    }
}
