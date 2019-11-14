package com.mikeflorianczyk.book_rental.service;

import com.mikeflorianczyk.book_rental.model.Rental;

import java.util.List;
import java.util.Optional;

/**
 * @author mikeflorianczyk
 */
public interface RentalService {

    List<Rental> findAll();

    Optional<Rental> getRental(Long bookingId);

    void rentBook(Rental rental);

    void returnBook(Long bookingId);
}
