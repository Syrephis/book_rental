package com.mikeflorianczyk.book_rental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.model.Customer;
import com.mikeflorianczyk.book_rental.model.Rental;
import com.mikeflorianczyk.book_rental.model.Status;
import com.mikeflorianczyk.book_rental.repository.RentalRepository;
import com.mikeflorianczyk.book_rental.service.BookService;
import com.mikeflorianczyk.book_rental.service.CustomerService;
import com.mikeflorianczyk.book_rental.service.RentalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author mikeflorianczyk
 */

@SpringBootTest
@AutoConfigureMockMvc
class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RentalService rentalService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BookService bookService;

    @Test
    void contextLoads() {
        assertNotNull(rentalService);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/rentals"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getRental() throws Exception {
        Book bookToAdd = new Book("8370239161", "Szara Strefa", "Tadeusz Różewicz", "Wydawnictwo Dolnośląskie", Status.AVAILABLE);
        bookService.addBook(bookToAdd);
        Customer customerToAdd = new Customer("Zenon", "Zenonkiewicz", new BigDecimal("0.0"));
        customerService.addCustomer(customerToAdd);
        Rental rentalToAdd = new Rental(bookToAdd, customerToAdd);
        rentalService.rentBook(rentalToAdd);

        mockMvc.perform(get("/rentals/{bookingId}", rentalToAdd.getBookingId()))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void rentBook() throws Exception {
        Book bookToAdd = new Book("8370236758", "Niepokój", "Tadeusz Różewicz", "Wydawnictwo Dolnośląskie", Status.AVAILABLE);
        bookService.addBook(bookToAdd);
        Customer customerToAdd = new Customer("Abraham", "Abrahamowicz", new BigDecimal("0.0"));
        customerService.addCustomer(customerToAdd);
        Rental rentalToAdd = new Rental(bookToAdd, customerToAdd);

        mockMvc.perform(post("/rentals")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rentalToAdd)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        assertEquals(Status.RENTED, Objects.requireNonNull(bookService.getBook(bookToAdd.getId()).orElse(null)).getStatus());
    }

    @Test
    void returnBook() throws Exception {
        Book bookToAdd = new Book("9788324002276", "Chwila", "Wisława Szymborska", "Znak", Status.AVAILABLE);
        bookService.addBook(bookToAdd);
        Customer customerToAdd = new Customer("Michal", "Michalowicz", new BigDecimal("0.0"));
        customerService.addCustomer(customerToAdd);
        Rental rentalToAdd = new Rental(bookToAdd, customerToAdd);
        rentalService.rentBook(rentalToAdd);

        assertEquals(Status.RENTED, Objects.requireNonNull(bookService.getBook(bookToAdd.getId()).orElse(null)).getStatus());

        mockMvc.perform(put("/rentals/{bookingId}", rentalToAdd.getBookingId()))
                .andDo(print()).andExpect(status().isOk());

        assertEquals(Status.AVAILABLE, Objects.requireNonNull(bookService.getBook(bookToAdd.getId()).orElse(null)).getStatus());
    }

    @Test
    void returnBookOverdue() throws Exception {
        Book bookToAdd = new Book("9788306033526", "Sprawdzone sobą. Wybór wierszy.", "Miron Białoszewski", "Państwowy Instytut Wydawniczy", Status.AVAILABLE);
        bookService.addBook(bookToAdd);
        Customer customerToAdd = new Customer("Staszek", "Staszkiewicz", new BigDecimal("0.0"));
        customerService.addCustomer(customerToAdd);
        Rental rentalToAdd = new Rental(bookToAdd, customerToAdd);
        rentalToAdd.setPredictedReturnDate(rentalToAdd.getIssueDate().minusWeeks(2));
        rentalService.rentBook(rentalToAdd);

        mockMvc.perform(put("/rentals/{bookingId}", rentalToAdd.getBookingId()))
                .andDo(print()).andExpect(status().isOk());

        assertEquals(new BigDecimal("5.50"), Objects.requireNonNull(customerService.getCustomer(customerToAdd.getId()).orElse(null)).getAccount());

    }
}