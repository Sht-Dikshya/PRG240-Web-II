
# Authentication and JWT in Spring MVC

## 1. Introduction
Authentication and authorization are essential security measures in web applications.  
- **Authentication** ensures that the user is who they claim to be (for example, logging in with a username and password).  
- **Authorization** determines what an authenticated user is allowed to do (for example, a regular user may view their profile, but only an administrator may access all users).  

Instead of traditional session-based authentication, modern applications often use **JWT (JSON Web Token)** for stateless authentication. With JWT, the server does not store session data; instead, a signed token is returned to the client after successful login, and this token must be provided with subsequent requests.  

---

## 2. Key Concepts  

### Authentication
Authentication is the process of verifying a user’s identity.  
- Example: A user provides a username and password.  
- If the credentials match what is stored in the database, the user is authenticated.  
- If they do not match, the server denies access.  

### Authorization
Authorization is the process of checking what resources or operations the authenticated user is allowed to access.  
- Example: A teacher and a student both log into the system. Both are authenticated users, but authorization ensures that the teacher can manage grades while the student can only view their own.  

### JSON Web Token (JWT)
A JSON Web Token is a compact and self-contained token format that is used to securely transmit information between the server and client.  

Structure of JWT:  
```
header.payload.signature
```

- **Header**: Contains metadata about the token, such as the type of token (JWT) and the signing algorithm (for example, HMAC SHA-256).  
- **Payload**: Contains claims. Claims are statements about the user, such as username or roles. It may also include an expiration time (`exp`) that defines how long the token is valid.  
- **Signature**: Used to verify that the token has not been altered. It is created using the header, payload, and a secret key.  

### Token Expiration
A JWT usually contains an `exp` (expiration time) claim. Once this time is passed, the token is considered invalid and the client must re-authenticate. Expiration is important to limit the lifetime of a token in case it is stolen.  

---

## 3. Project Setup in Spring Boot (Spring MVC)
We will build a Spring Boot application with:  
- REST API endpoints for login and form submission  
- JWT utility for generating and validating tokens  
- Error handling for invalid or expired tokens  

Required dependencies (Maven `pom.xml`):  
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

---

## 4. Step 1 – JWT Utility Class
The JWT utility is responsible for generating tokens when the user logs in and validating tokens when they are submitted with requests.  

```java
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.security.Key;

public class JwtUtil {
    private static final String SECRET_KEY = "mysecretkeymysecretkey12345"; // should be stored securely
    private static final long EXPIRATION_TIME = 60_000; // 1 minute in milliseconds

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generate JWT token for a given username
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // subject is usually the username
                .setIssuedAt(new Date(System.currentTimeMillis())) // token creation time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // token expiry
                .signWith(key, SignatureAlgorithm.HS256) // signing algorithm and secret key
                .compact();
    }

    // Validate a JWT token
    public static Jws<Claims> validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
```

Explanation:  
- The `generateToken` method creates a new token with the subject (username), issue time, and expiration time.  
- The `validateToken` method checks the token’s signature and expiration time. If the token is expired or tampered with, it throws an exception.  

---

## 5. Step 2 – Authentication Controller
This controller provides the login endpoint. The user submits a username and password. If correct, the server generates a JWT token and returns it.  

```java
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Hardcoded user for demonstration (use a database in production)
    private final String mockUsername = "student";
    private final String mockPassword = "password123";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user) {
        if (mockUsername.equals(user.getUsername()) && mockPassword.equals(user.getPassword())) {
            String token = JwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(new TokenResponse(token));
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}
```

Supporting classes:  

```java
public class UserDTO {
    private String username;
    private String password;
    // getters and setters
}

public class TokenResponse {
    private String token;
    public TokenResponse(String token) { this.token = token; }
    public String getToken() { return token; }
}
```

---

## 6. Step 3 – Protected Data Submission Endpoint
This endpoint requires a valid token. The client must include the token in the HTTP request header.  

```java
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class DataController {

    @PostMapping("/submit")
    public ResponseEntity<?> submitData(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody FormData formData) {

        try {
            // Extract token from header
            String token = authHeader.replace("Bearer ", "");

            // Validate token
            JwtUtil.validateToken(token);

            // If validation passes, save the data (mocked here)
            return ResponseEntity.ok("Data saved successfully: " + formData.getContent());

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return ResponseEntity.status(401).body("Expired token");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}
```

Supporting DTO:  
```java
public class FormData {
    private String content;
    // getter and setter
}
```

---

## 7. Error Handling
Different error scenarios and server responses:  

- **Wrong username or password**: The login endpoint returns HTTP 401 with the message "Invalid username or password".  
- **Missing token**: If the client does not provide a token in the Authorization header, the server should respond with HTTP 403 and the message "Token required".  
- **Invalid token**: If the token is not signed correctly or tampered with, the server responds with HTTP 401 and "Invalid token".  
- **Expired token**: If the token is expired, the server responds with HTTP 401 and "Expired token".  

---

## 8. Execution Flow

1. The client sends a POST request to `/auth/login` with the username and password.  
   - If valid, the server returns a JWT token.  
   - If invalid, the server returns 401 Unauthorized.  

2. The client stores the token locally (for example, in local storage or memory).  

3. When the client wants to submit data, it sends a POST request to `/data/submit` with the token included in the `Authorization` header as `Bearer <token>`.  

4. The server validates the token:  
   - If valid, the data is accepted and saved.  
   - If expired or invalid, the server responds with an error message.  

---

## 9. Key Takeaways and Discoveries
- JWT enables stateless authentication, meaning the server does not need to keep track of sessions.  
- Tokens should always have an expiration time to reduce security risks if they are stolen.  
- In production systems, user data must be stored in a secure database, and passwords must be hashed before storage.  
- Error handling should clearly inform the client whether the token is expired or invalid so that the client can decide whether to prompt for login again.  
- This pattern is widely used in RESTful APIs to secure communication between client and server.  
