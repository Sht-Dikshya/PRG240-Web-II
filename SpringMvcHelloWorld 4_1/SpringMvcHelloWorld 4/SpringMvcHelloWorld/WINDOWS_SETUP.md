# Windows Setup Guide - Spring MVC with Tomcat 10.1.44

## 🚀 Quick Start for Windows

### Prerequisites
- **Java 17** installed and in PATH
- **Maven** installed and in PATH
- **Apache Tomcat 10.1.44** downloaded and extracted
- **PowerShell** or **Command Prompt**

### 1. Set Tomcat Home (Optional)
```cmd
set TOMCAT_HOME=C:\path\to\apache-tomcat-10.1.44
```

### 2. Start the Application
```cmd
start-tomcat10.bat
```

### 3. Access Your Application
- **Main App**: http://localhost:8080/SpringMvcHelloWorld/
- **Register**: http://localhost:8080/SpringMvcHelloWorld/employee/register
- **API Health**: http://localhost:8080/SpringMvcHelloWorld/api/employee/health

### 4. Stop the Application
```cmd
stop-tomcat10.bat
```

## 📁 Windows Scripts

### `start-tomcat10.bat` - Windows Startup Script
- Builds the project using Maven
- Deploys the WAR file to Tomcat
- Starts Tomcat 10.1.44
- Handles port conflicts automatically
- Provides colored output and status information

### `stop-tomcat10.bat` - Windows Shutdown Script
- Gracefully stops Tomcat
- Force stops if graceful shutdown fails
- Optionally cleans up deployed application
- Provides status information

## 🔧 Configuration

### Environment Variables
Set the `TOMCAT_HOME` environment variable to specify your Tomcat installation path:

```cmd
set TOMCAT_HOME=C:\apache-tomcat-10.1.44
```

If not set, the script defaults to: `C:\apache-tomcat-10.1.44`

### Script Configuration
Edit the following variables in `start-tomcat10.bat` if needed:
- `TOMCAT_HOME`: Path to Tomcat installation
- `PROJECT_NAME`: Your project name (default: SpringMvcHelloWorld)
- `PORT`: Port number (default: 8080)

## 🛠️ Troubleshooting

### Port Already in Use
The script automatically handles this, but if you need manual intervention:
```cmd
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F
```

### Tomcat Not Found
Set the TOMCAT_HOME environment variable:
```cmd
set TOMCAT_HOME=C:\path\to\apache-tomcat-10.1.44
```

### Build Issues
Clean and rebuild:
```cmd
mvn clean package
```

### Maven Not Found
Ensure Maven is installed and in your PATH:
```cmd
mvn --version
```

### Java Not Found
Ensure Java 17 is installed and in your PATH:
```cmd
java -version
```

## 📋 Available Endpoints

- **Home**: `/SpringMvcHelloWorld/`
- **Register**: `/SpringMvcHelloWorld/employee/register`
- **Login**: `/SpringMvcHelloWorld/login`
- **API Health**: `/SpringMvcHelloWorld/api/employee/health`
- **API List**: `/SpringMvcHelloWorld/api/employee/list`
- **JWT Test**: `/SpringMvcHelloWorld/jwt-test.html`

## 🎯 Usage Examples

### Using Command Prompt
```cmd
cd C:\Users\user\Downloads\SpringMvcHelloWorld_1
start-tomcat10.bat
```

### Using PowerShell
```powershell
cd C:\Users\user\Downloads\SpringMvcHelloWorld_1
.\start-tomcat10.bat
```

### Setting Environment Variables Permanently
```cmd
setx TOMCAT_HOME "C:\apache-tomcat-10.1.44"
```

## 🔍 Features

- ✅ **Automatic Port Management**: Handles port conflicts automatically
- ✅ **Colored Output**: Easy-to-read status messages
- ✅ **Error Handling**: Comprehensive error checking and reporting
- ✅ **Health Checks**: Waits for application to be ready
- ✅ **Flexible Configuration**: Environment variable support
- ✅ **Windows Compatible**: Works with CMD and PowerShell
- ✅ **Process Management**: Graceful and force stop options

## 📝 Notes

- The scripts use Windows batch commands and PowerShell for health checks
- Make sure to run the scripts from the project root directory
- The scripts will pause at the end to show results
- All paths should use backslashes (`\`) for Windows compatibility

## 🆘 Support

If you encounter any issues:
1. Check the Tomcat logs in `%TOMCAT_HOME%\logs\`
2. Verify all prerequisites are installed
3. Ensure the correct Tomcat version (10.1.44) is being used
4. Check that Java 17 is being used (as required by the project)
5. Make sure Maven is in your PATH

Your Spring MVC application with JWT authentication is now ready to run on Windows with Tomcat 10.1.44! 🎉


