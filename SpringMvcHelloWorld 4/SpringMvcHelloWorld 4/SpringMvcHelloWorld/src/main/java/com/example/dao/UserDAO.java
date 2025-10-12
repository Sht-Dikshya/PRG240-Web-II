package com.example.dao;

import com.example.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for User operations
 * Follows the same pattern as EmployeeDAO
 */
public interface UserDAO {
    
    /**
     * Save a new user to the database
     * @param user the user to save
     * @return the saved user with generated ID
     */
    User save(User user);
    
    /**
     * Find user by username
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Check if user exists by username
     * @param username the username to check
     * @return true if user exists, false otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Find user by ID
     * @param id the user ID
     * @return Optional containing the user if found
     */
    Optional<User> findById(Long id);
    
    /**
     * Get all users
     * @return list of all users
     */
    List<User> findAll();
    
    /**
     * Update an existing user
     * @param user the user to update
     * @return the updated user
     */
    User update(User user);
    
    /**
     * Delete user by ID
     * @param id the user ID to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteById(Long id);
    
    /**
     * Delete user by username
     * @param username the username to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteByUsername(String username);
}





