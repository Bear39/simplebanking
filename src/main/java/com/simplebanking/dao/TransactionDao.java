package com.simplebanking.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.simplebanking.domain.Transaction;

public interface TransactionDao extends CrudRepository<Transaction, Long> {

    List<Transaction> findAll();
}
