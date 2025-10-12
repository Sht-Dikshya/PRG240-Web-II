package com.example.service;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.util.Optional;

@Service
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserDAO userDAO;
    
    public AuthService() {
        logger.info("AuthService constructor called");
        // Constructor - don't initialize users here
    }
    
    @PostConstruct
    @Transactional
    public void initializeDemoUsers() {
        logger.info("initializeDemoUsers() called via @PostConstruct");
        if (passwordEncoder == null) {
            logger.error("PasswordEncoder is null in @PostConstruct method!");
            return;
        }
        
        // Check if demo users already exist in database
        if (userDAO.existsByUsername("admin")) {
            logger.info("Demo users already exist in database, skipping initialization");
            return;
        }
        
        // Create demo users with encoded passwords and save to database
        User admin = new User("admin", passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        userDAO.save(admin);
        
        User user = new User("user", passwordEncoder.encode("user123"));
        user.setRole("USER");
        userDAO.save(user);
        
        User test = new User("test", passwordEncoder.encode("test123"));
        test.setRole("USER");
        userDAO.save(test);
        
        logger.info("Demo users initialized and saved to database: admin, user, test");
    }
    
    /**
     * Authenticate user with username and password
     * @param username the username
     * @param password the password
     * @return JWT token if authentication successful, null otherwise
     */
    public String authenticate(String username, String password) {
        logger.info("Attempting authentication for user: {}", username);
        
        Optional<User> userOpt = userDAO.findByUsername(username);
        if (userOpt.isEmpty()) {
            logger.warn("User not found: {}", username);
            return null;
        }
        
        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.warn("Invalid password for user: {}", username);
            return null;
        }
        
        if (!user.isEnabled()) {
            logger.warn("User account is disabled: {}", username);
            return null;
        }
        
        String token = jwtUtil.generateToken(username);
        logger.info("Authentication successful for user: {}", username);
        return token;
    }
    
    /**
     * Get user by username
     * @param username the username
     * @return User object or null if not found
     */
    public User getUserByUsername(String username) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        return userOpt.orElse(null);
    }
    
    /**
     * Register a new user
     * @param username the username
     * @param password the password
     * @return true if registration successful, false if user already exists
     */
    @Transactional
    public boolean registerUser(String username, String password) {
        logger.info("Attempting to register user: {}", username);
        
        if (userDAO.existsByUsername(username)) {
            logger.warn("User already exists: {}", username);
            return false;
        }
        
        User newUser = new User(username, passwordEncoder.encode(password));
        userDAO.save(newUser);
        
        logger.info("User registered successfully and saved to database: {}", username);
        return true;
    }
}
