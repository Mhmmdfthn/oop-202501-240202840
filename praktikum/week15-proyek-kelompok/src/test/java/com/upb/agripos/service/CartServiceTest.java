package com.upb.agripos.service;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.upb.agripos.exception.OutOfStockException;
import com.upb.agripos.model.Product;

public class CartServiceTest {

    private CartService cartService;
    private Product productA;
    private Product productB;

    @Before
    public void setUp() {
        cartService = new CartService();
        // Setup produk dummy
        productA = new Product("A001", "Produk A", "Umum", new BigDecimal("10000"), 10);
        productB = new Product("B001", "Produk B", "Umum", new BigDecimal("5000"), 5);
    }

    @Test
    public void testAddToCart_CalculateTotal() throws Exception {
        cartService.addToCart(productA, 2); // 2 * 10.000 = 20.000
        cartService.addToCart(productB, 1); // 1 * 5.000 = 5.000
        
        BigDecimal expectedTotal = new BigDecimal("25000");
        assertEquals(expectedTotal.compareTo(cartService.getTotal()), 0); // compareTo return 0 jika sama
        assertEquals(2, cartService.getCartItems().size());
    }

    @Test(expected = OutOfStockException.class)
    public void testAddToCart_ExceedStock_ThrowsException() throws Exception {
        // Stok cuma 10, minta 11 -> harus error
        cartService.addToCart(productA, 11);
    }

    @Test
    public void testClearCart() throws Exception {
        cartService.addToCart(productA, 1);
        cartService.clearCart();
        
        assertTrue(cartService.getCartItems().isEmpty());
        assertEquals(BigDecimal.ZERO, cartService.getTotal());
    }
}