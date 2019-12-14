package com.mikeflorianczyk.book_rental.service;

import com.mikeflorianczyk.book_rental.model.Book;
import com.mikeflorianczyk.book_rental.model.Status;
import com.mikeflorianczyk.book_rental.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.NestedServletException;

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

    public Optional<Book> getBookByISBN(String ISBN) {
        Book book = new Book();
        book.setISBN(ISBN);
        book.setStatus(null);

        Example<Book> example = Example.of(book);
        return bookRepository.findOne(example);
    }

    public ResponseEntity<String> addBook(Book book) {
        if (checkIfExists(book))
            return new ResponseEntity<String>("Book with provided ID already exists.", HttpStatus.BAD_REQUEST);
        if (isbnValidation(book.getISBN())) {
            if (ifExistsByISBN(book.getISBN())) return new ResponseEntity<>("Book with provided ISBN already exists.", HttpStatus.BAD_REQUEST);
            bookRepository.save(book);
            return new ResponseEntity<>("Book has been successfully added to the database.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("The provided ISBN is incorrect. The ISBN should be either 10 or 13 digits long and should only contain numeric characters. Ex: 9992158107", HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<String> deleteBook(Long id) {
        if (checkIfExists(bookRepository.getOne(id))) {
            bookRepository.deleteById(id);
            return new ResponseEntity<>("Book has been successfully deleted.", HttpStatus.OK);
        } return new ResponseEntity<String>("Book with provided ID doesn't exist.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateBook(Book book) {
        if (checkIfExists(book)) {
            if (isbnValidation(book.getISBN())) {
                bookRepository.save(book);
                return new ResponseEntity<>("Book has been updated!", HttpStatus.OK);
            } return new ResponseEntity<>("The provided ISBN is incorrect. The ISBN should be either 10 or 13 digits long and should only contain numeric characters. Ex: 9992158107", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Book with provided ID doesn't exist.", HttpStatus.BAD_REQUEST);
    }

    void updateStatus(Long id, Status status) {
        Book book = bookRepository.getOne(id);
        book.setStatus(status);
        bookRepository.save(book);
    }

    private boolean ifExistsByISBN(String ISBN) {
        Book book = new Book();
        book.setISBN(ISBN);
        book.setStatus(null);

        Example<Book> example = Example.of(book);
        return bookRepository.exists(example);
    }

    private boolean checkIfExists(Book book) {
        if (book.getId() == null) return false;
        return bookRepository.existsById(book.getId());
    }

    //TODO create as annotation
    private boolean isbnValidation(String ISBN) {
        if (ISBN.matches("[0-9]+")) {
            char[] digits = ISBN.toCharArray();

            if (digits.length == 10) {
                int sum = 0;
                for (int i = 0, j = 10; i < 10; i++, j--) sum += Character.getNumericValue(digits[i]) * j;
                return sum % 11 == 0;
            }
            if (digits.length == 13) {
                int sum = 0;
                for (int i = 0; i < 12; i++)
                    sum += (i % 2 == 0) ? Character.getNumericValue(digits[i]) : Character.getNumericValue(digits[i]) * 3;
                int checksum = 10 - (sum % 10);
                if (checksum == 10) checksum = 0;
                return checksum == Character.getNumericValue(digits[12]);
            }
        }
        return false;
    }

}
