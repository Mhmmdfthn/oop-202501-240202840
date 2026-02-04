package com.upb.agripos.model;
import java.math.BigDecimal;

public class EWalletPayment implements PaymentMethod {
    private String provider;
    private String number;
    
    public EWalletPayment(String provider, String number) {
        this.provider = provider;
        this.number = number;
    }

    @Override
    public boolean processPayment(BigDecimal amount) throws Exception {
        // Mock payment gateway logic
        if(number.isEmpty()) throw new Exception("Nomor E-Wallet kosong");
        return true;
    }
    @Override public String getMethodName() { return "EWALLET"; }
}