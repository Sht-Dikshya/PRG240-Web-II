package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import java.util.concurrent.ThreadLocalRandom

/**
 * Simple Gatling Load Test - User Registration Only
 * 
 * This simulation focuses solely on testing the user registration endpoint
 * with 1000 concurrent users.
 * 
 * Test Scenario:
 * - 1000 users attempt to register simultaneously
 * - Each user gets a unique username with timestamp
 * - Measures response times and success rates
 * 
 * @author Spring MVC Authentication Testing
 * @version 1.0
 */
class RegistrationLoadTest extends Simulation {

  // ========== HTTP PROTOCOL CONFIGURATION ==========
  val httpProtocol = http
    .baseUrl("http://localhost:8080/SpringMvcHelloWorld")
    .contentTypeHeader("application/json")
    .acceptHeader("application/json")
    .userAgentHeader("Gatling-RegistrationLoadTest/1.0")

  // ========== HELPER FUNCTION TO GENERATE UNIQUE USERNAME ==========
  def generateUsername(): String = {
    s"user_${System.currentTimeMillis()}_${ThreadLocalRandom.current().nextInt(10000, 99999)}"
  }

  // ========== USER REGISTRATION SCENARIO ==========
  val userRegistrationScenario = scenario("1000 Concurrent User Registrations")
    .exec { session =>
      // Generate unique credentials for each user
      val username = generateUsername()
      val password = s"Pass${ThreadLocalRandom.current().nextInt(1000, 9999)}!"
      
      session
        .set("username", username)
        .set("password", password)
    }
    .exec(
      http("Register User")
        .post("/api/auth/register")
        .body(StringBody("""{"username":"${username}","password":"${password}"}""")).asJson
        .check(status.in(200, 201))
        .check(jsonPath("$.success").is("true"))
        .check(jsonPath("$.message").exists)
    )
    .exec { session =>
      val username = session("username").as[String]
      println(s"✅ Registered: ${username}")
      session
    }

  // ========== LOAD TEST SETUP ==========
  /**
   * 1000 CONCURRENT USERS
   * All users attempt to register at the same time
   */
  setUp(
    userRegistrationScenario.inject(
      atOnceUsers(200) // All 1000 users at once
    )
  ).protocols(httpProtocol)
    .assertions(
      // At least 80% of requests should succeed
      global.successfulRequests.percent.gt(60),
      // Mean response time should be under 5 seconds
      global.responseTime.mean.lt(5000),
      // 95th percentile should be under 10 seconds
      global.responseTime.percentile(95).lt(10000)
    )
}

