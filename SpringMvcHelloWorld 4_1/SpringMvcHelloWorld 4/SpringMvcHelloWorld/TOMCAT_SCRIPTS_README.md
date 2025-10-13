# Tomcat 10.1.44 Scripts for Spring MVC Application

This directory contains scripts to easily run and manage your Spring MVC application with Apache Tomcat 10.1.44.

## Scripts Overview

### 1. `start-tomcat10.sh` - Application Startup Script
- Builds the project using Maven
- Deploys the WAR file to Tomcat
- Starts Tomcat 10.1.44
- Handles port conflicts automatically
- Provides colored output and status information

### 2. `stop-tomcat10.sh` - Application Shutdown Script
- Gracefully stops Tomcat
- Force stops if graceful shutdown fails
- Optionally cleans up deployed application
- Provides status information

## Prerequisites

1. **Java 17** (as specified in pom.xml)
2. **Maven** installed and in PATH
3. **Apache Tomcat 10.1.44** downloaded and extracted
4. **curl** (for health checks)

## Configuration

### Environment Variables
You can set the `TOMCAT_HOME` environment variable to specify your Tomcat installation path:

```bash
export TOMCAT_HOME=/path/to/apache-tomcat-10.1.44
```

If not set, the script defaults to: `/Users/abiralkhanal/Downloads/apache-tomcat-10.1.44`

### Script Configuration
Edit the following variables in `start-tomcat10.sh` if needed:
- `TOMCAT_HOME`: Path to Tomcat installation
- `PROJECT_NAME`: Your project name (default: SpringMvcHelloWorld)
- `PORT`: Port number (default: 8080)

## Usage

### Starting the Application

```bash
# Make sure you're in the project root directory
cd /Users/abiralkhanal/Documents/SpringMvcHelloWorld

# Run the start script
./start-tomcat10.sh
```

The script will:
1. Check if Tomcat is properly installed
2. Handle any port conflicts
3. Build the project with Maven
4. Deploy the WAR file to Tomcat
5. Start Tomcat
6. Wait for the application to be ready
7. Display access URLs and endpoints

### Stopping the Application

```bash
# Run the stop script
./stop-tomcat10.sh
```

The script will:
1. Gracefully stop Tomcat
2. Force stop if needed
3. Optionally clean up the deployment
4. Show final status

## Maven Integration

The `pom.xml` has been updated to use the Tomcat 10.1.44 Maven plugin. You can also run the application using Maven:

```bash
# Set Tomcat home
export TOMCAT_HOME=/path/to/apache-tomcat-10.1.44

# Run with Maven
mvn tomcat10:run
```

## Application URLs

Once started, your application will be available at:

- **Home**: http://localhost:8080/SpringMvcHelloWorld/
- **Register**: http://localhost:8080/SpringMvcHelloWorld/employee/register
- **Login**: http://localhost:8080/SpringMvcHelloWorld/login
- **API Health**: http://localhost:8080/SpringMvcHelloWorld/api/employee/health
- **API List**: http://localhost:8080/SpringMvcHelloWorld/api/employee/list
- **JWT Test**: http://localhost:8080/SpringMvcHelloWorld/jwt-test.html

## Troubleshooting

### Port Already in Use
The start script automatically handles port conflicts by stopping existing processes. If you encounter issues:

```bash
# Check what's using port 8080
lsof -i :8080

# Force kill processes on port 8080
lsof -ti:8080 | xargs kill -9
```

### Tomcat Not Found
Make sure Tomcat 10.1.44 is properly installed and set the `TOMCAT_HOME` environment variable:

```bash
export TOMCAT_HOME=/path/to/apache-tomcat-10.1.44
```

### Build Failures
Ensure Maven is installed and all dependencies are available:

```bash
mvn clean package
```

### Permission Issues
Make sure the scripts are executable:

```bash
chmod +x start-tomcat10.sh stop-tomcat10.sh
```

## Logs

Tomcat logs are available in: `$TOMCAT_HOME/logs/`

Application logs are configured in `src/main/resources/logback-spring.xml`

## Features

- ✅ Automatic port conflict resolution
- ✅ Colored output for better readability
- ✅ Comprehensive error handling
- ✅ Health check validation
- ✅ Graceful and force shutdown options
- ✅ Deployment cleanup options
- ✅ Environment variable configuration
- ✅ Maven integration
- ✅ Detailed status information

## Support

If you encounter any issues:
1. Check the Tomcat logs in `$TOMCAT_HOME/logs/`
2. Verify all prerequisites are installed
3. Ensure the correct Tomcat version (10.1.44) is being used
4. Check that Java 17 is being used (as required by the project)


