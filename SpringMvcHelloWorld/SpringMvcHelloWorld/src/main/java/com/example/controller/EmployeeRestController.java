package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeRestController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);
    
    @Autowired
    private EmployeeService employeeService;
    
    /**
     * REST API endpoint to register employee with JSON data
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody Employee employee) {
        logger.info("API: Processing employee registration for: {}", employee.getName());
        
        try {
            // Register the employee using the service
            Employee registeredEmployee = employeeService.registerEmployee(employee);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Employee registered successfully");
            response.put("employee", registeredEmployee);
            
            logger.info("API: Employee registered successfully: {}", registeredEmployee.getName());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("API: Error registering employee: {}", e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Internal server error: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Get all employees
     */
    @GetMapping("/list")
    public ResponseEntity<?> getAllEmployees() {
        logger.info("API: Fetching all employees");
        
        try {
            // Get all employees from service
            List<Employee> employees = employeeService.getAllEmployees();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Employees retrieved successfully");
            response.put("count", employees.size());
            response.put("employees", employees);
            
            logger.info("API: Retrieved {} employees", employees.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("API: Error fetching employees: {}", e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error fetching employees: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Employee API is running");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get employee by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id) {
        logger.info("API: Getting employee by ID: {}", id);
        
        try {
            Employee employee = employeeService.getEmployeeById(id);
            
            if (employee != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Employee found");
                response.put("employee", employee);
                
                logger.info("API: Employee found: {}", employee.getName());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Employee not found with ID: " + id);
                
                logger.info("API: Employee not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            logger.error("API: Error getting employee by ID {}: {}", id, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error getting employee: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Update employee by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        logger.info("API: Updating employee with ID: {}", id);
        
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            
            if (updatedEmployee != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Employee updated successfully");
                response.put("employee", updatedEmployee);
                
                logger.info("API: Employee updated successfully: {}", updatedEmployee.getName());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Employee not found with ID: " + id);
                
                logger.info("API: Employee not found for update with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            logger.error("API: Error updating employee with ID {}: {}", id, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error updating employee: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Delete employee by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        logger.info("API: Deleting employee with ID: {}", id);
        
        try {
            boolean deleted = employeeService.deleteEmployee(id);
            
            if (deleted) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Employee deleted successfully");
                
                logger.info("API: Employee deleted successfully with ID: {}", id);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Employee not found with ID: " + id);
                
                logger.info("API: Employee not found for deletion with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            logger.error("API: Error deleting employee with ID {}: {}", id, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error deleting employee: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
