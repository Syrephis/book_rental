package com.mikeflorianczyk.book_rental.repository;

import com.mikeflorianczyk.book_rental.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikeflorianczyk
 */

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}
