package com.example.dao;

import com.example.model.Login;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Login operations
 * Defines contract for database operations
 */
public interface LoginDAO {
    
    /**
     * Save a new login to the database
     * @param login the login to save
     * @return the saved login with generated ID
     */
    Login save(Login login);
    
    /**
     * Find an login by ID
     * @param id the login ID
     * @return Optional containing the login if found, empty otherwise
     */
    Optional<Login> findById(Long id);
    
    /**
     * Find all logins
     * @return list of all logins
     */
    List<Login> findAll();
    
    /**
     * Update an existing login
     * @param login the login with updated data
     * @return the updated login
     */
    Login update(Login login);
    
    /**
     * Delete an login by ID
     * @param id the login ID
     * @return true if login was deleted, false if not found
     */
    boolean deleteById(Long id);
}








