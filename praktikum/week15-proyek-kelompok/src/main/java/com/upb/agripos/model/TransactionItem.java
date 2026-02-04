package com.upb.agripos.model;
import java.math.BigDecimal;

public class TransactionItem {
    private String productCode;
    private String productName;
    private int quantity;
    private BigDecimal priceAtSale;
    private BigDecimal subtotal;

    public TransactionItem() {}
    public TransactionItem(Product p, int qty) {
        this.productCode = p.getCode();
        this.productName = p.getName();
        this.quantity = qty;
        this.priceAtSale = p.getPrice();
        this.subtotal = p.getPrice().multiply(new BigDecimal(qty));
    }
    // Getters
    public String getProductCode() { return productCode; }
    public void setProductCode(String c) { this.productCode = c; }
    public String getProductName() { return productName; }
    public void setProductName(String n) { this.productName = n; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int q) { this.quantity = q; }
    public BigDecimal getPriceAtSale() { return priceAtSale; }
    public void setPriceAtSale(BigDecimal p) { this.priceAtSale = p; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal s) { this.subtotal = s; }
}