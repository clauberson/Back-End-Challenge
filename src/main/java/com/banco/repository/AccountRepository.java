package com.banco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.model.Account;

interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByIdAndActive(Long id, Boolean active);
}
