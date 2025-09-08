package com.example.controller;

import com.example.model.Login;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/user")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;
    /**
     * Display the login registration form
     */
    @GetMapping("/login")
    public String showRegistrationForm(Model model) {
        logger.info("Displaying login user form");
        model.addAttribute("login", new Login());
        return "user/login";
    }
    /**
     * Handle the login registration form submission
     */
    @PostMapping("/login")
    public String registerLogin(@ModelAttribute("login") Login user, Model model) {
        logger.info("Processing login for: {}", user.getUsername());
        // Register the login using the service
        Login registeredLogin = loginService.registerLogin(user);
        // Add the registered login to the model for display
        model.addAttribute("login", registeredLogin);
        // Redirect to the summary page
        return "user/loginsummary";
    }
}
