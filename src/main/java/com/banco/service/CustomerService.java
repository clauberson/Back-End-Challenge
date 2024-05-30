package com.banco.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.exception.CustomException;
import com.banco.model.Customer;
import com.banco.repository.CustomerRepository;

@Service
public class CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
    	logger.debug("Creating customer with document: {}", customer.getDocument());
    	try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            logger.error("Error creating customer", e);
            throw new CustomException("Error creating customer: " + e.getMessage());
        }
    }

    // Outros metodos crud
}
