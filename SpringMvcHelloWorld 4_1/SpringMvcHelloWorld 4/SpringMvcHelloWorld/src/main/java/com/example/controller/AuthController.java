package com.example.controller;

import com.example.model.User;
import com.example.service.AuthService;
import com.example.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Login endpoint - authenticate user and return JWT token
     * @param loginRequest containing username and password
     * @return JWT token or error message
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        logger.info("Login attempt received");
        
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        
        if (username == null || password == null) {
            logger.warn("Missing username or password in login request");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Username and password are required");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            logger.info("Attempting to authenticate user: {}", username);
            // Authenticate user and get JWT token
            String token = authService.authenticate(username, password);
            logger.info("Authentication result for user {}: {}", username, token != null ? "SUCCESS" : "FAILED");
            
            if (token != null) {
                // Get user details for response
                User user = authService.getUserByUsername(username);
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("token", token);
                response.put("username", username);
                response.put("role", user != null ? user.getRole() : "USER");
                
                logger.info("Login successful for user: {}", username);
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Login failed for user: {} - Invalid credentials", username);
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            logger.error("Error during login for user {}: {}", username, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Internal server error during authentication: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Register endpoint - create new user account
     * @param registerRequest containing username and password
     * @return success or error message
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> registerRequest) {
        logger.info("Registration attempt received");
        
        String username = registerRequest.get("username");
        String password = registerRequest.get("password");
        
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            logger.warn("Missing username or password in registration request");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Username and password are required");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            boolean registered = authService.registerUser(username, password);
            
            if (registered) {
                logger.info("Registration successful for user: {}", username);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "User registered successfully");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Registration failed - user already exists: {}", username);
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Username already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
        } catch (Exception e) {
            logger.error("Error during registration: {}", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Internal server error during registration");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    
    /**
     * Validate token endpoint - check if JWT token is valid
     * @param tokenRequest containing JWT token
     * @return validation result
     */
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody Map<String, String> tokenRequest) {
        logger.info("Token validation request received");
        
        String token = tokenRequest.get("token");
        
        if (token == null) {
            logger.warn("Missing token in validation request");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Token is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            boolean isValid = jwtUtil.validateToken(token);
            
            if (isValid) {
                String username = jwtUtil.getUsernameFromToken(token);
                boolean isExpired = jwtUtil.isTokenExpired(token);
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Token is valid");
                response.put("username", username);
                response.put("expired", isExpired);
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Token validation failed");
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            logger.error("Error during token validation: {}", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error validating token");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
