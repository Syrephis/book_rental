package com.mikeflorianczyk.book_rental.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Calendar;

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
    @CreatedDate
    private Calendar issueDate, predictedReturnDate;
    private Calendar returnDate;

}
