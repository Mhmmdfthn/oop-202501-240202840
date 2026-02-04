package com.upb.agripos.service;

import com.upb.agripos.dao.UserDAO;
import com.upb.agripos.exception.AuthenticationException; // Wajib import ini
import com.upb.agripos.model.User;
import com.upb.agripos.model.UserRole;

public class AuthService {
    private UserDAO userDAO;
    private User currentUser;
    
    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public User authenticate(String username, String password) throws Exception {
        // Validasi input kosong (Best Practice)
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationException("Username tidak boleh kosong");
        }
        
        // Cek Login ke Database
        if(userDAO.validateCredentials(username, password)) {
            currentUser = userDAO.findByUsername(username);
            return currentUser;
        }
        
        // [PERBAIKAN DISINI]
        // Sebelumnya: throw new Exception("Login Gagal");
        // Sekarang: Gunakan AuthenticationException agar sesuai dengan Unit Test
        throw new AuthenticationException("Login Gagal: Username atau password salah");
    }
    
    public boolean validateRole(User user, UserRole requiredRole) {
        return user != null && user.getRole() == requiredRole;
    }
    
    public void logout() { currentUser = null; }
    public User getCurrentUser() { return currentUser; }
    public boolean isLoggedIn() { return currentUser != null; }
}