package com.mikeflorianczyk.book_rental.service;

import com.mikeflorianczyk.book_rental.model.Rental;
import com.mikeflorianczyk.book_rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author mikeflorianczyk
 */

@RequiredArgsConstructor
@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;

    @Override
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Override
    public Optional<Rental> getRental(Long bookingId) {
        return rentalRepository.findById(bookingId);
    }

    @Override
    public void rentBook(Rental rental) {
        rentalRepository.save(rental);
    }

    @Override
    public void returnBook(Long bookingId) {
        Rental rental = getRental(bookingId).orElse(null);
        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);
    }
}
