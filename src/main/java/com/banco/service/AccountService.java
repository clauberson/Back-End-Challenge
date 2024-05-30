package com.banco.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.banco.exception.CustomException;
import com.banco.model.Account;
import com.banco.model.Customer;
import com.banco.repository.AccountRepository;
import com.banco.repository.CustomerRepository;


@Service
public class AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Account createAccount(Long customerId, Account account) {
        logger.debug("Creating account for customer ID: {}", customerId);
        try {
            Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException("Customer not found"));
            account.setCustomer(customer);
            return accountRepository.save(account);
        } catch (Exception e) {
            logger.error("Error creating account", e);
            throw new CustomException("Error creating account: " + e.getMessage());
        }
    }

    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        logger.debug("Transferring amount {} from account {} to account {}", amount, fromAccountId, toAccountId);
        try {
            Account fromAccount = accountRepository.findByIdAndActive(fromAccountId, true)
                .orElseThrow(() -> new CustomException("From account not found or inactive"));
            Account toAccount = accountRepository.findByIdAndActive(toAccountId, true)
                .orElseThrow(() -> new CustomException("To account not found or inactive"));

            if (fromAccount.getBalance().compareTo(amount) < 0) {
                throw new CustomException("Insufficient balance");
            }

            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            sendNotification(fromAccount.getCustomer().getDocument());
            sendNotification(toAccount.getCustomer().getDocument());
        } catch (Exception e) {
            logger.error("Error during transfer", e);
            throw new CustomException("Error during transfer: " + e.getMessage());
        }
    }

    
    private void sendNotification(String document) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation("https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3", document);
        } catch (Exception e) {
            logger.warn("Notification service unavailable for document: {}", document);
            new CustomException("Error send notification");
        }
    }

    // Outros metodos crud
}
