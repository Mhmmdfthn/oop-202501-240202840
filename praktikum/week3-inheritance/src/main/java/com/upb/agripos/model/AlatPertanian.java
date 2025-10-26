package main.java.com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String material;

    public AlatPertanian(String kode, String nama, double harga, int stok, String material) {
        // Memanggil konstruktor superclass (Produk)
        super(kode, nama, harga, stok);
        this.material = material;
    }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    // Override method deskripsi untuk menambahkan info material
    @Override
    public String deskripsi() {
        return super.deskripsi() + ", Material: " + this.material;
    }
}