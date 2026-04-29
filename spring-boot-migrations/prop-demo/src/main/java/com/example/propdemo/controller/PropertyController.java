package com.example.propdemo.controller;

import com.example.propdemo.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/read-prop")
public class PropertyController {

    private static final Logger log = LoggerFactory.getLogger(PropertyController.class);

    private final AppProperties appProperties;

    public PropertyController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> readProperties() {
        log.info("DB Username from config: {}", appProperties.db().username());
        return ResponseEntity.ok(Map.of(
            "http.port", appProperties.http().port(),
            "http.path", appProperties.http().path(),
            "db.host", appProperties.db().host(),
            "db.port", appProperties.db().port(),
            "db.username", appProperties.db().username()
        ));
    }
}
