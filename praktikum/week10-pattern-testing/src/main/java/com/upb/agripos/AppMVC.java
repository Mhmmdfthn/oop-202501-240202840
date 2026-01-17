package main.java.com.upb.agripos;

import main.java.com.upb.agripos.config.DatabaseConnection;
import main.java.com.upb.agripos.controller.ProductController;
import main.java.com.upb.agripos.model.Product;
import main.java.com.upb.agripos.view.ConsoleView;

public class AppMVC {
    public static void main(String[] args) {
        // Ganti dengan Nama dan NIM asli kamu
        System.out.println("Praktikum Week 10 - [MUHAMMAD NUUR FATHAN] - [240202840]"); 
        System.out.println("----------------------------------------------");

        // 1. Test Singleton Pattern
        // Panggilan pertama (Object dibuat)
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        db1.connect();

        // Panggilan kedua (Menggunakan object yang sama, constructor tidak jalan lagi)
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        System.out.println("Apakah db1 sama dengan db2? " + (db1 == db2)); // Harus true
        System.out.println("----------------------------------------------");

        // 2. Test MVC Pattern
        Product product = new Product("P001", "Pupuk NPK Mutiara");
        ConsoleView view = new ConsoleView();
        ProductController controller = new ProductController(product, view);

        controller.updateView();
    }
}