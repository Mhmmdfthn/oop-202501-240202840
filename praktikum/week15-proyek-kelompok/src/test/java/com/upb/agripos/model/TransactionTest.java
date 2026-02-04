package com.upb.agripos.model;

import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void testCalculateTotal() {
        // 1. Siapkan Produk
        Product p1 = new Product("P1", "Item 1", new BigDecimal("10000"), 10);
        Product p2 = new Product("P2", "Item 2", new BigDecimal("20000"), 10);

        // 2. Siapkan Item Transaksi
        TransactionItem item1 = new TransactionItem(p1, 2); // 20.000
        TransactionItem item2 = new TransactionItem(p2, 1); // 20.000

        // 3. Masukkan ke Transaksi
        Transaction trx = new Transaction();
        List<TransactionItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        trx.setItems(items);

        // 4. Test Method calculateTotal()
        BigDecimal total = trx.calculateTotal();
        
        // Expected: 40.000
        assertEquals(0, new BigDecimal("40000").compareTo(total));
    }
}