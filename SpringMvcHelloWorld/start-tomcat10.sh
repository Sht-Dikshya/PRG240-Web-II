#!/bin/bash

echo "🚀 Starting Spring MVC Application with Tomcat 10.1.44"
echo "====================================================="

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
    
    # Copy WAR file to Tomcat webapps
    echo "📁 Deploying to Tomcat 10.1.44..."
    cp target/SpringMvcHelloWorld.war /Users/abiralkhanal/Downloads/apache-tomcat-10.1.44/webapps/
    
    # Start Tomcat
    echo "🌐 Starting Tomcat 10.1.44..."
    /Users/abiralkhanal/Downloads/apache-tomcat-10.1.44/bin/startup.sh
    
    echo ""
    echo "✅ Application deployed successfully!"
    echo "🌐 Access your application at: http://localhost:8080/SpringMvcHelloWorld/"
    echo "📋 Available endpoints:"
    echo "   • Home: http://localhost:8080/SpringMvcHelloWorld/"
    echo "   • Register: http://localhost:8080/SpringMvcHelloWorld/employee/register"
    echo "   • API Health: http://localhost:8080/SpringMvcHelloWorld/api/employee/health"
    echo "   • API List: http://localhost:8080/SpringMvcHelloWorld/api/employee/list"
    echo ""
    echo "To stop: /Users/abiralkhanal/Downloads/apache-tomcat-10.1.44/bin/shutdown.sh"
    echo "====================================================="
else
    echo "❌ Build failed!"
    exit 1
fi


