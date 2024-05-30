package com.banco.controller;

import java.awt.print.Book;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.model.Account;
import com.banco.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@Operation(summary = "Create account")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Account created with sucess", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Account.class)) }),
			@ApiResponse(responseCode = "400", description = "Error creating account", content = @Content),
			@ApiResponse(responseCode = "404", description = "Customer not found", content = @Content) })
	@PostMapping("/{customerId}")
	public ResponseEntity<Account> createAccount(@Parameter(description = "id of customer") @PathVariable Long customerId, 
			@Parameter(description = "account object") @RequestBody Account account) {
		logger.debug("Received request to create account for customer ID: {}", customerId);
		Account createdAccount = accountService.createAccount(customerId, account);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
	}

	@Operation(summary = "Create transfer")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Transfer created with sucess", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Account.class)) }),
			@ApiResponse(responseCode = "400", description = "Error creating transfer", content = @Content)})
	@PostMapping("/transfer")
	public ResponseEntity<Void> transfer(@RequestParam Long fromAccountId, 
			@Parameter(description = "Target account") @RequestParam Long toAccountId,
			@Parameter(description = "Origin account") @RequestParam BigDecimal amount) {
		logger.debug("Received transfer request from account {} to account {} with amount {}", fromAccountId,
				toAccountId, amount);
		accountService.transfer(fromAccountId, toAccountId, amount);
		return ResponseEntity.ok().build();
	}

	// Outros endponits para as operaçoes CRUD
}
