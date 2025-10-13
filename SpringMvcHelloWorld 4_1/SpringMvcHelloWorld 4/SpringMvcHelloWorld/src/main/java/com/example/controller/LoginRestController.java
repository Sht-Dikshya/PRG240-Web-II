package com.example.controller;

import com.example.model.Login;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class LoginRestController {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginRestController.class);
    
    @Autowired
    private LoginService loginService;
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Login API is running");
        return ResponseEntity.ok(response);
    }
    
    
    /**
     * Get all logins (requires JWT authentication)
     */
    @GetMapping("")
    public ResponseEntity<?> getAllLogins(Authentication authentication) {
        logger.info("API: Fetching all logins");
        
        // Check if user is authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.warn("API: Unauthenticated request to get all logins");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "JWT token is required for this operation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        String username = authentication.getName();
        logger.info("API: Fetching logins by authenticated user: {}", username);
        
        try {
            // Get all logins from service
            List<Login> logins = loginService.getAllLogins();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Logins retrieved successfully");
            response.put("count", logins.size());
            response.put("logins", logins);
            response.put("requestedBy", username);
            
            logger.info("API: Retrieved {} logins for user: {}", logins.size(), username);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("API: Error fetching logins for user {}: {}", username, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error fetching logins: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * REST API endpoint to register login with JSON data
     */
    @PostMapping("")
    public ResponseEntity<?> registerLogin(@RequestBody Login login) {
        logger.info("API: Processing login registration for: {}", login.getName());
        
        try {
            // Register the login using the service
            Login registeredLogin = loginService.registerLogin(login);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login registered successfully");
            response.put("login", registeredLogin);
            
            logger.info("API: Login registered successfully: {}", registeredLogin.getName());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("API: Error registering login: {}", e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Internal server error: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Get login by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getLoginById(@PathVariable("id") String id) {
        logger.info("API: Getting login by ID: {}", id);
        
        try {
            Long loginId = Long.parseLong(id);
            Login login = loginService.getLoginById(loginId);
            
            if (login != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login found");
                response.put("login", login);
                
                logger.info("API: Login found: {}", login.getName());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Login not found with ID: " + id);
                
                logger.info("API: Login not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            logger.error("API: Error getting login by ID {}: {}", id, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error getting login: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Update login by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLogin(@PathVariable("id") String id, @RequestBody Login login) {
        logger.info("API: Updating login with ID: {}", id);
        
        try {
            Long loginId = Long.parseLong(id);
            Login updatedLogin = loginService.updateLogin(loginId, login);
            
            if (updatedLogin != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login updated successfully");
                response.put("login", updatedLogin);
                
                logger.info("API: Login updated successfully: {}", updatedLogin.getName());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Login not found with ID: " + id);
                
                logger.info("API: Login not found for update with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            logger.error("API: Error updating login with ID {}: {}", id, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error updating login: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Delete login by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLogin(@PathVariable("id") String id) {
        logger.info("API: Deleting login with ID: {}", id);
        
        try {
            Long loginId = Long.parseLong(id);
            boolean deleted = loginService.deleteLogin(loginId);
            
            if (deleted) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login deleted successfully");
                
                logger.info("API: Login deleted successfully with ID: {}", id);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Login not found with ID: " + id);
                
                logger.info("API: Login not found for deletion with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            logger.error("API: Error deleting login with ID {}: {}", id, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error deleting login: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
