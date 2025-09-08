package com.example.service;

import com.example.model.Login;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public Login registerLogin(Login user) {
        logger.info("Registering new login: {}", user.getUsername());

        // In a real application, you would save to database here
        // For now, we'll just log and return the login
        logger.info("login registered successfully: {}", user);

        return user;
    }

    @Override
    public Login getLoginById(Long id) {
        logger.info("Getting user by ID: {}", id);

        // In a real application, you would fetch from database here
        // For now, we'll return null
        logger.info("Login with ID {} not found", id);
        return null;
    }
}
