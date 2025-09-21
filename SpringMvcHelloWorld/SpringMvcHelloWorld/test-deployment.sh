#!/bin/bash

echo "=== Testing Spring MVC Deployment ==="
echo ""

echo "1. Testing if Tomcat is running..."
if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/ | grep -q "404\|200"; then
    echo "✅ Tomcat is running on port 8080"
else
    echo "❌ Tomcat is not running on port 8080"
    echo "   Make sure to start Tomcat in IntelliJ first"
    exit 1
fi

echo ""
echo "2. Testing application context..."
echo "Testing: http://localhost:8080/SpringMvcHelloWorld/"
response=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/SpringMvcHelloWorld/)
echo "Response code: $response"

if [ "$response" = "200" ]; then
    echo "✅ Application is deployed and accessible!"
    echo ""
    echo "Available endpoints:"
    echo "- Home: http://localhost:8080/SpringMvcHelloWorld/"
    echo "- Registration: http://localhost:8080/SpringMvcHelloWorld/employee/register"
    echo "- API Health: http://localhost:8080/SpringMvcHelloWorld/api/employee/health"
elif [ "$response" = "404" ]; then
    echo "❌ 404 - Application not deployed or wrong context path"
    echo ""
    echo "Troubleshooting steps:"
    echo "1. Check IntelliJ console for Spring startup logs"
    echo "2. Verify deployment tab shows 'SpringMvcHelloWorld:war exploded'"
    echo "3. Make sure application context is '/SpringMvcHelloWorld'"
    echo "4. Try restarting Tomcat in IntelliJ"
else
    echo "❌ Unexpected response: $response"
fi

echo ""
echo "3. Testing root context (should give 404)..."
root_response=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/)
echo "Root context response: $root_response (expected: 404)"
