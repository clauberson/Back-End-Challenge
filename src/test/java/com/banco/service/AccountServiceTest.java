package com.banco.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.banco.model.Account;
import com.banco.model.Customer;
import com.banco.repository.CustomerRepository;

@SpringBootTest
public class AccountServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);
	
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testCreateAccount() {
    	logger.debug("Testing createAccount method");
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
        logger.debug("Testing transfer method");

        // Criando o peimeiro cliente e conta
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setDocument("11111111111");
        customer1.setAddress("123 Main St");
        customer1.setPassword("password");
        customerRepository.save(customer1);

        Account account1 = new Account();
        account1.setAgency("0001");
        account1.setBalance(BigDecimal.valueOf(1000));
        account1.setActive(true);
        accountService.createAccount(customer1.getId(), account1);

        // Criando o segundo cliente e conta
        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setDocument("22222222222");
        customer2.setAddress("456 Elm St");
        customer2.setPassword("password");
        customerRepository.save(customer2);

        Account account2 = new Account();
        account2.setAgency("0002");
        account2.setBalance(BigDecimal.valueOf(500));
        account2.setActive(true);
        accountService.createAccount(customer2.getId(), account2);

        // Realizar transferência
        accountService.transfer(account1.getId(), account2.getId(), BigDecimal.valueOf(200));

        // Recuperar contas atualizadas
        Account updatedAccount1 = accountRepository.findById(account1.getId()).orElseThrow();
        Account updatedAccount2 = accountRepository.findById(account2.getId()).orElseThrow();

        // Verifique os saldos após a transferência
        assertEquals(BigDecimal.valueOf(800), updatedAccount1.getBalance());
        assertEquals(BigDecimal.valueOf(700), updatedAccount2.getBalance());
    }
}
