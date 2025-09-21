# IntelliJ Tomcat Configuration Guide

## 🚀 Quick Setup for Spring MVC Application

### 1. Port Configuration
- **HTTP Port**: 8080 (default)
- **JMX Port**: 1099 (default)
- **HTTPS Port**: 8443 (if needed)

### 2. IntelliJ Tomcat Configuration

#### Option A: With JMX (Recommended)
1. Go to `Run → Edit Configurations`
2. Select your Tomcat configuration
3. Go to `Server` tab:
   - ✅ Check "Use JMX agent"
   - JMX port: `1099`
   - Username: `admin`
   - Password: `admin123`
4. Go to `Deployment` tab:
   - Add artifact: `SpringMvcHelloWorld:war exploded`
   - Application context: `/SpringMvcHelloWorld`
5. Click `Apply` and `OK`

#### Option B: Without JMX (Simpler)
1. Go to `Run → Edit Configurations`
2. Select your Tomcat configuration
3. Go to `Server` tab:
   - ❌ Uncheck "Use JMX agent"
4. Go to `Deployment` tab:
   - Add artifact: `SpringMvcHelloWorld:war exploded`
   - Application context: `/SpringMvcHelloWorld`
5. Click `Apply` and `OK`

### 3. Troubleshooting

#### Port Conflicts
If you get port conflict warnings:
```bash
./cleanup-ports.sh
```

#### JMX Errors
If you get JMX authentication errors:
```bash
./fix-intellij-tomcat.sh
```

#### Verify Setup
Check your configuration:
```bash
./verify-intellij-setup.sh
```

### 4. Running the Application

1. **Start**: Click the green "Run" button in IntelliJ
2. **Stop**: Click the red "Stop" button in IntelliJ
3. **Restart**: Click the "Restart" button in IntelliJ

### 5. Testing URLs

Once running, test these endpoints:
- **Home**: http://localhost:8080/SpringMvcHelloWorld/
- **Register**: http://localhost:8080/SpringMvcHelloWorld/employee/register
- **API Health**: http://localhost:8080/SpringMvcHelloWorld/api/employee/health
- **API List**: http://localhost:8080/SpringMvcHelloWorld/api/employee/list

### 6. Common Issues

#### Issue: "Application Server was not connected"
**Solution**: Run `./cleanup-ports.sh` and restart IntelliJ

#### Issue: "JMX Access denied"
**Solution**: Run `./fix-intellij-tomcat.sh` and configure JMX credentials

#### Issue: "Port already in use"
**Solution**: Run `./cleanup-ports.sh` to free the ports

### 7. Alternative: Use Maven Tomcat Plugin

If IntelliJ continues to have issues:
```bash
./start-app.sh
```

This will run the application using Maven's Tomcat plugin instead of IntelliJ's integration.
