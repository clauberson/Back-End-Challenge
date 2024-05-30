package com.banco.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.banco.model.Customer;

@SpringBootTest
public class CustomerServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);
	
    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
    	logger.debug("Testing createCustomer method");
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setDocument("12345678901");
        customer.setAddress("123 Main St");
        customer.setPassword("password");

        Customer createdCustomer = customerService.createCustomer(customer);
        assertNotNull(createdCustomer);
        assertEquals("John Doe", createdCustomer.getName());
    }
}
