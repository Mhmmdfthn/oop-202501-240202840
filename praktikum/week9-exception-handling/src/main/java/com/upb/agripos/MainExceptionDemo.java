package main.java.com.upb.agripos;

public class MainExceptionDemo {
    @SuppressWarnings("UseSpecificCatch")
    public static void main(String[] args) {
        // 1. Identitas (Wajib sesuai tugas)
        System.out.println("Hello, I am [Nama]-[NIM] (Week9)");
        System.out.println("------------------------------------");

        // 2. Inisialisasi Objek secara langsung (Tanpa Service)
        ShoppingCart cart = new ShoppingCart();
        Product p1 = new Product("P01", "Pupuk Organik", 25000, 3);

        // 3. Uji Coba InvalidQuantityException
        try {
            System.out.println("Mencoba tambah produk dengan qty -1...");
            cart.addProduct(p1, -1);
        } catch (InvalidQuantityException e) {
            System.out.println("Terjadi Kesalahan: " + e.getMessage());
        }

        // 4. Uji Coba ProductNotFoundException
        try {
            System.out.println("\nMencoba hapus produk yang tidak ada di keranjang...");
            cart.removeProduct(p1);
        } catch (ProductNotFoundException e) {
            System.out.println("Terjadi Kesalahan: " + e.getMessage());
        }

        // 5. Uji Coba InsufficientStockException
        try {
            System.out.println("\nMencoba checkout qty 5 (stok hanya 3)...");
            cart.addProduct(p1, 5);
            cart.checkout();
        } catch (Exception e) {
            // Menangkap semua jenis exception yang mungkin terjadi saat checkout
            System.out.println("Terjadi Kesalahan: " + e.getMessage());
        } finally {
            System.out.println("------------------------------------");
            System.out.println("Program Selesai.");
        }
    }
}
