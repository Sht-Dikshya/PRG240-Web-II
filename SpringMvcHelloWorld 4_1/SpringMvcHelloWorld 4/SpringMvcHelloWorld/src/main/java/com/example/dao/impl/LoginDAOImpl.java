package com.example.dao.impl;

import com.example.dao.LoginDAO;
import com.example.model.Login;
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
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of LoginDAO
 * Provides database operations using Spring JDBC Template
 */
@Repository
public class LoginDAOImpl implements LoginDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginDAOImpl.class);
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public LoginDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    // SQL queries
    private static final String INSERT_SQL = """
        INSERT INTO login (name, email, contact_number, position)
        VALUES (?, ?, ?, ?)
        """;
    
    private static final String SELECT_BY_ID_SQL = """
        SELECT login_id, name, email, contact_number, position
        FROM login WHERE login_id = ?
        """;
    
    private static final String SELECT_ALL_SQL = """
        SELECT login_id, name, email, contact_number, position
        FROM login ORDER BY login_id DESC
        """;
    
    private static final String UPDATE_SQL = """
        UPDATE login SET name = ?, email = ?, contact_number = ?, position = ?
        WHERE login_id = ?
        """;
    
    private static final String DELETE_SQL = "DELETE FROM login WHERE login_id = ?";
    
    // RowMapper for Login
    private static final RowMapper<Login> Login_ROW_MAPPER = new RowMapper<Login>() {
        @Override
        public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
            Login login = new Login();
            login.setLoginId(rs.getLong("login_id"));
            login.setName(rs.getString("name"));
            login.setEmail(rs.getString("email"));
            login.setContactNumber(rs.getString("contact_number"));
            login.setPosition(rs.getString("position"));
            
            return login;
        }
    };
    
    @Override
    public Login save(Login login) {
        logger.info("=== DATA INSERTION EVENT STARTED ===");
        logger.info("Attempting to save login: Name={}, Email={}, Position={}", 
                   login.getName(), login.getEmail(), login.getPosition());
        
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, login.getName());
                ps.setString(2, login.getEmail());
                ps.setString(3, login.getContactNumber());
                ps.setString(4, login.getPosition());
                logger.debug("Prepared statement created with parameters: name={}, email={}, contact={}, position={}", 
                           login.getName(), login.getEmail(), login.getContactNumber(), login.getPosition());
                return ps;
            }, keyHolder);
            
            if (rowsAffected > 0) {
                Long generatedId = keyHolder.getKey().longValue();
                login.setLoginId(generatedId);
                
                logger.info("=== DATA INSERTION EVENT SUCCESSFUL ===");
                logger.info("Login saved successfully with ID: {}", generatedId);
                logger.info("Login details - ID: {}, Name: {}, Email: {}, Position: {}", 
                           generatedId, login.getName(), login.getEmail(), login.getPosition());
                logger.info("=== DATA INSERTION EVENT COMPLETED ===");
                return login;
            } else {
                logger.error("=== DATA INSERTION EVENT FAILED ===");
                logger.error("No rows affected while saving login: {}", login.getName());
                logger.error("This indicates a database constraint violation or connection issue");
                throw new RuntimeException("Failed to save login - no rows affected");
            }
            
        } catch (DataAccessException e) {
            logger.error("=== DATA INSERTION EVENT ERROR ===");
            logger.error("Database error while saving login: {}", login.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Root cause: {}", e.getCause());
            throw new RuntimeException("Database error while saving login", e);
        } catch (Exception e) {
            logger.error("=== DATA INSERTION EVENT UNEXPECTED ERROR ===");
            logger.error("Unexpected error while saving login: {}", login.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Stack trace: ", e);
            throw new RuntimeException("Unexpected error while saving login", e);
        }
    }
    
    @Override
    public Optional<Login> findById(Long id) {
        logger.info("Finding login by ID: {}", id);
        
        try {
            Login login = jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Login_ROW_MAPPER, id);
            logger.info("Login found: {}", login != null ? login.getName() : "null");
            return Optional.ofNullable(login);
            
        } catch (EmptyResultDataAccessException e) {
            logger.info("Login with ID {} not found", id);
            return Optional.empty();
        } catch (DataAccessException e) {
            logger.error("Database error while finding login by ID: {}", id, e);
            throw new RuntimeException("Database error while finding login", e);
        }
    }
    
    @Override
    public List<Login> findAll() {
        logger.info("Finding all logins");
        
        try {
            List<Login> logins = jdbcTemplate.query(SELECT_ALL_SQL, Login_ROW_MAPPER);
            logger.info("Found {} logins", logins.size());
            return logins;
            
        } catch (DataAccessException e) {
            logger.error("Database error while finding all logins", e);
            throw new RuntimeException("Database error while finding all logins", e);
        }
    }
    
    
    @Override
    public Login update(Login login) {
        logger.info("Updating login with ID: {}", login.getLoginId());
        
        try {
            int rowsAffected = jdbcTemplate.update(UPDATE_SQL,
                login.getName(),
                login.getEmail(),
                login.getContactNumber(),
                login.getPosition(),
                login.getLoginId()
            );
            
            if (rowsAffected > 0) {
                logger.info("Login updated successfully: {}", login.getName());
                return login;
            } else {
                logger.error("Login with ID {} not found for update", login.getLoginId());
                throw new RuntimeException("Login not found for update");
            }
            
        } catch (DataAccessException e) {
            logger.error("Database error while updating login: {}", login.getLoginId(), e);
            throw new RuntimeException("Database error while updating login", e);
        }
    }
    
    @Override
    public boolean deleteById(Long id) {
        logger.info("Deleting login with ID: {}", id);
        
        try {
            int rowsAffected = jdbcTemplate.update(DELETE_SQL, id);
            
            if (rowsAffected > 0) {
                logger.info("Login with ID {} deleted successfully", id);
                return true;
            } else {
                logger.info("Login with ID {} not found for deletion", id);
                return false;
            }
            
        } catch (DataAccessException e) {
            logger.error("Database error while deleting login: {}", id, e);
            throw new RuntimeException("Database error while deleting login", e);
        }
    }
    
}
