package com.scaler.ProductService.configurations.security;

import java.util.List;

public class UserDetailsResponse {
    private String username;
    private String password;
    private List<String> roles;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getters and setters
}
