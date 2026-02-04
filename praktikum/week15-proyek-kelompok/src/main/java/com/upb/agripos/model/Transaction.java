package com.upb.agripos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String id;
    private LocalDateTime transactionDate;
    private User cashier;
    private PaymentMethod paymentMethod;
    private BigDecimal totalAmount;
    private String status;
    private List<TransactionItem> items;
    private String notes;

    public Transaction() {
        this.items = new ArrayList<>();
        this.status = "COMPLETED";
    }

    // METHOD INI YANG HILANG DAN MENYEBABKAN ERROR DI TEST
    public BigDecimal calculateTotal() {
        return items.stream()
            .map(TransactionItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public User getCashier() { return cashier; }
    public void setCashier(User cashier) { this.cashier = cashier; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<TransactionItem> getItems() { return items; }
    public void setItems(List<TransactionItem> items) { this.items = items; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}