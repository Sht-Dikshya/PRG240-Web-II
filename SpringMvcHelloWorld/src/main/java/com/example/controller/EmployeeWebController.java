package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Web Controller for Employee JSP pages
 * Handles web form requests and JSP view rendering
 */
@Controller
@RequestMapping("/employee")
public class EmployeeWebController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeWebController.class);
    
    @Autowired
    private EmployeeService employeeService;
    
    /**
     * Display employee list page
     */
    @GetMapping("/list")
    public String listEmployees(Model model) {
        logger.info("Web: Displaying employee list page");
        
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            model.addAttribute("employees", employees);
            model.addAttribute("employeeCount", employees.size());
            logger.info("Web: Loaded {} employees for list view", employees.size());
            return "employee/list";
        } catch (Exception e) {
            logger.error("Web: Error loading employee list: {}", e.getMessage(), e);
            model.addAttribute("error", "Error loading employees: " + e.getMessage());
            return "employee/list";
        }
    }
    
    /**
     * Display add employee form
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        logger.info("Web: Displaying add employee form");
        model.addAttribute("employee", new Employee());
        model.addAttribute("formTitle", "Add New Employee");
        model.addAttribute("formAction", "add");
        return "employee/form";
    }
    
    /**
     * Process add employee form submission
     */
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee, 
                             RedirectAttributes redirectAttributes) {
        logger.info("Web: Processing add employee form for: {}", employee.getName());
        
        try {
            Employee savedEmployee = employeeService.registerEmployee(employee);
            redirectAttributes.addFlashAttribute("success", 
                "Employee '" + savedEmployee.getName() + "' added successfully!");
            logger.info("Web: Employee added successfully: {}", savedEmployee.getName());
            return "redirect:/employee/list";
        } catch (Exception e) {
            logger.error("Web: Error adding employee: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error adding employee: " + e.getMessage());
            return "redirect:/employee/add";
        }
    }
    
    /**
     * Display edit employee form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, 
                              RedirectAttributes redirectAttributes) {
        logger.info("Web: Displaying edit form for employee ID: {}", id);
        
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                model.addAttribute("employee", employee);
                model.addAttribute("formTitle", "Edit Employee");
                model.addAttribute("formAction", "edit");
                logger.info("Web: Loaded employee for editing: {}", employee.getName());
                return "employee/form";
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Employee with ID " + id + " not found!");
                logger.warn("Web: Employee not found for editing: {}", id);
                return "redirect:/employee/list";
            }
        } catch (Exception e) {
            logger.error("Web: Error loading employee for edit: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error loading employee: " + e.getMessage());
            return "redirect:/employee/list";
        }
    }
    
    /**
     * Process edit employee form submission
     */
    @PostMapping("/edit")
    public String updateEmployee(@ModelAttribute Employee employee, 
                                RedirectAttributes redirectAttributes) {
        logger.info("Web: Processing edit employee form for ID: {}", employee.getEmployeeId());
        
        try {
            Employee updatedEmployee = employeeService.updateEmployee(
                employee.getEmployeeId(), employee);
            
            if (updatedEmployee != null) {
                redirectAttributes.addFlashAttribute("success", 
                    "Employee '" + updatedEmployee.getName() + "' updated successfully!");
                logger.info("Web: Employee updated successfully: {}", updatedEmployee.getName());
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Employee not found or could not be updated!");
                logger.warn("Web: Employee update failed - not found: {}", employee.getEmployeeId());
            }
            return "redirect:/employee/list";
        } catch (Exception e) {
            logger.error("Web: Error updating employee: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error updating employee: " + e.getMessage());
            return "redirect:/employee/edit/" + employee.getEmployeeId();
        }
    }
    
    /**
     * Display employee details
     */
    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable("id") Long id, Model model, 
                              RedirectAttributes redirectAttributes) {
        logger.info("Web: Displaying employee details for ID: {}", id);
        
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                model.addAttribute("employee", employee);
                logger.info("Web: Loaded employee details: {}", employee.getName());
                return "employee/detail";
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Employee with ID " + id + " not found!");
                logger.warn("Web: Employee not found for viewing: {}", id);
                return "redirect:/employee/list";
            }
        } catch (Exception e) {
            logger.error("Web: Error loading employee details: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error loading employee details: " + e.getMessage());
            return "redirect:/employee/list";
        }
    }
    
    /**
     * Delete employee
     */
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id, 
                                RedirectAttributes redirectAttributes) {
        logger.info("Web: Processing delete employee request for ID: {}", id);
        
        try {
            boolean deleted = employeeService.deleteEmployee(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", 
                    "Employee deleted successfully!");
                logger.info("Web: Employee deleted successfully: {}", id);
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Employee not found or could not be deleted!");
                logger.warn("Web: Employee deletion failed - not found: {}", id);
            }
            return "redirect:/employee/list";
        } catch (Exception e) {
            logger.error("Web: Error deleting employee: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error deleting employee: " + e.getMessage());
            return "redirect:/employee/list";
        }
    }
    
    /**
     * Home page redirect to employee list
     */
    @GetMapping("/")
    public String home() {
        logger.info("Web: Redirecting to employee list");
        return "redirect:/employee/list";
    }
}
