package com.upb.agripos.dao;

import java.time.LocalDate;
import java.util.List;

import com.upb.agripos.model.Transaction;

public interface TransactionDAO {
    void save(Transaction transaction) throws Exception;
    Transaction findById(String id) throws Exception;
    List<Transaction> findByDate(LocalDate date) throws Exception;
    List<Transaction> findByPeriod(LocalDate startDate, LocalDate endDate) throws Exception;
}