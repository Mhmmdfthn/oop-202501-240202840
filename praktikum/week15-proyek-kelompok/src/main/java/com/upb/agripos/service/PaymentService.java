package com.upb.agripos.service;

import java.math.BigDecimal;

import com.upb.agripos.model.PaymentMethod;
import com.upb.agripos.model.Receipt;
import com.upb.agripos.model.Transaction;

public class PaymentService {
    public boolean processPayment(BigDecimal amount, PaymentMethod method) throws Exception {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Jumlah pembayaran tidak valid");
        }
        return method.processPayment(amount);
    }
    
    public Receipt generateReceipt(Transaction transaction) {
        return new Receipt(transaction);
    }
}