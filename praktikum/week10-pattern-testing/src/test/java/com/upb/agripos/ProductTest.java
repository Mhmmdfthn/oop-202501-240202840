package test.java.com.upb.agripos;

import main.java.com.upb.agripos.model.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testProductConstructorAndGetters() {
        // Arrange (Persiapan)
        String kode = "B01";
        String nama = "Benih Jagung Hibrida";
        
        // Act (Tindakan)
        Product p = new Product(kode, nama);

        // Assert (Verifikasi)
        assertEquals(kode, p.getCode(), "Kode produk harus sesuai dengan input constructor");
        assertEquals(nama, p.getName(), "Nama produk harus sesuai dengan input constructor");
    }

    @Test
    public void testProductNotNull() {
        Product p = new Product("T01", "Traktor Tangan");
        assertNotNull(p, "Objek produk tidak boleh null setelah dibuat");
    }
}
