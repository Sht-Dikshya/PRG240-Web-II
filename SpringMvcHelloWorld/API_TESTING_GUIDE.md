# Employee API Testing Guide

## Overview
This Spring MVC application provides a complete REST API for employee management with CRUD operations.

## Base URL
```
http://localhost:8080/SpringMvcHelloWorld/api/employee
```

## API Endpoints

### 1. Health Check
**GET** `/health`
- **Description**: Check if the API is running
- **Response**: Status and message

**Example:**
```bash
curl http://localhost:8080/SpringMvcHelloWorld/api/employee/health
```

**Response:**
```json
{
  "status": "UP",
  "message": "Employee API is running"
}
```

### 2. Register Employee
**POST** `/register`
- **Description**: Create a new employee
- **Content-Type**: `application/json`

**Request Body:**
```json
{
  "employeeId": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "contactNumber": "123-456-7890",
  "position": "Software Developer"
}
```

**Example:**
```bash
curl -X POST http://localhost:8080/SpringMvcHelloWorld/api/employee/register \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "contactNumber": "123-456-7890",
    "position": "Software Developer"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Employee registered successfully",
  "employee": {
    "employeeId": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "contactNumber": "123-456-7890",
    "position": "Software Developer"
  }
}
```

### 3. Get All Employees
**GET** `/list`
- **Description**: Retrieve all employees

**Example:**
```bash
curl http://localhost:8080/SpringMvcHelloWorld/api/employee/list
```

**Response:**
```json
{
  "success": true,
  "message": "Employees retrieved successfully",
  "count": 2,
  "employees": [
    {
      "employeeId": 1,
      "name": "John Doe",
      "email": "john.doe@example.com",
      "contactNumber": "123-456-7890",
      "position": "Software Developer"
    },
    {
      "employeeId": 2,
      "name": "Jane Smith",
      "email": "jane.smith@example.com",
      "contactNumber": "098-765-4321",
      "position": "Project Manager"
    }
  ]
}
```

### 4. Get Employee by ID
**GET** `/{id}`
- **Description**: Retrieve a specific employee by ID

**Example:**
```bash
curl http://localhost:8080/SpringMvcHelloWorld/api/employee/1
```

**Response (Success):**
```json
{
  "success": true,
  "message": "Employee found",
  "employee": {
    "employeeId": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "contactNumber": "123-456-7890",
    "position": "Software Developer"
  }
}
```

**Response (Not Found):**
```json
{
  "success": false,
  "message": "Employee not found with ID: 999"
}
```

### 5. Update Employee
**PUT** `/{id}`
- **Description**: Update an existing employee
- **Content-Type**: `application/json`

**Request Body:**
```json
{
  "name": "John Updated",
  "email": "john.updated@example.com",
  "contactNumber": "111-222-3333",
  "position": "Senior Software Developer"
}
```

**Example:**
```bash
curl -X PUT http://localhost:8080/SpringMvcHelloWorld/api/employee/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "email": "john.updated@example.com",
    "contactNumber": "111-222-3333",
    "position": "Senior Software Developer"
  }'
```

**Response (Success):**
```json
{
  "success": true,
  "message": "Employee updated successfully",
  "employee": {
    "employeeId": 1,
    "name": "John Updated",
    "email": "john.updated@example.com",
    "contactNumber": "111-222-3333",
    "position": "Senior Software Developer"
  }
}
```

**Response (Not Found):**
```json
{
  "success": false,
  "message": "Employee not found with ID: 999"
}
```

### 6. Delete Employee
**DELETE** `/{id}`
- **Description**: Delete an employee by ID

**Example:**
```bash
curl -X DELETE http://localhost:8080/SpringMvcHelloWorld/api/employee/1
```

**Response (Success):**
```json
{
  "success": true,
  "message": "Employee deleted successfully"
}
```

**Response (Not Found):**
```json
{
  "success": false,
  "message": "Employee not found with ID: 999"
}
```

## Complete Testing Workflow

### Step 1: Check API Health
```bash
curl http://localhost:8080/SpringMvcHelloWorld/api/employee/health
```

### Step 2: Register Some Employees
```bash
# Employee 1
curl -X POST http://localhost:8080/SpringMvcHelloWorld/api/employee/register \
  -H "Content-Type: application/json" \
  -d '{"employeeId": 1, "name": "John Doe", "email": "john@example.com", "contactNumber": "123-456-7890", "position": "Developer"}'

# Employee 2
curl -X POST http://localhost:8080/SpringMvcHelloWorld/api/employee/register \
  -H "Content-Type: application/json" \
  -d '{"employeeId": 2, "name": "Jane Smith", "email": "jane@example.com", "contactNumber": "098-765-4321", "position": "Manager"}'
```

### Step 3: List All Employees
```bash
curl http://localhost:8080/SpringMvcHelloWorld/api/employee/list
```

### Step 4: Get Specific Employee
```bash
curl http://localhost:8080/SpringMvcHelloWorld/api/employee/1
```

### Step 5: Update Employee
```bash
curl -X PUT http://localhost:8080/SpringMvcHelloWorld/api/employee/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "John Updated", "email": "john.updated@example.com", "contactNumber": "111-222-3333", "position": "Senior Developer"}'
```

### Step 6: Delete Employee
```bash
curl -X DELETE http://localhost:8080/SpringMvcHelloWorld/api/employee/2
```

### Step 7: Verify Deletion
```bash
curl http://localhost:8080/SpringMvcHelloWorld/api/employee/list
```

## Error Handling

All endpoints return consistent error responses:

```json
{
  "success": false,
  "message": "Error description"
}
```

**Common HTTP Status Codes:**
- `200 OK`: Success
- `404 Not Found`: Employee not found
- `500 Internal Server Error`: Server error

## Notes

- **In-memory storage**: Data is stored in memory and will be lost when the server restarts
- **Employee ID**: Must be unique integers
- **Content-Type**: Always use `application/json` for POST and PUT requests
- **CORS**: API supports cross-origin requests from any domain

