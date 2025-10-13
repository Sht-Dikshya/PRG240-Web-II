package com.example.controller;

import com.example.model.Login;
import com.example.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Web Controller for Login JSP pages
 * Handles web form requests and JSP view rendering
 */
@Controller
@RequestMapping("/login")
public class LoginWebController {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginWebController.class);
    
    @Autowired
    private LoginService loginService;
    
    /**
     * Display login list page
     */
    @GetMapping("/list")
    public String listLogins(Model model) {
        logger.info("Web: Displaying login list page");
        
        try {
            List<Login> logins = loginService.getAllLogins();
            model.addAttribute("logins", logins);
            model.addAttribute("loginCount", logins.size());
            logger.info("Web: Loaded {} logins for list view", logins.size());
            return "login/list";
        } catch (Exception e) {
            logger.error("Web: Error loading login list: {}", e.getMessage(), e);
            model.addAttribute("error", "Error loading logins: " + e.getMessage());
            return "login/list";
        }
    }
    
    /**
     * Display add login form
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        logger.info("Web: Displaying add login form");
        model.addAttribute("login", new Login());
        model.addAttribute("formTitle", "Add New Login");
        model.addAttribute("formAction", "add");
        return "login/form";
    }
    
    /**
     * Process add login form submission
     */
    @PostMapping("/add")
    public String addLogin(@ModelAttribute Login login, 
                             RedirectAttributes redirectAttributes) {
        logger.info("Web: Processing add login form for: {}", login.getName());
        
        try {
            Login savedLogin = loginService.registerLogin(login);
            redirectAttributes.addFlashAttribute("success", 
                "Login '" + savedLogin.getName() + "' added successfully!");
            logger.info("Web: Login added successfully: {}", savedLogin.getName());
            return "redirect:/login/list";
        } catch (Exception e) {
            logger.error("Web: Error adding login: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error adding login: " + e.getMessage());
            return "redirect:/login/add";
        }
    }
    
    /**
     * Display edit login form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, 
                              RedirectAttributes redirectAttributes) {
        logger.info("Web: Displaying edit form for login ID: {}", id);
        
        try {
            Login login = loginService.getLoginById(id);
            if (login != null) {
                model.addAttribute("login", login);
                model.addAttribute("formTitle", "Edit Login");
                model.addAttribute("formAction", "edit");
                logger.info("Web: Loaded login for editing: {}", login.getName());
                return "login/form";
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Login with ID " + id + " not found!");
                logger.warn("Web: Login not found for editing: {}", id);
                return "redirect:/login/list";
            }
        } catch (Exception e) {
            logger.error("Web: Error loading login for edit: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error loading login: " + e.getMessage());
            return "redirect:/login/list";
        }
    }
    
    /**
     * Process edit login form submission
     */
    @PostMapping("/edit")
    public String updateLogin(@ModelAttribute Login login, 
                                RedirectAttributes redirectAttributes) {
        logger.info("Web: Processing edit login form for ID: {}", login.getLoginId());
        
        try {
            Login updatedLogin = loginService.updateLogin(
                login.getLoginId(), login);
            
            if (updatedLogin != null) {
                redirectAttributes.addFlashAttribute("success", 
                    "Login '" + updatedLogin.getName() + "' updated successfully!");
                logger.info("Web: Login updated successfully: {}", updatedLogin.getName());
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Login not found or could not be updated!");
                logger.warn("Web: Login update failed - not found: {}", login.getLoginId());
            }
            return "redirect:/login/list";
        } catch (Exception e) {
            logger.error("Web: Error updating login: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error updating login: " + e.getMessage());
            return "redirect:/login/edit/" + login.getLoginId();
        }
    }
    
    /**
     * Display login details
     */
    @GetMapping("/view/{id}")
    public String viewLogin(@PathVariable("id") Long id, Model model, 
                              RedirectAttributes redirectAttributes) {
        logger.info("Web: Displaying login details for ID: {}", id);
        
        try {
            Login login = loginService.getLoginById(id);
            if (login != null) {
                model.addAttribute("login", login);
                logger.info("Web: Loaded login details: {}", login.getName());
                return "login/detail";
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Login with ID " + id + " not found!");
                logger.warn("Web: Login not found for viewing: {}", id);
                return "redirect:/login/list";
            }
        } catch (Exception e) {
            logger.error("Web: Error loading login details: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error loading login details: " + e.getMessage());
            return "redirect:/login/list";
        }
    }
    
    /**
     * Delete login
     */
    @PostMapping("/delete/{id}")
    public String deleteLogin(@PathVariable("id") Long id, 
                                RedirectAttributes redirectAttributes) {
        logger.info("Web: Processing delete login request for ID: {}", id);
        
        try {
            boolean deleted = loginService.deleteLogin(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", 
                    "Login deleted successfully!");
                logger.info("Web: Login deleted successfully: {}", id);
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Login not found or could not be deleted!");
                logger.warn("Web: Login deletion failed - not found: {}", id);
            }
            return "redirect:/login/list";
        } catch (Exception e) {
            logger.error("Web: Error deleting login: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error deleting login: " + e.getMessage());
            return "redirect:/login/list";
        }
    }
    
    /**
     * Home page redirect to login list
     */
    @GetMapping("/")
    public String home() {
        logger.info("Web: Redirecting to login list");
        return "redirect:/login/list";
    }
}
