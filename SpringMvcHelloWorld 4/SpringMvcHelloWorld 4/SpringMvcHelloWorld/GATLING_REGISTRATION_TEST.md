# Gatling Load Test - User Registration (1000 Concurrent Users)

## 🎯 Test Overview

This is a **focused load test** that tests the user registration endpoint with **1000 concurrent users** registering at the same time.

### What This Test Does:
- ✅ Creates 1000 unique users simultaneously
- ✅ Each user gets a unique username with timestamp
- ✅ Measures response times and success rates
- ✅ Generates a beautiful HTML report with charts

---

## 🚀 How to Run the Test

### Step 1: Start the Server
```bash
cd /Users/abiralkhanal/Documents/SpringMvcHelloWorld
export TOMCAT_HOME=/Users/abiralkhanal/Downloads/apache-tomcat-10.1.44

# Start Tomcat
./start-tomcat10.sh

# Wait for server to be ready (25-30 seconds)
sleep 30

# Verify server is running
curl -s http://localhost:8080/SpringMvcHelloWorld/ && echo "✅ Server Ready!"
```

### Step 2: Run the Gatling Test
```bash
# Run the registration load test
mvn gatling:test -Dgatling.simulationClass=simulations.RegistrationLoadTest
```

That's it! The test will run and generate a report.

---

## 📊 What You'll See

### Console Output (During Test)
```
================================================================================
---- Requests ------------------------------------------------------------------
> Global                                                   (OK=850    KO=150  )
> Register User                                            (OK=850    KO=150  )

---- 1000 Concurrent User Registrations ----------------------------------------
[##################################################]  100%
          waiting: 0      / active: 0      / done: 1000   
================================================================================
```

**Legend:**
- `OK`: Successful registrations (HTTP 200/201)
- `KO`: Failed registrations (errors/timeouts)
- `waiting`: Users waiting to start
- `active`: Users currently registering
- `done`: Users completed

### HTML Report Location
After the test completes, you'll see:
```
Reports generated in 0s.
Please open the following file: file:///Users/abiralkhanal/Documents/SpringMvcHelloWorld/target/gatling/registrationloadtest-[timestamp]/index.html
```

**Open the report:**
```bash
# macOS
open target/gatling/registrationloadtest-*/index.html

# Or manually open in browser:
# file:///Users/abiralkhanal/Documents/SpringMvcHelloWorld/target/gatling/registrationloadtest-[timestamp]/index.html
```

---

## 📈 Understanding the Report

The HTML report contains:

### 1. **Global Statistics**
- Total Requests: 1000
- Success Count: Shows how many succeeded
- Failure Count: Shows how many failed
- Response Time Statistics:
  - Min: Fastest registration
  - Max: Slowest registration
  - Mean: Average time
  - Std Dev: Variation in times
  - 50th percentile (Median)
  - 95th percentile (95% of requests were faster than this)
  - 99th percentile (99% of requests were faster than this)

### 2. **Response Time Distribution**
Chart showing distribution of response times:
- Most requests should cluster around the mean
- Few outliers at the high end

### 3. **Response Time Percentiles Over Time**
Line chart showing:
- How response times changed during the test
- If the system degraded under load
- Recovery patterns

### 4. **Requests Per Second**
Shows the load generation rate:
- Should show a spike at the beginning (1000 users at once)

### 5. **Responses Per Second**
Shows how many responses the server returned:
- Green: Successful responses
- Red: Failed responses

---

## 🎯 Expected Results

### Good Performance Indicators:
- ✅ Success rate > 80%
- ✅ Mean response time < 5000ms (5 seconds)
- ✅ 95th percentile < 10000ms (10 seconds)
- ✅ No timeout errors
- ✅ Response times are consistent

### Warning Signs:
- ⚠️ Success rate < 80%
- ⚠️ Mean response time > 5000ms
- ⚠️ Many timeout errors
- ⚠️ Response times increase over time

### Example Good Results:
```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                       1000 (OK=950   KO=50   )
> min response time                                    234 (OK=234   KO=5002 )
> max response time                                   4567 (OK=4567  KO=10001)
> mean response time                                  1234 (OK=1123  KO=8234 )
> std deviation                                        890 (OK=678   KO=2345 )
> response time 50th percentile                       1100 (OK=1050  KO=7890 )
> response time 75th percentile                       1678 (OK=1567  KO=9012 )
> response time 95th percentile                       2890 (OK=2567  KO=10001)
> response time 99th percentile                       3890 (OK=3567  KO=10001)
> mean requests/sec                                   333.333 (OK=316.667 KO=16.667)
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                           245 ( 25%)
> 800 ms < t < 1200 ms                                 312 ( 31%)
> t > 1200 ms                                          393 ( 39%)
> failed                                                50 (  5%)
================================================================================
```

**Interpretation:**
- 95% success rate (950/1000) ✅
- Mean response time: 1.2 seconds ✅
- 95% of requests completed under 2.9 seconds ✅
- System handled the load well!

---

## 🔍 Troubleshooting

