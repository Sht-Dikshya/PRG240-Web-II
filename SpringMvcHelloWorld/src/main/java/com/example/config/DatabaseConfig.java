package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Bean
    public DataSource dataSource() {
        logger.info("=== DATABASE CONNECTION INITIALIZATION STARTED ===");
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application.properties"));
            
            String driverClass = props.getProperty("spring.datasource.driver-class-name");
            String url = props.getProperty("spring.datasource.url");
            String username = props.getProperty("spring.datasource.username");
            
            logger.info("Database configuration loaded:");
            logger.info("Driver: {}", driverClass);
            logger.info("URL: {}", url);
            logger.info("Username: {}", username);
            
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(driverClass);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(props.getProperty("spring.datasource.password"));
            
            // Test the connection
            try (java.sql.Connection connection = dataSource.getConnection()) {
                logger.info("=== DATABASE CONNECTION TEST SUCCESSFUL ===");
                logger.info("Database connection established successfully");
                logger.info("Connection URL: {}", connection.getMetaData().getURL());
                logger.info("Database Product: {}", connection.getMetaData().getDatabaseProductName());
                logger.info("Database Version: {}", connection.getMetaData().getDatabaseProductVersion());
                logger.info("=== DATABASE CONNECTION INITIALIZATION COMPLETED ===");
            } catch (SQLException e) {
                logger.error("=== DATABASE CONNECTION TEST FAILED ===");
                logger.error("Failed to establish database connection: {}", e.getMessage());
                logger.error("SQL State: {}", e.getSQLState());
                logger.error("Error Code: {}", e.getErrorCode());
                throw new RuntimeException("Database connection test failed", e);
            }
            
            return dataSource;
        } catch (IOException e) {
            logger.error("=== DATABASE CONNECTION INITIALIZATION FAILED ===");
            logger.error("Failed to load database properties: {}", e.getMessage());
            logger.error("Error type: {}", e.getClass().getSimpleName());
            logger.error("Stack trace: ", e);
            throw new RuntimeException("Failed to load database properties", e);
        }
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application.properties"));
            
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource);
            em.setPackagesToScan("com.example.model");
            
            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            vendorAdapter.setDatabasePlatform(props.getProperty("spring.jpa.database-platform"));
            vendorAdapter.setShowSql(Boolean.parseBoolean(props.getProperty("spring.jpa.show-sql", "true")));
            em.setJpaVendorAdapter(vendorAdapter);
            
            Properties jpaProperties = new Properties();
            jpaProperties.setProperty("hibernate.hbm2ddl.auto", props.getProperty("spring.jpa.hibernate.ddl-auto"));
            jpaProperties.setProperty("hibernate.dialect", props.getProperty("spring.jpa.database-platform"));
            jpaProperties.setProperty("hibernate.show_sql", props.getProperty("spring.jpa.show-sql"));
            jpaProperties.setProperty("hibernate.format_sql", props.getProperty("spring.jpa.properties.hibernate.format_sql"));
            jpaProperties.setProperty("hibernate.use_sql_comments", props.getProperty("spring.jpa.properties.hibernate.use_sql_comments"));
            em.setJpaProperties(jpaProperties);
            
            return em;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JPA properties", e);
        }
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }
}
