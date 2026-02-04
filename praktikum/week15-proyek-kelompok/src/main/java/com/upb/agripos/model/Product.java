package com.upb.agripos.model;

import java.math.BigDecimal;

public class Product {
    private String code;
    private String name;
    private String category;
    private BigDecimal price;
    private int stock;

    public Product() {}

    // CONSTRUCTOR INI YANG HILANG DAN MENYEBABKAN ERROR DI TEST
    public Product(String code, String name, BigDecimal price, int stock) {
        this.code = code;
        this.name = name;
        this.category = "Umum"; // Default category
        this.price = price;
        this.stock = stock;
    }
    
    public Product(String code, String name, String category, BigDecimal price, int stock) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    // Getters
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public BigDecimal getPrice() { return price; }
    public int getStock() { return stock; }
    
    // Setters
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("%s - %s [%s] (Rp%,.0f, Stok: %d)", 
            code, name, category, price, stock);
    }
}