package com.upb.agripos.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upb.agripos.exception.OutOfStockException;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;

public class CartService {
    private Map<String, CartItem> items = new HashMap<>();

    public void addToCart(Product product, int qty) throws Exception {
        // 1. Validasi Input Negatif
        if (qty <= 0) {
            throw new Exception("Jumlah harus lebih dari 0");
        }
        
        // 2. Validasi Stok Awal (INI YANG DITES DAN GAGAL)
        if (qty > product.getStock()) {
            throw new OutOfStockException("Stok tidak cukup! Tersedia: " + product.getStock());
        }
        
        String code = product.getCode();
        
        if (items.containsKey(code)) {
            CartItem existing = items.get(code);
            int newQty = existing.getQuantity() + qty;
            
            // 3. Validasi Stok Akumulasi (Jika user nambah lagi)
            if (newQty > product.getStock()) {
                throw new OutOfStockException("Stok tidak cukup! Tersedia: " + product.getStock());
            }
            
            existing.setQuantity(newQty);
        } else {
            items.put(code, new CartItem(product, qty));
        }
    }
    
    // ... method lainnya (removeFromCart, updateQuantity, dll) biarkan sama ...
    public void removeFromCart(String productCode) { items.remove(productCode); }
    public void updateQuantity(String productCode, int newQty) throws Exception {
        if (!items.containsKey(productCode)) throw new Exception("Produk tidak ada");
        if (newQty <= 0) removeFromCart(productCode);
        else {
            CartItem item = items.get(productCode);
            if (newQty > item.getProduct().getStock()) throw new OutOfStockException("Stok kurang");
            item.setQuantity(newQty);
        }
    }
    public List<CartItem> getCartItems() { return new ArrayList<>(items.values()); }
    public void clearCart() { items.clear(); }
    public BigDecimal getTotal() {
        return items.values().stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public int getTotalItems() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }
}