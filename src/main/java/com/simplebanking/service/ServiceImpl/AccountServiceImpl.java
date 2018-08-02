package com.simplebanking.service.ServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplebanking.dao.AccountDao;
import com.simplebanking.domain.Account;
import com.simplebanking.domain.Transaction;
import com.simplebanking.domain.User;
import com.simplebanking.service.AccountService;
import com.simplebanking.service.UserService;
import com.simplebanking.service.TransactionService;

@Service
public class AccountServiceImpl implements AccountService {

    private static int nextAccountNumber = 0;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;


    public Account createAccount() {
        Account account = new Account();
        account.setAccountBalance(new BigDecimal(0.0));
        account.setAccountNumber(accountGen()+account.getAccountNumber());

        accountDao.save(account);

        return accountDao.findByAccountNumber(account.getAccountNumber());
    }

    public void deposit(double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        Account account = user.getAccount();
        account.setAccountBalance(account.getAccountBalance().add(new BigDecimal(amount)));
        accountDao.save(account);

        Date date = new Date();

        Transaction transaction = new Transaction(date, "Deposit to Account", "Deposit", "Finished", amount, account.getAccountBalance(), account);
        transactionService.saveDepositTransaction(transaction);
    }

    public void withdraw(double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        Account account = user.getAccount();
        account.setAccountBalance(account.getAccountBalance().subtract(new BigDecimal(amount)));
        accountDao.save(account);

        Date date = new Date();

        Transaction transaction = new Transaction(date, "Withdraw from Account", "Withdraw", "Finished", amount, account.getAccountBalance(), account);
        transactionService.saveWithdrawTransaction(transaction);
    }

    private int accountGen() {
        return ++nextAccountNumber;
    }

}
