package com.mikeflorianczyk.book_rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author mikeflorianczyk
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book {

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
    @Size(max = 1000)
    private String description;

    public Book(Long id, String ISBN, String title, String author, String publisher, Status status) {
        this.id = id;
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
    }

    public Book(String ISBN, String title, String author, String publisher, Status status) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
    }
}
