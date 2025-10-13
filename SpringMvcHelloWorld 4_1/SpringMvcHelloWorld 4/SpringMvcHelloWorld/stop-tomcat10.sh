#!/bin/bash

# Spring MVC Application Stop Script for Tomcat 10.1.44
# =====================================================

# Configuration - Update these paths as needed
TOMCAT_HOME="${TOMCAT_HOME:-/Users/abiralkhanal/Downloads/apache-tomcat-10.1.44}"
PROJECT_NAME="SpringMvcHelloWorld"
PORT=8080

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}🛑 $1${NC}"
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

# Function to check if port is in use
check_port() {
    if lsof -Pi :$PORT -sTCP:LISTEN -t >/dev/null 2>&1; then
        return 0
    else
        return 1
    fi
}

# Function to stop Tomcat gracefully
stop_tomcat_gracefully() {
    print_status "Stopping Tomcat gracefully..."
    
    if [ -f "$TOMCAT_HOME/bin/shutdown.sh" ]; then
        chmod +x "$TOMCAT_HOME/bin/shutdown.sh"
        "$TOMCAT_HOME/bin/shutdown.sh"
        
        # Wait for graceful shutdown
        local max_attempts=15
        local attempt=1
        
        while [ $attempt -le $max_attempts ]; do
            if ! check_port; then
                print_success "Tomcat stopped gracefully!"
                return 0
            fi
            echo -n "."
            sleep 2
            attempt=$((attempt + 1))
        done
        
        print_warning "Graceful shutdown taking too long, forcing stop..."
        return 1
    else
        print_error "Tomcat shutdown script not found: $TOMCAT_HOME/bin/shutdown.sh"
        return 1
    fi
}

# Function to force stop processes on port
force_stop_port() {
    print_warning "Force stopping processes on port $PORT..."
    
    # Find and kill processes using the port
    local pids=$(lsof -ti:$PORT 2>/dev/null)
    
    if [ -n "$pids" ]; then
        echo "Found processes: $pids"
        echo "$pids" | xargs kill -9 2>/dev/null || true
        sleep 2
        
        # Check if still running
        if check_port; then
            print_error "Failed to stop all processes on port $PORT"
            return 1
        else
            print_success "All processes stopped!"
            return 0
        fi
    else
        print_warning "No processes found on port $PORT"
        return 0
    fi
}

# Function to clean up deployment
cleanup_deployment() {
    print_status "Cleaning up deployment..."
    
    if [ -d "$TOMCAT_HOME/webapps/$PROJECT_NAME" ]; then
        print_warning "Removing deployed application directory..."
        rm -rf "$TOMCAT_HOME/webapps/$PROJECT_NAME"
    fi
    
    if [ -f "$TOMCAT_HOME/webapps/$PROJECT_NAME.war" ]; then
        print_warning "Removing WAR file..."
        rm -f "$TOMCAT_HOME/webapps/$PROJECT_NAME.war"
    fi
    
    print_success "Deployment cleaned up!"
}

# Function to display status
display_status() {
    echo ""
    echo "====================================================="
    if check_port; then
        print_warning "Tomcat is still running on port $PORT"
        echo "You may need to force stop it with: kill -9 \$(lsof -ti:$PORT)"
    else
        print_success "Tomcat has been stopped successfully!"
    fi
    echo "====================================================="
}

# Main execution
main() {
    echo "🛑 Stopping Spring MVC Application on Tomcat 10.1.44"
    echo "====================================================="
    
    # Check if Tomcat is running
    if ! check_port; then
        print_warning "No Tomcat process found on port $PORT"
        echo "Tomcat may already be stopped or running on a different port"
        exit 0
    fi
    
    # Try graceful shutdown first
    if stop_tomcat_gracefully; then
        print_success "Tomcat stopped gracefully!"
    else
        # Force stop if graceful shutdown failed
        if force_stop_port; then
            print_success "Tomcat force stopped!"
        else
            print_error "Failed to stop Tomcat completely"
            exit 1
        fi
    fi
    
    # Clean up deployment (optional)
    read -p "Do you want to clean up the deployed application? (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        cleanup_deployment
    fi
    
    # Display final status
    display_status
}

# Run main function
main "$@"


