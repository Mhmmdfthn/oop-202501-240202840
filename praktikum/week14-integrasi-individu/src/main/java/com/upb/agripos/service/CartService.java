package com.upb.agripos.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;

public class CartService {
    private Map<String, CartItem> items = new HashMap<>();

    public void addToCart(Product p, int qty) throws InvalidQuantityException, InsufficientStockException {
        if (qty <= 0) throw new InvalidQuantityException("Kuantitas harus lebih dari 0!");
        if (p.getStock() < qty) throw new InsufficientStockException("Stok tidak mencukupi!");
        
        if (items.containsKey(p.getCode())) {
            int newQty = items.get(p.getCode()).getQuantity() + qty;
            items.put(p.getCode(), new CartItem(p, newQty));
        } else {
            items.put(p.getCode(), new CartItem(p, qty));
        }
    }

    public void removeItem(String code) { items.remove(code); }
    public void clear() { items.clear(); }
    public List<CartItem> getCartItems() { return new ArrayList<>(items.values()); }
    public double getTotal() { return items.values().stream().mapToDouble(CartItem::getSubtotal).sum(); }
}

// Custom Exceptions Week 9
class InvalidQuantityException extends Exception { public InvalidQuantityException(String m) { super(m); } }
class InsufficientStockException extends Exception { public InsufficientStockException(String m) { super(m); } }