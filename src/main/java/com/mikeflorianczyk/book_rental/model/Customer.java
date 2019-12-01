package com.mikeflorianczyk.book_rental.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author mikeflorianczyk
 */

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "First and last names are mandatory")
    @Column(nullable = false)
    private String firstName, lastName;
    @Column(nullable = false)
    //TODO BigDecimal
    private BigDecimal account = new BigDecimal("0.0");
    //@OneToMany(mappedBy = "customer")
    //private List<Rental> rentals;

    public Customer() {
    }

    public Customer(String firstName, String lastName, BigDecimal account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
    }

    public Customer(long id, String firstName, String lastName, BigDecimal account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
    }
}
