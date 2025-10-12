Feature: User Authentication
  As a user of the system
  I want to register and login
  So that I can access protected resources

  Background:
    Given the authentication API is available at "http://localhost:8080/SpringMvcHelloWorld"

  Scenario: Successful User Registration
    When I register a new user with the following credentials:
      | username | testuser_cucumber |
      | password | TestPass123!      |
    Then the registration response status code should be 200
    And the registration response should indicate success
    And the registration response should contain message "User registered successfully"

  Scenario: Successful User Login
    Given a user exists with username "loginuser_cucumber" and password "LoginPass123!"
    When I login with username "loginuser_cucumber" and password "LoginPass123!"
    Then the login response status code should be 200 or 201
    And the login response should contain a JWT token
    And the login response should indicate success

  Scenario: Failed Login with Invalid Credentials
    When I attempt to login with username "invaliduser" and password "wrongpassword"
    Then the login response status code should be 401
    And the login response should contain message "Invalid username or password"

  Scenario: Access Protected Endpoint Without Token
    When I attempt to access protected endpoint "/api/login" without a token
    Then the protected endpoint response status code should be 401
    And the protected endpoint response should contain message "JWT token is required"

  Scenario: Access Protected Endpoint With Valid Token
    Given a user exists with username "protecteduser_cucumber" and password "ProtectedPass123!"
    And I have a valid JWT token for user "protecteduser_cucumber"
    When I access protected endpoint "/api/login" with valid token
    Then the protected endpoint response status code should be 200
    And the protected endpoint response should indicate success

  Scenario: Complete Authentication Flow
    When I register a new user with username "flowuser" and password "FlowPass123!"
    Then the registration should be successful
    When I login with username "flowuser" and password "FlowPass123!"
    Then the login should be successful
    And I should receive a JWT token
    When I use the JWT token to access protected endpoint "/api/login"
    Then I should be able to access the protected resource

