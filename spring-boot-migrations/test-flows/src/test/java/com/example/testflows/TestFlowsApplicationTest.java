package com.example.testflows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestFlowsApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sampleEndpointReturnsMfvAndPrivateFlowResult() throws Exception {
        mockMvc.perform(get("/sample"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.mainFlowVariable").value("MFV"))
            .andExpect(jsonPath("$.privateFlowResult").value("Private flow result"))
            .andExpect(jsonPath("$.asyncSubFlow").value("triggered (fire-and-forget)"));
    }
}
