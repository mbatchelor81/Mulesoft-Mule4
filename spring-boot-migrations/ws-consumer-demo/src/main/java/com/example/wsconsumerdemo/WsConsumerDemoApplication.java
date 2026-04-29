package com.example.wsconsumerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.wsconsumerdemo", "com.example.common.security"})
public class WsConsumerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsConsumerDemoApplication.class, args);
    }
}
