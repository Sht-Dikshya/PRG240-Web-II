#!/bin/bash

# Setup Tomcat 10.1.44 for Spring MVC application
echo "Setting up Tomcat 10.1.44..."

# Create tomcat directory
TOMCAT_DIR="$HOME/tomcat-10.1.44"
mkdir -p "$TOMCAT_DIR"
cd "$TOMCAT_DIR"

# Download Tomcat 10.1.44 if not already downloaded
if [ ! -f "apache-tomcat-10.1.44.tar.gz" ]; then
    echo "Downloading Tomcat 10.1.44..."
    curl -O https://archive.apache.org/dist/tomcat/tomcat-10/v10.1.44/bin/apache-tomcat-10.1.44.tar.gz
fi

# Extract if not already extracted
if [ ! -d "apache-tomcat-10.1.44" ]; then
    echo "Extracting Tomcat..."
    tar -xzf apache-tomcat-10.1.44.tar.gz
fi

# Set permissions
chmod +x apache-tomcat-10.1.44/bin/*.sh

echo "Tomcat 10.1.44 setup complete!"
echo "Tomcat location: $TOMCAT_DIR/apache-tomcat-10.1.44"
echo ""
echo "To start Tomcat:"
echo "cd $TOMCAT_DIR/apache-tomcat-10.1.44"
echo "./bin/catalina.sh start"
echo ""
echo "To deploy your application:"
echo "cp /Users/abiralkhanal/Documents/SpringMvcHelloWorld/target/SpringMvcHelloWorld.war $TOMCAT_DIR/apache-tomcat-10.1.44/webapps/"

