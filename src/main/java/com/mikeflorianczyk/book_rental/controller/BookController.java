package com.mikeflorianczyk.book_rental.controller;

import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.service.BookService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    public Optional<Book> getbook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletebook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }

    @PostMapping
    public ResponseEntity<String> addbook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping
    public void updateBook(@Valid @RequestBody Book book) {
        bookService.updateBook(book);
    }

}
