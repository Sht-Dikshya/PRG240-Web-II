package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Cucumber Step Definitions for Authentication Testing
 * This class contains the step definitions that map Gherkin steps to Java code
 * for testing user registration, login, and JWT authentication flows.
 * 
 * @author Spring MVC Learning Project
 * @version 1.0
 */
public class AuthenticationSteps {
    
    private Response response;
    private String baseUrl;
    private String jwtToken;
    private Map<String, String> registeredUsers = new HashMap<>();

    /**
     * Background step: Set up the API base URL
     */
    @Given("the authentication API is available at {string}")
    public void theAuthenticationAPIIsAvailableAt(String url) {
        this.baseUrl = url;
        RestAssured.baseURI = url;
        System.out.println("✓ Authentication API Base URL set to: " + url);
    }

    /**
     * Step: Register a new user with credentials from DataTable
     */
    @When("I register a new user with the following credentials:")
    public void iRegisterANewUserWithTheFollowingCredentials(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> credentials = dataTable.asMap(String.class, String.class);
        
        // Add timestamp to username to make it unique
        String username = credentials.get("username") + "_" + System.currentTimeMillis();
        String password = credentials.get("password");
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", password);
        
        System.out.println("\n=== Registering New User ===");
        System.out.println("Username: " + username);
        System.out.println("Request body: " + requestBody);
        
        response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/register")
                .then()
                .extract().response();
        
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        
        // Store registered user for later use
        registeredUsers.put(username, password);
    }

    /**
     * Step: Register a new user with specific username and password
     */
    @When("I register a new user with username {string} and password {string}")
    public void iRegisterANewUserWithUsernameAndPassword(String username, String password) {
        // Add timestamp to username to make it unique
        String uniqueUsername = username + "_" + System.currentTimeMillis();
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", uniqueUsername);
        requestBody.put("password", password);
        
        System.out.println("\n=== Registering New User ===");
        System.out.println("Username: " + uniqueUsername);
        
        response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/register")
                .then()
                .extract().response();
        
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        
        // Store registered user with BOTH the original name and the unique name mapping
        // This allows the scenario to reference the user by the original name
        registeredUsers.put(username, uniqueUsername + ":" + password);  // Store mapping for original name
        registeredUsers.put(uniqueUsername, password);  // Store password for unique name
    }

    /**
     * Step: Create a user that exists for login testing
     */
    @Given("a user exists with username {string} and password {string}")
    public void aUserExistsWithUsernameAndPassword(String username, String password) {
        // Add timestamp to username to make it unique
        String uniqueUsername = username + "_" + System.currentTimeMillis();
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", uniqueUsername);
        requestBody.put("password", password);
        
        System.out.println("\n=== Creating User for Testing ===");
        System.out.println("Username: " + uniqueUsername);
        
        Response registerResponse = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/register")
                .then()
                .extract().response();
        
        System.out.println("User Registration Status: " + registerResponse.getStatusCode());
        assertEquals(200, registerResponse.getStatusCode(), "User registration should succeed");
        
        // Store registered user (using original username as key for easy retrieval)
        registeredUsers.put(username, uniqueUsername + ":" + password);
    }

    /**
     * Step: Login with username and password
     */
    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
        // Check if this user was registered in a previous step
        String actualCredentials = registeredUsers.get(username);
        String actualUsername = username;
        String actualPassword = password;
        
        if (actualCredentials != null) {
            if (actualCredentials.contains(":")) {
                // Format: "actualUsername:actualPassword"
                String[] parts = actualCredentials.split(":");
                actualUsername = parts[0];
                actualPassword = parts[1];
            } else {
                // If it's just a stored password, the key IS the actual username
                actualPassword = actualCredentials;
            }
        }
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", actualUsername);
        requestBody.put("password", actualPassword);
        
        System.out.println("\n=== Logging In User ===");
        System.out.println("Username: " + actualUsername);
        
