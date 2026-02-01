package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

public class ProductService {
    private final ProductDAO dao;

    public ProductService(ProductDAO dao) { this.dao = dao; }

    public List<Product> getAll() throws Exception { return dao.findAll(); }

    public void add(Product p) throws Exception {
        if (p.getPrice() < 0) throw new Exception("Harga tidak valid!");
        dao.insert(p);
    }

    public void remove(String code) throws Exception { dao.delete(code); }
}