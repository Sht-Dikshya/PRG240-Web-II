package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
    
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    /**
     * Display the home page
     */
    @GetMapping("/")
    public RedirectView home() {
        logger.info("Displaying home page");
        return new RedirectView("/SpringMvcHelloWorld/index.html");
    }
    
    /**
     * Display the API test page
     */
    @GetMapping("/api-test")
    public RedirectView apiTest() {
        logger.info("Displaying API test page");
        return new RedirectView("/SpringMvcHelloWorld/index.html");
    }
}
