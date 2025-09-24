package com.example.service;

import com.example.dao.UserDAO;
import com.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of UserService using JDBC DAO
 * Provides basic CRUD operations with comprehensive logging
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User registerUser(User user) {
        logger.info("=== USER REGISTRATION PROCESS STARTED ===");
        logger.info("Processing registration for user: Name={}, Email={}, Position={}",
                user.getName(), user.getEmail(), user.getPosition());

        try {
            // Validate users data
            logger.info("Validating user data...");
            validateUser(user);
            logger.info("User data validation successful");

            // Save user using DAO
            logger.info("Calling DAO to save user to database...");
            User savedUser = userDAO.save(user);

            logger.info("=== USER REGISTRATION PROCESS SUCCESSFUL ===");
            logger.info("User registration completed successfully: ID={}, Name={}",
                    savedUser.getUserId(), savedUser.getName());
            logger.info("User details saved: ID={}, Name={}, Email={}, Position={}",
                    savedUser.getUserId(), savedUser.getName(),
                    savedUser.getEmail(), savedUser.getPosition());
            logger.info("=== USER REGISTRATION PROCESS COMPLETED ===");

            return savedUser;

        } catch (DataAccessException e) {
            logger.error("=== USER REGISTRATION PROCESS FAILED - DATABASE ERROR ===");
            logger.error("Database error during user registration: {}", user.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Root cause: {}", e.getCause());
            logger.error("=== USER REGISTRATION PROCESS FAILED ===");
            throw new RuntimeException("Database error occurred during registration", e);
        } catch (IllegalArgumentException e) {
            logger.error("=== USER REGISTRATION PROCESS FAILED - VALIDATION ERROR ===");
            logger.error("Data validation error during user registration: {}", user.getName());
            logger.error("Validation error: {}", e.getMessage());
            logger.error("User data: Name={}, Email={}, Contact={}, Position={}, Address={}",
                    user.getName(), user.getEmail(),
                    user.getContactNumber(), user.getPosition(), user.getAddress());
            logger.error("=== USER REGISTRATION PROCESS FAILED ===");
            throw new RuntimeException("Data validation error: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("=== USER REGISTRATION PROCESS FAILED - UNEXPECTED ERROR ===");
            logger.error("Unexpected error during user registration: {}", user.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Stack trace: ", e);
            logger.error("=== USER REGISTRATION PROCESS FAILED ===");
            throw new RuntimeException("An unexpected error occurred during registration", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        logger.info("Retrieving all users");

        try {
            List<User> users = userDAO.findAll();
            logger.info("Successfully retrieved {} users", users.size());
            return users;

        } catch (DataAccessException e) {
            logger.error("Database error while retrieving all users", e);
            throw new RuntimeException("Database error while retrieving users", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        logger.info("Retrieving user by ID: {}", id);

        try {
            Optional<User> user = userDAO.findById(id);

            if (user.isPresent()) {
                logger.info("User found: {}", user.get().getName());
                return user.get();
            } else {
                logger.info("User with ID {} not found", id);
                return null;
            }

        } catch (DataAccessException e) {
            logger.error("Database error while retrieving user by ID: {}", id, e);
            throw new RuntimeException("Database error while retrieving user", e);
        }
    }

    @Override
    public User updateUser(Long id, User user) {
        logger.info("Starting user update process for ID: {}", id);

        try {
            // Validate user data
            validateUser(user);

            // Check if user exists
            Optional<User> existingUser = userDAO.findById(id);
            if (existingUser.isEmpty()) {
                logger.warn("User update failed - user not found: {}", id);
                return null;
            }

            // Set the ID for the user to update
            user.setUserId(id);

            // Update user using DAO
            User updatedUser = userDAO.update(user);

            logger.info("User update completed successfully: ID={}, Name={}",
                    updatedUser.getUserId(), updatedUser.getName());

            return updatedUser;

        } catch (DataAccessException e) {
            logger.error("Database error during user update: ID={}", id, e);
            throw new RuntimeException("Database error occurred during update", e);
        } catch (Exception e) {
            logger.error("Unexpected error during user update: ID={}", id, e);
            throw new RuntimeException("An unexpected error occurred during update", e);
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        logger.info("Starting user deletion process for ID: {}", id);

        try {
            // Check if user exists
            Optional<User> existingUser = userDAO.findById(id);
            if (existingUser.isEmpty()) {
                logger.warn("User deletion failed - user not found: {}", id);
                return false;
            }

            // Delete user using DAO
            boolean deleted = userDAO.deleteById(id);

            if (deleted) {
                logger.info("User deletion completed successfully: ID={}", id);
            } else {
                logger.warn("User deletion failed: ID={}", id);
            }

            return deleted;

        } catch (DataAccessException e) {
            logger.error("Database error during user deletion: ID={}", id, e);
            throw new RuntimeException("Database error occurred during deletion", e);
        }
    }

    /**
     * Validate user data
     */
    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name is required");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User email is required");
        }
        if (user.getContactNumber() == null || user.getContactNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("User contact number is required");
        }
        if (user.getPosition() == null || user.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("User position is required");
        }
        if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("User address is required");
        }
    }
}