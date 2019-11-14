package com.mikeflorianczyk.book_rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookRentalApplication.class, args);
	}

}
