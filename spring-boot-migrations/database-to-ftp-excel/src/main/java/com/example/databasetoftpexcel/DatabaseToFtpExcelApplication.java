package com.example.databasetoftpexcel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DatabaseToFtpExcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseToFtpExcelApplication.class, args);
    }
}
