package com.mikeflorianczyk.book_rental.service;

import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.model.Status;

import java.util.List;
import java.util.Optional;

/**
 * @author mikeflorianczyk
 */
public interface BookService {

    List<Book> findAll();

    Optional<Book> getBook(Long ISBN);

    void addBook(Book book);

    void deleteBook(Long ISBN);

    void updateBook(Book book);

    void updateStatus(Long ISBN, Status status);
}
