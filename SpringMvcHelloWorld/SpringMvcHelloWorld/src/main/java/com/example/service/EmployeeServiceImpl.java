package com.example.service;

import com.example.model.Employee;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    // Simple in-memory storage for demonstration
    private final List<Employee> employees = new ArrayList<>();
    
    @Override
    public Employee registerEmployee(Employee employee) {
        logger.info("Registering new employee: {}", employee.getName());
        
        // Add to in-memory list
        employees.add(employee);
        
        logger.info("Employee registered successfully: {}", employee);
        logger.info("Total employees: {}", employees.size());
        
        return employee;
    }
    
    @Override
    public List<Employee> getAllEmployees() {
        logger.info("Getting all employees");
        
        // Return the in-memory list
        logger.info("Retrieved {} employees", employees.size());
        return new ArrayList<>(employees); // Return a copy to prevent external modification
    }
    
    @Override
    public Employee getEmployeeById(Integer id) {
        logger.info("Getting employee by ID: {}", id);
        
        // Search for employee by ID in the in-memory list
        for (Employee employee : employees) {
            if (employee.getEmployeeId() != null && employee.getEmployeeId().equals(id)) {
                logger.info("Employee found: {}", employee.getName());
                return employee;
            }
        }
        
        logger.info("Employee with ID {} not found", id);
        return null;
    }
    
    @Override
    public Employee updateEmployee(Integer id, Employee updatedEmployee) {
        logger.info("Updating employee with ID: {}", id);
        
        // Find the employee by ID
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if (employee.getEmployeeId() != null && employee.getEmployeeId().equals(id)) {
                // Update the employee data
                updatedEmployee.setEmployeeId(id); // Ensure ID remains the same
                employees.set(i, updatedEmployee);
                
                logger.info("Employee updated successfully: {}", updatedEmployee.getName());
                return updatedEmployee;
            }
        }
        
        logger.info("Employee with ID {} not found for update", id);
        return null;
    }
    
    @Override
    public boolean deleteEmployee(Integer id) {
        logger.info("Deleting employee with ID: {}", id);
        
        // Find and remove the employee by ID
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if (employee.getEmployeeId() != null && employee.getEmployeeId().equals(id)) {
                Employee deletedEmployee = employees.remove(i);
                logger.info("Employee deleted successfully: {}", deletedEmployee.getName());
                logger.info("Remaining employees: {}", employees.size());
                return true;
            }
        }
        
        logger.info("Employee with ID {} not found for deletion", id);
        return false;
    }
}
