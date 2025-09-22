package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
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
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    /**
     * Display the user registration form
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        logger.info("Displaying user registration form");
        model.addAttribute("user", new User());
        return "user/registration";
    }
    
    /**
     * Handle the user registration form submission
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        logger.info("Processing user registration for: {}", user.getName());
        
        // Register the user using the service
        User registeredUser = userService.registerUser(user);
        
        // Add the registered user to the model for display
        model.addAttribute("user", registeredUser);
        
        // Redirect to the summary page
        return "user/summary";
    }
    
    /**
     * Simple test method for JSP
     */
    @GetMapping("/test")
    public String testJsp() {
        logger.info("Testing simple JSP");
        return "simple";
    }
    
    /**
     * Basic test method for JSP
     */
    @GetMapping("/basic")
    public String basicJsp() {
        logger.info("Testing basic JSP");
        return "basic";
    }
    
}
