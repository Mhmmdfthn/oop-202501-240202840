package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.model.Product;
import com.upb.agripos.util.DatabaseConnection;

public class JdbcProductDAO implements ProductDAO {
    
    /**
     * Mendapatkan koneksi database melalui Singleton
     */
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Product> findAll() throws Exception {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY code";
        
        try (Statement stmt = getConnection().createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            throw new Exception("Error mengambil data produk: " + e.getMessage());
        }
        
        return products;
    }

    @Override
    public Product findByCode(String code) throws Exception {
        String sql = "SELECT * FROM products WHERE code = ?";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, code);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new Exception("Error mencari produk: " + e.getMessage());
        }
    }

    @Override
    public void insert(Product product) throws Exception {
        String sql = "INSERT INTO products (code, name, category, price, stock) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, product.getCode());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getCategory());
            pstmt.setBigDecimal(4, product.getPrice());
            pstmt.setInt(5, product.getStock());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new Exception("Gagal menambahkan produk, tidak ada baris yang terpengaruh");
            }
            
        } catch (SQLException e) {
            // Check jika error karena duplicate key
            if (e.getMessage().contains("duplicate key") || 
                e.getMessage().contains("unique constraint")) {
                throw new Exception("Kode produk sudah ada: " + product.getCode());
            }
            throw new Exception("Error menambahkan produk: " + e.getMessage());
        }
    }

    @Override
    public void update(Product product) throws Exception {
        String sql = "UPDATE products SET name = ?, category = ?, price = ?, stock = ? WHERE code = ?";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setBigDecimal(3, product.getPrice());
            pstmt.setInt(4, product.getStock());
            pstmt.setString(5, product.getCode());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new Exception("Produk tidak ditemukan: " + product.getCode());
            }
            
        } catch (SQLException e) {
            throw new Exception("Error mengupdate produk: " + e.getMessage());
        }
    }

    @Override
    public void delete(String code) throws Exception {
        String sql = "DELETE FROM products WHERE code = ?";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, code);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new Exception("Produk tidak ditemukan: " + code);
            }
            
        } catch (SQLException e) {
            // Check jika error karena foreign key constraint
            if (e.getMessage().contains("foreign key") || 
                e.getMessage().contains("constraint")) {
                throw new Exception("Tidak dapat menghapus produk karena masih digunakan dalam transaksi");
            }
            throw new Exception("Error menghapus produk: " + e.getMessage());
        }
    }

    @Override
    public void updateStock(String code, int newStock) throws Exception {
        if (newStock < 0) {
            throw new Exception("Stok tidak boleh negatif");
        }
        
        String sql = "UPDATE products SET stock = ? WHERE code = ?";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, newStock);
            pstmt.setString(2, code);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new Exception("Produk tidak ditemukan: " + code);
            }
            
        } catch (SQLException e) {
            throw new Exception("Error mengupdate stok: " + e.getMessage());
        }
    }
    
    /**
     * Helper method untuk mapping ResultSet ke Object Product
     */
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setCode(rs.getString("code"));
        product.setName(rs.getString("name"));
        product.setCategory(rs.getString("category"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStock(rs.getInt("stock"));
        return product;
    }
}
