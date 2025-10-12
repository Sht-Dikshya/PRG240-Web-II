package com.example.dao.impl;

import com.example.dao.UserDAO;
import com.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Repository
public class UserDAOImpl implements UserDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // RowMapper for User objects
    private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setEnabled(rs.getBoolean("enabled"));
            return user;
        }
    };
    
    @Override
    public User save(User user) {
        logger.info("Saving user to database: {}", user.getUsername());
        
        String sql = "INSERT INTO users (username, password, role, enabled) VALUES (?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setBoolean(4, user.isEnabled());
            return ps;
        }, keyHolder);
        
        if (rowsAffected > 0) {
            Long generatedId = keyHolder.getKey().longValue();
            user.setId(generatedId);
            logger.info("User saved successfully with ID: {}", generatedId);
        } else {
            logger.error("Failed to save user: {}", user.getUsername());
        }
        
        return user;
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        logger.info("Finding user by username: {}", username);
        
        String sql = "SELECT id, username, password, role, enabled FROM users WHERE username = ?";
        
        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, username);
            logger.info("User found: {}", user != null ? user.getUsername() : "null");
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.warn("User not found with username: {}", username);
            return Optional.empty();
        }
    }
    
    @Override
    public boolean existsByUsername(String username) {
        logger.info("Checking if user exists with username: {}", username);
        
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
            boolean exists = count != null && count > 0;
            logger.info("User exists check for '{}': {}", username, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Error checking user existence for username: {}", username, e);
            return false;
        }
    }
    
    @Override
    public Optional<User> findById(Long id) {
        logger.info("Finding user by ID: {}", id);
        
        String sql = "SELECT id, username, password, role, enabled FROM users WHERE id = ?";
        
        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, id);
            logger.info("User found: {}", user != null ? user.getUsername() : "null");
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.warn("User not found with ID: {}", id);
            return Optional.empty();
        }
    }
    
    @Override
    public List<User> findAll() {
        logger.info("Finding all users");
        
        String sql = "SELECT id, username, password, role, enabled FROM users ORDER BY id";
        
        try {
            List<User> users = jdbcTemplate.query(sql, userRowMapper);
            logger.info("Found {} users", users.size());
            return users;
        } catch (Exception e) {
            logger.error("Error finding all users", e);
            return List.of();
        }
    }
    
    @Override
    public User update(User user) {
        logger.info("Updating user: {}", user.getUsername());
        
        String sql = "UPDATE users SET username = ?, password = ?, role = ?, enabled = ? WHERE id = ?";
        
        int rowsAffected = jdbcTemplate.update(sql, 
            user.getUsername(), 
            user.getPassword(), 
            user.getRole(), 
            user.isEnabled(), 
            user.getId());
        
        if (rowsAffected > 0) {
            logger.info("User updated successfully: {}", user.getUsername());
        } else {
            logger.error("Failed to update user: {}", user.getUsername());
        }
        
        return user;
    }
    
    @Override
    public boolean deleteById(Long id) {
        logger.info("Deleting user by ID: {}", id);
        
        String sql = "DELETE FROM users WHERE id = ?";
        
        int rowsAffected = jdbcTemplate.update(sql, id);
        boolean deleted = rowsAffected > 0;
        
        if (deleted) {
            logger.info("User deleted successfully with ID: {}", id);
        } else {
            logger.warn("No user found with ID: {}", id);
        }
        
        return deleted;
    }
    
    @Override
    public boolean deleteByUsername(String username) {
        logger.info("Deleting user by username: {}", username);
        
        String sql = "DELETE FROM users WHERE username = ?";
        
        int rowsAffected = jdbcTemplate.update(sql, username);
        boolean deleted = rowsAffected > 0;
        
        if (deleted) {
            logger.info("User deleted successfully with username: {}", username);
        } else {
            logger.warn("No user found with username: {}", username);
        }
        
        return deleted;
    }
}





