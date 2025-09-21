#!/bin/bash

echo "🧪 Testing All API Endpoints"
echo "============================"

BASE_URL="http://localhost:8080/SpringMvcHelloWorld/api/employee"

echo "1. Health Check:"
curl -s "$BASE_URL/health" | jq . 2>/dev/null || curl -s "$BASE_URL/health"
echo ""

echo "2. List All Employees:"
curl -s "$BASE_URL/list" | jq . 2>/dev/null || curl -s "$BASE_URL/list"
echo ""

echo "3. Get Employee by ID (1):"
curl -s "$BASE_URL/1" | jq . 2>/dev/null || curl -s "$BASE_URL/1"
echo ""

echo "4. Update Employee (ID 1):"
curl -s -X PUT "$BASE_URL/1" \
  -H "Content-Type: application/json" \
  -d '{"employeeId":1,"name":"John Updated","email":"john.updated@example.com","contactNumber":"111-222-3333","position":"Senior Developer"}' \
  | jq . 2>/dev/null || echo "Update test completed"
echo ""

echo "5. Delete Employee (ID 1):"
curl -s -X DELETE "$BASE_URL/1" | jq . 2>/dev/null || curl -s -X DELETE "$BASE_URL/1"
echo ""

echo "6. Verify Deletion - List Employees:"
curl -s "$BASE_URL/list" | jq . 2>/dev/null || curl -s "$BASE_URL/list"
echo ""

echo "7. Register New Employee:"
curl -s -X POST "$BASE_URL/register" \
  -H "Content-Type: application/json" \
  -d '{"employeeId":999,"name":"Test User","email":"test@example.com","contactNumber":"123-456-7890","position":"Developer"}' \
  | jq . 2>/dev/null || echo "Registration test completed"
echo ""

echo "8. Final List - All Employees:"
curl -s "$BASE_URL/list" | jq . 2>/dev/null || curl -s "$BASE_URL/list"
echo ""

echo "✅ API testing complete!"
