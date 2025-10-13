package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.Produk;
import main.java.com.upb.agripos.util.CreditBy;

public class MainProduk {
    public static void main(String[] args) {
        Produk p1 = new Produk("BNH-001", "Benih Padi IR64", 25000, 100);
        Produk p2 = new Produk("PPK-101", "Pupuk Urea 50kg", 350000, 40);
        Produk p3 = new Produk("ALT-501", "Cangkul Baja", 90000, 15);

        // 🔹 tampilkan semua info produk
        p1.tampilkanInfo();
        p2.tampilkanInfo();
        p3.tampilkanInfo();

        // Simulasi transaksi stok
        System.out.println("\n=== Simulasi Transaksi ===");
        // Kurangi stok benih
        p1.kurangiStok(6);
        // Tambah stok cangkul
        p3.tambahStok(8);
        // Tambah stok pupuk
        p2.tambahStok(10);
        // Kurangi stok pupuk
        p2.kurangiStok(3);

        // 🔹 tampilkan credit
        CreditBy.print("240202840", "Muhammad Nur Fathan");
    }
}

