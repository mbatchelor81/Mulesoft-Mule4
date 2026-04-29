package com.example.wsconsumerdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.ws.client.core.WebServiceTemplate;

import java.io.StringReader;
import java.io.StringWriter;

@Service
public class CalculatorClient {

    private static final Logger log = LoggerFactory.getLogger(CalculatorClient.class);

    @Value("${calculator.service.url:http://www.dneonline.com/calculator.asmx}")
    private String serviceUrl;

    private final WebServiceTemplate webServiceTemplate;

    public CalculatorClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public int add(int firstNo, int secondNo) {
        String requestXml = """
            <Add xmlns="http://tempuri.org/">
                <intA>%d</intA>
                <intB>%d</intB>
            </Add>
            """.formatted(firstNo, secondNo);

        log.info("Calling Calculator SOAP service: {} + {}", firstNo, secondNo);

        try {
            Source requestSource = new StreamSource(new StringReader(requestXml));
            StringWriter responseWriter = new StringWriter();
            StreamResult result = new StreamResult(responseWriter);
            webServiceTemplate.sendSourceAndReceiveToResult(serviceUrl, requestSource, result);

            String responseXml = responseWriter.toString();
            log.info("SOAP response: {}", responseXml);

            int startIdx = responseXml.indexOf("<AddResult>") + "<AddResult>".length();
            int endIdx = responseXml.indexOf("</AddResult>");
            return Integer.parseInt(responseXml.substring(startIdx, endIdx).trim());
        } catch (Exception e) {
            log.error("Error calling Calculator service: {}", e.getMessage());
            return firstNo + secondNo;
        }
    }
}
