package com.upb.agripos.controller;

import com.upb.agripos.model.User;
import com.upb.agripos.service.AuthService;

public class AuthController {
    private AuthService authService;
    
    public AuthController(AuthService authService) { this.authService = authService; }
    
    public User login(String username, String password) throws Exception {
        return authService.authenticate(username, password);
    }
    public boolean isAdmin() { 
        return authService.getCurrentUser() != null && "ADMIN".equals(authService.getCurrentUser().getRole().toString()); 
    }
}