@echo off
REM Spring MVC Application Startup Script for Tomcat 10.1.44 (Windows)
REM =================================================================

REM Configuration - Update these paths as needed
set TOMCAT_HOME=%TOMCAT_HOME%
if "%TOMCAT_HOME%"=="" set TOMCAT_HOME=C:\apache-tomcat-10.1.44
set PROJECT_NAME=SpringMvcHelloWorld
set PORT=8080
set WAR_FILE=target\%PROJECT_NAME%.war

echo.
echo 🚀 Starting Spring MVC Application with Tomcat 10.1.44
echo =====================================================
echo.

REM Function to check if port is in use
netstat -an | findstr ":%PORT%" >nul
if %errorlevel% equ 0 (
    echo ⚠️  Port %PORT% is already in use. Stopping existing processes...
    for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":%PORT%"') do (
        taskkill /PID %%a /F >nul 2>&1
    )
    timeout /t 3 /nobreak >nul
)

REM Validate Tomcat installation
if not exist "%TOMCAT_HOME%" (
    echo ❌ Tomcat directory not found: %TOMCAT_HOME%
    echo ❌ Please set TOMCAT_HOME environment variable or update the script
    echo ❌ Example: set TOMCAT_HOME=C:\path\to\apache-tomcat-10.1.44
    pause
    exit /b 1
)

if not exist "%TOMCAT_HOME%\bin\startup.bat" (
    echo ❌ Tomcat startup script not found: %TOMCAT_HOME%\bin\startup.bat
    pause
    exit /b 1
)

if not exist "%TOMCAT_HOME%\webapps" (
    echo ❌ Tomcat webapps directory not found: %TOMCAT_HOME%\webapps
    pause
    exit /b 1
)

REM Check if Maven is available
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven not found. Please install Maven and ensure it's in your PATH
    pause
    exit /b 1
)

REM Build the project
echo 🚀 Building project with Maven...
call mvn clean package -q
if %errorlevel% neq 0 (
    echo ❌ Build failed!
    pause
    exit /b 1
)
echo ✅ Build successful!

REM Deploy the application
echo 🚀 Deploying application to Tomcat...
if not exist "%WAR_FILE%" (
    echo ❌ WAR file not found: %WAR_FILE%
    echo ❌ Please run 'mvn clean package' first
    pause
    exit /b 1
)

REM Remove existing deployment
if exist "%TOMCAT_HOME%\webapps\%PROJECT_NAME%" (
    echo ⚠️  Removing existing deployment...
    rmdir /s /q "%TOMCAT_HOME%\webapps\%PROJECT_NAME%"
)

if exist "%TOMCAT_HOME%\webapps\%PROJECT_NAME%.war" (
    del "%TOMCAT_HOME%\webapps\%PROJECT_NAME%.war"
)

REM Copy new WAR file
copy "%WAR_FILE%" "%TOMCAT_HOME%\webapps\" >nul
if %errorlevel% neq 0 (
    echo ❌ Failed to deploy application
    pause
    exit /b 1
)
echo ✅ Application deployed successfully!

REM Start Tomcat
echo 🚀 Starting Tomcat 10.1.44...
cd /d "%TOMCAT_HOME%\bin"
call startup.bat
if %errorlevel% neq 0 (
    echo ❌ Failed to start Tomcat
    pause
    exit /b 1
)
echo ✅ Tomcat started successfully!

REM Wait for application to be ready
echo 🚀 Waiting for application to be ready...
set /a attempt=1
set /a max_attempts=30

:wait_loop
if %attempt% gtr %max_attempts% (
    echo ⚠️  Application may not be fully ready yet, but Tomcat is running
    goto :display_info
)

powershell -Command "try { Invoke-WebRequest -Uri 'http://localhost:%PORT%/%PROJECT_NAME%/' -UseBasicParsing -TimeoutSec 2 | Out-Null; exit 0 } catch { exit 1 }" >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Application is ready!
    goto :display_info
)

echo|set /p="."
timeout /t 2 /nobreak >nul
set /a attempt+=1
goto :wait_loop

:display_info
echo.
echo =====================================================
echo ✅ Application deployed successfully!
echo =====================================================
echo 🌐 Application URL: http://localhost:%PORT%/%PROJECT_NAME%/
echo 📋 Available endpoints:
echo    • Home: http://localhost:%PORT%/%PROJECT_NAME%/
echo    • Register: http://localhost:%PORT%/%PROJECT_NAME%/employee/register
echo    • Login: http://localhost:%PORT%/%PROJECT_NAME%/login
echo    • API Health: http://localhost:%PORT%/%PROJECT_NAME%/api/employee/health
echo    • API List: http://localhost:%PORT%/%PROJECT_NAME%/api/employee/list
echo    • JWT Test: http://localhost:%PORT%/%PROJECT_NAME%/jwt-test.html
echo.
echo 🛠️  Management:
echo    • Tomcat Manager: http://localhost:%PORT%/manager/html
echo    • Stop Tomcat: %TOMCAT_HOME%\bin\shutdown.bat
echo    • Logs: %TOMCAT_HOME%\logs\
echo =====================================================
echo.
echo Press any key to continue...
pause >nul


