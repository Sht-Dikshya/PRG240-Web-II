package com.example.dao.impl;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of EmployeeDAO
 * Provides database operations using Spring JDBC Template
 */
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    // SQL queries
    private static final String INSERT_SQL = """
        INSERT INTO employee (name, email, contact_number, position, address)
        VALUES (?, ?, ?, ?, ?)
        """;
    
    private static final String SELECT_BY_ID_SQL = """
        SELECT employee_id, name, email, contact_number, position, address
        FROM employee WHERE employee_id = ?
        """;
    
    private static final String SELECT_ALL_SQL = """
        SELECT employee_id, name, email, contact_number, position, address
        FROM employee ORDER BY employee_id DESC
        """;
    
    private static final String UPDATE_SQL = """
        UPDATE employee SET name = ?, email = ?, contact_number = ?, position = ?, address = ?
        WHERE employee_id = ?
        """;
    
    private static final String DELETE_SQL = "DELETE FROM employee WHERE employee_id = ?";
    
    // RowMapper for Employee
    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = new RowMapper<Employee>() {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setEmployeeId(rs.getLong("employee_id"));
            employee.setName(rs.getString("name"));
            employee.setEmail(rs.getString("email"));
            employee.setContactNumber(rs.getString("contact_number"));
            employee.setPosition(rs.getString("position"));
            employee.setAddress(rs.getString("address"));
            return employee;
        }
    };
    
    @Override
    public Employee save(Employee employee) {
        logger.info("=== DATA INSERTION EVENT STARTED ===");
        logger.info("Attempting to save employee: Name={}, Email={}, Position={}", 
                   employee.getName(), employee.getEmail(), employee.getPosition());
        
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, employee.getName());
                ps.setString(2, employee.getEmail());
                ps.setString(3, employee.getContactNumber());
                ps.setString(4, employee.getPosition());
                ps.setString(5, employee.getAddress());
                logger.debug("Prepared statement created with parameters: name={}, email={}, contact={}, position={}, address={}",
                           employee.getName(), employee.getEmail(), employee.getContactNumber(), employee.getPosition(), employee.getAddress());
                return ps;
            }, keyHolder);
            
            if (rowsAffected > 0) {
                Long generatedId = keyHolder.getKey().longValue();
                employee.setEmployeeId(generatedId);
                
                logger.info("=== DATA INSERTION EVENT SUCCESSFUL ===");
                logger.info("Employee saved successfully with ID: {}", generatedId);
                logger.info("Employee details - ID: {}, Name: {}, Email: {}, Position: {}", 
                           generatedId, employee.getName(), employee.getEmail(), employee.getPosition());
                logger.info("=== DATA INSERTION EVENT COMPLETED ===");
                return employee;
            } else {
                logger.error("=== DATA INSERTION EVENT FAILED ===");
                logger.error("No rows affected while saving employee: {}", employee.getName());
                logger.error("This indicates a database constraint violation or connection issue");
                throw new RuntimeException("Failed to save employee - no rows affected");
            }
            
        } catch (DataAccessException e) {
            logger.error("=== DATA INSERTION EVENT ERROR ===");
            logger.error("Database error while saving employee: {}", employee.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Root cause: {}", e.getCause());
            throw new RuntimeException("Database error while saving employee", e);
        } catch (Exception e) {
            logger.error("=== DATA INSERTION EVENT UNEXPECTED ERROR ===");
            logger.error("Unexpected error while saving employee: {}", employee.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Stack trace: ", e);
            throw new RuntimeException("Unexpected error while saving employee", e);
        }
    }
    
    @Override
    public Optional<Employee> findById(Long id) {
        logger.info("Finding employee by ID: {}", id);
        
        try {
            Employee employee = jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, EMPLOYEE_ROW_MAPPER, id);
            logger.info("Employee found: {}", employee != null ? employee.getName() : "null");
            return Optional.ofNullable(employee);
            
        } catch (EmptyResultDataAccessException e) {
            logger.info("Employee with ID {} not found", id);
            return Optional.empty();
        } catch (DataAccessException e) {
            logger.error("Database error while finding employee by ID: {}", id, e);
            throw new RuntimeException("Database error while finding employee", e);
        }
    }
    
    @Override
    public List<Employee> findAll() {
        logger.info("Finding all employees");
        
        try {
            List<Employee> employees = jdbcTemplate.query(SELECT_ALL_SQL, EMPLOYEE_ROW_MAPPER);
            logger.info("Found {} employees", employees.size());
            return employees;
            
        } catch (DataAccessException e) {
            logger.error("Database error while finding all employees", e);
            throw new RuntimeException("Database error while finding all employees", e);
        }
    }
    
    
    @Override
    public Employee update(Employee employee) {
        logger.info("Updating employee with ID: {}", employee.getEmployeeId());
        
        try {
            int rowsAffected = jdbcTemplate.update(UPDATE_SQL,
                employee.getName(),
                employee.getEmail(),
                employee.getContactNumber(),
                employee.getPosition(),
                employee.getAddress(),
                employee.getEmployeeId()
            );
            
            if (rowsAffected > 0) {
                logger.info("Employee updated successfully: {}", employee.getName());
                return employee;
            } else {
                logger.error("Employee with ID {} not found for update", employee.getEmployeeId());
                throw new RuntimeException("Employee not found for update");
            }
            
        } catch (DataAccessException e) {
            logger.error("Database error while updating employee: {}", employee.getEmployeeId(), e);
            throw new RuntimeException("Database error while updating employee", e);
        }
    }
    
    @Override
    public boolean deleteById(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        
        try {
            int rowsAffected = jdbcTemplate.update(DELETE_SQL, id);
            
            if (rowsAffected > 0) {
                logger.info("Employee with ID {} deleted successfully", id);
                return true;
            } else {
                logger.info("Employee with ID {} not found for deletion", id);
                return false;
            }
            
        } catch (DataAccessException e) {
            logger.error("Database error while deleting employee: {}", id, e);
            throw new RuntimeException("Database error while deleting employee", e);
        }
    }
    
}
