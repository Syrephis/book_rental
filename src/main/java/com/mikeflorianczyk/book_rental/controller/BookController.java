package com.mikeflorianczyk.book_rental.controller;

import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author mikeflorianczyk
 */

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    //TODO Filtering
    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    //TODO Move to service and change return type?
    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id) {
        Optional<Book> book = bookService.getBook(id);
        if (book.isPresent()) return new ResponseEntity<>(book, HttpStatus.OK);
        String message = String.format("Book with ID: %d couldn't be found.", id);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }

    @PostMapping
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping
    public ResponseEntity<String> updateBook(@Valid @RequestBody Book book) {
        return bookService.updateBook(book);
    }

}
