#!/bin/bash

# Spring MVC Application Startup Script for Tomcat 10.1.44
# ========================================================

# Configuration - Update these paths as needed
TOMCAT_HOME="${TOMCAT_HOME:-/Users/abiralkhanal/Downloads/apache-tomcat-10.1.44}"
PROJECT_NAME="SpringMvcHelloWorld"
PORT=8080
WAR_FILE="target/${PROJECT_NAME}.war"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}🚀 $1${NC}"
}

print_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Function to check if port is in use
check_port() {
    if lsof -Pi :$PORT -sTCP:LISTEN -t >/dev/null 2>&1; then
        return 0
    else
        return 1
    fi
}

# Function to stop processes on port
stop_port_processes() {
    print_warning "Port $PORT is already in use. Stopping existing processes..."
    pkill -f tomcat
    sleep 3
    
    # Check again
    if check_port; then
        print_warning "Some processes still using port $PORT. Force killing..."
        lsof -ti:$PORT | xargs kill -9 2>/dev/null || true
        sleep 2
    fi
}

# Function to validate Tomcat installation
validate_tomcat() {
    if [ ! -d "$TOMCAT_HOME" ]; then
        print_error "Tomcat directory not found: $TOMCAT_HOME"
        print_error "Please set TOMCAT_HOME environment variable or update the script"
        print_error "Example: export TOMCAT_HOME=/path/to/apache-tomcat-10.1.44"
        exit 1
    fi
    
    if [ ! -f "$TOMCAT_HOME/bin/startup.sh" ]; then
        print_error "Tomcat startup script not found: $TOMCAT_HOME/bin/startup.sh"
        exit 1
    fi
    
    if [ ! -d "$TOMCAT_HOME/webapps" ]; then
        print_error "Tomcat webapps directory not found: $TOMCAT_HOME/webapps"
        exit 1
    fi
}

# Function to build the project
build_project() {
    print_status "Building project with Maven..."
    
    if ! command_exists mvn; then
        print_error "Maven not found. Please install Maven and ensure it's in your PATH"
        exit 1
    fi
    
    mvn clean package -q
    
    if [ $? -eq 0 ]; then
        print_success "Build successful!"
    else
        print_error "Build failed!"
        exit 1
    fi
}

# Function to deploy the application
deploy_application() {
    print_status "Deploying application to Tomcat..."
    
    if [ ! -f "$WAR_FILE" ]; then
        print_error "WAR file not found: $WAR_FILE"
        print_error "Please run 'mvn clean package' first"
        exit 1
    fi
    
    # Remove existing deployment
    if [ -d "$TOMCAT_HOME/webapps/$PROJECT_NAME" ]; then
        print_warning "Removing existing deployment..."
        rm -rf "$TOMCAT_HOME/webapps/$PROJECT_NAME"
    fi
    
    if [ -f "$TOMCAT_HOME/webapps/$PROJECT_NAME.war" ]; then
        rm -f "$TOMCAT_HOME/webapps/$PROJECT_NAME.war"
    fi
    
    # Copy new WAR file
    cp "$WAR_FILE" "$TOMCAT_HOME/webapps/"
    
    if [ $? -eq 0 ]; then
        print_success "Application deployed successfully!"
    else
        print_error "Failed to deploy application"
        exit 1
    fi
}

# Function to start Tomcat
start_tomcat() {
    print_status "Starting Tomcat 10.1.44..."
    
    # Make sure startup script is executable
    chmod +x "$TOMCAT_HOME/bin/startup.sh"
    
    # Start Tomcat
    "$TOMCAT_HOME/bin/startup.sh"
    
    if [ $? -eq 0 ]; then
        print_success "Tomcat started successfully!"
    else
        print_error "Failed to start Tomcat"
        exit 1
    fi
}

# Function to wait for application to be ready
wait_for_application() {
    print_status "Waiting for application to be ready..."
    
    local max_attempts=30
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        if curl -s "http://localhost:$PORT/$PROJECT_NAME/" >/dev/null 2>&1; then
            print_success "Application is ready!"
            return 0
        fi
        
        echo -n "."
        sleep 2
        attempt=$((attempt + 1))
    done
    
    print_warning "Application may not be fully ready yet, but Tomcat is running"
}

# Function to display application information
display_info() {
    echo ""
    echo "====================================================="
    print_success "Application deployed successfully!"
    echo "====================================================="
    echo -e "${BLUE}🌐 Application URL:${NC} http://localhost:$PORT/$PROJECT_NAME/"
    echo -e "${BLUE}📋 Available endpoints:${NC}"
    echo "   • Home: http://localhost:$PORT/$PROJECT_NAME/"
    echo "   • Register: http://localhost:$PORT/$PROJECT_NAME/employee/register"
    echo "   • Login: http://localhost:$PORT/$PROJECT_NAME/login"
    echo "   • API Health: http://localhost:$PORT/$PROJECT_NAME/api/employee/health"
    echo "   • API List: http://localhost:$PORT/$PROJECT_NAME/api/employee/list"
    echo "   • JWT Test: http://localhost:$PORT/$PROJECT_NAME/jwt-test.html"
    echo ""
    echo -e "${BLUE}🛠️  Management:${NC}"
    echo "   • Tomcat Manager: http://localhost:$PORT/manager/html"
    echo "   • Stop Tomcat: $TOMCAT_HOME/bin/shutdown.sh"
    echo "   • Logs: $TOMCAT_HOME/logs/"
    echo "====================================================="
}

# Main execution
main() {
    echo "🚀 Starting Spring MVC Application with Tomcat 10.1.44"
    echo "====================================================="
    
    # Validate environment
    validate_tomcat
    
    # Check and handle port conflicts
    if check_port; then
        stop_port_processes
    fi
    
    # Build the project
    build_project
    
    # Deploy the application
    deploy_application
    
    # Start Tomcat
    start_tomcat
    
    # Wait for application to be ready
    wait_for_application
    
    # Display information
    display_info
}

# Run main function
main "$@"







