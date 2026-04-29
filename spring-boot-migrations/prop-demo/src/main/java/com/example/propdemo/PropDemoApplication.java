package com.example.propdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.propdemo.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class PropDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropDemoApplication.class, args);
    }
}
