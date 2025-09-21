# Spring MVC Employee Registration System

This project demonstrates a complete Spring MVC application with employee registration functionality, implementing the MVC pattern with Controller, Service, and POJO layers.

## Features

- **Employee Registration Form**: Collects employee information (Name, Email, Contact Number, Position)
- **Form Validation**: Client-side and server-side validation
- **Data Processing**: Service layer handles business logic
- **Summary Display**: Shows registration confirmation with employee details
- **Responsive Design**: Bootstrap-based UI with custom CSS styling

## Architecture

### 1. Model (POJO)
- `Employee.java` - Plain Old Java Object with fields for employee data

### 2. Controller
- `EmployeeController.java` - Handles HTTP requests and form submissions
- Routes: `/employee/register` (GET/POST)

### 3. Service
- `EmployeeService.java` - Interface defining business operations
- `EmployeeServiceImpl.java` - Implementation of employee registration logic

### 4. Views (JSP)
- `registration.jsp` - Employee registration form
- `summary.jsp` - Registration confirmation and summary

## How to Use

1. **Start the Application**: Deploy the WAR file to a servlet container (Tomcat, etc.)

2. **Access the Application**: 
   - Home page: `http://localhost:8080/SpringMvcHelloWorld/`
   - Employee Registration: `http://localhost:8080/SpringMvcHelloWorld/employee/register`

3. **Register an Employee**:
   - Fill out the form with employee details
   - Click "Register Employee" button
   - View the registration summary

4. **Navigation**:
   - Use the "Employee Registration" button on the home page
   - Navigate between registration and summary pages
   - Return to home page using the back button

## Technical Details

- **Framework**: Spring MVC 6.2.10
- **Java Version**: 17
- **View Technology**: JSP with JSTL
- **Styling**: Bootstrap 5.3.3 + Custom CSS
- **Build Tool**: Maven

## Project Structure

```
src/main/
├── java/com/example/
│   ├── controller/
│   │   ├── HelloController.java
│   │   └── EmployeeController.java
│   ├── service/
│   │   ├── EmployeeService.java
│   │   └── EmployeeServiceImpl.java
│   └── model/
│       └── Employee.java
└── webapp/
    ├── WEB-INF/
    │   ├── views/
    │   │   ├── employee/
    │   │   │   ├── registration.jsp
    │   │   │   └── summary.jsp
    │   │   ├── home.jsp
    │   │   └── hello.jsp
    │   ├── dispatcher-servlet.xml
    │   └── web.xml
    └── resources/
        └── css/
            └── style.css
```

## Learning Objectives

This project demonstrates:
- Spring MVC form handling
- Service layer implementation
- POJO data binding
- JSP view rendering
- Controller-based routing
- Separation of concerns (MVC pattern)

## Future Enhancements

- Database integration
- Form validation with Bean Validation API
- Employee list view
- Employee editing and deletion
- Search and filtering capabilities
