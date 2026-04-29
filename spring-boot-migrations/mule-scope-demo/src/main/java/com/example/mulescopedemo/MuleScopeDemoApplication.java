package com.example.mulescopedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.example.mulescopedemo", "com.example.common.security"})
@EnableRetry
@EnableAsync
public class MuleScopeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuleScopeDemoApplication.class, args);
    }
}
