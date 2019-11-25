package com.mikeflorianczyk.book_rental.model;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author mikeflorianczyk
 */

@Data
@Entity
@Table(name = "books")
public class Book implements Cloneable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NaturalId(mutable = true)
    @Column(nullable = false)
    private String ISBN;
    @NotBlank(message = "Book title, author name and publisher are mandatory.")
    @Column(nullable = false)
    private String title, author, publisher;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.UNAVAILABLE;

}
