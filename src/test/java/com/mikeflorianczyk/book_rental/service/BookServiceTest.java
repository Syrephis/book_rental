package com.mikeflorianczyk.book_rental.service;

import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.model.Status;
import com.mikeflorianczyk.book_rental.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mikeflorianczyk
 */
//Testing some of the business logic
@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests")
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    @Test
    @DisplayName("Testing ISBN validation through addBook()")
    void addBook() {
        List<String> goodISBNList = new ArrayList<>();
        List<String> badISBNList = new ArrayList<>();
        goodISBNList.add("9788374959551");
        goodISBNList.add("9788387347420");
        goodISBNList.add("9780306406157");
        goodISBNList.add("9992158107");
        badISBNList.add("99921-58-10-7");
        badISBNList.add("978-3-16-148410-0");
        badISBNList.add("978-83-87347-42-0");
        badISBNList.add("978-0-306-40615-7");
        badISBNList.add("978-0-306-406");
        badISBNList.add("cde-0-ABC-40615-0");
        badISBNList.add("aaa-a-aaa-aaaaa-0");
        badISBNList.add("aaaaaaaaaaaa0");
        for (int i = 0; i < goodISBNList.size(); i++) {
            ResponseEntity<?> responseEntity = bookService.addBook(new Book(goodISBNList.get(i), "Title", "Author", "Publisher", Status.AVAILABLE));
            assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        }
        for (int i = 0; i < badISBNList.size(); i++) {
            ResponseEntity<?> responseEntity = bookService.addBook(new Book(badISBNList.get(i), "Title", "Author", "Publisher", Status.AVAILABLE));
            assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
        }
    }
}