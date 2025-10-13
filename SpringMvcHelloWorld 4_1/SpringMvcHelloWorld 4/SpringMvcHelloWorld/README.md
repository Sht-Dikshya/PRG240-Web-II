# Employee Management System

A Spring MVC application providing RESTful APIs for employee CRUD operations with comprehensive logging.

## Features

- **Employee CRUD Operations**: Create, Read, Update, Delete employees
- **RESTful API**: Clean REST endpoints for all operations
- **Database Integration**: MySQL database with Spring JDBC
- **Comprehensive Logging**: Detailed logging for all operations, errors, and database connections
- **Data Validation**: Input validation with detailed error messages

## API Endpoints

All endpoints are prefixed with `/api/employee`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/health` | Health check endpoint |
| GET | `/` | Get all employees |
| POST | `/` | Create a new employee |
| GET | `/{id}` | Get employee by ID |
| PUT | `/{id}` | Update employee by ID |
| DELETE | `/{id}` | Delete employee by ID |

## Sample API Usage

### Create Employee
```bash
curl -X POST "http://localhost:8080/SpringMvcHelloWorld/api/employee" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "contactNumber": "1234567890",
    "position": "Software Engineer"
  }'
```

### Get All Employees
```bash
curl -X GET "http://localhost:8080/SpringMvcHelloWorld/api/employee"
```

### Get Employee by ID
```bash
curl -X GET "http://localhost:8080/SpringMvcHelloWorld/api/employee/1"
```

### Update Employee
```bash
curl -X PUT "http://localhost:8080/SpringMvcHelloWorld/api/employee/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "email": "john.smith@example.com",
    "contactNumber": "9876543210",
    "position": "Senior Software Engineer"
  }'
```

### Delete Employee
```bash
curl -X DELETE "http://localhost:8080/SpringMvcHelloWorld/api/employee/1"
```

## Logging

All operations are logged to `application.log` with detailed information including:

- **Database Operations**: INSERT, UPDATE, DELETE, SELECT operations
- **Validation Errors**: Data validation failures
- **Database Connection**: Connection status and errors
- **API Requests**: All incoming API requests and responses
- **Error Handling**: Comprehensive error logging with stack traces

## Project Structure

```
src/main/java/com/example/
├── config/
│   └── DatabaseConfig.java          # Database configuration
├── controller/
│   └── EmployeeRestController.java  # REST API controller
├── dao/
│   ├── EmployeeDAO.java             # DAO interface
│   └── impl/
│       └── EmployeeDAOImpl.java     # DAO implementation
├── dto/
│   └── EmployeeDTO.java             # Data Transfer Object
├── model/
│   └── Employee.java                # Entity model
└── service/
    ├── EmployeeService.java         # Service interface
    └── EmployeeServiceImpl.java     # Service implementation
```

## Running the Application

1. **Using Standalone Tomcat** (Recommended):
   ```bash
   ./start-tomcat10.sh
   ```

2. **Using Maven Tomcat Plugin**:
   ```bash
   mvn tomcat7:run
   ```

The application will be available at: `http://localhost:8080/SpringMvcHelloWorld/`

## Database Configuration

The application uses MySQL database. Configure the connection in `src/main/resources/application.properties`:

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/employeedb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
```

## Dependencies

- Spring Web MVC 6.1.5
- Spring JDBC 6.1.5
- Spring Data JPA 6.1.5
- MySQL Connector 8.0.33
- Jackson 2.15.2
- Logback 1.4.11