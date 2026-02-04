package com.upb.agripos.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Product;

public class ProductServiceTest {

    private ProductService productService;
    private MockProductDAO mockDao;

    // --- MOCK DAO (Database Tiruan di Memori) ---
    private class MockProductDAO implements ProductDAO {
        private List<Product> db = new ArrayList<>();

        @Override
        public List<Product> findAll() { return db; }
        @Override
        public Product findByCode(String code) {
            return db.stream().filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
        }
        @Override
        public void insert(Product p) { db.add(p); }
        @Override
        public void update(Product p) { /* Logic update simple */ }
        @Override
        public void delete(String code) { db.removeIf(p -> p.getCode().equals(code)); }
        @Override
        public void updateStock(String code, int newStock) {
            Product p = findByCode(code);
            if (p != null) p.setStock(newStock);
        }
    }

    @Before
    public void setUp() {
        mockDao = new MockProductDAO();
        productService = new ProductService(mockDao);
    }

    @Test
    public void testAddProduct_Success() throws Exception {
        Product p = new Product("P001", "Beras", "Umum", new BigDecimal("10000"), 10);
        productService.addProduct(p);
        
        assertNotNull(mockDao.findByCode("P001"));
        assertEquals("Beras", mockDao.findByCode("P001").getName());
    }

    @Test(expected = ValidationException.class)
    public void testAddProduct_EmptyName_ThrowsException() throws Exception {
        Product p = new Product("P002", "", "Umum", new BigDecimal("10000"), 10);
        productService.addProduct(p); // Harus error karena nama kosong
    }

    @Test(expected = ValidationException.class)
    public void testAddProduct_DuplicateCode_ThrowsException() throws Exception {
        Product p1 = new Product("P001", "Beras A", "Umum", BigDecimal.TEN, 10);
        Product p2 = new Product("P001", "Beras B", "Umum", BigDecimal.TEN, 10);
        
        productService.addProduct(p1);
        productService.addProduct(p2); // Harus error karena kode P001 sudah ada
    }

    @Test
    public void testValidateStock() throws Exception {
        Product p = new Product("P003", "Jagung", "Umum", BigDecimal.TEN, 5);
        mockDao.insert(p);

        assertTrue("Stok cukup", productService.validateStock("P003", 5));
        assertFalse("Stok kurang", productService.validateStock("P003", 6));
    }
}