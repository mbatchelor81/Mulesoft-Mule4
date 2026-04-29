package com.example.emphrsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.emphrsapi", "com.example.common.security"})
public class EmpHrSapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpHrSapiApplication.class, args);
    }
}
