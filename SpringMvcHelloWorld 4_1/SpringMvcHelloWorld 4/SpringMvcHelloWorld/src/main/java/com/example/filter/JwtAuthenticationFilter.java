package com.example.filter;

import com.example.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        logger.info("Processing request: {} {}", method, requestURI);
        
        // Skip authentication for public endpoints
        if (isPublicEndpoint(requestURI)) {
            logger.info("Skipping authentication for public endpoint: {} {}", method, requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        
        logger.info("Endpoint {} {} requires authentication", method, requestURI);
        
        try {
            String token = extractTokenFromRequest(request);
            
            if (token == null) {
                logger.warn("No JWT token found in request to: {}", requestURI);
                sendErrorResponse(response, "JWT token is required", HttpStatus.UNAUTHORIZED);
                return;
            }
            
            // Validate token
            if (!jwtUtil.validateToken(token)) {
                logger.warn("Invalid JWT token in request to: {}", requestURI);
                sendErrorResponse(response, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
                return;
            }
            
            // Check if token is expired
            if (jwtUtil.isTokenExpired(token)) {
                logger.warn("Expired JWT token in request to: {}", requestURI);
                sendErrorResponse(response, "JWT token has expired", HttpStatus.UNAUTHORIZED);
                return;
            }
            
            // Extract username and set authentication
            String username = jwtUtil.getUsernameFromToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.debug("Setting authentication for user: {}", username);
                
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                        username, 
                        null, 
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );
                
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            
            logger.debug("JWT authentication successful for user: {} on endpoint: {}", username, requestURI);
            filterChain.doFilter(request, response);
            
        } catch (Exception e) {
            logger.error("Error processing JWT authentication: {}", e.getMessage(), e);
            sendErrorResponse(response, "Authentication error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Extract JWT token from request header
     * @param request HTTP request
     * @return JWT token or null if not found
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        // Also check for token in request parameter (for form submissions)
        String tokenParam = request.getParameter("token");
        if (tokenParam != null && !tokenParam.isEmpty()) {
            return tokenParam;
        }
        
        return null;
    }
    
    /**
     * Check if the endpoint is public (doesn't require authentication)
     * @param requestURI the request URI
     * @return true if public endpoint
     */
    private boolean isPublicEndpoint(String requestURI) {
        logger.info("Checking if endpoint is public: {}", requestURI);
        
        boolean isPublic = requestURI.startsWith("/api/auth/") || 
               requestURI.startsWith("/SpringMvcHelloWorld/api/auth/") ||
               requestURI.equals("/") ||
               requestURI.equals("/SpringMvcHelloWorld/") ||
               requestURI.equals("/login") ||
               requestURI.equals("/register") ||
               requestURI.equals("/SpringMvcHelloWorld/login") ||
               requestURI.equals("/SpringMvcHelloWorld/register") ||
               requestURI.startsWith("/login/") ||
               requestURI.startsWith("/SpringMvcHelloWorld/login/") ||
               requestURI.startsWith("/resources/") ||
               requestURI.startsWith("/SpringMvcHelloWorld/resources/") ||
               requestURI.endsWith(".css") ||
               requestURI.endsWith(".js") ||
               requestURI.endsWith(".html") ||
               requestURI.endsWith(".jsp") ||
               requestURI.endsWith(".jspx");
               
        logger.info("Endpoint {} is public: {}", requestURI, isPublic);
        logger.info("Pattern checks: startsWith('/api/auth/'): {}, startsWith('/SpringMvcHelloWorld/api/auth/'): {}", 
                   requestURI.startsWith("/api/auth/"), requestURI.startsWith("/SpringMvcHelloWorld/api/auth/"));
        return isPublic;
    }
    
    /**
     * Send error response with JSON format
     * @param response HTTP response
     * @param message error message
     * @param status HTTP status
     * @throws IOException if response writing fails
     */
    private void sendErrorResponse(HttpServletResponse response, String message, HttpStatus status) 
            throws IOException {
        
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        errorResponse.put("status", status.value());
        
        String jsonResponse = "{\"success\":false,\"message\":\"" + message + "\",\"status\":" + status.value() + "}";
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
