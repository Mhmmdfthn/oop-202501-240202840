package com.upb.agripos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;

public class CartServiceTest {
    @Test
    public void testInvalidQuantityException() {
        CartService cart = new CartService();
        Product p = new Product("P01", "Pupuk", 10000.0, 10);
        
        Exception exception = assertThrows(Exception.class, () -> {
            cart.addToCart(p, 0);
        });

        assertTrue(exception.getMessage().contains("lebih dari 0"), 
                   "Pesan error harus menginformasikan qty tidak valid");
    }

    @Test
    public void testCalculateTotal() throws Exception {
        CartService cart = new CartService();
        Product p = new Product("P01", "Pupuk", 10000.0, 10);
        cart.addToCart(p, 2);
        assertEquals(20000.0, cart.getTotal());
    }
}