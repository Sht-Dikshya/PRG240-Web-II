package com.example.service;

import com.example.dao.UserDAO;
import com.example.model.Login;
import com.example.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final EmployeeDAO employeeDAO;

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