# Gatling Load Testing Guide - Authentication API

## 📊 Overview

This guide explains how to perform load testing on the Spring MVC authentication API using **Gatling**, a powerful open-source load testing framework.

### What is Gatling?

Gatling is a highly capable load testing tool designed for ease of use, high performance, and powerful analytics. It's written in Scala and provides:
- **High Performance**: Can simulate thousands of users with minimal resources
- **Real-time Metrics**: Live monitoring during tests
- **Beautiful Reports**: HTML reports with detailed charts and statistics
- **Code-based Scenarios**: Tests are written as code, enabling version control and CI/CD integration
- **Accurate Results**: More precise than traditional load testing tools

---

## 🎯 Test Scenarios Included

The `AuthenticationLoadTest.scala` simulation includes the following scenarios:

### 1. User Registration Load Test
- Tests registration endpoint performance
- Generates unique usernames with timestamps
- Validates response times and success rates
- **Expected Response Time**: < 3000ms

### 2. User Login Load Test
- Tests login endpoint performance
- Registers users first, then logs them in
- Validates JWT token generation
- **Expected Response Time**: < 2000ms

### 3. Invalid Login Attempts
- Tests system behavior under invalid credentials
- Validates proper 401 rejection
- Ensures security mechanisms don't slow down the system
- **Expected Response Time**: < 1500ms

### 4. Complete Authentication Flow
- **Step 1**: Register new user
- **Step 2**: Login and receive JWT token
- **Step 3**: Access protected endpoint with token
- Tests end-to-end authentication workflow
- **Expected Total Time**: < 8000ms

### 5. Unauthorized Access Attempts
- Tests protected endpoints without JWT tokens
- Validates 401 responses
- Ensures security filters work under load
- **Expected Response Time**: < 1000ms

### 6. JWT Token Validation Performance
- Tests token validation performance
- Makes multiple requests with the same token
- Validates caching and validation efficiency
- **Expected Response Time**: < 2000ms per request

---

## 🚀 Load Test Profiles

### Light Load Test (Baseline)
**Purpose**: Establish baseline performance metrics

**Configuration**:
- 5 concurrent registrations
- 5 concurrent logins
- 3 complete authentication flows

**Assertions**:
- Mean response time < 2000ms
- Max response time < 5000ms
- Success rate > 95%

**Use When**: Initial testing, development environment

---

### Medium Load Test (Normal Traffic)
**Purpose**: Simulate typical production load

**Configuration**:
- 50 users ramped up over 30 seconds
- Mix of registration, login, and complete flows
- Includes invalid login attempts

**Assertions**:
- Mean response time < 3000ms
- Max response time < 8000ms
- Success rate > 90%

**Use When**: Pre-production testing, capacity planning

---

### Heavy Load Test (High Traffic)
**Purpose**: Test system under significant load

**Configuration**:
- 100 users ramped up over 60 seconds
- All scenarios active
- Includes unauthorized access attempts

**Assertions**:
- Mean response time < 5000ms
- Max response time < 10000ms
- Success rate > 85%

**Use When**: Peak traffic simulation, scaling decisions

---

### Stress Test (Breaking Point)
**Purpose**: Identify system limits and breaking points

**Configuration**:
- 200 users with spike patterns
- Variable load intensity
- Sustained high load periods

**Assertions**:
- Mean response time < 8000ms
- Max response time < 15000ms
- Success rate > 75%

**Use When**: Capacity planning, disaster recovery planning

---

### Spike Test (Recovery)
**Purpose**: Test system recovery from sudden traffic spikes

**Configuration**:
- Sudden bursts: 50 → 100 → 50 users
- Idle periods between spikes
- Tests auto-scaling and recovery

**Assertions**:
- Max response time < 20000ms
- Success rate > 70%

**Use When**: Testing auto-scaling, resilience validation

---

### Sustained Load Test (Endurance)
**Purpose**: Long-running test to identify memory leaks and degradation

**Configuration**:
- Constant rate for 120 seconds (2 minutes)
- 2 registrations/sec, 3 logins/sec
- Continuous JWT validation

