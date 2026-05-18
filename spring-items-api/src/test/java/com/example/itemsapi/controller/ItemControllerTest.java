package com.example.itemsapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getItems_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/items"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser   // simulates an authenticated user — bypasses JWT validation in tests
    void getItems_withValidUser_returns200() throws Exception {
        mockMvc.perform(get("/api/items"))
               .andExpect(status().isOk());
    }
}
