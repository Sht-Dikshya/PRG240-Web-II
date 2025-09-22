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
     * Get all employees
     * @return list of all employees
     */
    List <Login> getAllLogins();
    /**
     * Get login by ID (for future use)
     * @param id the login ID
     * @return the login if found, null otherwise
     */
    Login getLoginById(Long id);

    /**
     * Update an existing login
     * @param id the login ID
     * @param user the updated login data
     * @return the updated login if found, null otherwise
     */
    Login updateLogin(Long id, Login user);

    /**
     * Delete an login by ID
     * @param id the login ID
     * @return true if login was deleted, false if not found
     */
    boolean deleteLogin(Long id);
}