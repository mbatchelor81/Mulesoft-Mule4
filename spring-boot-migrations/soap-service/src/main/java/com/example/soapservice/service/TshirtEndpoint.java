package com.example.soapservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@Endpoint
public class TshirtEndpoint {

    private static final Logger log = LoggerFactory.getLogger(TshirtEndpoint.class);
    private static final String NAMESPACE_URI = "http://mulesoft.org/tshirt-service";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "OrderTshirt")
    @ResponsePayload
    public Source orderTshirt(@RequestPayload Source request) {
        log.info("Received OrderTshirt request");
        String response = """
            <OrderTshirtResponse xmlns="%s">
                <orderId>STUB-001</orderId>
            </OrderTshirtResponse>
            """.formatted(NAMESPACE_URI);
        return new StreamSource(new StringReader(response));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListInventory")
    @ResponsePayload
    public Source listInventory(@RequestPayload Source request) {
        log.info("Received ListInventory request");
        String response = """
            <ListInventoryResponse xmlns="%s">
                <inventory>
                    <productCode>TS001</productCode>
                    <size>M</size>
                    <description>MuleSoft T-Shirt</description>
                    <count>100</count>
                </inventory>
            </ListInventoryResponse>
            """.formatted(NAMESPACE_URI);
        return new StreamSource(new StringReader(response));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TrackOrder")
    @ResponsePayload
    public Source trackOrder(@RequestPayload Source request) {
        log.info("Received TrackOrder request");
        String response = """
            <TrackOrderResponse xmlns="%s">
                <orderId>STUB-001</orderId>
                <status>Shipped</status>
                <size>M</size>
            </TrackOrderResponse>
            """.formatted(NAMESPACE_URI);
        return new StreamSource(new StringReader(response));
    }
}
