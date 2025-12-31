package main.java.com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartMap {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product p) { 
        // getOrDefault mengambil qty lama, jika belum ada set 0, lalu tambah 1
        items.put(p, items.getOrDefault(p, 0) + 1); 
    }

    public void removeProduct(Product p) {
        if (!items.containsKey(p)) return;
        
        int qty = items.get(p);
        if (qty > 1) {
            items.put(p, qty - 1);
        } else {
            items.remove(p);
        }
    }

    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void printCart() {
        System.out.println("--- Isi Keranjang (HashMap/Quantity) ---");
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            Product p = e.getKey();
            int qty = e.getValue();
            System.out.println("- " + p.getName() + " [" + p.getCode() + "] x" + qty + " = Rp" + (p.getPrice() * qty));
        }
        System.out.println("TOTAL BAYAR: Rp" + getTotal());
        System.out.println("----------------------------------------");
    }
}