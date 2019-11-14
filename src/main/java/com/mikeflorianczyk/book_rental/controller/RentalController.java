package com.mikeflorianczyk.book_rental.controller;

import com.mikeflorianczyk.book_rental.model.Rental;
import com.mikeflorianczyk.book_rental.model.Status;
import com.mikeflorianczyk.book_rental.service.BookService;
import com.mikeflorianczyk.book_rental.service.RentalService;
import lombok.RequiredArgsConstructor;
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
    private final BookService bookService;

    @GetMapping
    public List<Rental> findAll() {
        return rentalService.findAll();
    }

    @GetMapping("/{bookingId}")
    public Optional<Rental> getRental(@PathVariable Long bookingId) {
        return rentalService.getRental(bookingId);
    }

    //Checks if book is available for renting.
    @PostMapping
    public void rentBook(@Valid @RequestBody Rental rental) {
        if (rental.getBook().getStatus() == Status.AVAILABLE) {
            rentalService.rentBook(rental);
            bookService.updateStatus(rental.getBook().getISBN(), Status.RENTED);
        }
    }
}
