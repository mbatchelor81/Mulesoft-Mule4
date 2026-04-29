package com.example.soapservice;

import com.example.soapservice.service.TshirtEndpoint;
import org.junit.jupiter.api.Test;

import javax.xml.transform.Source;

import static org.assertj.core.api.Assertions.assertThat;

class SoapServiceApplicationTest {

    private final TshirtEndpoint endpoint = new TshirtEndpoint();

    @Test
    void orderTshirtReturnsStubResponse() {
        Source response = endpoint.orderTshirt(null);
        assertThat(response).isNotNull();
    }

    @Test
    void listInventoryReturnsStubResponse() {
        Source response = endpoint.listInventory(null);
        assertThat(response).isNotNull();
    }

    @Test
    void trackOrderReturnsStubResponse() {
        Source response = endpoint.trackOrder(null);
        assertThat(response).isNotNull();
    }
}