        response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response();
        
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        
        // Extract JWT token if login is successful
        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            jwtToken = response.jsonPath().getString("token");
            if (jwtToken != null) {
                System.out.println("JWT Token received: " + jwtToken.substring(0, Math.min(30, jwtToken.length())) + "...");
            }
        }
    }

    /**
     * Step: Attempt login with invalid credentials
     */
    @When("I attempt to login with username {string} and password {string}")
    public void iAttemptToLoginWithUsernameAndPassword(String username, String password) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", password);
        
        System.out.println("\n=== Attempting Login (Expected to Fail) ===");
        System.out.println("Username: " + username);
        
        response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response();
        
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }

    /**
     * Step: Get valid JWT token for a user
     */
    @Given("I have a valid JWT token for user {string}")
    public void iHaveAValidJWTTokenForUser(String username) {
        // Get actual credentials
        String actualCredentials = registeredUsers.get(username);
        assertNotNull(actualCredentials, "User must be registered first");
        
        String[] parts = actualCredentials.split(":");
        String actualUsername = parts[0];
        String actualPassword = parts[1];
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", actualUsername);
        requestBody.put("password", actualPassword);
        
        System.out.println("\n=== Obtaining JWT Token ===");
        System.out.println("Username: " + actualUsername);
        
        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response();
        
        assertEquals(200, loginResponse.getStatusCode(), "Login should succeed");
        jwtToken = loginResponse.jsonPath().getString("token");
        assertNotNull(jwtToken, "JWT token should be present");
        System.out.println("JWT Token obtained: " + jwtToken.substring(0, Math.min(30, jwtToken.length())) + "...");
    }

    /**
     * Step: Access protected endpoint without token
     */
    @When("I attempt to access protected endpoint {string} without a token")
    public void iAttemptToAccessProtectedEndpointWithoutAToken(String endpoint) {
        System.out.println("\n=== Accessing Protected Endpoint Without Token ===");
        System.out.println("Endpoint: " + endpoint);
        
        Map<String, String> loginData = new HashMap<>();
        loginData.put("name", "Test Login");
        loginData.put("email", "test@example.com");
        loginData.put("contactNumber", "+1-555-000-0000");
        loginData.put("position", "Tester");

        response = given()
                .contentType(ContentType.JSON)
                .body(loginData)
                .when()
                .post(endpoint)
                .then()
                .extract().response();

        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }

    /**
     * Step: Access protected endpoint with valid token
     */
    @When("I access protected endpoint {string} with valid token")
    public void iAccessProtectedEndpointWithValidToken(String endpoint) {
        assertNotNull(jwtToken, "JWT token must be available");

        System.out.println("\n=== Accessing Protected Endpoint With Token ===");
        System.out.println("Endpoint: " + endpoint);
        System.out.println("Using JWT Token: " + jwtToken.substring(0, Math.min(30, jwtToken.length())) + "...");

        Map<String, String> loginData = new HashMap<>();
        loginData.put("name", "Authorized Login");
        loginData.put("email", "authorized_" + System.currentTimeMillis() + "@example.com");
        loginData.put("contactNumber", "+1-555-999-9999");
        loginData.put("position", "Senior Developer");

        response = given()
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(ContentType.JSON)
                .body(loginData)
                .when()
                .post(endpoint)
                .then()
                .extract().response();
        
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }

    /**
     * Step: Use JWT token to access protected endpoint
     */
    @When("I use the JWT token to access protected endpoint {string}")
    public void iUseTheJWTTokenToAccessProtectedEndpoint(String endpoint) {
        assertNotNull(jwtToken, "JWT token must be available");
        iAccessProtectedEndpointWithValidToken(endpoint);
    }

    // ==================== THEN/AND ASSERTIONS ====================

    @Then("the registration response status code should be {int}")
    public void theRegistrationResponseStatusCodeShouldBe(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode(), 
            "Expected status " + expectedStatus + " but got " + response.getStatusCode());
        System.out.println("✓ Registration status code is " + expectedStatus);
    }

    @And("the registration response should indicate success")
    public void theRegistrationResponseShouldIndicateSuccess() {
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("success") && responseBody.contains("true"), 
            "Response should indicate success");
        System.out.println("✓ Registration indicates success");
    }

    @And("the registration response should contain message {string}")
    public void theRegistrationResponseShouldContainMessage(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        assertEquals(expectedMessage, actualMessage, "Message should match");
        System.out.println("✓ Registration message: " + actualMessage);
    }

    @Then("the registration should be successful")
    public void theRegistrationShouldBeSuccessful() {
        assertEquals(200, response.getStatusCode(), "Registration should return 200");
        assertTrue(response.jsonPath().getBoolean("success"), "Registration should indicate success");
        System.out.println("✓ Registration successful");
    }

    @Then("the login response status code should be {int} or {int}")
    public void theLoginResponseStatusCodeShouldBeOr(int status1, int status2) {
        int actualStatus = response.getStatusCode();
        assertTrue(actualStatus == status1 || actualStatus == status2, 
            "Expected status " + status1 + " or " + status2 + " but got " + actualStatus);
        System.out.println("✓ Login status code is " + actualStatus);
    }

    @Then("the login response status code should be {int}")
    public void theLoginResponseStatusCodeShouldBe(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode(), 
            "Expected status " + expectedStatus + " but got " + response.getStatusCode());
        System.out.println("✓ Login status code is " + expectedStatus);
    }

    @And("the login response should contain a JWT token")
    public void theLoginResponseShouldContainAJWTToken() {
        String token = response.jsonPath().getString("token");
        assertNotNull(token, "JWT token should be present in response");
        assertFalse(token.isEmpty(), "JWT token should not be empty");
        System.out.println("✓ JWT token is present");
    }

    @And("the login response should indicate success")
    public void theLoginResponseShouldIndicateSuccess() {
        assertTrue(response.jsonPath().getBoolean("success"), "Login should indicate success");
        System.out.println("✓ Login indicates success");
    }

    @And("the login response should contain message {string}")
    public void theLoginResponseShouldContainMessage(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        assertTrue(actualMessage.contains(expectedMessage), 
            "Expected message to contain: " + expectedMessage + ", but got: " + actualMessage);
        System.out.println("✓ Login message: " + actualMessage);
    }

    @Then("the login should be successful")
    public void theLoginShouldBeSuccessful() {
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 201, "Login should return 200 or 201");
        assertTrue(response.jsonPath().getBoolean("success"), "Login should indicate success");
        System.out.println("✓ Login successful");
    }

    @And("I should receive a JWT token")
    public void iShouldReceiveAJWTToken() {
        assertNotNull(jwtToken, "JWT token should be available");
        assertFalse(jwtToken.isEmpty(), "JWT token should not be empty");
        System.out.println("✓ JWT token received");
    }

    @Then("the protected endpoint response status code should be {int}")
    public void theProtectedEndpointResponseStatusCodeShouldBe(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode(), 
            "Expected status " + expectedStatus + " but got " + response.getStatusCode());
        System.out.println("✓ Protected endpoint status code is " + expectedStatus);
    }

    @And("the protected endpoint response should contain message {string}")
    public void theProtectedEndpointResponseShouldContainMessage(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        assertTrue(actualMessage.contains(expectedMessage), 
            "Expected message to contain: " + expectedMessage + ", but got: " + actualMessage);
        System.out.println("✓ Protected endpoint message: " + actualMessage);
    }

    @And("the protected endpoint response should indicate success")
    public void theProtectedEndpointResponseShouldIndicateSuccess() {
        assertTrue(response.jsonPath().getBoolean("success"), 
            "Protected endpoint should indicate success");
        System.out.println("✓ Protected endpoint indicates success");
    }

    @Then("I should be able to access the protected resource")
    public void iShouldBeAbleToAccessTheProtectedResource() {
        assertEquals(200, response.getStatusCode(), "Should be able to access protected resource");
        assertTrue(response.jsonPath().getBoolean("success"), "Access should be successful");
        System.out.println("✓ Successfully accessed protected resource");
    }
}

