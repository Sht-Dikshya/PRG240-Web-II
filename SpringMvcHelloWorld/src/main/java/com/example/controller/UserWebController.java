package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.model.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Web Controller for User JSP pages
 * Handles web form requests and JSP view rendering
 */
@Controller
@RequestMapping("/user")
public class UserWebController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserWebController.class);
    
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showRegistrationForm(Model model) {
        logger.info("Displaying login user form");
        model.addAttribute("login", new Login());
        return "user/login";
    }

    /**
     * Display user list page
     */
    @GetMapping("/list")
    public String listUsers(Model model) {
        logger.info("Web: Displaying user list page");
        
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            model.addAttribute("userCount", users.size());
            logger.info("Web: Loaded {} users for list view", users.size());
            return "user/list";
        } catch (Exception e) {
            logger.error("Web: Error loading user list: {}", e.getMessage(), e);
            model.addAttribute("error", "Error loading users: " + e.getMessage());
            return "user/list";
        }
    }
    
    /**
     * Display add user form
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        logger.info("Web: Displaying add user form");
        model.addAttribute("user", new User());
        model.addAttribute("formTitle", "Add New User");
        model.addAttribute("formAction", "add");
        return "user/form";
    }
    
    /**
     * Process add user form submission
     */
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user,
                             RedirectAttributes redirectAttributes) {
        logger.info("Web: Processing add user form for: {}", user.getName());
        
        try {
            User savedUser = userService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", 
                "User '" + savedUser.getName() + "' added successfully!");
            logger.info("Web: User added successfully: {}", savedUser.getName());
            return "redirect:/user/list";
        } catch (Exception e) {
            logger.error("Web: Error adding user: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error adding user: " + e.getMessage());
            return "redirect:/user/add";
        }
    }
    
    /**
     * Display edit user form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, 
                              RedirectAttributes redirectAttributes) {
        logger.info("Web: Displaying edit form for user ID: {}", id);
        
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("formTitle", "Edit User");
                model.addAttribute("formAction", "edit");
                logger.info("Web: Loaded user for editing: {}", user.getName());
                return "user/form";
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "User with ID " + id + " not found!");
                logger.warn("Web: User not found for editing: {}", id);
                return "redirect:/user/list";
            }
        } catch (Exception e) {
            logger.error("Web: Error loading user for edit: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error loading user: " + e.getMessage());
            return "redirect:/user/list";
        }
    }
    
    /**
     * Process edit user form submission
     */
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user,
                                RedirectAttributes redirectAttributes) {
        logger.info("Web: Processing edit user form for ID: {}", user.getUserId());
        
        try {
            User updatedUser = userService.updateUser(
                    user.getUserId(), user);
            
            if (updatedUser != null) {
                redirectAttributes.addFlashAttribute("success", 
                    "User '" + updatedUser.getName() + "' updated successfully!");
                logger.info("Web: User updated successfully: {}", updatedUser.getName());
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "User not found or could not be updated!");
                logger.warn("Web: User update failed - not found: {}", user.getUserId());
            }
            return "redirect:/user/list";
        } catch (Exception e) {
            logger.error("Web: Error updating user: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error updating user: " + e.getMessage());
            return "redirect:/user/edit/" + user.getUserId();
        }
    }
    
    /**
     * Display user details
     */
    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable("id") Long id, Model model,
                              RedirectAttributes redirectAttributes) {
        logger.info("Web: Displaying user details for ID: {}", id);
        
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                model.addAttribute("user", user);
                logger.info("Web: Loaded user details: {}", user.getName());
                return "user/detail";
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "User with ID " + id + " not found!");
                logger.warn("Web: User not found for viewing: {}", id);
                return "redirect:/user/list";
            }
        } catch (Exception e) {
            logger.error("Web: Error loading user details: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error loading user details: " + e.getMessage());
            return "redirect:/user/list";
        }
    }
    
    /**
     * Delete user
     */
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id,
                                RedirectAttributes redirectAttributes) {
        logger.info("Web: Processing delete user request for ID: {}", id);
        
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", 
                    "User deleted successfully!");
                logger.info("Web: User deleted successfully: {}", id);
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "User not found or could not be deleted!");
                logger.warn("Web: User deletion failed - not found: {}", id);
            }
            return "redirect:/user/list";
        } catch (Exception e) {
            logger.error("Web: Error deleting user: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error deleting user: " + e.getMessage());
            return "redirect:/user/list";
        }
    }
    
    /**
     * Home page redirect to user list
     */
    @GetMapping("/")
    public String home() {
        logger.info("Web: Redirecting to user list");
        return "redirect:/user/list";
    }
}
