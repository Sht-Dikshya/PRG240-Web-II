# Test Execution Guide

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- Spring MVC Application running on Tomcat

### 1. Start the Application
```bash
# Unix/Linux/macOS
./start-tomcat10.sh

# Windows
start-tomcat10.bat
```

### 2. Run Tests

#### JUnit Tests (Unit Testing)
```bash
# Run all JUnit tests
mvn test -Dtest=EmployeeValidationTest

# Run with verbose output
mvn test -Dtest=EmployeeValidationTest -X
```

#### Cucumber Tests (BDD Integration Testing)
```bash
# Run Cucumber tests (requires application to be running)
mvn test -Dtest=CucumberTest

# Run specific tags
mvn test -Dtest=CucumberTest -Dcucumber.filter.tags="@positive"
```

#### Gatling Tests (Performance Testing)
```bash
# Run Gatling simulation (requires application to be running)
mvn gatling:test

# Run specific simulation
mvn gatling:test -Dgatling.simulationClass=simulations.EmployeeFormSimulation
```

## 📊 Test Results

### JUnit Reports
- **Location**: `target/surefire-reports/`
- **Files**: `TEST-*.xml`, `*.html`

### Cucumber Reports
- **Location**: `target/cucumber-reports/`
- **Files**: `cucumber.html`, `cucumber.json`

### Gatling Reports
- **Location**: `target/gatling/`
- **Files**: HTML reports with performance metrics

## 🎯 Test Scenarios

### JUnit Tests
- ✅ Valid employee data validation
- ✅ Invalid email format validation
- ✅ Name length constraints
- ✅ Phone number pattern validation
- ✅ Position field validation
- ✅ Edge cases (min/max lengths)

### Cucumber Tests
- ✅ Valid form submission workflow
- ✅ Invalid email format handling
- ✅ Short name validation
- ✅ Invalid phone number handling
- ✅ Blank position validation
- ✅ Boundary value testing
- ✅ Multiple phone format testing

### Gatling Tests
- ✅ Light load (5 users)
- ✅ Medium load (50 users over 15s)
- ✅ Heavy load (100 users over 30s)
- ✅ Stress test (200 users over 60s)
- ✅ Spike test (sudden traffic increase)
- ✅ Sustained load (constant rate)

## 📈 Expected Results

### JUnit
- **Tests Run**: 10
- **Success Rate**: 100%
- **Execution Time**: < 1 second

### Cucumber
- **Scenarios**: 8+
- **Success Rate**: 90%+ (some may fail due to API requirements)
- **Execution Time**: 30-60 seconds

### Gatling
- **Response Time**: < 3 seconds (mean)
- **Success Rate**: 90%+
- **Throughput**: 10-50 requests/second

## 🔧 Troubleshooting

### Common Issues
1. **Application not running**: Start Tomcat before running integration/performance tests
2. **Port conflicts**: Use `./stop-tomcat10.sh` to stop existing instances
3. **Memory issues**: Increase JVM heap size for Gatling tests
4. **Network timeouts**: Check application health endpoint

### Debug Commands
```bash
# Check if application is running
curl http://localhost:8080/SpringMvcHelloWorld/api/employee/health

# Check test dependencies
mvn dependency:tree

# Clean and rebuild
mvn clean compile test-compile
```

## 📚 Learning Outcomes

After running these tests, you'll understand:
- **Unit Testing**: Model validation with JUnit 5
- **BDD Testing**: Behavior-driven development with Cucumber
- **Performance Testing**: Load testing with Gatling
- **Test Automation**: End-to-end testing strategies
- **Quality Assurance**: Comprehensive testing approaches

## 🎓 Next Steps

1. **Modify Tests**: Add your own test scenarios
2. **Extend Coverage**: Test additional endpoints
3. **CI/CD Integration**: Add to your build pipeline
4. **Monitoring**: Set up test result monitoring
5. **Documentation**: Keep tests as living documentation

Happy Testing! 🚀

