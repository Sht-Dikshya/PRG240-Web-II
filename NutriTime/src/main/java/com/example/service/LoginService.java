package com.example.service;

import com.example.model.Login;

public interface LoginService {

    /**
     * Register a new user
     * @param user the login to register
     * @return the registered login
     */
    Login registerLogin(Login  user);

    /**
     * Get login by ID (for future use)
     * @param id the login ID
     * @return the login if found, null otherwise
     */
    Login getLoginById(Long id);
}
