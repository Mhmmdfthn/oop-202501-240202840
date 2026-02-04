package com.upb.agripos.dao;

import com.upb.agripos.model.User;
import com.upb.agripos.model.UserRole;
import com.upb.agripos.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcUserDAO implements UserDAO {
    
    private Connection getConnection() throws Exception {
        return DatabaseConnection.getInstance().getConnection();
    }
    
    @Override
    public User findByUsername(String username) throws Exception {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                return user;
            }
            return null;
        }
    }
    
    @Override
    public boolean validateCredentials(String username, String password) throws Exception {
        User user = findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}