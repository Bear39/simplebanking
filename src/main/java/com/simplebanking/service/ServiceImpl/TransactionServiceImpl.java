package com.simplebanking.service.ServiceImpl;

import com.simplebanking.dao.AccountDao;
import com.simplebanking.dao.ReceiverDao;
import com.simplebanking.dao.TransactionDao;
import com.simplebanking.domain.Account;
import com.simplebanking.domain.Receiver;
import com.simplebanking.domain.Transaction;
import com.simplebanking.domain.User;
import com.simplebanking.service.TransactionService;
import com.simplebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ReceiverDao receiverDao;

    public List<Transaction> findTransactionList(String username) {
        User user = userService.findByUsername(username);
        List<Transaction> transactionList = user.getAccount().getTransactionList();

        return transactionList;
    }

    public void saveDepositTransaction(Transaction transaction) {
        transactionDao.save(transaction);
    }

    public void saveWithdrawTransaction(Transaction transaction) {
        transactionDao.save(transaction);
    }

    public List<Receiver> findReceiverList(Principal principal) {
        String username = principal.getName();
        List<Receiver> receiverList = receiverDao.findAll().stream() 			//convert list to stream
                .filter(receiver -> username.equals(receiver.getUser().getUsername()))	//filters the line, equals to username
                .collect(Collectors.toList());

        return receiverList;
    }

    public Receiver saveReceiver(Receiver receiver) {
        return receiverDao.save(receiver);
    }

    public Receiver findReceiverByName(String receiverName) {
        return receiverDao.findByName(receiverName);
    }

    public void deleteReceiverByName(String receiverName) {
        receiverDao.deleteByName(receiverName);
    }

    public void toOtherAccountTransfer(String amount, Account userAccount,String username, String receiverName, Account receiverAccount) {
        userAccount.setAccountBalance(userAccount.getAccountBalance().subtract(new BigDecimal(amount)));
        receiverAccount.setAccountBalance(receiverAccount.getAccountBalance().add(new BigDecimal(amount)));

        accountDao.save(userAccount);
        accountDao.save(receiverAccount);

        Date date = new Date();

        Transaction transaction = new Transaction(date, "Transfer to "+receiverName, "Transfer", "Finished", Double.parseDouble(amount), userAccount.getAccountBalance(), userAccount);
        Transaction receiverTransaction = new Transaction(date, "Transfer from "+username, "Transfer", "Finished", Double.parseDouble(amount), receiverAccount.getAccountBalance(), receiverAccount);
        transactionDao.save(transaction);
        transactionDao.save(receiverTransaction);
    }
}
