# Spring MVC CRUD Operations - Complete File Flow Documentation

## Table of Contents
1. [Overview](#overview)
2. [Architecture Layers](#architecture-layers)
3. [File Structure and Roles](#file-structure-and-roles)
4. [CRUD Operations Flow](#crud-operations-flow)
5. [Database Configuration](#database-configuration)
6. [Logging and Error Handling](#logging-and-error-handling)
7. [Web Interface Flow](#web-interface-flow)
8. [API Endpoints Flow](#api-endpoints-flow)

## Overview

This Spring MVC application implements a complete Employee Management System with CRUD (Create, Read, Update, Delete) operations. The application follows the MVC (Model-View-Controller) pattern with clear separation of concerns across multiple layers.

## Architecture Layers

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                       │
├─────────────────────────────────────────────────────────────┤
│  JSP Views (list.jsp, form.jsp, detail.jsp, index.html)   │
│  Web Controllers (EmployeeWebController)                   │
│  REST Controllers (EmployeeRestController)                 │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                     SERVICE LAYER                          │
├─────────────────────────────────────────────────────────────┤
│  Service Interface (EmployeeService)                       │
│  Service Implementation (EmployeeServiceImpl)              │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                    DATA ACCESS LAYER                       │
├─────────────────────────────────────────────────────────────┤
│  DAO Interface (EmployeeDAO)                               │
│  DAO Implementation (EmployeeDAOImpl)                      │
│  Database Configuration (DatabaseConfig)                   │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                      DATA LAYER                            │
├─────────────────────────────────────────────────────────────┤
│  Entity Model (Employee)                                   │
│  DTO (EmployeeDTO)                                         │
│  MySQL Database                                            │
└─────────────────────────────────────────────────────────────┘
```

## File Structure and Roles

### 1. **Model Layer Files**

#### `src/main/java/com/example/model/Employee.java`
**Role:** Entity class representing the database table structure
- **Purpose:** Maps to the `employees` table in MySQL database
- **Annotations:** JPA annotations for ORM mapping
- **Fields:** 
  - `employeeId` (Primary Key)
  - `firstName`, `lastName`, `email`, `contactNumber`
  - `department`, `position`, `salary`
  - `createdAt`, `updatedAt` (Timestamps)
- **CRUD Role:** 
  - **CREATE:** New Employee objects are created and saved
  - **READ:** Employee objects are retrieved from database
  - **UPDATE:** Existing Employee objects are modified
  - **DELETE:** Employee objects are removed from database

#### `src/main/java/com/example/dto/EmployeeDTO.java`
**Role:** Data Transfer Object for form data and API communication
- **Purpose:** Transfers data between presentation layer and service layer
- **Validation:** Contains Jakarta validation annotations
- **Fields:** Same as Employee entity but without database-specific fields
- **CRUD Role:**
  - **CREATE:** Form data is bound to DTO, then converted to Entity
  - **READ:** Entity data is converted to DTO for display
  - **UPDATE:** Form data updates DTO, then updates Entity
  - **DELETE:** DTO contains ID for deletion operations

### 2. **Data Access Layer Files**

#### `src/main/java/com/example/dao/EmployeeDAO.java`
**Role:** Data Access Object interface defining database operations
- **Purpose:** Abstract contract for database operations
- **Methods:**
  - `save(Employee employee)` - Insert new employee
  - `findById(Long id)` - Retrieve employee by ID
  - `findAll()` - Retrieve all employees
  - `update(Employee employee)` - Update existing employee
  - `deleteById(Long id)` - Delete employee by ID
- **CRUD Role:** Defines the contract for all CRUD operations

#### `src/main/java/com/example/dao/impl/EmployeeDAOImpl.java`
**Role:** Implementation of database operations using JDBC
- **Purpose:** Executes actual SQL queries against MySQL database
- **Dependencies:** Uses `JdbcTemplate` for database operations
- **SQL Operations:**
  - **CREATE:** `INSERT INTO employees (...) VALUES (...)`
  - **READ:** `SELECT * FROM employees WHERE employee_id = ?`
  - **UPDATE:** `UPDATE employees SET ... WHERE employee_id = ?`
  - **DELETE:** `DELETE FROM employees WHERE employee_id = ?`
- **Logging:** Comprehensive logging for all database operations
- **Error Handling:** Catches and wraps database exceptions

#### `src/main/java/com/example/config/DatabaseConfig.java`
**Role:** Database configuration and connection management
- **Purpose:** Configures database connection, JdbcTemplate, and transaction management
- **Components:**
  - `DataSource` - Database connection pool
  - `JdbcTemplate` - JDBC operations helper
  - `PlatformTransactionManager` - Transaction management
- **Configuration:** Reads database properties from `application.properties`
- **CRUD Role:** Provides the foundation for all database operations

### 3. **Service Layer Files**

#### `src/main/java/com/example/service/EmployeeService.java`
**Role:** Service interface defining business logic operations
- **Purpose:** Abstract contract for business logic
- **Methods:** Same as DAO but with business logic context
- **CRUD Role:** Defines business operations for CRUD

#### `src/main/java/com/example/service/EmployeeServiceImpl.java`
**Role:** Implementation of business logic and validation
- **Purpose:** Contains business rules, validation, and error handling
- **Dependencies:** Uses `EmployeeDAO` for data operations
- **Business Logic:**
  - Input validation
  - Data transformation (DTO ↔ Entity)
  - Error handling and logging
  - Transaction management
- **CRUD Role:**
  - **CREATE:** Validates input, converts DTO to Entity, calls DAO
  - **READ:** Calls DAO, converts Entity to DTO, handles exceptions
  - **UPDATE:** Validates input, checks existence, updates Entity
  - **DELETE:** Validates existence, calls DAO delete

### 4. **Controller Layer Files**

#### `src/main/java/com/example/controller/EmployeeWebController.java`
**Role:** Web controller for JSP-based user interface
- **Purpose:** Handles HTTP requests from web forms and returns JSP views
- **Mappings:**
  - `GET /employee/list` - Display employee list
  - `GET /employee/register` - Show add employee form
  - `POST /employee/save` - Process add employee form
  - `GET /employee/edit/{id}` - Show edit employee form
  - `POST /employee/update` - Process update employee form
  - `GET /employee/view/{id}` - Show employee details
  - `GET /employee/delete/{id}` - Delete employee
- **CRUD Role:**
  - **CREATE:** `saveEmployee()` - Processes form submission
  - **READ:** `listEmployees()`, `viewEmployee()` - Displays data
  - **UPDATE:** `showEditForm()`, `updateEmployee()` - Handles updates
  - **DELETE:** `deleteEmployee()` - Handles deletions
- **Flash Attributes:** Sets success/error messages for user feedback

#### `src/main/java/com/example/controller/EmployeeRestController.java`
**Role:** REST API controller for programmatic access
- **Purpose:** Provides RESTful API endpoints for external applications
- **Mappings:**
  - `GET /api/employee/list` - Get all employees (JSON)
  - `GET /api/employee/{id}` - Get employee by ID (JSON)
  - `POST /api/employee` - Create new employee (JSON)
  - `PUT /api/employee/{id}` - Update employee (JSON)
  - `DELETE /api/employee/{id}` - Delete employee
  - `GET /api/employee/health` - Health check
- **CRUD Role:** Same as WebController but returns JSON responses
- **Content-Type:** `application/json`
- **Response:** JSON formatted data for API consumers

### 5. **View Layer Files**

#### `src/main/webapp/WEB-INF/views/employee/list.jsp`
**Role:** Employee listing page with Bootstrap styling
- **Purpose:** Displays all employees in a table format
- **Features:**
  - Bootstrap table with responsive design
  - Action buttons (View, Edit, Delete)
  - Flash message display
  - Delete confirmation modal
- **CRUD Role:**
  - **READ:** Displays employee list
  - **DELETE:** Provides delete buttons with confirmation

#### `src/main/webapp/WEB-INF/views/employee/form.jsp`
**Role:** Employee add/edit form page
- **Purpose:** Single form for both adding and editing employees
- **Features:**
  - Bootstrap form styling
  - Client-side validation
  - Dynamic form action (save vs update)
  - Flash message display
- **CRUD Role:**
  - **CREATE:** Form for adding new employees
  - **UPDATE:** Pre-populated form for editing existing employees

#### `src/main/webapp/WEB-INF/views/employee/detail.jsp`
**Role:** Employee details view page
- **Purpose:** Displays individual employee information
- **Features:**
  - Bootstrap card layout
  - Employee information display
  - Action buttons (Edit, Delete)
  - Clean, minimal design
- **CRUD Role:**
  - **READ:** Displays detailed employee information

#### `src/main/webapp/index.html`
**Role:** Application home page
- **Purpose:** Landing page with navigation to employee management
- **Features:**
  - Bootstrap styling
  - Navigation links
  - Clean, professional design
- **CRUD Role:** Entry point to CRUD operations

### 6. **Configuration Files**

#### `src/main/resources/application.properties`
**Role:** Application configuration properties
- **Purpose:** Contains database connection settings and other configurations
- **Properties:**
  - Database URL, username, password
  - Driver class name
  - Connection pool settings
- **CRUD Role:** Provides database connection for all CRUD operations

#### `src/main/resources/logback-spring.xml`
**Role:** Logging configuration
- **Purpose:** Configures logging levels and output destinations
- **Features:**
  - Console logging
  - File logging to `application.log`
  - Different log levels for different packages
- **CRUD Role:** Logs all CRUD operations for debugging and monitoring

#### `src/main/webapp/WEB-INF/dispatcher-servlet.xml`
**Role:** Spring MVC configuration
- **Purpose:** Configures Spring MVC components and view resolution
- **Components:**
  - Component scanning
  - View resolver for JSP files
  - Message source for internationalization
- **CRUD Role:** Enables Spring MVC for web-based CRUD operations

#### `src/main/webapp/WEB-INF/web.xml`
**Role:** Web application deployment descriptor
- **Purpose:** Configures servlet mappings and application context
- **Features:**
  - DispatcherServlet configuration
  - Context parameter setup
  - Welcome file configuration
- **CRUD Role:** Enables web application to handle CRUD requests

#### `pom.xml`
**Role:** Maven project configuration
- **Purpose:** Defines project dependencies and build configuration
- **Dependencies:**
  - Spring Framework libraries
  - MySQL connector
  - Servlet API
  - JSTL libraries
  - Logging frameworks
- **CRUD Role:** Provides all necessary libraries for CRUD operations

## CRUD Operations Flow

### 1. CREATE (Add Employee)

#### Web Interface Flow:
```
1. User clicks "Add Employee" button
   ↓
2. Browser requests: GET /employee/register
   ↓
3. EmployeeWebController.showAddForm()
   ↓
4. Returns: form.jsp (empty form)
   ↓
5. User fills form and clicks "Add Employee"
   ↓
6. Browser submits: POST /employee/save
   ↓
7. EmployeeWebController.saveEmployee()
   ↓
8. EmployeeServiceImpl.saveEmployee()
   ↓
9. EmployeeDAOImpl.save()
   ↓
10. SQL: INSERT INTO employees (...)
    ↓
11. Redirect to: /employee/list
    ↓
12. EmployeeWebController.listEmployees()
    ↓
13. Returns: list.jsp (with new employee)
```

#### API Flow:
```
1. Client sends: POST /api/employee
   ↓
2. EmployeeRestController.createEmployee()
   ↓
3. EmployeeServiceImpl.saveEmployee()
   ↓
4. EmployeeDAOImpl.save()
   ↓
5. SQL: INSERT INTO employees (...)
    ↓
6. Returns: JSON response with created employee
```

### 2. READ (View Employees)

#### Web Interface Flow:
```
1. User navigates to employee list
   ↓
2. Browser requests: GET /employee/list
   ↓
3. EmployeeWebController.listEmployees()
   ↓
4. EmployeeServiceImpl.getAllEmployees()
   ↓
5. EmployeeDAOImpl.findAll()
   ↓
6. SQL: SELECT * FROM employees
    ↓
7. Returns: list.jsp (with employee data)
```

#### API Flow:
```
1. Client sends: GET /api/employee/list
   ↓
2. EmployeeRestController.getAllEmployees()
   ↓
3. EmployeeServiceImpl.getAllEmployees()
   ↓
4. EmployeeDAOImpl.findAll()
   ↓
5. SQL: SELECT * FROM employees
    ↓
6. Returns: JSON array of employees
```

### 3. UPDATE (Edit Employee)

#### Web Interface Flow:
```
1. User clicks "Edit" button
   ↓
2. Browser requests: GET /employee/edit/{id}
   ↓
3. EmployeeWebController.showEditForm()
   ↓
4. EmployeeServiceImpl.getEmployeeById()
   ↓
5. EmployeeDAOImpl.findById()
   ↓
6. SQL: SELECT * FROM employees WHERE employee_id = ?
    ↓
7. Returns: form.jsp (pre-populated form)
    ↓
8. User modifies data and clicks "Update"
    ↓
9. Browser submits: POST /employee/update
    ↓
10. EmployeeWebController.updateEmployee()
     ↓
11. EmployeeServiceImpl.updateEmployee()
     ↓
12. EmployeeDAOImpl.update()
     ↓
13. SQL: UPDATE employees SET ... WHERE employee_id = ?
      ↓
14. Redirect to: /employee/list
```

#### API Flow:
```
1. Client sends: PUT /api/employee/{id}
   ↓
2. EmployeeRestController.updateEmployee()
   ↓
3. EmployeeServiceImpl.updateEmployee()
   ↓
4. EmployeeDAOImpl.update()
   ↓
5. SQL: UPDATE employees SET ... WHERE employee_id = ?
    ↓
6. Returns: JSON response with updated employee
```

### 4. DELETE (Remove Employee)

#### Web Interface Flow:
```
1. User clicks "Delete" button
   ↓
2. JavaScript shows confirmation dialog
   ↓
3. User confirms deletion
   ↓
4. Browser requests: GET /employee/delete/{id}
   ↓
5. EmployeeWebController.deleteEmployee()
   ↓
6. EmployeeServiceImpl.deleteEmployee()
   ↓
7. EmployeeDAOImpl.deleteById()
   ↓
8. SQL: DELETE FROM employees WHERE employee_id = ?
    ↓
9. Redirect to: /employee/list
```

#### API Flow:
```
1. Client sends: DELETE /api/employee/{id}
   ↓
2. EmployeeRestController.deleteEmployee()
   ↓
3. EmployeeServiceImpl.deleteEmployee()
   ↓
4. EmployeeDAOImpl.deleteById()
   ↓
5. SQL: DELETE FROM employees WHERE employee_id = ?
    ↓
6. Returns: JSON response with success message
```

## Database Configuration

### Connection Flow:
```
1. Application starts
   ↓
2. Spring loads DatabaseConfig.java
   ↓
3. Reads application.properties
   ↓
4. Creates DataSource bean
   ↓
5. Creates JdbcTemplate bean
   ↓
6. Tests database connection
   ↓
7. Logs connection status
```

### Database Schema:
```sql
CREATE TABLE employees (
    employee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contact_number VARCHAR(20),
    department VARCHAR(50),
    position VARCHAR(50),
    salary DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Logging and Error Handling

### Logging Flow:
```
1. Request received
   ↓
2. Controller logs request details
   ↓
3. Service logs business logic
   ↓
4. DAO logs database operations
   ↓
5. Database executes SQL
   ↓
6. Results logged at each layer
   ↓
7. Response sent to client
```

### Error Handling:
- **Controller Layer:** Catches service exceptions, sets flash messages
- **Service Layer:** Validates input, handles business logic errors
- **DAO Layer:** Catches database exceptions, wraps in DataAccessException
- **Database Layer:** MySQL error handling and constraint violations

## Web Interface Flow

### Page Navigation:
```
index.html
    ↓
employee/list.jsp (View all employees)
    ↓
employee/form.jsp (Add/Edit employee)
    ↓
employee/detail.jsp (View employee details)
```

### Form Processing:
```
1. Form submission
   ↓
2. Spring form binding (@ModelAttribute)
   ↓
3. Validation (client-side + server-side)
   ↓
4. Service layer processing
   ↓
5. Database operation
   ↓
6. Flash message setting
   ↓
7. Redirect to appropriate page
```

## API Endpoints Flow

### REST API Structure:
```
Base URL: /api/employee

GET    /list          - Get all employees
GET    /{id}          - Get employee by ID
POST   /              - Create new employee
PUT    /{id}          - Update employee
DELETE /{id}          - Delete employee
GET    /health        - Health check
```

### JSON Response Format:
```json
{
  "employeeId": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "contactNumber": "1234567890",
  "department": "IT",
  "position": "Developer",
  "salary": 75000.00,
  "createdAt": "2025-01-01T10:00:00Z",
  "updatedAt": "2025-01-01T10:00:00Z"
}
```

## Key Features

### 1. **Separation of Concerns**
- Clear layer separation (Controller → Service → DAO → Database)
- Each layer has specific responsibilities
- Easy to maintain and test

### 2. **Comprehensive Logging**
- Logging at every layer
- Database operation tracking
- Error logging and debugging

### 3. **Error Handling**
- Graceful error handling
- User-friendly error messages
- Proper exception propagation

### 4. **Data Validation**
- Client-side validation (JavaScript)
- Server-side validation (Jakarta validation)
- Database constraints

### 5. **Responsive Design**
- Bootstrap-based UI
- Mobile-friendly interface
- Clean, professional appearance

### 6. **Dual Interface**
- Web interface for end users
- REST API for programmatic access
- Same business logic for both

This comprehensive documentation shows how every file in the application contributes to the CRUD operations, from the user interface down to the database level, providing a complete understanding of the application's architecture and data flow.
