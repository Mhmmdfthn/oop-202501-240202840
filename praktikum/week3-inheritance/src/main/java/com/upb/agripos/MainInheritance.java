package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.AlatPertanian;
import main.java.com.upb.agripos.model.Benih;
import main.java.com.upb.agripos.model.Pupuk;
import main.java.com.upb.agripos.util.CreditBy;

public class MainInheritance {
    public static void main(String[] args) {

        Benih benihPadi = new Benih("BNH-001", "Benih Padi IR64", 25000, 150, "IR64");
        Pupuk pupukUrea = new Pupuk("PPK-101", "Pupuk Urea Subsidi", 350000, 20, "Urea");
        AlatPertanian cangkul = new AlatPertanian("ALT-501", "Cangkul Baja", 100000, 25, "Baja");

        System.out.println("===== INFORMASI PRODUK AGRI-POS =====");
        
        System.out.println("\n--- Data Dasar Produk ---");
        System.out.println("Benih: " + benihPadi.getNama() + " | Varietas: " + benihPadi.getVarietas());
        System.out.println("Pupuk: " + pupukUrea.getNama() + " | Jenis: " + pupukUrea.getJenis());
        System.out.println("Alat: " + cangkul.getNama() + " | Material: " + cangkul.getMaterial());

        System.out.println("\n---Deskripsi Lengkap Produk---");
        System.out.println("1. Deskripsi Benih : " + benihPadi.deskripsi());
        System.out.println("2. Deskripsi Pupuk : " + pupukUrea.deskripsi());
        System.out.println("3. Deskripsi Alat  : " + cangkul.deskripsi());


        CreditBy.print("240202840", "Muhammad Nuur Fathan");
    }
    
}