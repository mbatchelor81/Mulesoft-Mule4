package com.example.jmspushoperations;

import com.example.jmspushoperations.controller.EmployeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class JmsPushOperationsApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JmsTemplate jmsTemplate;

    @Test
    @WithMockUser
    void addEmployeeSendsToQueue() throws Exception {
        mockMvc.perform(post("/add-employee")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"empName\":\"Test\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").exists());
    }
}