### Problem: Connection Refused
**Error**: `java.net.ConnectException: Connection refused`

**Solution**:
```bash
# Check if server is running
curl http://localhost:8080/SpringMvcHelloWorld/

# If not running, start it
./start-tomcat10.sh
sleep 30
```

### Problem: High Failure Rate (> 20%)
**Possible Causes:**
1. **Database connection pool exhausted**
   - Solution: Increase connection pool size in application configuration

2. **Server CPU/Memory maxed out**
   - Check with: `top` or `htop`
   - Solution: Increase server resources

3. **Tomcat thread pool exhausted**
   - Solution: Increase Tomcat maxThreads in server.xml

### Problem: Slow Response Times
**Possible Causes:**
1. **Slow password hashing (bcrypt)**
   - Expected: bcrypt is intentionally slow for security
   - 1000 concurrent users will stress this

2. **Database write performance**
   - Solution: Add database indexes, use connection pooling

3. **Insufficient server resources**
   - Solution: Increase CPU/RAM

### Problem: Test Doesn't Start
**Error**: `Compilation failed`

**Solution**:
```bash
# Clean and recompile
mvn clean compile test-compile

# Then run again
mvn gatling:test -Dgatling.simulationClass=simulations.RegistrationLoadTest
```

---

## 🛠️ Customizing the Test

### Change Number of Users
Edit `RegistrationLoadTest.scala`:

```scala
setUp(
  userRegistrationScenario.inject(
    atOnceUsers(500)  // Change from 1000 to 500
  )
).protocols(httpProtocol)
```

### Ramp Up Gradually Instead
```scala
setUp(
  userRegistrationScenario.inject(
    rampUsers(1000).during(60.seconds)  // 1000 users over 60 seconds
  )
).protocols(httpProtocol)
```

### Add Think Time Between Requests
```scala
val userRegistrationScenario = scenario("Registration")
  .exec { ... }
  .exec(http("Register User") ... )
  .pause(1.second, 3.seconds)  // Add this line
```

---

## 📊 Complete Test Execution Example

```bash
# 1. Navigate to project
cd /Users/abiralkhanal/Documents/SpringMvcHelloWorld

# 2. Set Tomcat home
export TOMCAT_HOME=/Users/abiralkhanal/Downloads/apache-tomcat-10.1.44

# 3. Start server
./start-tomcat10.sh

# 4. Wait for server
echo "Waiting for server to start..."
sleep 30

# 5. Verify server is ready
curl -s http://localhost:8080/SpringMvcHelloWorld/ && echo "✅ Server Ready!"

# 6. Run the test
echo "Starting Gatling Load Test..."
mvn gatling:test -Dgatling.simulationClass=simulations.RegistrationLoadTest

# 7. Open report (after test completes)
echo "Opening report..."
open target/gatling/registrationloadtest-*/index.html
```

---

## 📝 Quick Reference Commands

```bash
# Run the test
mvn gatling:test -Dgatling.simulationClass=simulations.RegistrationLoadTest

# Run with debug logging
mvn gatling:test -Dgatling.simulationClass=simulations.RegistrationLoadTest -X

# View latest report
open target/gatling/$(ls -t target/gatling/ | head -1)/index.html

# Clean old reports
rm -rf target/gatling/

# Check if server is running
ps aux | grep tomcat | grep -v grep

# Stop server
./stop-tomcat10.sh
```

---

## 🎓 Understanding the Test Code

The test is simple and focused:

```scala
// 1. Generate unique username and password for each user
val username = generateUsername()  // e.g., "user_1759854500123_45678"
val password = s"Pass${random}!"   // e.g., "Pass4567!"

// 2. Make POST request to /api/auth/register
POST http://localhost:8080/SpringMvcHelloWorld/api/auth/register
Content-Type: application/json
{
  "username": "user_1759854500123_45678",
  "password": "Pass4567!"
}

// 3. Verify response
- Status: 200 or 201
- Response body contains: {"success": true, "message": "..."}
- Response time < 10 seconds

// 4. All 1000 users do this AT THE SAME TIME (atOnceUsers(1000))
```

---

## 💡 Tips for Best Results

1. **Run on a clean database** for consistent results
2. **Close other applications** to free up resources
3. **Don't use the application** while test is running
4. **Run multiple times** and compare results
5. **Check server logs** for errors: `tail -f $TOMCAT_HOME/logs/catalina.out`
6. **Monitor system resources**: `top` or Activity Monitor

---

## 📞 Next Steps

After this test, you can:

1. **Analyze the results** in the HTML report
2. **Optimize slow endpoints** based on findings
3. **Gradually increase load** (1500, 2000 users)
4. **Test other scenarios** (login, complete auth flow)
5. **Implement the full `AuthenticationLoadTest`** with all scenarios

---

**Happy Load Testing! 🚀**

*For the complete authentication load test suite with multiple scenarios, see: [GATLING_LOAD_TESTING_GUIDE.md](./GATLING_LOAD_TESTING_GUIDE.md)*

