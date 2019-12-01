package com.mikeflorianczyk.book_rental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.model.Status;
import com.mikeflorianczyk.book_rental.service.BookService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;

    @Test
    void contextLoads() {
        assertNotNull(bookService);
    }

    //TODO add check for empty
    @DisplayName("findAll() if DB is empty")
    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(jsonPath("$").value(IsNull.notNullValue()));
    }

    //TODO Check JSON Body
    @DisplayName("getBook() if book exists")
    @Test
    void getBook_ok() throws Exception {
        Book bookToAdd = new Book("9788374959551", "Norwegian Wood", "Haruki Murakami", "Muza", Status.AVAILABLE);
        bookService.addBook(bookToAdd);

        mockMvc.perform(get("/books/{id}", bookToAdd.getId()))
                .andDo(print()).andExpect(status().isOk());
    }

    @DisplayName("getBook() if book doesn't exist")
    @Test
    void getBook_fail() throws Exception {
        mockMvc.perform(get("/books/{id}", 0))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("deleteBook() if book exists")
    @Test
    void deleteBook_ok() throws Exception {
        Book bookToDelete = new Book("9788374959551", "Norwegian Wood", "Haruki Murakami", "Muza", Status.AVAILABLE);
        bookService.addBook(bookToDelete);

        mockMvc.perform(delete("/books/{id}", bookToDelete.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("deleteBook() if book doesn't exist")
    @Test
    void deleteBook_fail() throws Exception {
        mockMvc.perform(delete("/books/{id}", 0))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("addBook() if book doesn't exist")
    @Test
    void addBook_ok() throws Exception {
        Book bookToAdd = new Book("9788377585092", "Przygoda z owcą", "Haruki Murakami", "Muza", Status.AVAILABLE);

        mockMvc.perform(post("/books")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookToAdd)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    //Throws DataIntegrityViolationException
    //FIXME Throws DataIntegrityViolationException because of already existing ISBN.
    @Disabled
    @DisplayName("addBook() if book already exists by ISBN")
    @Test
    void addBook_fail() throws Exception {
        Book bookToAdd = new Book("9788375781557", "Andrzej Sapkowski", "Szpony i kły", "superNOWA", Status.AVAILABLE);

        mockMvc.perform(post("/books")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookToAdd)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @DisplayName("addBook() if book already exists by ID")
    @Test
    void addBook_fail_1() throws Exception {
        Book bookToAdd = new Book((long) 1, "9788375781557", "Andrzej Sapkowski", "Szpony i kły", "superBOMBA", Status.AVAILABLE);

        mockMvc.perform(post("/books")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookToAdd)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("updateBook() if book exists")
    @Test
    void updateBook_ok() throws Exception {
        Book bookToUpdate = new Book("9788373199651", "Przygoda z owcą", "Haruki Murakami", "Muza", Status.AVAILABLE);
        bookService.addBook(bookToUpdate);
        Book updatedBook = new Book(bookToUpdate.getId(), bookToUpdate.getISBN(), "Tańcz, tańcz, tańcz", bookToUpdate.getAuthor(), bookToUpdate.getPublisher(), bookToUpdate.getStatus());

        mockMvc.perform(put("/books/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedBook)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        assertEquals(updatedBook.getTitle(), Objects.requireNonNull(bookService.getBook(bookToUpdate.getId()).orElse(null)).getTitle());
    }

    @DisplayName("updateBook() if book doesn't exist")
    @Test
    void updateBook_fail() throws Exception {
        Book bookToUpdate = new Book((long) 0, "9788373199651", "Przygoda z owcą", "Haruki Murakami", "Muza", Status.AVAILABLE);
        mockMvc.perform(put("/books/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("updateBook() if new ISBN isn't valid")
    @Test
    void updateBook_fail_1() throws Exception {
        Book bookToUpdate = new Book((long) 1, "99921-58-10-7", "Przygoda z owcą", "Haruki Murakami", "Muza", Status.AVAILABLE);
        mockMvc.perform(put("/books/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}