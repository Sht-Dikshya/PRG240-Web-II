package com.example.service;

import com.example.model.Login;
import java.util.List;

public interface LoginService {
    
    /**
     * Register a new login
     * @param login the login to register
     * @return the registered login
     */
    Login registerLogin(Login login);
    
    /**
     * Get all logins
     * @return list of all logins
     */
    List<Login> getAllLogins();
    
    /**
     * Get login by ID
     * @param id the login ID
     * @return the login if found, null otherwise
     */
    Login getLoginById(Long id);
    
    /**
     * Update an existing login
     * @param id the login ID
     * @param login the updated login data
     * @return the updated login if found, null otherwise
     */
    Login updateLogin(Long id, Login login);
    
    /**
     * Delete an login by ID
     * @param id the login ID
     * @return true if login was deleted, false if not found
     */
    boolean deleteLogin(Long id);
}
