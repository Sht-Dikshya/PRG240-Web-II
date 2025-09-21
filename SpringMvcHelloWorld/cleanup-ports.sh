#!/bin/bash

echo "🧹 Cleaning up ports for IntelliJ Tomcat"
echo "======================================="

# Function to kill processes on a specific port
kill_port() {
    local port=$1
    echo "🔍 Checking port $port..."
    
    # Find processes using the port
    local pids=$(lsof -ti :$port 2>/dev/null)
    
    if [ -n "$pids" ]; then
        echo "⚠️  Found processes using port $port: $pids"
        echo "🔄 Stopping processes..."
        echo $pids | xargs kill -9 2>/dev/null
        sleep 2
        
        # Verify they're stopped
        local remaining=$(lsof -ti :$port 2>/dev/null)
        if [ -n "$remaining" ]; then
            echo "❌ Failed to stop processes on port $port"
            return 1
        else
            echo "✅ Port $port is now free"
        fi
    else
        echo "✅ Port $port is already free"
    fi
}

# Clean up ports
kill_port 8080
kill_port 1099

# Kill any remaining Tomcat processes
echo "🔄 Stopping any remaining Tomcat processes..."
pkill -f tomcat 2>/dev/null
pkill -f catalina 2>/dev/null

# Wait a moment
sleep 2

# Final verification
echo ""
echo "🔍 Final port status:"
echo "Port 8080: $(lsof -i :8080 >/dev/null 2>&1 && echo "❌ In use" || echo "✅ Free")"
echo "Port 1099: $(lsof -i :1099 >/dev/null 2>&1 && echo "❌ In use" || echo "✅ Free")"

echo ""
echo "✅ Port cleanup complete!"
echo "🚀 You can now run your application from IntelliJ"
