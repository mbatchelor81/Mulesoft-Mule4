package com.example.restoverjms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication(scanBasePackages = {"com.example.restoverjms", "com.example.common.security"})
@EnableJms
public class RestOverJmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestOverJmsApplication.class, args);
    }
}
