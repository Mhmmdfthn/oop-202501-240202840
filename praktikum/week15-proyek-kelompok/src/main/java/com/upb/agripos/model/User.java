package com.upb.agripos.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private UserRole role;

    public User() {}

    // CONSTRUCTOR INI YANG HILANG
    public User(int id, String username, String name, UserRole role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}