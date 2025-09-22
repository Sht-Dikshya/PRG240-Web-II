package com.example.dao;

import com.example.model.Login;
import java.util.Optional;

/**
 * Data Access Object interface for Login operations
 * Defines contract for login validation and persistence
 */
public interface LoginDAO {

    /**
     * Save a new login attempt or credentials
     * @param login the login object
     * @return the saved login (with generated ID if applicable)
     */
    Login save(Login login);

    /**
     * Validate login by username and password
     * @param username the username
     * @param password the password
     * @return Optional containing login if credentials are valid, empty otherwise
     */
    Optional<Login> validate(String username, String password);

    /**
     * Delete login record by username
     * @param username the username
     * @return true if deleted, false otherwise
     */
    boolean deleteByUsername(String username);
}
