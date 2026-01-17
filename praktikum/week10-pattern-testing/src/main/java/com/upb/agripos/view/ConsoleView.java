package main.java.com.upb.agripos.view;

public class ConsoleView {
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void displayProductDetails(String code, String name) {
        System.out.println("=== DETAIL PRODUK ===");
        System.out.println("Kode Produk : " + code);
        System.out.println("Nama Produk : " + name);
        System.out.println("=====================");
    }
}