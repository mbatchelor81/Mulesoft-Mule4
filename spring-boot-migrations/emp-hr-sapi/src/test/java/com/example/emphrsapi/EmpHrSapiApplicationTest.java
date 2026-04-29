package com.example.emphrsapi;

import com.example.emphrsapi.controller.EmployeeHrController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeHrController.class)
class EmpHrSapiApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void addEmployeeReturns501Stub() throws Exception {
        String json = """
            {"empID":1,"empName":"Test","empAddress":"AU","empStatus":"A"}
            """;
        mockMvc.perform(post("/emp-hr-sapi/v1/add-employee")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNotImplemented())
            .andExpect(jsonPath("$.code").value(501));
    }

    @Test
    @WithMockUser
    void fetchEmployeeReturns501Stub() throws Exception {
        mockMvc.perform(get("/emp-hr-sapi/v1/fetch-employee/1"))
            .andExpect(status().isNotImplemented());
    }
}
