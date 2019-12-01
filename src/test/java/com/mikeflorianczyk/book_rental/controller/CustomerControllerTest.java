package com.mikeflorianczyk.book_rental.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.model.Customer;
import com.mikeflorianczyk.book_rental.model.Status;
import com.mikeflorianczyk.book_rental.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author mikeflorianczyk
 */

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    @Test
    void contextLoads() {
        assertNotNull(customerService);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/customers"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getCustomer() throws Exception{
        Customer customerToAdd = new Customer("Jacek", "Placek", new BigDecimal("0.0"));
        customerService.addCustomer(customerToAdd);

        mockMvc.perform(get("/customers/{id}", customerToAdd.getId()))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteCustomer() throws Exception{
        Customer customerToAdd = new Customer("Kazik", "Kazikowy", new BigDecimal("0.0"));
        customerService.addCustomer(customerToAdd);

        mockMvc.perform(delete("/customers/{id}", customerToAdd.getId()))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void addCustomer() throws Exception {
        Customer customerToAdd = new Customer("Adam", "Nowak", new BigDecimal("0.0"));

        mockMvc.perform(post("/customers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(customerToAdd)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}