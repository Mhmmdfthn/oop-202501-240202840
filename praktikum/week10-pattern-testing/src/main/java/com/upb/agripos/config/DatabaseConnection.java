package main.java.com.upb.agripos.config;

public class DatabaseConnection {
    // 1. Static variable untuk menyimpan satu-satunya instance
    private static DatabaseConnection instance;

    // 2. Constructor private agar tidak bisa di-new dari luar
    private DatabaseConnection() {
        System.out.println("Koneksi Database Berhasil Dibuat (Instance Created).");
    }

    // 3. Static method untuk mengambil instance (Lazy Initialization)
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Contoh method fungsionalitas
    public void connect() {
        System.out.println("Database terhubung ke Server AgriPOS...");
    }
}
