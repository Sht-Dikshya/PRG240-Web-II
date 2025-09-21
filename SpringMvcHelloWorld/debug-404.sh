#!/bin/bash

echo "=== Spring MVC Tomcat 10.1.44 Debug Script ==="
echo ""

echo "1. Checking project structure..."
if [ -f "target/SpringMvcHelloWorld.war" ]; then
    echo "✅ WAR file exists: target/SpringMvcHelloWorld.war"
else
    echo "❌ WAR file missing. Run 'mvn clean package' first."
    exit 1
fi

echo ""
echo "2. Checking key files..."
files=(
    "src/main/webapp/WEB-INF/web.xml"
    "src/main/webapp/WEB-INF/dispatcher-servlet.xml"
    "src/main/webapp/WEB-INF/views/home.jsp"
    "src/main/java/com/example/controller/HomeController.java"
)

for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file exists"
    else
        echo "❌ $file missing"
    fi
done

echo ""
echo "3. Checking web.xml configuration..."
if grep -q "jakarta.ee" src/main/webapp/WEB-INF/web.xml; then
    echo "✅ web.xml uses Jakarta EE namespace (Tomcat 10 compatible)"
else
    echo "❌ web.xml not using Jakarta EE namespace"
fi

echo ""
echo "4. Checking dispatcher servlet mapping..."
if grep -q "<url-pattern>/</url-pattern>" src/main/webapp/WEB-INF/web.xml; then
    echo "✅ Dispatcher servlet mapped to root path"
else
    echo "❌ Dispatcher servlet not mapped to root path"
fi

echo ""
echo "5. Checking controller mappings..."
if grep -q "@GetMapping(\"/\")" src/main/java/com/example/controller/HomeController.java; then
    echo "✅ Home controller has root mapping"
else
    echo "❌ Home controller missing root mapping"
fi

echo ""
echo "=== Troubleshooting Steps ==="
echo ""
echo "If you're getting 404 errors, try these URLs in order:"
echo "1. http://localhost:8080/SpringMvcHelloWorld/"
echo "2. http://localhost:8080/SpringMvcHelloWorld/employee/register"
echo "3. http://localhost:8080/SpringMvcHelloWorld/api/employee/health"
echo ""
echo "Common issues:"
echo "- Application context path should be '/SpringMvcHelloWorld' in IntelliJ"
echo "- Make sure you're using 'war exploded' artifact, not just 'war'"
echo "- Check that Tomcat 10.1.44 is properly configured in IntelliJ"
echo "- Verify the deployment tab shows the correct artifact"
echo ""
echo "If still getting 404, check IntelliJ console for Spring startup logs."

