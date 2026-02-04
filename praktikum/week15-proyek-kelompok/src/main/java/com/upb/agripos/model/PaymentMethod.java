package com.upb.agripos.model;
import java.math.BigDecimal;

public interface PaymentMethod {
    boolean processPayment(BigDecimal amount) throws Exception;
    String getMethodName();
}