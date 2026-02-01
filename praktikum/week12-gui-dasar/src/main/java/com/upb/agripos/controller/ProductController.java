package com.upb.agripos.controller;

import java.util.List;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    public void processAdd(String c, String n, String p, String s) throws Exception {
        Product product = new Product(c, n, Double.parseDouble(p), Integer.parseInt(s));
        service.addProduct(product);
    }

    public List<Product> fetchAll() throws Exception {
        return service.getAllProducts();
    }
}