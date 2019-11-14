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

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long bookingId;
    @OneToOne//(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "isbn", nullable = false)
    private Book book;
    @OneToOne//(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Customer customer;
    //@CreatedDate JPAAuditing must be enabled.
    private LocalDate issueDate = LocalDate.now();
    private LocalDate returnDate, predictedReturnDate = LocalDate.now().plusMonths(2);

}
