package main.java.com.upb.agripos;

public class MainCart {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("Nama : Muhammad Nuur Fathan");
        System.out.println("NIM  : 240202840");
        System.out.println("Tugas: Week 7 - Java Collections");
        System.out.println("==========================================\n");

        Product p1 = new Product("P01", "Beras Rojolele 5kg", 75000);
        Product p2 = new Product("P02", "Pupuk NPK 1kg", 45000);
        Product p3 = new Product("P03", "Bibit Tomat", 15000);

        System.out.println("MENGGUNAKAN ARRAYLIST:");
        ShoppingCart cartList = new ShoppingCart();
        cartList.addProduct(p1);
        cartList.addProduct(p2);
        cartList.addProduct(p1); // Beras ditambah lagi (muncul 2 baris)
        cartList.printCart();

        System.out.println("\nMENGGUNAKAN MAP (DENGAN QUANTITY):");
        ShoppingCartMap cartMap = new ShoppingCartMap();
        cartMap.addProduct(p1);
        cartMap.addProduct(p1); // Beras x2
        cartMap.addProduct(p2); // Pupuk x1
        cartMap.addProduct(p3); // Bibit x1
        cartMap.printCart();

        // Contoh Hapus Produk
        System.out.println("\nMenghapus 1 unit Beras dari Map...");
        cartMap.removeProduct(p1);
        cartMap.printCart();
    }
}