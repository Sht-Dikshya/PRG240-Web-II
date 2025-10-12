package com.example.service;

import com.example.dao.LoginDAO;
import com.example.model.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of LoginService using JDBC DAO
 * Provides basic CRUD operations with comprehensive logging
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    
    private final LoginDAO loginDAO;
    
    @Autowired
    public LoginServiceImpl(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }
    
    @Override
    public Login registerLogin(Login login) {
        logger.info("=== LOGIN REGISTRATION PROCESS STARTED ===");
        logger.info("Processing registration for login: Name={}, Email={}, Position={}", 
                   login.getName(), login.getEmail(), login.getPosition());
        
        try {
            // Validate login data
            logger.info("Validating login data...");
            validateLogin(login);
            logger.info("Login data validation successful");
            
            // Save login using DAO
            logger.info("Calling DAO to save login to database...");
            Login savedLogin = loginDAO.save(login);
            
            logger.info("=== LOGIN REGISTRATION PROCESS SUCCESSFUL ===");
            logger.info("Login registration completed successfully: ID={}, Name={}",
                       savedLogin.getLoginId(), savedLogin.getName());
            logger.info("Login details saved: ID={}, Name={}, Email={}, Position={}",
                       savedLogin.getLoginId(), savedLogin.getName(),
                       savedLogin.getEmail(), savedLogin.getPosition());
            logger.info("=== Login REGISTRATION PROCESS COMPLETED ===");
            
            return savedLogin;
            
        } catch (DataAccessException e) {
            logger.error("=== LOGIN REGISTRATION PROCESS FAILED - DATABASE ERROR ===");
            logger.error("Database error during login registration: {}", login.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Root cause: {}", e.getCause());
            logger.error("=== LOGIN REGISTRATION PROCESS FAILED ===");
            throw new RuntimeException("Database error occurred during registration", e);
        } catch (IllegalArgumentException e) {
            logger.error("=== LOGIN REGISTRATION PROCESS FAILED - VALIDATION ERROR ===");
            logger.error("Data validation error during login registration: {}", login.getName());
            logger.error("Validation error: {}", e.getMessage());
            logger.error("Login data: Name={}, Email={}, Contact={}, Position={}",
                       login.getName(), login.getEmail(), 
                       login.getContactNumber(), login.getPosition());
            logger.error("=== LOGIN REGISTRATION PROCESS FAILED ===");
            throw new RuntimeException("Data validation error: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("=== LOGIN REGISTRATION PROCESS FAILED - UNEXPECTED ERROR ===");
            logger.error("Unexpected error during login registration: {}", login.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Stack trace: ", e);
            logger.error("=== LOGIN REGISTRATION PROCESS FAILED ===");
            throw new RuntimeException("An unexpected error occurred during registration", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Login> getAllLogins() {
        logger.info("Retrieving all logins");
        
        try {
            List<Login> logins = loginDAO.findAll();
            logger.info("Successfully retrieved {} logins", logins.size());
            return logins;
            
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving all logins", e);
            throw new RuntimeException("Database error while retrieving logins", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Login getLoginById(Long id) {
        logger.info("Retrieving login by ID: {}", id);
        
        try {
            Optional<Login> login = loginDAO.findById(id);
            
            if (login.isPresent()) {
                logger.info("Login found: {}", login.get().getName());
                return login.get();
            } else {
                logger.info("Login with ID {} not found", id);
                return null;
            }
            
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving login by ID: {}", id, e);
            throw new RuntimeException("Database error while retrieving login", e);
        }
    }
    
    @Override
    public Login updateLogin(Long id, Login login) {
        logger.info("Starting login update process for ID: {}", id);
        
        try {
            // Validate login data
            validateLogin(login);
            
            // Check if login exists
            Optional<Login> existingLogin = loginDAO.findById(id);
            if (existingLogin.isEmpty()) {
                logger.warn("Login update failed - login not found: {}", id);
                return null;
            }
            
            // Set the ID for the login to update
            login.setLoginId(id);
            
            // Update login using DAO
            Login updatedLogin = loginDAO.update(login);
            
            logger.info("Login update completed successfully: ID={}, Name={}", 
                       updatedLogin.getLoginId(), updatedLogin.getName());
            
            return updatedLogin;
            
        } catch (DataAccessException e) {
            logger.error("Database error during login update: ID={}", id, e);
            throw new RuntimeException("Database error occurred during update", e);
        } catch (Exception e) {
            logger.error("Unexpected error during login update: ID={}", id, e);
            throw new RuntimeException("An unexpected error occurred during update", e);
        }
    }
    
    @Override
    public boolean deleteLogin(Long id) {
        logger.info("Starting login deletion process for ID: {}", id);
        
        try {
            // Check if login exists
            Optional<Login> existingLogin = loginDAO.findById(id);
            if (existingLogin.isEmpty()) {
                logger.warn("Login deletion failed - login not found: {}", id);
                return false;
            }
            
            // Delete login using DAO
            boolean deleted = loginDAO.deleteById(id);
            
            if (deleted) {
                logger.info("Login deletion completed successfully: ID={}", id);
            } else {
                logger.warn("Login deletion failed: ID={}", id);
            }
            
            return deleted;
            
        } catch (DataAccessException e) {
            logger.error("Database error during login deletion: ID={}", id, e);
            throw new RuntimeException("Database error occurred during deletion", e);
        }
    }
    
    /**
     * Validate login data
     */
    private void validateLogin(Login login) {
        if (login == null) {
            throw new IllegalArgumentException("Login cannot be null");
        }
        if (login.getName() == null || login.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Login name is required");
        }
        if (login.getEmail() == null || login.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Login email is required");
        }
        if (login.getContactNumber() == null || login.getContactNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Login contact number is required");
        }
        if (login.getPosition() == null || login.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Login position is required");
        }
    }
}