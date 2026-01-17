package main.java.com.upb.agripos.model;

public class Product {
    private String code;
    private String name;
    private double price; // <--- INI WAJIB DOUBLE
    private int stock;

    // Perhatikan baris di bawah ini, price harus double!
    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    
    // Ini juga wajib double
    public double getPrice() { return price; } 
    public int getStock() { return stock; }

    public void setName(String name) { this.name = name; }
    
    // Ini juga wajib double
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
}
