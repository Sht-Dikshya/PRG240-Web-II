package com.example.service;

import com.example.model.Employee;
import java.util.List;

public interface EmployeeService {
    
    /**
     * Register a new employee
     * @param employee the employee to register
     * @return the registered employee
     */
    Employee registerEmployee(Employee employee);
    
    /**
     * Get all employees
     * @return list of all employees
     */
    List<Employee> getAllEmployees();
    
    /**
     * Get employee by ID
     * @param id the employee ID
     * @return the employee if found, null otherwise
     */
    Employee getEmployeeById(Integer id);
    
    /**
     * Update an existing employee
     * @param id the employee ID
     * @param employee the updated employee data
     * @return the updated employee if found, null otherwise
     */
    Employee updateEmployee(Integer id, Employee employee);
    
    /**
     * Delete an employee by ID
     * @param id the employee ID
     * @return true if employee was deleted, false if not found
     */
    boolean deleteEmployee(Integer id);
}
