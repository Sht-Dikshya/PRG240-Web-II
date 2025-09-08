package com.example.controller;

import com.example.model.Signup;
import com.example.service.SignupService;
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
public class SignupController {
    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    private SignupService signupService;
    // Display the signup form
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        logger.info("Displaying signup form");
        model.addAttribute("signup", new Signup());
        return "user/signup";
    }
    // Handle signup form submission
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("signup") Signup user, Model model) {
        logger.info("Processing signup for: {}", user.getEmail());
        // Save the signup using service
        Signup registeredUser = signupService.registerSignup(user);
        // Add the registered user to the model for display
        model.addAttribute("signup", registeredUser);
        // Redirect to signup summary page
        return "user/summary";
    }
}
