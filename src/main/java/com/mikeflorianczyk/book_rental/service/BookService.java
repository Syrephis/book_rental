package com.mikeflorianczyk.book_rental.service;

import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.model.Status;
import com.mikeflorianczyk.book_rental.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author mikeflorianczyk
 */
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBook(Long ISBN) {
        return bookRepository.findById(ISBN);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long ISBN) {
        bookRepository.deleteById(ISBN);
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public void updateStatus(Long ISBN, Status status) {
        Book book = getBook(ISBN).orElse(null);
        book.setStatus(status);
        bookRepository.save(book);
    }

}
