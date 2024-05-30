package com.banco.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.banco.model.Account;
import com.banco.model.Customer;

import com.banco.repository.*;


@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Account createAccount(Long customerId, Account account) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomException("Customer not found"));
        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
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

        // Mandar notificações
        sendNotification(fromAccount.getCustomer().getDocument());
        sendNotification(toAccount.getCustomer().getDocument());
    }

    private void sendNotification(String document) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation("https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3", document);
        } catch (Exception e) {
            // logs
        }
    }

    // Outros metodos crud
}
