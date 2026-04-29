package com.example.empsapi;

import com.example.empsapi.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
class EmpSapiApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void helloEndpointReturnsGreeting() throws Exception {
        mockMvc.perform(get("/emp-sapi/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hello from Employee SAPI - migrated from MuleSoft to Spring Boot"));
    }
}
