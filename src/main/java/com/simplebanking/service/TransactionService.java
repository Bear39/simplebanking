package com.simplebanking.service;

import com.simplebanking.domain.Account;
import com.simplebanking.domain.Receiver;
import com.simplebanking.domain.Transaction;

import java.security.Principal;
import java.util.List;

public interface TransactionService {
    List<Transaction> findTransactionList(String username);

    void saveDepositTransaction(Transaction transaction);

    void saveWithdrawTransaction(Transaction transaction);

    List<Receiver> findReceiverList(Principal principal);

    Receiver saveReceiver(Receiver receiver);

    Receiver findReceiverByName(String receiverName);

    void deleteReceiverByName(String receiverName);

    void toOtherAccountTransfer(String amount, Account userAccount, String username, String receiverName, Account receiverAccount);
}