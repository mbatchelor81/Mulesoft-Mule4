package com.example.wsconsumerdemo;

import com.example.wsconsumerdemo.controller.CalculatorController;
import com.example.wsconsumerdemo.service.CalculatorClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
class WsConsumerDemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorClient calculatorClient;

    @Test
    @WithMockUser
    void additionEndpointReturnsResult() throws Exception {
        when(calculatorClient.add(1, 2)).thenReturn(3);
        mockMvc.perform(post("/addition")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstNo\":1,\"secondNo\":2}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value(3));
    }
}
