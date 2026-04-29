package com.example.propdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class PropDemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void readPropertiesReturnsDevConfig() throws Exception {
        mockMvc.perform(get("/read-prop"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.['db.username']").value("sys as sysdba of DEV"));
    }
}
