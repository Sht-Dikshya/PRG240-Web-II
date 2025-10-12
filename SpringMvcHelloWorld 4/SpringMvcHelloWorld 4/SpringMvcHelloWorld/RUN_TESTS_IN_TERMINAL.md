# Running Tests in Terminal - Quick Guide

## 🎯 Running JUnit Tests (Current Setup)

### Basic Commands

#### 1. Run All Tests with Output in Terminal
```bash
mvn clean test
```

#### 2. Run Specific Test Class
```bash
mvn test -Dtest=AuthenticationApiTest
```

#### 3. Run Tests with Detailed Output (No Debug Logs)
```bash
mvn clean test -Dtest=AuthenticationApiTest -q
```

#### 4. Run Tests with Summary Only (Clean Output)
```bash
mvn clean test -Dtest=AuthenticationApiTest 2>&1 | grep -A 50 "T E S T S"
```

#### 5. Run Tests and Show Only Final Results
```bash
mvn clean test -Dtest=AuthenticationApiTest 2>&1 | tail -20
```

---

## 📊 Best Command for Clean Terminal Output

### Recommended: Show Test Execution and Results
```bash
cd /Users/abiralkhanal/Documents/SpringMvcHelloWorld
mvn test -Dtest=AuthenticationApiTest 2>&1 | grep -E "(T E S T S|Running|Tests run:|BUILD SUCCESS|BUILD FAILURE|===)"
```

### Alternative: Show Everything Except Debug Logs
```bash
mvn test -Dtest=AuthenticationApiTest -Dorg.slf4j.simpleLogger.defaultLogLevel=INFO
```

---

## 🎨 Formatted Output in Terminal

### Create a Test Runner Script

Create file: `run-tests.sh`

```bash
#!/bin/bash

echo "========================================="
echo "🧪 Running Authentication Tests"
echo "========================================="
echo ""

# Run tests and capture output
mvn test -Dtest=AuthenticationApiTest -Dorg.slf4j.simpleLogger.defaultLogLevel=WARN 2>&1 | \
    grep -E "(===|✅|⚠️|❌|Tests run:|BUILD SUCCESS|BUILD FAILURE)"

echo ""
echo "========================================="
echo "📊 Test Report Generated"
echo "========================================="
echo "View HTML Report: target/reports/surefire.html"
echo "View Markdown Report: JUNIT_TEST_REPORT.md"
echo ""
```

Make it executable:
```bash
chmod +x run-tests.sh
```

Run it:
```bash
./run-tests.sh
```

---

## 🔍 Viewing Test Results in Terminal

### See Test Names and Results
```bash
cat target/surefire-reports/com.example.api.AuthenticationApiTest.txt
```

### See Test Summary
```bash
cat target/surefire-reports/com.example.api.AuthenticationApiTest.txt | tail -10
```

### See All Test Files
```bash
ls -la target/surefire-reports/
```

---

## 🚀 TestNG Setup (Alternative to JUnit)

If you want to use TestNG instead of JUnit, here's how:

### Step 1: Add TestNG Dependency to pom.xml

```xml
<!-- Add to <dependencies> section -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.0</version>
    <scope>test</scope>
</dependency>

<!-- RestAssured for API testing -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.2</version>
    <scope>test</scope>
</dependency>
```

### Step 2: Update Maven Surefire Plugin

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
    <configuration>
        <suiteXmlFiles>
            <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
        </suiteXmlFiles>
    </configuration>
</plugin>
```

### Step 3: Create TestNG Test Class

File: `src/test/java/com/example/api/AuthenticationTestNG.java`

```java
package com.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.*;
import java.util.Map;
import static org.testng.Assert.*;

public class AuthenticationTestNG {
    
