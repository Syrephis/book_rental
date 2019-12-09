package com.mikeflorianczyk.book_rental.controller;

import com.mikeflorianczyk.book_rental.model.Customer;
import com.mikeflorianczyk.book_rental.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author mikeflorianczyk
 */

@CrossOrigin
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomer(id);
        if (customer.isPresent()) return new ResponseEntity<>(customer, HttpStatus.OK);
        String message = String.format("Book with ID: %d couldn't be found.", id);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    @PostMapping
    public void addCustomer(@Valid @RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

}
