package com.upb.agripos.dao;

import java.util.List;

import com.upb.agripos.model.Product;

public interface ProductDAO {
    /**
     * Mengambil semua produk dari database
     * @return List of all products
     * @throws Exception jika terjadi error database
     */
    List<Product> findAll() throws Exception;
    
    /**
     * Mencari produk berdasarkan kode
     * @param code Kode produk
     * @return Product jika ditemukan, null jika tidak
     * @throws Exception jika terjadi error database
     */
    Product findByCode(String code) throws Exception;
    
    /**
     * Menambah produk baru ke database
     * @param product Produk yang akan ditambahkan
     * @throws Exception jika terjadi error atau kode duplikat
     */
    void insert(Product product) throws Exception;
    
    /**
     * Mengupdate data produk
     * @param product Produk dengan data baru
     * @throws Exception jika terjadi error database
     */
    void update(Product product) throws Exception;
    
    /**
     * Menghapus produk berdasarkan kode
     * @param code Kode produk yang akan dihapus
     * @throws Exception jika terjadi error database
     */
    void delete(String code) throws Exception;
    
    /**
     * Mengupdate stok produk
     * @param code Kode produk
     * @param newStock Stok baru
     * @throws Exception jika terjadi error database
     */
    void updateStock(String code, int newStock) throws Exception;
}