**Assertions**:
- Mean response time < 3000ms
- 95th percentile < 6000ms
- Success rate > 92%

**Use When**: Stability testing, memory leak detection

---

### Mixed Workload Test (Realistic)
**Purpose**: Simulate realistic usage patterns

**Configuration**:
- Mix of all scenarios
- Different intensities
- Includes security testing (invalid attempts)

**Assertions**:
- Mean response time < 4000ms
- 95th percentile < 8000ms
- Success rate > 88%

**Use When**: Final acceptance testing, production readiness

---

## 🛠️ Prerequisites

### 1. Ensure Server is Running
```bash
# Start Tomcat
cd /Users/abiralkhanal/Documents/SpringMvcHelloWorld
export TOMCAT_HOME=/Users/abiralkhanal/Downloads/apache-tomcat-10.1.44
./start-tomcat10.sh

# Wait for server to be ready (25-30 seconds)
sleep 30

# Verify server is running
curl -s http://localhost:8080/SpringMvcHelloWorld/ && echo "Server is ready!"
```

### 2. Clean Database (Optional)
For consistent results, you may want to clean the database before each test run:
```bash
# Stop and restart the application
./stop-tomcat10.sh
rm -f data/employeedb.mv.db  # Remove H2 database file
./start-tomcat10.sh
```

---

## 📝 Running Gatling Tests

### Option 1: Run Default Test (Medium Load)
```bash
cd /Users/abiralkhanal/Documents/SpringMvcHelloWorld
mvn gatling:test
```
This will run the default configuration defined at the bottom of `AuthenticationLoadTest.scala`.

### Option 2: Run Specific Simulation
```bash
# Run only the Authentication Load Test
mvn gatling:test -Dgatling.simulationClass=simulations.AuthenticationLoadTest
```

### Option 3: Run with Custom Parameters
```bash
# Run with increased logging
mvn gatling:test -Dgatling.simulationClass=simulations.AuthenticationLoadTest -Dlogback.configurationFile=logback-test.xml

# Run with custom results folder
mvn gatling:test -Dresults.folder=/path/to/custom/results
```

### Option 4: Interactive Mode
```bash
# Maven will prompt you to select which simulation to run
mvn gatling:test

# You'll see:
# Choose a simulation number:
#   [0] simulations.AuthenticationLoadTest
#   [1] simulations.EmployeeFormSimulation
```

---

## 📊 Understanding the Results

### Console Output
During the test, you'll see:
```
================================================================================
2025-10-07 22:30:15                                           5s elapsed
---- Requests ------------------------------------------------------------------
> Global                                                   (OK=45     KO=0     )
> Register New User                                        (OK=15     KO=0     )
> Login User                                               (OK=15     KO=0     )
> Step 3: Access Protected Endpoint                        (OK=15     KO=0     )

---- User Registration Load Test -----------------------------------------------
[##########                                                ]  33%
          waiting: 0      / active: 10     / done: 5     
---- User Login Load Test ------------------------------------------------------
[##########                                                ]  33%
          waiting: 0      / active: 10     / done: 5     
================================================================================
```

**Legend**:
- `OK`: Successful requests
- `KO`: Failed requests
- `waiting`: Users waiting to start
- `active`: Users currently executing
- `done`: Users completed

### HTML Report Location
After the test completes:
```
Gatling Enterprise report: target/gatling/authenticationloadtest-[timestamp]/index.html
```

Open the report in your browser:
```bash
# macOS
open target/gatling/authenticationloadtest-*/index.html

# Linux
xdg-open target/gatling/authenticationloadtest-*/index.html

# Or manually navigate to:
# file:///Users/abiralkhanal/Documents/SpringMvcHelloWorld/target/gatling/authenticationloadtest-[timestamp]/index.html
```

### Report Sections

#### 1. **Global Statistics**
- Total requests
- Success/failure rates
- Min/Max/Mean/Std Dev response times
- Percentiles (50th, 75th, 95th, 99th)

#### 2. **Response Time Distribution**
- Histogram showing response time distribution
- Identifies patterns and outliers

