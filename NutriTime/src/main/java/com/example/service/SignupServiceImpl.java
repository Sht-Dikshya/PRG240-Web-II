package com.example.service;

import com.example.model.Signup;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SignupServiceImpl implements SignupService {

    private static final Logger logger = LoggerFactory.getLogger(SignupServiceImpl.class);

    @Override
    public Signup registerSignup(Signup user) {
        logger.info("Signup: {}", user.getEmail());

        // In a real application, you would save to database here
        // For now, we'll just log and return the login
        logger.info("Signup successfully: {}", user);

        return user;
    }

    @Override
    public Signup getSignupById(Long id) {
        logger.info("Getting user by ID: {}", id);

        // In a real application, you would fetch from database here
        // For now, we'll return null
        logger.info("Login with ID {} not found", id);
        return null;
    }
}