    private static final String BASE_URL = "http://localhost:8080/SpringMvcHelloWorld";
    private String jwtToken;
    private String testUsername;
    
    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        testUsername = "testuser_" + System.currentTimeMillis();
        System.out.println("\n🧪 Starting Authentication Test Suite\n");
    }
    
    @Test(priority = 1, description = "Test user registration")
    public void testRegistration() {
        System.out.println("Test 1: User Registration");
        
        Map<String, String> body = Map.of(
            "username", testUsername,
            "password", "TestPassword123!"
        );
        
        Response response = RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body(body)
            .when()
                .post("/api/auth/register");
        
        int status = response.getStatusCode();
        System.out.println("  Status: " + status);
        System.out.println("  Response: " + response.asString());
        
        assertTrue(status == 200 || status == 201, "Registration failed");
        System.out.println("  ✅ PASSED\n");
    }
    
    @Test(priority = 2, description = "Test user login", dependsOnMethods = "testRegistration")
    public void testLogin() {
        System.out.println("Test 2: User Login");
        
        Map<String, String> body = Map.of(
            "username", testUsername,
            "password", "TestPassword123!"
        );
        
        Response response = RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body(body)
            .when()
                .post("/api/auth/login");
        
        int status = response.getStatusCode();
        System.out.println("  Status: " + status);
        
        assertTrue(status == 200 || status == 201, "Login failed");
        
        if (response.asString().contains("token")) {
            jwtToken = response.jsonPath().getString("token");
            System.out.println("  Token: " + jwtToken.substring(0, 20) + "...");
        }
        
        System.out.println("  ✅ PASSED\n");
    }
    
    @Test(priority = 3, description = "Test invalid login")
    public void testInvalidLogin() {
        System.out.println("Test 3: Invalid Login");
        
        Map<String, String> body = Map.of(
            "username", "invaliduser",
            "password", "wrongpassword"
        );
        
        Response response = RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body(body)
            .when()
                .post("/api/auth/login");
        
        int status = response.getStatusCode();
        System.out.println("  Status: " + status);
        
        assertEquals(status, 401, "Should return 401 for invalid credentials");
        System.out.println("  ✅ PASSED\n");
    }
    
    @AfterSuite
    public void tearDown() {
        System.out.println("\n========================================");
        System.out.println("✅ Test Suite Completed");
        System.out.println("========================================\n");
    }
}
```

### Step 4: Create testng.xml Configuration

File: `src/test/resources/testng.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Authentication Test Suite" verbose="2">
    <test name="Authentication Tests">
        <classes>
            <class name="com.example.api.AuthenticationTestNG"/>
        </classes>
    </test>
</suite>
```

### Step 5: Run TestNG Tests

```bash
# Run all TestNG tests
mvn clean test

# Run with verbose output
mvn test -Dsurefire.useFile=false

# Run specific test class
mvn test -Dtest=AuthenticationTestNG
```

---

## 📺 Terminal Output Comparison

### JUnit 5 (Current)
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.example.api.AuthenticationApiTest
=== Test 1: Testing User Registration ===
✅ Registration successful!
=== Test 1: COMPLETED ===
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### TestNG
```
🧪 Starting Authentication Test Suite

Test 1: User Registration
  Status: 200
  Response: {"success":true,"message":"User registered successfully"}
  ✅ PASSED

Test 2: User Login
  Status: 200
  Token: eyJhbGciOiJIUzUxMiJ9...
  ✅ PASSED

========================================
✅ Test Suite Completed
========================================
```

---

## 🎯 Quick Commands Reference

### JUnit 5 (Current Setup)

| Command | Description |
|---------|-------------|
| `mvn test` | Run all tests |
| `mvn test -Dtest=AuthenticationApiTest` | Run specific test class |
| `mvn test -Dtest=AuthenticationApiTest#testUserLogin` | Run single test method |
| `mvn clean test` | Clean and run all tests |
| `mvn test -Dsurefire.useFile=false` | Print results to console |

### TestNG Commands

| Command | Description |
|---------|-------------|
| `mvn test` | Run all TestNG tests |
| `mvn test -Dtest=AuthenticationTestNG` | Run specific test class |
| `mvn test -Dsurefire.useFile=false` | Print to console |
| `mvn test -Dtestng.dtd.http=true` | Download DTD if needed |

---

## 💡 Best Terminal Output Command

### For Clean, Readable Output (JUnit)
```bash
mvn test -Dtest=AuthenticationApiTest -Dsurefire.useFile=false -Djansi.force=true 2>&1 | \
    grep -v "DEBUG" | \
    grep -v "WARNING:" | \
    grep -E "(===|✅|Tests run:|BUILD)"
```

