package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;

public class PosController {
    private final ProductService productService;
    private final CartService cartService;

    public PosController(ProductService ps, CartService cs) {
        this.productService = ps;
        this.cartService = cs;
    }

    public void addProduct(String c, String n, String p, String s) throws Exception {
        productService.add(new Product(c, n, Double.parseDouble(p), Integer.parseInt(s)));
    }

    public void deleteProduct(String code) throws Exception { productService.remove(code); }

    public void addToCart(Product p, int qty) throws Exception { cartService.addToCart(p, qty); }
}