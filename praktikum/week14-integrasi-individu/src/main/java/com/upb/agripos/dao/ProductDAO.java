package com.upb.agripos.dao;
import java.util.List;

import com.upb.agripos.model.Product;

public interface ProductDAO {
    List<Product> findAll() throws Exception;
    void insert(Product p) throws Exception;
    void delete(String code) throws Exception;
}