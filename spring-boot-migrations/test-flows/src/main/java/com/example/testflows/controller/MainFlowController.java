package com.example.testflows.controller;

import com.example.testflows.service.AsyncService;
import com.example.testflows.service.PrivateFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MainFlowController {

    private static final Logger log = LoggerFactory.getLogger(MainFlowController.class);

    private final AsyncService asyncService;
    private final PrivateFlowService privateFlowService;

    public MainFlowController(AsyncService asyncService, PrivateFlowService privateFlowService) {
        this.asyncService = asyncService;
        this.privateFlowService = privateFlowService;
    }

    @GetMapping("/sample")
    public ResponseEntity<Map<String, String>> sample() {
        String mainFlowVariable = "MFV";
        log.info("Main flow variable set: {}", mainFlowVariable);

        asyncService.subFlowAsync(mainFlowVariable);
        log.info("Async sub-flow triggered (fire-and-forget)");

        String privateFlowResult = privateFlowService.execute();
        log.info("Private flow result: {}", privateFlowResult);

        return ResponseEntity.ok(Map.of(
            "mainFlowVariable", mainFlowVariable,
            "privateFlowResult", privateFlowResult,
            "asyncSubFlow", "triggered (fire-and-forget)"
        ));
    }
}
