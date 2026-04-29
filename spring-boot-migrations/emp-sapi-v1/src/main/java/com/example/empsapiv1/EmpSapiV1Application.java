package com.example.empsapiv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.empsapiv1", "com.example.common.security"})
public class EmpSapiV1Application {

    public static void main(String[] args) {
        SpringApplication.run(EmpSapiV1Application.class, args);
    }
}
