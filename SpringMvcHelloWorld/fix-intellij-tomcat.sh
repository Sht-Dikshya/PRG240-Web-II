#!/bin/bash

echo "🔧 Fixing IntelliJ Tomcat JMX Configuration"
echo "==========================================="

# Find IntelliJ Tomcat cache directory
TOMCAT_CACHE_DIR="/Users/abiralkhanal/Library/Caches/JetBrains/IntelliJIdea2025.2/tomcat"
echo "📁 Tomcat cache directory: $TOMCAT_CACHE_DIR"

# Find the specific Tomcat instance directory
TOMCAT_INSTANCE=$(find "$TOMCAT_CACHE_DIR" -name "b8ea2dd1-fd30-42ef-8dac-10a4575852d0" -type d 2>/dev/null)

if [ -z "$TOMCAT_INSTANCE" ]; then
    echo "❌ Tomcat instance directory not found. Creating a new one..."
    TOMCAT_INSTANCE="$TOMCAT_CACHE_DIR/b8ea2dd1-fd30-42ef-8dac-10a4575852d0"
    mkdir -p "$TOMCAT_INSTANCE/conf"
else
    echo "✅ Found Tomcat instance: $TOMCAT_INSTANCE"
fi

# Create jmxremote.access file
echo "📝 Creating jmxremote.access file..."
cat > "$TOMCAT_INSTANCE/conf/jmxremote.access" << 'EOF'
# JMX access control file
# Format: role readwrite|readonly
# Role names are case sensitive

# Admin role - full access
admin readwrite

# Monitor role - read-only access
monitor readonly

# Default role - read-only access
readonly readonly
EOF

# Create jmxremote.password file
echo "📝 Creating jmxremote.password file..."
cat > "$TOMCAT_INSTANCE/conf/jmxremote.password" << 'EOF'
# JMX password file
# Format: username password
# Username and password are case sensitive

# Admin user
admin admin123

# Monitor user
monitor monitor123

# Readonly user
readonly readonly123
EOF

# Set proper permissions
chmod 600 "$TOMCAT_INSTANCE/conf/jmxremote.password"
chmod 644 "$TOMCAT_INSTANCE/conf/jmxremote.access"

echo "✅ JMX configuration files created successfully!"
echo ""
echo "📋 Next steps:"
echo "1. In IntelliJ IDEA:"
echo "   - Go to Run → Edit Configurations"
echo "   - Select your Tomcat configuration"
echo "   - Go to Server tab"
echo "   - Set 'Use JMX agent' to 'Enabled'"
echo "   - Set 'JMX port' to '1099'"
echo "   - Set 'Username' to 'admin'"
echo "   - Set 'Password' to 'admin123'"
echo "   - Click Apply and OK"
echo ""
echo "2. Or simply disable JMX:"
echo "   - Uncheck 'Use JMX agent' in Server tab"
echo "   - Click Apply and OK"
echo ""
echo "✅ Configuration complete!"
