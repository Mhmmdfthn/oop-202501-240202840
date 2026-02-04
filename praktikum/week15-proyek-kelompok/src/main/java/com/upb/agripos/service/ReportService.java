package com.upb.agripos.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.upb.agripos.dao.TransactionDAO;
import com.upb.agripos.model.Transaction;

public class ReportService {
    private TransactionDAO dao;
    public ReportService(TransactionDAO dao) { this.dao = dao; }
    
    public List<Transaction> getDailySales(LocalDate date) throws Exception {
        return dao.findByDate(date);
    }
    public BigDecimal calculateTotalRevenue(List<Transaction> transactions) {
        return transactions.stream().map(Transaction::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}