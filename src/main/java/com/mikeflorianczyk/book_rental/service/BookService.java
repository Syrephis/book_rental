package com.mikeflorianczyk.book_rental.service;

import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.model.Status;
import com.mikeflorianczyk.book_rental.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public Optional<Book> getBook(Long id) {
        return bookRepository.findById(id);
    }

    public ResponseEntity<String> addBook(Book book) {
        if (isbnValidation(book.getISBN()) == true) {
            bookRepository.save(book);
            return new ResponseEntity<>("Book has been successfully added to the database.", HttpStatus.OK);
        }
        return new ResponseEntity<>("The provided ISBN is incorret.", HttpStatus.PRECONDITION_FAILED);
    }

    public ResponseEntity<String> deleteBook(Long id) {
        bookRepository.deleteById(id);
        return new ResponseEntity<>("Book has been successfully deleted.", HttpStatus.OK);
    }

    public void updateBook(Book book) {
        if (bookRepository.getOne(book.getId()) != null) {
            bookRepository.save(book);
        }
    }

    public void updateStatus(Long id, Status status) {
        Book book = bookRepository.getOne(id);
        book.setStatus(status);
        bookRepository.save(book);
    }

    private boolean isbnValidation(String ISBN) {
        ISBN = ISBN.replaceAll("-", "");
        char[] digits = ISBN.toCharArray();

        if (digits.length == 10) {
            int sum = 0;
            for (int i = 0, j = 10; i < 10; i++, j--) sum += Character.getNumericValue(digits[i]) * j;
            if (sum % 11 == 0) return true;
        }
        if (digits.length == 13) {
            int sum = 0;
            for (int i = 0; i < 13; i++)
                sum += (i % 2 == 0) ? Character.getNumericValue(digits[i]) * 1 : Character.getNumericValue(digits[i]) * 3;
            if (sum % 10 == 0) return true;
        }
        return false;
    }

}
