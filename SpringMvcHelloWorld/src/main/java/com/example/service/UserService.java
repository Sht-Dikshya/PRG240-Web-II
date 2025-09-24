package com.example.service;

import com.example.model.User;
import java.util.List;

public interface UserService {

    /**
     * Register a new user
     * @param user the user to register
     * @return the registered user
     */
    User registerUser(User user);

    /**
     * Get all user
     * @return list of all user
     */
    List<User> getAllUsers();

    /**
     * Get user by ID
     * @param id the user ID
     * @return the user if found, null otherwise
     */
    User getUserById(Long id);

    /**
     * Update an existing user
     * @param id the user ID
     * @param user the updated user data
     * @return the updated user if found, null otherwise
     */
    User updateUser(Long id, User user);

    /**
     * Delete an user by ID
     * @param id the user ID
     * @return true if user was deleted, false if not found
     */
    boolean deleteUser(Long id);
}