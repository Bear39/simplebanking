package com.simplebanking.service;

import com.simplebanking.domain.Account;

import java.security.Principal;

public interface AccountService {
    Account createAccount();
    void deposit(double amount, Principal principal);
    void withdraw(double amount, Principal principal);
}