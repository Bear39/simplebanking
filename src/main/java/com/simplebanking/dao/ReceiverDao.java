package com.simplebanking.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.simplebanking.domain.Receiver;

public interface ReceiverDao extends CrudRepository<Receiver, Long> {
    List<Receiver> findAll();

    Receiver findByName(String receiverName);

    Receiver findByAccountNumber(String accountNumber);

    void deleteByName(String receiverName);

    void deleteByAccountNumber(String accountNumber);
}
