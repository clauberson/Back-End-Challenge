package com.banco.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.banco.model.Account;
import com.banco.model.Customer;
import com.banco.repository.CustomerRepository;

@SpringBootTest
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testCreateAccount() {
        Customer customer = new Customer();
        customer.setName("Jane Doe");
        customer.setDocument("09876543210");
        customer.setAddress("456 Elm St");
        customer.setPassword("password");
        customerRepository.save(customer);

        Account account = new Account();
        account.setAgency("0001");
        account.setBalance(BigDecimal.valueOf(1000));
        account.setActive(true);

        Account createdAccount = accountService.createAccount(customer.getId(), account);
        assertNotNull(createdAccount);
        assertEquals("0001", createdAccount.getAgency());
    }

    @Test
    public void testTransfer() {
        // Setup customers and accounts, then perform a transfer and assert balances
    }
}
