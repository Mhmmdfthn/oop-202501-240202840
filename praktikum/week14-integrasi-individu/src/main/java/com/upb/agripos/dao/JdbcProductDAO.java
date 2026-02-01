package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.model.Product;

public class JdbcProductDAO {
    private final String url = "jdbc:postgresql://localhost:5432/agripos";
    private final String user = "postgres";
    private final String pass = "admin123";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    public List<Product> findAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY code ASC";
        try (Connection conn = getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(rs.getString("code"), rs.getString("name"), rs.getDouble("price"), rs.getInt("stock")));
            }
        }
        return list;
    }

    public void insert(Product p) throws Exception {
        String sql = "INSERT INTO products(code, name, price, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getCode()); ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice()); ps.setInt(4, p.getStock());
            ps.executeUpdate();
        }
    }

    public void delete(String code) throws Exception {
        String sql = "DELETE FROM products WHERE code = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code); ps.executeUpdate();
        }
    }
    public void update(Product p) throws Exception {
    String sql = "UPDATE products SET name = ?, price = ?, stock = ? WHERE code = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, p.getName());
        ps.setDouble(2, p.getPrice());
        ps.setInt(3, p.getStock());
        ps.setString(4, p.getCode()); // Code sebagai kunci (Primary Key)
        ps.executeUpdate();
    }
}
}