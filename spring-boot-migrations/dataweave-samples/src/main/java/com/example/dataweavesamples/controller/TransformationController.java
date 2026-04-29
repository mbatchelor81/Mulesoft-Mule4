package com.example.dataweavesamples.controller;

import com.example.dataweavesamples.service.TransformationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/transform")
public class TransformationController {

    private final TransformationService transformationService;

    public TransformationController(TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @PostMapping(value = "/json-to-xml", consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> jsonToXml(@RequestBody String json) throws IOException {
        return ResponseEntity.ok(transformationService.jsonToXml(json));
    }

    @PostMapping(value = "/xml-to-json", consumes = MediaType.APPLICATION_XML_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> xmlToJson(@RequestBody String xml) throws IOException {
        return ResponseEntity.ok(transformationService.xmlToJson(xml));
    }

    @PostMapping(value = "/json-to-csv", consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = "text/csv")
    public ResponseEntity<String> jsonToCsv(@RequestBody String json) throws IOException {
        return ResponseEntity.ok(transformationService.jsonToCsv(json));
    }

    @PostMapping(value = "/csv-to-json", consumes = "text/csv",
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> csvToJson(@RequestBody String csv) throws IOException {
        return ResponseEntity.ok(transformationService.csvToJson(csv));
    }

    @PostMapping(value = "/json-to-xlsx", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> jsonToXlsx(@RequestBody String json) throws IOException {
        byte[] xlsx = transformationService.jsonToXlsx(json);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.xlsx")
            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(xlsx);
    }
}
