package com.mikeflorianczyk.book_rental.controller;

import com.mikeflorianczyk.book_rental.model.Rental;
import com.mikeflorianczyk.book_rental.model.Status;
import com.mikeflorianczyk.book_rental.service.BookService;
import com.mikeflorianczyk.book_rental.service.CustomerService;
import com.mikeflorianczyk.book_rental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
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
    private final CustomerService customerService;

    @GetMapping
    public List<Rental> findAll() {
        return rentalService.findAll();
    }

    @GetMapping("/{bookingId}")
    public Optional<Rental> getRental(@PathVariable Long bookingId) {
        return rentalService.getRental(bookingId);
    }

    //Checks if book is available for renting.
    //FIXME BigDecimal
    //TODO move to service layer
    @PostMapping
    public void rentBook(@Valid @RequestBody Rental rental) {
        Status status = bookService.getBook(rental.getBook().getISBN()).orElse(null).getStatus();
        BigDecimal account = customerService.getCustomer(rental.getCustomer().getId()).orElse(null).getAccount();
        //Split into 2 ifs.
        if (status == Status.AVAILABLE && account.compareTo(BigDecimal.ZERO) == 0) {
            rentalService.rentBook(rental);
            bookService.updateStatus(rental.getBook().getISBN(), Status.RENTED);
        }
    }

    //FIXME BigDecimal
    //TODO optimize + move to service layer
    @PutMapping("/{bookingId}")
    public void returnBook(@PathVariable Long bookingId) {
        rentalService.returnBook(bookingId);
        Rental rental = rentalService.getRental(bookingId).orElse(null);
        long daysOverdue = ChronoUnit.DAYS.between(rental.getPredictedReturnDate(), rental.getReturnDate());
        if (daysOverdue > 0) {
            rental.getCustomer().setAccount(new BigDecimal("2.0"));
            if (daysOverdue > 7) {
                //BigDecimal
                BigDecimal daysTimesMoney = BigDecimal.valueOf((daysOverdue - 7) * 0.5);
                //FIXME Add previous account.
                rental.getCustomer().setAccount(new BigDecimal("2.0").add(daysTimesMoney));
            }
            customerService.addCustomer(rental.getCustomer());
        }
        bookService.updateStatus(rental.getBook().getISBN(), Status.AVAILABLE);
    }
}
