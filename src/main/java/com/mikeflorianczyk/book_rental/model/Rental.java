package com.mikeflorianczyk.book_rental.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author mikeflorianczyk
 */

@Data
@Entity
@Table(name = "rentals")
@EntityListeners(AuditingEntityListener.class)
public class Rental {

    //TODO check persistence relations.
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long bookingId;
    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Book book;
    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Customer customer;
    private LocalDate issueDate = LocalDate.now();
    private LocalDate returnDate, predictedReturnDate = LocalDate.now().plusMonths(2);

    public Rental() {}

    public Rental(Book book, Customer customer) {
        this.book = book;
        this.customer = customer;
    }

}
