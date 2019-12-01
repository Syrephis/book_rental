package com.mikeflorianczyk.book_rental.service;

import com.mikeflorianczyk.book_rental.model.Rental;
import com.mikeflorianczyk.book_rental.model.Status;
import com.mikeflorianczyk.book_rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * @author mikeflorianczyk
 */
@RequiredArgsConstructor
@Service
public class RentalService {

    private static final BigDecimal BASECOST = new BigDecimal("2.0");
    private static final BigDecimal OVERDUEDAYCOST = new BigDecimal("2.0");
    private static final int DAYSLIMIT = 7;

    private final RentalRepository rentalRepository;

    private final BookService bookService;
    private final CustomerService customerService;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRental(Long bookingId) {
        return rentalRepository.findById(bookingId);
    }

    //FIXME can't call methods on null.
    public ResponseEntity<String> rentBook(Rental rental) {
        Status status = bookService.getBook(rental.getBook().getId()).orElse(null).getStatus();
        if (status == Status.AVAILABLE) {
            BigDecimal account = customerService.getCustomer(rental.getCustomer().getId()).orElse(null).getAccount();
            if (account.compareTo(BigDecimal.ZERO) == 0) {
                rentalRepository.save(rental);
                bookService.updateStatus(rental.getBook().getId(), Status.RENTED);
            } else
                return new ResponseEntity<>("User is in debt. Book can't be rented. Pay debt.", HttpStatus.PRECONDITION_FAILED);
        } else return new ResponseEntity<>("Book is not available for renting.", HttpStatus.PRECONDITION_FAILED);
        return new ResponseEntity<>("Book has been rented. Happy reading!", HttpStatus.OK);
    }

    //FIXME can't call methods on null.
    public ResponseEntity<String> returnBook(Long bookingId) {
        Rental rental = getRental(bookingId).orElse(null);
        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);
        int daysOverdue = (int) ChronoUnit.DAYS.between(rental.getPredictedReturnDate(), rental.getReturnDate());
        if (daysOverdue > 0) {
            BigDecimal cost = BASECOST.add(BigDecimal.valueOf((daysOverdue > DAYSLIMIT ? daysOverdue : DAYSLIMIT) - DAYSLIMIT)).multiply(OVERDUEDAYCOST);
            rental.getCustomer().setAccount(rental.getCustomer().getAccount().add(cost));
            customerService.addCustomer(rental.getCustomer());
        }
        bookService.updateStatus(rental.getBook().getId(), Status.AVAILABLE);
        String message = String.format("Book has been returned. You were %d days overdue.", daysOverdue > 0 ? daysOverdue : 0);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //TODO  Learn about tranzakcyjność :D
    //@Transactional

}
