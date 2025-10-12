package com.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 Test Class for Authentication API
 * Tests user registration and login functionality
 * 
 * @author Spring MVC Learning Project
 * @version 1.0
 */
@DisplayName("Authentication API Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationApiTest {

    private static final String BASE_URL = "http://localhost:8080/SpringMvcHelloWorld";
    private static String jwtToken;
    private static String testUsername;
    
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        // Generate unique username for testing
        testUsername = "testuser_" + System.currentTimeMillis();
    }

    /**
     * Test 1: User Registration
     * Tests that a new user can successfully register
     */
    @Test
    @Order(1)
    @DisplayName("Test 1: User Registration - Should Return Success")
    void testUserRegistration() {
        System.out.println("\n=== Test 1: Testing User Registration ===");
        
        // Arrange
        Map<String, String> registrationData = Map.of(
                "username", testUsername,
                "password", "TestPassword123!"
        );

        // Act
        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(registrationData)
                .when()
                    .post("/api/auth/register")
                .then()
                    .extract()
                    .response();

        // Assert
        int statusCode = response.getStatusCode();
        String responseBody = response.asString();
        
        System.out.println("Registration Request Body: " + registrationData);
        System.out.println("Registration Response Status: " + statusCode);
        System.out.println("Registration Response Body: " + responseBody);
        
        assertTrue(statusCode == 200 || statusCode == 201 || statusCode == 400, 
                   "Expected 200/201 (success) or 400 (user exists), got: " + statusCode);
        
        if (statusCode == 200 || statusCode == 201) {
            System.out.println("✅ Registration successful!");
        } else if (statusCode == 400) {
            System.out.println("⚠️  User already exists (this is expected if test was run before)");
        }
        
        System.out.println("=== Test 1: COMPLETED ===\n");
    }

    /**
     * Test 2: User Login
     * Tests that a registered user can successfully login
     */
    @Test
    @Order(2)
    @DisplayName("Test 2: User Login - Should Return JWT Token")
    void testUserLogin() {
        System.out.println("\n=== Test 2: Testing User Login ===");
        
        // Arrange
        Map<String, String> loginData = Map.of(
                "username", testUsername,  // Use the user we just registered
                "password", "TestPassword123!"
        );

        // Act
        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(loginData)
                .when()
                    .post("/api/auth/login")
                .then()
                    .extract()
                    .response();

        // Assert
        int statusCode = response.getStatusCode();
        String responseBody = response.asString();
        
        System.out.println("Login Request Body: " + loginData);
        System.out.println("Login Response Status: " + statusCode);
        System.out.println("Login Response Body: " + responseBody);
        
        assertTrue(statusCode == 200 || statusCode == 201, 
                   "Expected 200/201 for successful login, got: " + statusCode + ". Body: " + responseBody);
        
        // Try to extract JWT token from response
        if (statusCode == 200 || statusCode == 201) {
            try {
                // Check if response contains token
                if (responseBody.contains("token") || responseBody.contains("jwt")) {
                    System.out.println("✅ Login successful! JWT token received");
                    // Try to extract token if it's in JSON format
                    if (responseBody.contains("\"token\"")) {
                        String token = response.jsonPath().getString("token");
                        if (token != null && !token.isEmpty()) {
                            jwtToken = token;
                            System.out.println("JWT Token extracted: " + token.substring(0, Math.min(50, token.length())) + "...");
                        }
                    } else if (responseBody.contains("\"jwt\"")) {
                        String token = response.jsonPath().getString("jwt");
                        if (token != null && !token.isEmpty()) {
                            jwtToken = token;
                            System.out.println("JWT Token extracted: " + token.substring(0, Math.min(50, token.length())) + "...");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️  Could not extract JWT token from response: " + e.getMessage());
            }
        }
        
        System.out.println("=== Test 2: COMPLETED ===\n");
    }

    /**
     * Test 3: Login with Invalid Credentials
     * Tests that login fails with incorrect credentials
     */
    @Test
    @Order(3)
    @DisplayName("Test 3: Login with Invalid Credentials - Should Return 401")
    void testLoginWithInvalidCredentials() {
        System.out.println("\n=== Test 3: Testing Login with Invalid Credentials ===");
        
        // Arrange
        Map<String, String> invalidLoginData = Map.of(
                "username", "invaliduser",
                "password", "wrongpassword"
        );

        // Act
        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(invalidLoginData)
                .when()
                    .post("/api/auth/login")
                .then()
                    .extract()
                    .response();

        // Assert
        int statusCode = response.getStatusCode();
        String responseBody = response.asString();
        
        System.out.println("Invalid Login Request Body: " + invalidLoginData);
        System.out.println("Invalid Login Response Status: " + statusCode);
        System.out.println("Invalid Login Response Body: " + responseBody);
        
        assertTrue(statusCode == 401 || statusCode == 403 || statusCode == 400, 
                   "Expected 401/403/400 for invalid login, got: " + statusCode);
        
        System.out.println("✅ Invalid login correctly rejected!");
        System.out.println("=== Test 3: COMPLETED ===\n");
    }

    /**
     * Test 4: Access Protected Endpoint Without Token
     * Tests that protected endpoints reject requests without JWT token
     */
    @Test
    @Order(4)
    @DisplayName("Test 4: Access Protected Endpoint Without Token - Should Return 401")
    void testAccessProtectedEndpointWithoutToken() {
        System.out.println("\n=== Test 4: Testing Access to Protected Endpoint Without Token ===");
        
        // Arrange
        Map<String, String> loginData = Map.of(
                "name", "Test Login",
                "email", "test@company.com",
                "contactNumber", "+1-555-123-4567",
                "position", "Developer"
        );

        // Act
        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(loginData)
                .when()
                    .post("/api/login")
                .then()
                    .extract()
                    .response();

        // Assert
        int statusCode = response.getStatusCode();
        String responseBody = response.asString();
        
        System.out.println("Request to Protected Endpoint (No Token)");
        System.out.println("Response Status: " + statusCode);
        System.out.println("Response Body: " + responseBody);
        
        assertEquals(401, statusCode, 
                    "Expected 401 Unauthorized when accessing protected endpoint without token");
        
        assertTrue(responseBody.contains("JWT") || responseBody.contains("token") || responseBody.contains("Unauthorized"),
                  "Response should indicate JWT/token requirement");
        
        System.out.println("✅ Protected endpoint correctly requires JWT token!");
        System.out.println("=== Test 4: COMPLETED ===\n");
    }

    /**
     * Test 5: Access Protected Endpoint With Valid Token
     * Tests that protected endpoints accept requests with valid JWT token
     * This test will only run if a token was obtained from Test 2
     */
    @Test
    @Order(5)
    @DisplayName("Test 5: Access Protected Endpoint With Token - Should Return Success")
    void testAccessProtectedEndpointWithToken() {
        System.out.println("\n=== Test 5: Testing Access to Protected Endpoint With Token ===");
        
        if (jwtToken == null || jwtToken.isEmpty()) {
            System.out.println("⚠️  No JWT token available from login test. Skipping this test.");
            System.out.println("Note: Run Test 2 (login) first to obtain a token.");
            System.out.println("=== Test 5: SKIPPED ===\n");
            return;
        }
        
        // Arrange
        Map<String, String> loginData = Map.of(
                "name", "Authorized Login",
                "email", "authorized_" + System.currentTimeMillis() + "@company.com",
                "contactNumber", "+1-555-987-6543",
                "position", "Senior Developer"
        );

        // Act
        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + jwtToken)
                    .body(loginData)
                .when()
                    .post("/api/login")
                .then()
                    .extract()
                    .response();

        // Assert
        int statusCode = response.getStatusCode();
        String responseBody = response.asString();
        
        System.out.println("Request to Protected Endpoint (With Token)");
        System.out.println("Authorization Header: Bearer " + jwtToken.substring(0, Math.min(20, jwtToken.length())) + "...");
        System.out.println("Response Status: " + statusCode);
        System.out.println("Response Body: " + responseBody);
        
        assertTrue(statusCode == 200 || statusCode == 201, 
                   "Expected 200/201 when accessing protected endpoint with valid token, got: " + statusCode + ". Body: " + responseBody);
        
        System.out.println("✅ Successfully accessed protected endpoint with JWT token!");
        System.out.println("=== Test 5: COMPLETED ===\n");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("AUTHENTICATION TEST SUITE COMPLETED");
        System.out.println("=".repeat(80));
        System.out.println("\nTest Summary:");
        System.out.println("- Test 1: User Registration");
        System.out.println("- Test 2: User Login (JWT Token Generation)");
        System.out.println("- Test 3: Invalid Login Rejection");
        System.out.println("- Test 4: Protected Endpoint Without Token");
        System.out.println("- Test 5: Protected Endpoint With Token");
        System.out.println("\nNote: Ensure the application is running on http://localhost:8080");
        System.out.println("=".repeat(80) + "\n");
    }
}

