package com.mikeflorianczyk.book_rental.controller;

import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.service.BookService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{ISBN}")
    public Optional<Book> getbook(@PathVariable Long ISBN) {
        return bookService.getBook(ISBN);
    }

    @DeleteMapping("/{ISBN}")
    public void deletebook(@PathVariable Long ISBN) {
        bookService.deleteBook(ISBN);
    }

    @PostMapping
    public void addbook(@Valid @RequestBody Book book) {
        bookService.addBook(book);
    }

    @PutMapping
    public void updateBook(@Valid @RequestBody Book book) {
        bookService.updateBook(book);
    }

}
