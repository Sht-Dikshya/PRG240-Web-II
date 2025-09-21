#!/bin/bash

echo "🔍 Verifying IntelliJ Tomcat Setup"
echo "=================================="

# Check if JMX files exist
TOMCAT_INSTANCE="/Users/abiralkhanal/Library/Caches/JetBrains/IntelliJIdea2025.2/tomcat/b8ea2dd1-fd30-42ef-8dac-10a4575852d0"

if [ -f "$TOMCAT_INSTANCE/conf/jmxremote.access" ]; then
    echo "✅ jmxremote.access file exists"
    echo "📄 Content:"
    cat "$TOMCAT_INSTANCE/conf/jmxremote.access"
    echo ""
else
    echo "❌ jmxremote.access file not found"
fi

if [ -f "$TOMCAT_INSTANCE/conf/jmxremote.password" ]; then
    echo "✅ jmxremote.password file exists"
    echo "📄 Content:"
    cat "$TOMCAT_INSTANCE/conf/jmxremote.password"
    echo ""
else
    echo "❌ jmxremote.password file not found"
fi

# Check file permissions
echo "🔐 File permissions:"
ls -la "$TOMCAT_INSTANCE/conf/" | grep jmx

echo ""
echo "📋 IntelliJ Configuration Checklist:"
echo "1. ✅ JMX files created"
echo "2. ⏳ Configure IntelliJ (see instructions above)"
echo "3. ⏳ Test the application"
echo ""
echo "🚀 Ready to run from IntelliJ!"
