# Quick Start Guide - Spring MVC with Tomcat 10.1.44

## 🚀 Quick Start

### Prerequisites
- Java 17 installed
- Maven installed
- Apache Tomcat 10.1.44 downloaded and extracted

### 1. Set Tomcat Home (Optional)
```bash
export TOMCAT_HOME=/path/to/apache-tomcat-10.1.44
```

### 2. Start the Application
```bash
./start-tomcat10.sh
```

### 3. Access Your Application
- **Main App**: http://localhost:8080/SpringMvcHelloWorld/
- **Register**: http://localhost:8080/SpringMvcHelloWorld/employee/register
- **API Health**: http://localhost:8080/SpringMvcHelloWorld/api/employee/health

### 4. Stop the Application
```bash
./stop-tomcat10.sh
```

## 📁 What Was Created/Updated

### Scripts
- ✅ `start-tomcat10.sh` - Enhanced startup script with error handling
- ✅ `stop-tomcat10.sh` - Graceful shutdown script
- ✅ Both scripts are executable and ready to use

### Configuration
- ✅ `pom.xml` - Updated to use Tomcat 10.1.44 Maven plugin
- ✅ `src/main/java/com/example/util/JwtUtil.java` - Created missing JWT utility class

### Documentation
- ✅ `TOMCAT_SCRIPTS_README.md` - Comprehensive documentation
- ✅ `QUICK_START.md` - This quick start guide

## 🔧 Features

- **Automatic Port Management**: Handles port conflicts automatically
- **Colored Output**: Easy-to-read status messages
- **Error Handling**: Comprehensive error checking and reporting
- **Health Checks**: Waits for application to be ready
- **Flexible Configuration**: Environment variable support
- **Maven Integration**: Works with Maven Tomcat plugin

## 🛠️ Troubleshooting

### Port Already in Use
The script automatically handles this, but if you need manual intervention:
```bash
lsof -ti:8080 | xargs kill -9
```

### Tomcat Not Found
Set the TOMCAT_HOME environment variable:
```bash
export TOMCAT_HOME=/path/to/apache-tomcat-10.1.44
```

### Build Issues
Clean and rebuild:
```bash
mvn clean package
```

## 📋 Available Endpoints

- **Home**: `/SpringMvcHelloWorld/`
- **Register**: `/SpringMvcHelloWorld/employee/register`
- **Login**: `/SpringMvcHelloWorld/login`
- **API Health**: `/SpringMvcHelloWorld/api/employee/health`
- **API List**: `/SpringMvcHelloWorld/api/employee/list`
- **JWT Test**: `/SpringMvcHelloWorld/jwt-test.html`

## 🎯 Next Steps

1. Run `./start-tomcat10.sh` to start your application
2. Open http://localhost:8080/SpringMvcHelloWorld/ in your browser
3. Test the registration and login functionality
4. Use `./stop-tomcat10.sh` when you're done

Your Spring MVC application with JWT authentication is now ready to run on Tomcat 10.1.44! 🎉


