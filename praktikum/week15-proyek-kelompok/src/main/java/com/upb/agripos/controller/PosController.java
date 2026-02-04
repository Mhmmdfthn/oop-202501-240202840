package com.upb.agripos.controller;

import java.math.BigDecimal;
import java.util.List;

import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.PaymentMethod;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.Receipt;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.User;
import com.upb.agripos.service.AuthService;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.ReportService;
import com.upb.agripos.service.TransactionService;

public class PosController {
    private final ProductService productService;
    private final CartService cartService;
    private final TransactionService transactionService;
    private final AuthService authService;
    private final ReportService reportService;

    public PosController(ProductService ps, CartService cs, TransactionService ts, AuthService as, ReportService rs) {
        this.productService = ps;
        this.cartService = cs;
        this.transactionService = ts;
        this.authService = as;
        this.reportService = rs;
    }

    // --- FITUR KASIR (KERANJANG & TRANSAKSI) ---
    public void addToCart(Product p, int qty) throws Exception { cartService.addToCart(p, qty); }
    public List<CartItem> getCartItems() { return cartService.getCartItems(); }
    public BigDecimal getCartTotal() { return cartService.getTotal(); }
    public void clearCart() { cartService.clearCart(); }
    public void updateCartQty(String code, int qty) throws Exception { cartService.updateQuantity(code, qty); }
    public void removeFromCart(String code) { cartService.removeFromCart(code); }

    public Transaction checkout(PaymentMethod method) throws Exception {
        User kasir = authService.getCurrentUser();
        Transaction trx = transactionService.processTransaction(cartService.getCartItems(), method, kasir);
        cartService.clearCart(); // Auto clear jika sukses
        return trx;
    }
    
    public Receipt generateReceipt(Transaction t) { return new Receipt(t); }

    // --- FITUR ADMIN (PRODUK) ---
    public List<Product> getAllProducts() throws Exception { return productService.getAllProducts(); }
    
    public void saveProduct(String code, String name, String cat, BigDecimal price, int stock, boolean isUpdate) throws Exception {
        Product p = new Product(code, name, cat, price, stock);
        if (isUpdate) productService.updateProduct(p);
        else productService.addProduct(p);
    }
    
    public void deleteProduct(String code) throws Exception { productService.deleteProduct(code); }

    // --- HELPER ---
    public User getCurrentUser() { return authService.getCurrentUser(); }
    public boolean isAdmin() { 
        return getCurrentUser() != null && "ADMIN".equals(getCurrentUser().getRole().toString()); 
    }
}