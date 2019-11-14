package com.mikeflorianczyk.book_rental.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author mikeflorianczyk
 */

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(nullable = false)
    private Long ISBN;
    @NotBlank(message = "Book title, author name and publisher are mandatory.")
    @Column(nullable = false)
    private String bookTitle, author, publisher;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.UNAVAILABLE;

}
