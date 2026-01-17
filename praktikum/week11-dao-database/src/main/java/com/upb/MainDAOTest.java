package main.java.com.upb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import main.java.com.upb.agripos.dao.ProductDAO;
import main.java.com.upb.agripos.dao.ProductDAOImpl;
import main.java.com.upb.agripos.model.Product;

public class MainDAOTest {
    public static void main(String[] args) {
        // Ganti parameter DB_URL, USER, PASS sesuai setting lokalmu
        String DB_URL = "jdbc:postgresql://localhost:5432/agripos";
        String USER = "postgres"; 
        String PASS = "admin123"; 

        try {
            // 1. Buat Koneksi
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Koneksi ke database berhasil!");

            // 2. Inisialisasi DAO
            ProductDAO dao = new ProductDAOImpl(conn);

            // --- TEST INSERT ---
            System.out.println("\n[1] Menambahkan Produk Baru...");
            Product p1 = new Product("P01", "Pupuk Organik", 25000.0, 50);
            Product p2 = new Product("P02", "Benih Jagung", 15000.0, 100);
            dao.insert(p1);
            dao.insert(p2);
            System.out.println("Produk P01 dan P02 berhasil ditambahkan.");

            // --- TEST READ (Find All) ---
            System.out.println("\n[2] Menampilkan Semua Produk:");
            List<Product> allProducts = dao.findAll();
            for (Product p : allProducts) {
                System.out.println("- " + p.getCode() + " | " + p.getName() + " | Rp" + p.getPrice());
            }

            // --- TEST UPDATE ---
            System.out.println("\n[3] Mengupdate Stok P01...");
            Product pUpdate = dao.findByCode("P01");
            if(pUpdate != null) {
                pUpdate.setName("Pupuk Organik Super");
                pUpdate.setPrice(28000);
                dao.update(pUpdate);
                System.out.println("Update berhasil.");
            }

            // --- TEST READ (Find One) ---
            System.out.println("\n[4] Cek Hasil Update P01:");
            Product pCheck = dao.findByCode("P01");
            if(pCheck != null){
                System.out.println("Data: " + pCheck.getName() + " - Harga: " + pCheck.getPrice());
            }

            // --- TEST DELETE ---
            System.out.println("\n[5] Menghapus P02...");
            dao.delete("P02");
            System.out.println("P02 dihapus.");

            // Tutup koneksi
            conn.close();
            System.out.println("\nKoneksi ditutup. Program selesai.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}