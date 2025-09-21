package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
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
@RequestMapping("/employee")
public class EmployeeController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    
    @Autowired
    private EmployeeService employeeService;
    
    /**
     * Display the employee registration form
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        logger.info("Displaying employee registration form");
        model.addAttribute("employee", new Employee());
        return "employee/registration";
    }
    
    /**
     * Handle the employee registration form submission
     */
    @PostMapping("/register")
    public String registerEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        logger.info("Processing employee registration for: {}", employee.getName());
        
        // Register the employee using the service
        Employee registeredEmployee = employeeService.registerEmployee(employee);
        
        // Add the registered employee to the model for display
        model.addAttribute("employee", registeredEmployee);
        
        // Redirect to the summary page
        return "employee/summary";
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
