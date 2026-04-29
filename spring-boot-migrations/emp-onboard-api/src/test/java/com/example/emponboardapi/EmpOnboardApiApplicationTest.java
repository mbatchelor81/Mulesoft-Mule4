package com.example.emponboardapi;

import com.example.emponboardapi.controller.EmployeeOnboardController;
import com.example.emponboardapi.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({EmployeeOnboardController.class, GlobalExceptionHandler.class})
class EmpOnboardApiApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void addEmployeeReturnsSuccess() throws Exception {
        String json = """
            {"emp-name":"Gaurav","status":"A"}
            """;
        mockMvc.perform(post("/emp-onboard-api/v1/add-employee")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    @WithMockUser
    void addEmployeeWithMissingFieldReturns400() throws Exception {
        String json = """
            {"status":"A"}
            """;
        mockMvc.perform(post("/emp-onboard-api/v1/add-employee")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }
}
