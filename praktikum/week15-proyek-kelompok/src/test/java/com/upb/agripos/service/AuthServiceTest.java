package com.upb.agripos.service;

import com.upb.agripos.dao.UserDAO;
import com.upb.agripos.exception.AuthenticationException;
import com.upb.agripos.model.User;
import com.upb.agripos.model.UserRole;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuthServiceTest {

    private AuthService authService;

    // --- MOCK USER DAO ---
    private class MockUserDAO implements UserDAO {
        @Override
        public User findByUsername(String username) {
            if ("admin".equals(username)) {
                return new User(1, "admin", "Admin System", UserRole.ADMIN);
            }
            return null;
        }

        @Override
        public boolean validateCredentials(String username, String password) {
            // Simulasi: password benar jika sama dengan "secret"
            return "admin".equals(username) && "secret".equals(password);
        }
    }

    @Before
    public void setUp() {
        authService = new AuthService(new MockUserDAO());
    }

    @Test
    public void testLogin_Success() throws Exception {
        User user = authService.authenticate("admin", "secret");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test(expected = AuthenticationException.class)
    public void testLogin_WrongPassword_ThrowsException() throws Exception {
        authService.authenticate("admin", "wrongpass");
    }

    @Test(expected = AuthenticationException.class)
    public void testLogin_UserNotFound_ThrowsException() throws Exception {
        authService.authenticate("unknown", "secret");
    }
}