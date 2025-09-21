package com.example.dao;

import com.example.model.Employee;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Employee operations
 * Defines contract for database operations
 */
public interface EmployeeDAO {
    
    /**
     * Save a new employee to the database
     * @param employee the employee to save
     * @return the saved employee with generated ID
     */
    Employee save(Employee employee);
    
    /**
     * Find an employee by ID
     * @param id the employee ID
     * @return Optional containing the employee if found, empty otherwise
     */
    Optional<Employee> findById(Long id);
    
    /**
     * Find all employees
     * @return list of all employees
     */
    List<Employee> findAll();
    
    /**
     * Update an existing employee
     * @param employee the employee with updated data
     * @return the updated employee
     */
    Employee update(Employee employee);
    
    /**
     * Delete an employee by ID
     * @param id the employee ID
     * @return true if employee was deleted, false if not found
     */
    boolean deleteById(Long id);
}
