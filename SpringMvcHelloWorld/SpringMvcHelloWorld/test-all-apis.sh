#!/bin/bash

# Test all Employee APIs
BASE_URL="http://localhost:8080/SpringMvcHelloWorld/api/employee"

echo "=== Employee API Testing Script ==="
echo ""

# Test 1: Health Check
echo "1. Testing Health Check..."
curl -s "$BASE_URL/health" | jq '.' 2>/dev/null || curl -s "$BASE_URL/health"
echo ""
echo ""

# Test 2: Register Employee 1
echo "2. Registering Employee 1..."
curl -s -X POST "$BASE_URL/register" \
  -H "Content-Type: application/json" \
  -d '{"employeeId": 1, "name": "John Doe", "email": "john@example.com", "contactNumber": "123-456-7890", "position": "Developer"}' | jq '.' 2>/dev/null || curl -s -X POST "$BASE_URL/register" -H "Content-Type: application/json" -d '{"employeeId": 1, "name": "John Doe", "email": "john@example.com", "contactNumber": "123-456-7890", "position": "Developer"}'
echo ""
echo ""

# Test 3: Register Employee 2
echo "3. Registering Employee 2..."
curl -s -X POST "$BASE_URL/register" \
  -H "Content-Type: application/json" \
  -d '{"employeeId": 2, "name": "Jane Smith", "email": "jane@example.com", "contactNumber": "098-765-4321", "position": "Manager"}' | jq '.' 2>/dev/null || curl -s -X POST "$BASE_URL/register" -H "Content-Type: application/json" -d '{"employeeId": 2, "name": "Jane Smith", "email": "jane@example.com", "contactNumber": "098-765-4321", "position": "Manager"}'
echo ""
echo ""

# Test 4: List All Employees
echo "4. Listing All Employees..."
curl -s "$BASE_URL/list" | jq '.' 2>/dev/null || curl -s "$BASE_URL/list"
echo ""
echo ""

# Test 5: Get Employee by ID
echo "5. Getting Employee by ID (1)..."
curl -s "$BASE_URL/1" | jq '.' 2>/dev/null || curl -s "$BASE_URL/1"
echo ""
echo ""

# Test 6: Update Employee
echo "6. Updating Employee 1..."
curl -s -X PUT "$BASE_URL/1" \
  -H "Content-Type: application/json" \
  -d '{"name": "John Updated", "email": "john.updated@example.com", "contactNumber": "111-222-3333", "position": "Senior Developer"}' | jq '.' 2>/dev/null || curl -s -X PUT "$BASE_URL/1" -H "Content-Type: application/json" -d '{"name": "John Updated", "email": "john.updated@example.com", "contactNumber": "111-222-3333", "position": "Senior Developer"}'
echo ""
echo ""

# Test 7: Verify Update
echo "7. Verifying Update..."
curl -s "$BASE_URL/1" | jq '.' 2>/dev/null || curl -s "$BASE_URL/1"
echo ""
echo ""

# Test 8: Delete Employee
echo "8. Deleting Employee 2..."
curl -s -X DELETE "$BASE_URL/2" | jq '.' 2>/dev/null || curl -s -X DELETE "$BASE_URL/2"
echo ""
echo ""

# Test 9: Verify Deletion
echo "9. Verifying Deletion (List all employees)..."
curl -s "$BASE_URL/list" | jq '.' 2>/dev/null || curl -s "$BASE_URL/list"
echo ""
echo ""

# Test 10: Test Not Found
echo "10. Testing Not Found (Get non-existent employee)..."
curl -s "$BASE_URL/999" | jq '.' 2>/dev/null || curl -s "$BASE_URL/999"
echo ""
echo ""

echo "=== API Testing Complete ==="
echo ""
echo "If you see JSON responses above, the APIs are working correctly!"
echo "If you see HTML error pages, make sure the application is running in IntelliJ."
