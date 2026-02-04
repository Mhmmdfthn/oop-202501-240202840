package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.exception.OutOfStockException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) { 
        this.productDAO = productDAO; 
    }

    public List<Product> getAllProducts() throws Exception { 
        return productDAO.findAll(); 
    }

    public void addProduct(Product product) throws Exception {
        // === VALIDASI INI YANG KEMUNGKINAN HILANG/TIDAK JALAN ===
        if (product.getCode() == null || product.getCode().trim().isEmpty()) {
            throw new ValidationException("Kode produk tidak boleh kosong");
        }
        
        // Validasi Nama Kosong (Ini yang dites oleh Unit Test yang error)
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ValidationException("Nama produk tidak boleh kosong");
        }
        
        if (productDAO.findByCode(product.getCode()) != null) {
            throw new ValidationException("Kode produk sudah ada!");
        }
        // ========================================================
        
        productDAO.insert(product);
    }
    
    public void updateProduct(Product product) throws Exception {
        if (productDAO.findByCode(product.getCode()) == null) {
            throw new ValidationException("Produk tidak ditemukan");
        }
        productDAO.update(product);
    }

    public void deleteProduct(String code) throws Exception { 
        if (productDAO.findByCode(code) == null) {
            throw new ValidationException("Produk tidak ditemukan");
        }
        productDAO.delete(code); 
    }
    
    public boolean validateStock(String productCode, int requiredQty) throws Exception {
        Product product = productDAO.findByCode(productCode);
        return product != null && product.getStock() >= requiredQty;
    }
    
    public void reduceStock(String productCode, int quantity) throws Exception {
        Product product = productDAO.findByCode(productCode);
        if (product == null) throw new ValidationException("Produk tidak ditemukan");
        
        int newStock = product.getStock() - quantity;
        if (newStock < 0) throw new OutOfStockException("Stok tidak cukup");
        
        productDAO.updateStock(productCode, newStock);
    }

    public void processCheckout(List<CartItem> items) throws Exception {
        for (CartItem item : items) {
            reduceStock(item.getProduct().getCode(), item.getQuantity());
        }
    }
}