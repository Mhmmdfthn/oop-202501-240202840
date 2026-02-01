package com.upb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;

public class MainDAOTest {
    public static void main(String[] args) {
        // Konfigurasi koneksi database
        String url = "jdbc:postgresql://localhost:5432/agripos";
        String user = "postgres";
        String pass = "admin123"; 

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Koneksi Berhasil!\n");

            ProductDAO dao = new ProductDAOImpl(conn);

            // 1. INSERT (Create)
            System.out.println(">> Menambahkan data P01 dan P02...");
            dao.insert(new Product("P01", "Pupuk Organik", 25000, 10));
            dao.insert(new Product("P02", "Bibit Padi Unggul", 15000, 50));

            // 2. FIND ALL (Read)
            showData(dao);

            // 3. UPDATE (Update)
            System.out.println("\n>> Mengupdate harga P01...");
            Product p01 = dao.findByCode("P01");
            if (p01 != null) {
                p01.setName("Pupuk Organik Premium");
                p01.setPrice(30000);
                dao.update(p01);
            }

            // 4. FIND ONE (Read)
            Product updatedProduct = dao.findByCode("P01");
            System.out.println("Data P01 Sekarang: " + updatedProduct.getName() + " | Harga: " + updatedProduct.getPrice());

            // 5. DELETE (Delete)
            System.out.println("\n>> Menghapus data P02...");
            dao.delete("P02");

            // Tampilkan hasil akhir
            showData(dao);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method untuk menampilkan data ke konsol
    private static void showData(ProductDAO dao) throws Exception {
        List<Product> products = dao.findAll();
        System.out.println("--- Daftar Produk di Database ---");
        for (Product p : products) {
            System.out.printf("[%s] %-25s | Rp%,.2f | Stok: %d\n", 
                p.getCode(), p.getName(), p.getPrice(), p.getStock());
        }
        System.out.println("Total: " + products.size() + " produk.");
    }
}