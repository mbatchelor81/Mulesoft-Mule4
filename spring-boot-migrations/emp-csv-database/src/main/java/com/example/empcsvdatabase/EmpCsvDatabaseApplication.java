package com.example.empcsvdatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmpCsvDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpCsvDatabaseApplication.class, args);
    }
}
