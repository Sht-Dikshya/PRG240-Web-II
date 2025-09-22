package com.example.dao.impl;

import com.example.dao.UserDAO;
import com.example.model.User;
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
 * JDBC implementation of UserDAO
 * Provides database operations using Spring JDBC Template
 */
@Repository
public class UserDAOImpl implements UserDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    // SQL queries
    private static final String INSERT_SQL = """
        INSERT INTO user (name, email, contact_number, position, address)
        VALUES (?, ?, ?, ?, ?)
        """;
    
    private static final String SELECT_BY_ID_SQL = """
        SELECT user_id, name, email, contact_number, position, address
        FROM user WHERE user_id = ?
        """;
    
    private static final String SELECT_ALL_SQL = """
        SELECT user_id, name, email, contact_number, position, address
        FROM user ORDER BY user_id DESC
        """;
    
    private static final String UPDATE_SQL = """
        UPDATE user SET name = ?, email = ?, contact_number = ?, position = ?, address = ?
        WHERE user_id = ?
        """;
    
    private static final String DELETE_SQL = "DELETE FROM user WHERE user_id = ?";
    
    // RowMapper for User
    private static final RowMapper<User> USER_ROW_MAPPER = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setContactNumber(rs.getString("contact_number"));
            user.setPosition(rs.getString("position"));
            user.setAddress(rs.getString("address"));
            return user;
        }
    };
    
    @Override
    public User save(User user) {
        logger.info("=== DATA INSERTION EVENT STARTED ===");
        logger.info("Attempting to save user: Name={}, Email={}, Position={}",
                   user.getName(), user.getEmail(), user.getPosition());
        
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getContactNumber());
                ps.setString(4, user.getPosition());
                ps.setString(5, user.getAddress());
                logger.debug("Prepared statement created with parameters: name={}, email={}, contact={}, position={}, address={}",
                        user.getName(), user.getEmail(), user.getContactNumber(), user.getPosition(), user.getAddress());
                return ps;
            }, keyHolder);
            
            if (rowsAffected > 0) {
                Long generatedId = keyHolder.getKey().longValue();
                user.setUserId(generatedId);
                
                logger.info("=== DATA INSERTION EVENT SUCCESSFUL ===");
                logger.info("User saved successfully with ID: {}", generatedId);
                logger.info("User details - ID: {}, Name: {}, Email: {}, Position: {}",
                           generatedId, user.getName(), user.getEmail(), user.getPosition());
                logger.info("=== DATA INSERTION EVENT COMPLETED ===");
                return user;
            } else {
                logger.error("=== DATA INSERTION EVENT FAILED ===");
                logger.error("No rows affected while saving user: {}", user.getName());
                logger.error("This indicates a database constraint violation or connection issue");
                throw new RuntimeException("Failed to save user - no rows affected");
            }
            
        } catch (DataAccessException e) {
            logger.error("=== DATA INSERTION EVENT ERROR ===");
            logger.error("Database error while saving user: {}", user.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Root cause: {}", e.getCause());
            throw new RuntimeException("Database error while saving user", e);
        } catch (Exception e) {
            logger.error("=== DATA INSERTION EVENT UNEXPECTED ERROR ===");
            logger.error("Unexpected error while saving user: {}", user.getName());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Error message: {}", e.getMessage());
            logger.error("Stack trace: ", e);
            throw new RuntimeException("Unexpected error while saving user", e);
        }
    }
    
    @Override
    public Optional<User> findById(Long id) {
        logger.info("Finding user by ID: {}", id);
        
        try {
            User user = jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, USER_ROW_MAPPER, id);
            logger.info("User found: {}", user != null ? user.getName() : "null");
            return Optional.ofNullable(user);
            
        } catch (EmptyResultDataAccessException e) {
            logger.info("User with ID {} not found", id);
            return Optional.empty();
        } catch (DataAccessException e) {
            logger.error("Database error while finding user by ID: {}", id, e);
            throw new RuntimeException("Database error while finding user", e);
        }
    }
    
    @Override
    public List<User> findAll() {
        logger.info("Finding all users");
        
        try {
            List<User> users = jdbcTemplate.query(SELECT_ALL_SQL, USER_ROW_MAPPER);
            logger.info("Found {} users", users.size());
            return users;
            
        } catch (DataAccessException e) {
            logger.error("Database error while finding all users", e);
            throw new RuntimeException("Database error while finding all users", e);
        }
    }
    
    
    @Override
    public User update(User user) {
        logger.info("Updating user with ID: {}", user.getUserId());
        
        try {
            int rowsAffected = jdbcTemplate.update(UPDATE_SQL,
                    user.getName(),
                    user.getEmail(),
                    user.getContactNumber(),
                    user.getPosition(),
                    user.getAddress(),
                    user.getUserId()
            );
            
            if (rowsAffected > 0) {
                logger.info("User updated successfully: {}", user.getName());
                return user;
            } else {
                logger.error("User with ID {} not found for update", user.getUserId());
                throw new RuntimeException("User not found for update");
            }
            
        } catch (DataAccessException e) {
            logger.error("Database error while updating user: {}", user.getUserId(), e);
            throw new RuntimeException("Database error while updating user", e);
        }
    }
    
    @Override
    public boolean deleteById(Long id) {
        logger.info("Deleting user with ID: {}", id);
        
        try {
            int rowsAffected = jdbcTemplate.update(DELETE_SQL, id);
            
            if (rowsAffected > 0) {
                logger.info("User with ID {} deleted successfully", id);
                return true;
            } else {
                logger.info("User with ID {} not found for deletion", id);
                return false;
            }
            
        } catch (DataAccessException e) {
            logger.error("Database error while deleting user: {}", id, e);
            throw new RuntimeException("Database error while deleting user", e);
        }
    }
    
}
