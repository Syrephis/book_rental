package com.mikeflorianczyk.book_rental.controller;

import com.mikeflorianczyk.book_rental.model.Rental;
import com.mikeflorianczyk.book_rental.service.BookService;
import com.mikeflorianczyk.book_rental.service.CustomerService;
import com.mikeflorianczyk.book_rental.service.RentalService;
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
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    public List<Rental> findAll() {
        return rentalService.findAll();
    }

    @GetMapping("/{bookingId}")
    public Optional<Rental> getRental(@PathVariable Long bookingId) {
        return rentalService.getRental(bookingId);
    }

    @PostMapping
    public ResponseEntity<String> rentBook(@Valid @RequestBody Rental rental) {
        return rentalService.rentBook(rental);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookingId) {
        return rentalService.returnBook(bookingId);
    }
}
