package com.example.service;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of EmployeeService using JDBC DAO
 * Provides basic CRUD operations with comprehensive logging
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    private final EmployeeDAO employeeDAO;
    
    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    
    @Override
    public Employee registerEmployee(Employee employee) {
        logger.info("=== EMPLOYEE REGISTRATION PROCESS STARTED ===");
        logger.info("Processing registration for employee: Name={}, Email={}, Position={}", 
                   employee.getName(), employee.getEmail(), employee.getPosition());
        
        try {
            // Validate employee data
            logger.info("Validating employee data...");
            validateEmployee(employee);
            logger.info("Employee data validation successful");
            
            // Save employee using DAO
            logger.info("Calling DAO to save employee to database...");
            Employee savedEmployee = employeeDAO.save(employee);
            
            logger.info("=== EMPLOYEE REGISTRATION PROCESS SUCCESSFUL ===");
            logger.info("Employee registration completed successfully: ID={}, Name={}", 
                       savedEmployee.getEmployeeId(), savedEmployee.getName());
            logger.info("Employee details saved: ID={}, Name={}, Email={}, Position={}", 
                       savedEmployee.getEmployeeId(), savedEmployee.getName(), 
                       savedEmployee.getEmail(), savedEmployee.getPosition());
            logger.info("=== EMPLOYEE REGISTRATION PROCESS COMPLETED ===");
            
            return savedEmployee;
            
        } catch (DataAccessException e) {
            logger.error("=== EMPLOYEE REGISTRATION PROCESS FAILED - DATABASE ERROR ===");
            logger.error("Database error during employee registration: {}", employee.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Root cause: {}", e.getCause());
            logger.error("=== EMPLOYEE REGISTRATION PROCESS FAILED ===");
            throw new RuntimeException("Database error occurred during registration", e);
        } catch (IllegalArgumentException e) {
            logger.error("=== EMPLOYEE REGISTRATION PROCESS FAILED - VALIDATION ERROR ===");
            logger.error("Data validation error during employee registration: {}", employee.getName());
            logger.error("Validation error: {}", e.getMessage());
            logger.error("Employee data: Name={}, Email={}, Contact={}, Position={}, Address={}",
                       employee.getName(), employee.getEmail(), 
                       employee.getContactNumber(), employee.getPosition(), employee.getAddress());
            logger.error("=== EMPLOYEE REGISTRATION PROCESS FAILED ===");
            throw new RuntimeException("Data validation error: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("=== EMPLOYEE REGISTRATION PROCESS FAILED - UNEXPECTED ERROR ===");
            logger.error("Unexpected error during employee registration: {}", employee.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Stack trace: ", e);
            logger.error("=== EMPLOYEE REGISTRATION PROCESS FAILED ===");
            throw new RuntimeException("An unexpected error occurred during registration", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        logger.info("Retrieving all employees");
        
        try {
            List<Employee> employees = employeeDAO.findAll();
            logger.info("Successfully retrieved {} employees", employees.size());
            return employees;
            
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving all employees", e);
            throw new RuntimeException("Database error while retrieving employees", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        logger.info("Retrieving employee by ID: {}", id);
        
        try {
            Optional<Employee> employee = employeeDAO.findById(id);
            
            if (employee.isPresent()) {
                logger.info("Employee found: {}", employee.get().getName());
                return employee.get();
            } else {
                logger.info("Employee with ID {} not found", id);
                return null;
            }
            
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving employee by ID: {}", id, e);
            throw new RuntimeException("Database error while retrieving employee", e);
        }
    }
    
    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        logger.info("Starting employee update process for ID: {}", id);
        
        try {
            // Validate employee data
            validateEmployee(employee);
            
            // Check if employee exists
            Optional<Employee> existingEmployee = employeeDAO.findById(id);
            if (existingEmployee.isEmpty()) {
                logger.warn("Employee update failed - employee not found: {}", id);
                return null;
            }
            
            // Set the ID for the employee to update
            employee.setEmployeeId(id);
            
            // Update employee using DAO
            Employee updatedEmployee = employeeDAO.update(employee);
            
            logger.info("Employee update completed successfully: ID={}, Name={}", 
                       updatedEmployee.getEmployeeId(), updatedEmployee.getName());
            
            return updatedEmployee;
            
        } catch (DataAccessException e) {
            logger.error("Database error during employee update: ID={}", id, e);
            throw new RuntimeException("Database error occurred during update", e);
        } catch (Exception e) {
            logger.error("Unexpected error during employee update: ID={}", id, e);
            throw new RuntimeException("An unexpected error occurred during update", e);
        }
    }
    
    @Override
    public boolean deleteEmployee(Long id) {
        logger.info("Starting employee deletion process for ID: {}", id);
        
        try {
            // Check if employee exists
            Optional<Employee> existingEmployee = employeeDAO.findById(id);
            if (existingEmployee.isEmpty()) {
                logger.warn("Employee deletion failed - employee not found: {}", id);
                return false;
            }
            
            // Delete employee using DAO
            boolean deleted = employeeDAO.deleteById(id);
            
            if (deleted) {
                logger.info("Employee deletion completed successfully: ID={}", id);
            } else {
                logger.warn("Employee deletion failed: ID={}", id);
            }
            
            return deleted;
            
        } catch (DataAccessException e) {
            logger.error("Database error during employee deletion: ID={}", id, e);
            throw new RuntimeException("Database error occurred during deletion", e);
        }
    }
    
    /**
     * Validate employee data
     */
    private void validateEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name is required");
        }
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee email is required");
        }
        if (employee.getContactNumber() == null || employee.getContactNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee contact number is required");
        }
        if (employee.getPosition() == null || employee.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee position is required");
        }
        if (employee.getAddress() == null || employee.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee address is required");
        }
    }
}