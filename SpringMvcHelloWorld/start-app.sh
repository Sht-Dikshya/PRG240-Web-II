#!/bin/bash

echo "🚀 Starting Spring MVC Application with Maven Tomcat Plugin"
echo "=========================================================="

# Check if port 8080 is available
if lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null ; then
    echo "❌ Port 8080 is already in use. Stopping existing processes..."
    pkill -f tomcat
    sleep 2
fi

# Build the project
echo "📦 Building project..."
mvn clean package -q

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "🌐 Starting application on http://localhost:8080/SpringMvcHelloWorld/"
    echo "📋 Available endpoints:"
    echo "   • Home: http://localhost:8080/SpringMvcHelloWorld/"
    echo "   • Register: http://localhost:8080/SpringMvcHelloWorld/employee/register"
    echo "   • API Health: http://localhost:8080/SpringMvcHelloWorld/api/employee/health"
    echo "   • API List: http://localhost:8080/SpringMvcHelloWorld/api/employee/list"
    echo ""
    echo "Press Ctrl+C to stop the application"
    echo "=========================================================="
    
    # Start the application
    mvn tomcat7:run
else
    echo "❌ Build failed!"
    exit 1
fi
