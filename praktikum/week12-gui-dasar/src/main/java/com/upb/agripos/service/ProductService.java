package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

public class ProductService {
    private final ProductDAO dao;

    public ProductService(ProductDAO dao) { this.dao = dao; }

    public void addProduct(Product p) throws Exception {
        if (p.getPrice() < 0) throw new Exception("Harga tidak boleh negatif!");
        dao.insert(p);
    }

    public List<Product> getAllProducts() throws Exception {
        return dao.findAll();
    }
}