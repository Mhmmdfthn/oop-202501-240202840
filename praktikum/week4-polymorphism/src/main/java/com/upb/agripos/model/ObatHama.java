package main.java.com.upb.agripos.model;

public class ObatHama extends Produk {
    private String targetHama; 

    public ObatHama(String kode, String nama, double harga, int stok, String targetHama) {
        super(kode, nama, harga, stok);
        this.targetHama = targetHama;
    }

    @Override
    public String getInfo() {
        return "Obat   : " + super.getInfo() + ", Target: " + targetHama;
    }
}
