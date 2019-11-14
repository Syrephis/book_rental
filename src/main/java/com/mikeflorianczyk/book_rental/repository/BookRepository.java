package com.mikeflorianczyk.book_rental.repository;

import com.mikeflorianczyk.book_rental.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikeflorianczyk
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
