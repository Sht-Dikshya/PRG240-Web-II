package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT Utility class for token generation, validation, and extraction
 */
@Component
public class JwtUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    
    // JWT Secret Key - Must be at least 512 bits (64 characters) for HS512 algorithm
    // In production, this should be stored securely (environment variable, secret manager, etc.)
    private static final String SECRET_KEY = "mySecretKey1234567890123456789012345678901234567890123456789012345";
    
    // Token validity period (24 hours)
    private static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000; // 24 hours in milliseconds
    
    // Create secret key
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    
    /**
     * Generate JWT token for a given username
     * @param username the username to generate token for
     * @return JWT token string
     */
    public String generateToken(String username) {
        logger.debug("Generating JWT token for user: {}", username);
        
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }
    
    /**
     * Create JWT token with claims and subject
     * @param claims additional claims to include in token
     * @param subject the subject (username)
     * @return JWT token string
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_TOKEN_VALIDITY);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    /**
     * Extract username from JWT token
     * @param token JWT token
     * @return username from token
     */
    public String getUsernameFromToken(String token) {
        try {
            return getClaimFromToken(token, Claims::getSubject);
        } catch (Exception e) {
            logger.error("Error extracting username from token: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Extract expiration date from JWT token
     * @param token JWT token
     * @return expiration date
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    /**
     * Extract a specific claim from JWT token
     * @param token JWT token
     * @param claimsResolver function to extract specific claim
     * @param <T> type of claim
     * @return claim value
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Extract all claims from JWT token
     * @param token JWT token
     * @return all claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * Check if JWT token is expired
     * @param token JWT token
     * @return true if expired, false otherwise
     */
    public Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            boolean expired = expiration.before(new Date());
            if (expired) {
                logger.debug("Token is expired");
            }
            return expired;
        } catch (Exception e) {
            logger.error("Error checking token expiration: {}", e.getMessage());
            return true; // Consider invalid if we can't parse
        }
    }
    
    /**
     * Validate JWT token
     * @param token JWT token to validate
     * @return true if valid, false otherwise
     */
    public Boolean validateToken(String token) {
        try {
            // Check if token can be parsed and is not expired
            getAllClaimsFromToken(token); // Validate token structure
            boolean isValid = !isTokenExpired(token);
            
            if (isValid) {
                logger.debug("Token is valid");
            } else {
                logger.debug("Token validation failed");
            }
            
            return isValid;
        } catch (Exception e) {
            logger.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Validate JWT token for a specific user
     * @param token JWT token
     * @param username username to validate against
     * @return true if valid for user, false otherwise
     */
    public Boolean validateToken(String token, String username) {
        try {
            final String tokenUsername = getUsernameFromToken(token);
            boolean isValid = (username.equals(tokenUsername) && !isTokenExpired(token));
            
            if (isValid) {
                logger.debug("Token is valid for user: {}", username);
            } else {
                logger.debug("Token validation failed for user: {}", username);
            }
            
            return isValid;
        } catch (Exception e) {
            logger.error("Error validating token for user {}: {}", username, e.getMessage());
            return false;
        }
    }
    
    /**
     * Get token validity period in milliseconds
     * @return token validity period
     */
    public long getTokenValidityPeriod() {
        return JWT_TOKEN_VALIDITY;
    }
}


