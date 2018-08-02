package com.simplebanking.dao;

import com.simplebanking.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountDao extends CrudRepository<Account,Long> {

    Account findByAccountNumber (int accountNumber);

}
