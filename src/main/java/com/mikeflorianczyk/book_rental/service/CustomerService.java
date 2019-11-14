package com.mikeflorianczyk.book_rental.service;

import com.mikeflorianczyk.book_rental.model.Customer;

import java.util.List;
import java.util.Optional;

/**
 * @author mikeflorianczyk
 */
public interface CustomerService {

    List<Customer> findAll();

    Optional<Customer> getCustomer(Long id);

    void addCustomer(Customer customer);

    void deleteCustomer(Long id);

}
