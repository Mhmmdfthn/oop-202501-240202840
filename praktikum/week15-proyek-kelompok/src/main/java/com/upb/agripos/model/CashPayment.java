package com.upb.agripos.model;
import java.math.BigDecimal;

public class CashPayment implements PaymentMethod {
    private BigDecimal amountPaid;
    public CashPayment(BigDecimal amountPaid) { this.amountPaid = amountPaid; }

    @Override
    public boolean processPayment(BigDecimal amount) throws Exception {
        if (amountPaid.compareTo(amount) < 0) throw new Exception("Uang kurang! Total: " + amount + ", Bayar: " + amountPaid);
        return true;
    }
    @Override public String getMethodName() { return "CASH"; }
}