#### 3. **Response Time Percentiles Over Time**
- Chart showing how response times change during the test
- Helps identify degradation under load

#### 4. **Requests Per Second**
- Shows request rate throughout the test
- Validates load generation

#### 5. **Responses Per Second**
- Shows response rate (OK and KO)
- Helps identify bottlenecks

#### 6. **Detailed Request Statistics**
- Per-endpoint metrics
- Identifies which endpoints are slow

---

## 🎯 Performance Benchmarks

### Expected Results (Development Environment)

| Scenario | Mean Response Time | 95th Percentile | Success Rate |
|----------|-------------------|-----------------|--------------|
| **User Registration** | < 2000ms | < 3000ms | > 95% |
| **User Login** | < 1500ms | < 2500ms | > 95% |
| **Complete Auth Flow** | < 5000ms | < 8000ms | > 90% |
| **JWT Validation** | < 1000ms | < 2000ms | > 98% |
| **Invalid Login** | < 1000ms | < 1500ms | 100% (401) |
| **Unauthorized Access** | < 500ms | < 1000ms | 100% (401) |

### Production Targets

| Metric | Target | Alert Threshold |
|--------|--------|----------------|
| **Mean Response Time** | < 1000ms | > 2000ms |
| **95th Percentile** | < 2000ms | > 4000ms |
| **99th Percentile** | < 5000ms | > 8000ms |
| **Success Rate** | > 99% | < 95% |
| **Throughput** | > 100 req/sec | < 50 req/sec |
| **Error Rate** | < 0.5% | > 2% |

---

## 🔍 Analyzing Results

### Good Performance Indicators
✅ Response times remain consistent under load  
✅ No significant increase in error rates  
✅ 95th percentile stays within acceptable range  
✅ Request rate matches expected load pattern  
✅ No timeout errors  
✅ JWT validation is fast (< 100ms)

### Warning Signs
⚠️ Response times increase linearly with load  
⚠️ Error rate above 5%  
⚠️ Response time spikes without load increase  
⚠️ Growing queue of waiting users  
⚠️ Database connection errors  
⚠️ Memory-related errors

### Critical Issues
🚨 Response times exceed 10 seconds  
🚨 Error rate above 20%  
🚨 Server crashes or restarts  
🚨 Timeout errors  
🚨 OutOfMemory errors  
🚨 Database connection pool exhaustion

---

## 🛠️ Troubleshooting

### Issue: Connection Refused
**Symptoms**: `Connection refused` errors in Gatling output

**Solutions**:
1. Ensure Tomcat is running:
   ```bash
   ps aux | grep tomcat
   ```
2. Check application deployment:
   ```bash
   curl http://localhost:8080/SpringMvcHelloWorld/
   ```
3. Check Tomcat logs:
   ```bash
   tail -f $TOMCAT_HOME/logs/catalina.out
   ```

### Issue: High Response Times
**Symptoms**: Response times > 5000ms

**Solutions**:
1. Check database performance
2. Review JWT token generation (bcrypt rounds)
3. Check for database connection pool exhaustion
4. Review server logs for errors
5. Monitor CPU and memory usage

### Issue: Authentication Failures
**Symptoms**: High rate of 401 errors for valid credentials

**Solutions**:
1. Check JWT secret key configuration
2. Verify password encoding
3. Check database user records
4. Review authentication logs

### Issue: Low Throughput
**Symptoms**: Gatling can't generate expected load

**Solutions**:
1. Increase heap size:
   ```bash
   export JAVA_OPTS="-Xmx4g"
   mvn gatling:test
   ```
2. Adjust Gatling configuration
3. Use multiple load generators (distributed testing)

---

## 📈 Optimization Tips

### 1. Database Optimization
- Add indexes on `username` column in `users` table
- Use connection pooling
- Optimize queries

### 2. JWT Optimization
- Cache public keys for validation
- Use appropriate bcrypt rounds (10-12)
- Consider token expiration times

### 3. Application Optimization
- Enable HTTP caching where appropriate
- Use async processing for non-critical operations
- Implement rate limiting to prevent abuse

