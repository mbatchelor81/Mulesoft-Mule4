package com.example.propdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
    HttpConfig http,
    DbConfig db
) {
    public record HttpConfig(int port, String path) {}
    public record DbConfig(String host, int port, String username, String password) {}
}
