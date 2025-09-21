#!/bin/bash

# Script to run Spring MVC application with Tomcat 10.1.44
echo "Starting Spring MVC application with Tomcat 10.1.44..."

# Build the project
echo "Building the project..."
mvn clean package -q

if [ $? -ne 0 ]; then
    echo "Build failed! Please check the errors above."
    exit 1
fi

echo "Build successful!"
echo ""
echo "To run this application with Tomcat 10.1.44 in IntelliJ:"
echo ""
echo "1. Open IntelliJ IDEA"
echo "2. Go to Run -> Edit Configurations"
echo "3. Click '+' and select 'Tomcat Server -> Local'"
echo "4. Set the following:"
echo "   - Name: SpringMvcHelloWorld"
echo "   - Application server: Select Tomcat 10.1.44 installation"
echo "   - Deployment tab:"
echo "     - Click '+' and select 'Artifact'"
echo "     - Choose 'SpringMvcHelloWorld:war exploded'"
echo "     - Set Application context to '/SpringMvcHelloWorld'"
echo "5. Click Apply and OK"
echo "6. Run the configuration"
echo ""
echo "The application will be available at:"
echo "http://localhost:8080/SpringMvcHelloWorld/"
echo ""
echo "Key endpoints:"
echo "- Home: http://localhost:8080/SpringMvcHelloWorld/"
echo "- Registration: http://localhost:8080/SpringMvcHelloWorld/employee/register"
echo "- API Health: http://localhost:8080/SpringMvcHelloWorld/api/employee/health"
echo "- API List: http://localhost:8080/SpringMvcHelloWorld/api/employee/list"