### 4. Server Configuration
- Tune Tomcat thread pool size
- Adjust connection timeout values
- Enable compression for responses

---

## 🔄 Continuous Integration

### Integrate with CI/CD Pipeline

**Example GitHub Actions**:
```yaml
name: Performance Tests

on:
  push:
    branches: [ main ]
  schedule:
    - cron: '0 2 * * *'  # Daily at 2 AM

jobs:
  gatling:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Start Application
        run: |
          ./start-tomcat10.sh &
          sleep 30
      - name: Run Gatling Tests
        run: mvn gatling:test
      - name: Archive Gatling Results
        if: always()
        uses: actions/upload-artifact@v2
        with:
          name: gatling-reports
          path: target/gatling/
```

---

## 📚 Advanced Topics

### Custom Scenarios
To create custom scenarios, edit `AuthenticationLoadTest.scala`:

```scala
val customScenario = scenario("My Custom Test")
  .exec(
    http("My Request")
      .post("/api/auth/register")
      .body(StringBody("""{"username":"test","password":"pass"}"""))
      .check(status.is(200))
  )

setUp(
  customScenario.inject(rampUsers(10).during(10.seconds))
).protocols(httpProtocol)
```

### Distributed Load Testing
For very high loads, distribute across multiple machines:

```bash
# Machine 1
mvn gatling:test -Dgatling.http.ahc.keepAlive=true

# Machine 2
mvn gatling:test -Dgatling.http.ahc.keepAlive=true

# Combine results manually or use Gatling Enterprise
```

### Monitoring During Tests
Use monitoring tools to observe system behavior:

```bash
# Monitor CPU/Memory
top

# Monitor network connections
netstat -an | grep 8080 | wc -l

# Monitor database connections
# (depends on your database monitoring tools)
```

---

## 🎓 Best Practices

1. **Start Small**: Begin with light load, gradually increase
2. **Isolate Tests**: Run one scenario at a time initially
3. **Baseline First**: Establish baseline performance before optimization
4. **Monitor Everything**: Watch CPU, memory, network, database
5. **Realistic Data**: Use production-like data volumes
6. **Test Regularly**: Integrate into CI/CD pipeline
7. **Document Results**: Keep records of performance over time
8. **Test Different Times**: Performance may vary by time of day
9. **Clean Between Tests**: Reset database/cache for consistency
10. **Version Control**: Track test scenarios in git

---

## 📊 Sample Test Execution

### Complete Test Run Example

```bash
# 1. Prepare Environment
cd /Users/abiralkhanal/Documents/SpringMvcHelloWorld
export TOMCAT_HOME=/Users/abiralkhanal/Downloads/apache-tomcat-10.1.44

# 2. Clean and Start Server
./stop-tomcat10.sh
sleep 5
./start-tomcat10.sh
sleep 30

# 3. Verify Server
curl -s http://localhost:8080/SpringMvcHelloWorld/ && echo "✅ Server Ready"

# 4. Run Load Test
mvn gatling:test -Dgatling.simulationClass=simulations.AuthenticationLoadTest

# 5. View Results
open target/gatling/authenticationloadtest-*/index.html

# 6. Stop Server (optional)
./stop-tomcat10.sh
```

---

## 📞 Need Help?

### Resources
- [Gatling Documentation](https://gatling.io/docs/current/)
- [Gatling GitHub](https://github.com/gatling/gatling)
- [Performance Testing Guide](./TESTING_GUIDE_COMPREHENSIVE.md)

### Common Commands Reference
```bash
# Run all Gatling tests
mvn gatling:test

# Run specific simulation
mvn gatling:test -Dgatling.simulationClass=simulations.AuthenticationLoadTest

# Clean Gatling results
rm -rf target/gatling/

# View latest report
open target/gatling/$(ls -t target/gatling/ | head -1)/index.html
```

---

**Happy Load Testing! 🚀**

*For questions or issues, refer to the [TESTING_GUIDE_COMPREHENSIVE.md](./TESTING_GUIDE_COMPREHENSIVE.md) or consult the Gatling documentation.*

