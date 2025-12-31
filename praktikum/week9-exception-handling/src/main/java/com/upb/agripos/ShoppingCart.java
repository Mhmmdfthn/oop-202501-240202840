package main.java.com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product p, int qty) throws InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Gagal Tambah: Quantity harus lebih dari 0.");
        }
        items.put(p, items.getOrDefault(p, 0) + qty);
        System.out.println("Berhasil menambahkan " + qty + " " + p.getName() + " ke keranjang.");
    }

    public void removeProduct(Product p) throws ProductNotFoundException {
        if (!items.containsKey(p)) {
            throw new ProductNotFoundException("Gagal Hapus: Produk '" + p.getName() + "' tidak ada dalam keranjang.");
        }
        items.remove(p);
        System.out.println("Berhasil menghapus " + p.getName() + " dari keranjang.");
    }

    public void checkout() throws InsufficientStockException {
        System.out.println("--- Memulai Proses Checkout ---");
        // Validasi ketersediaan stok untuk semua barang
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int qtyInCart = entry.getValue();
            
            if (product.getStock() < qtyInCart) {
                throw new InsufficientStockException(
                    "Gagal Checkout: Stok " + product.getName() + 
                    " tidak cukup (Tersedia: " + product.getStock() + ", Diminta: " + qtyInCart + ")"
                );
            }
        }

        // Jika semua stok cukup, baru kurangi stok asli
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().reduceStock(entry.getValue());
        }
        
        items.clear();
        System.out.println("Checkout Berhasil! Stok produk telah diperbarui.");
    }
}