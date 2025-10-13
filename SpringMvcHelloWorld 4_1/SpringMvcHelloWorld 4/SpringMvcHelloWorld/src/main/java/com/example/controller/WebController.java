package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    /**
     * Display login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    /**
     * Display registration page
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    /**
     * Display login dashboard (protected by JWT)
     */
    @GetMapping("/login/dashboard")
    public String loginDashboard() {
        return "login/dashboard";
    }
    
}

