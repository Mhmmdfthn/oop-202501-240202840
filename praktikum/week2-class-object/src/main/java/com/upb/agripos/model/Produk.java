package main.java.com.upb.agripos.model;

public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter & Setter
    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }


    // Tambah stok produk
    public void tambahStok(int jumlah) {
        this.stok += jumlah;
        System.out.println("Stok " + nama + " bertambah " + jumlah + " -> total stok: " + stok);
    }

    // Kurangi stok produk
    public void kurangiStok(int jumlah) {
        if (this.stok >= jumlah) {
            this.stok -= jumlah;
            System.out.println("Stok " + nama + " berkurang " + jumlah + " -> sisa stok: " + stok);
        } else {
            System.out.println("Stok tidak mencukupi untuk " + nama + "!");
        }
    }

    // Tampilkan info produk
    public void tampilkanInfo() {
        System.out.println("Kode: " + kode + ", Nama: " + nama + ", Harga: " + harga + ", Stok: " + stok);
    }

    // Hitung total harga (harga * jumlah)
    public double hitungTotal(int jumlah) {
        return harga * jumlah;
    }
}