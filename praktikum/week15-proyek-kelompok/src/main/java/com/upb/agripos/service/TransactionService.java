package com.upb.agripos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.dao.TransactionDAO;
import com.upb.agripos.exception.OutOfStockException;
import com.upb.agripos.exception.PaymentFailedException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.PaymentMethod;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.TransactionItem;
import com.upb.agripos.model.User;

public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final ProductService productService;

    public TransactionService(TransactionDAO transactionDAO, ProductService productService) {
        this.transactionDAO = transactionDAO;
        this.productService = productService;
    }

    public Transaction processTransaction(List<CartItem> cartItems, PaymentMethod paymentMethod, User cashier) throws Exception {
        if (cartItems.isEmpty()) throw new ValidationException("Keranjang kosong!");

        BigDecimal total = BigDecimal.ZERO;
        List<TransactionItem> trxItems = new ArrayList<>();
        
        // 1. Validasi & Hitung
        for (CartItem c : cartItems) {
            if (!productService.validateStock(c.getProduct().getCode(), c.getQuantity())) {
                throw new OutOfStockException("Stok kurang: " + c.getProduct().getName());
            }
            TransactionItem ti = new TransactionItem(c.getProduct(), c.getQuantity());
            trxItems.add(ti);
            total = total.add(ti.getSubtotal());
        }

        // 2. Bayar
        if (!paymentMethod.processPayment(total)) {
            throw new PaymentFailedException("Pembayaran gagal");
        }

        // 3. Create Object
        Transaction trx = new Transaction();
        trx.setId("TRX-" + System.currentTimeMillis());
        trx.setTransactionDate(LocalDateTime.now());
        trx.setCashier(cashier);
        trx.setPaymentMethod(paymentMethod);
        trx.setTotalAmount(total);
        trx.setItems(trxItems);
        trx.setStatus("COMPLETED");

        // 4. Save DB & Kurangi Stok
        transactionDAO.save(trx);
        for (CartItem c : cartItems) {
            productService.reduceStock(c.getProduct().getCode(), c.getQuantity());
        }
        return trx;
    }
}