### Create Alias for Easy Use
Add to your `~/.zshrc` or `~/.bashrc`:

```bash
alias run-auth-tests='cd ~/Documents/SpringMvcHelloWorld && mvn test -Dtest=AuthenticationApiTest -Dsurefire.useFile=false 2>&1 | grep -E "(===|✅|Tests run:|BUILD)"'
```

Then simply run:
```bash
run-auth-tests
```

---

## 📋 Viewing Results After Test Execution

### View Text Report in Terminal
```bash
cat target/surefire-reports/com.example.api.AuthenticationApiTest.txt
```

### View Summary Only
```bash
cat target/surefire-reports/com.example.api.AuthenticationApiTest.txt | grep -A 5 "Tests run"
```

### View All Test Results
```bash
cat target/surefire-reports/*.txt
```

---

## 🎨 Pretty Terminal Output Script

Create: `pretty-test-run.sh`

```bash
#!/bin/bash

clear
echo "╔════════════════════════════════════════╗"
echo "║   Authentication API Test Runner      ║"
echo "╚════════════════════════════════════════╝"
echo ""

# Run tests
mvn test -Dtest=AuthenticationApiTest -Dsurefire.useFile=false 2>&1 | \
    while IFS= read -r line; do
        if [[ $line == *"=== Test"* ]]; then
            echo ""
            echo "📝 $line"
        elif [[ $line == *"✅"* ]]; then
            echo "   $line"
        elif [[ $line == *"Tests run:"* ]]; then
            echo ""
            echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            echo "📊 $line"
            echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        elif [[ $line == *"BUILD SUCCESS"* ]]; then
            echo ""
            echo "✅ $line"
        fi
    done

echo ""
echo "📄 View detailed report: open target/reports/surefire.html"
echo ""
```

Make it executable and run:
```bash
chmod +x pretty-test-run.sh
./pretty-test-run.sh
```

---

## 🔥 Best Options for You

Since you're using **JUnit 5** (not TestNG), here are the best commands:

### Option A: Minimal Output (Clean)
```bash
mvn test -Dtest=AuthenticationApiTest -Dsurefire.useFile=false 2>&1 | grep -E "(Test [0-9]|✅|Tests run:|SUCCESS|FAILURE)"
```

### Option B: Full Test Output (Verbose)
```bash
mvn test -Dtest=AuthenticationApiTest -Dsurefire.useFile=false
```

### Option C: Test Output + Summary
```bash
mvn clean test -Dtest=AuthenticationApiTest && \
echo "========================================"  && \
cat target/surefire-reports/com.example.api.AuthenticationApiTest.txt
```

---

## 📖 Example: Running Tests Right Now

Try this command:
```bash
cd /Users/abiralkhanal/Documents/SpringMvcHelloWorld
mvn test -Dtest=AuthenticationApiTest -Dsurefire.useFile=false -Dorg.slf4j.simpleLogger.defaultLogLevel=WARN
```

This will show:
- ✅ Test names and results
- ⏱️ Execution times
- 📊 Final summary
- 🎯 Build status

WITHOUT showing:
- ❌ Debug logs
- ❌ HTTP wire logs  
- ❌ Hibernate logs
- ❌ Spring logs

---

## 🆚 JUnit vs TestNG - Quick Comparison

| Feature | JUnit 5 (Current) | TestNG |
|---------|-------------------|--------|
| **Annotations** | `@Test`, `@BeforeEach`, `@AfterEach` | `@Test`, `@BeforeMethod`, `@AfterMethod` |
| **Test Order** | `@Order(1)` | `priority = 1` in `@Test` |
| **Grouping** | `@Tag("smoke")` | `groups = "smoke"` in `@Test` |
| **Dependencies** | No built-in support | `dependsOnMethods` |
| **Parallel Execution** | Yes (via Maven) | Yes (built-in) |
| **Reports** | Surefire reports | Built-in HTML reports |

**Recommendation**: Stick with JUnit 5 (your current setup) as it's more modern and widely used in Spring applications.

---

*Created: October 7, 2025*

