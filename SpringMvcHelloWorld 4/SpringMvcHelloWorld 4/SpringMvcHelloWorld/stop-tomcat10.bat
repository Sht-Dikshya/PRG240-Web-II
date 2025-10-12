@echo off
REM Spring MVC Application Stop Script for Tomcat 10.1.44 (Windows)
REM ================================================================

REM Configuration - Update these paths as needed
set TOMCAT_HOME=%TOMCAT_HOME%
if "%TOMCAT_HOME%"=="" set TOMCAT_HOME=C:\apache-tomcat-10.1.44
set PROJECT_NAME=SpringMvcHelloWorld
set PORT=8080

echo.
echo 🛑 Stopping Spring MVC Application on Tomcat 10.1.44
echo =====================================================
echo.

REM Check if port is in use
netstat -an | findstr ":%PORT%" >nul
if %errorlevel% neq 0 (
    echo ⚠️  No Tomcat process found on port %PORT%
    echo Tomcat may already be stopped or running on a different port
    pause
    exit /b 0
)

REM Stop Tomcat gracefully
echo 🛑 Stopping Tomcat gracefully...
if exist "%TOMCAT_HOME%\bin\shutdown.bat" (
    cd /d "%TOMCAT_HOME%\bin"
    call shutdown.bat
    if %errorlevel% neq 0 (
        echo ❌ Failed to stop Tomcat gracefully
        goto :force_stop
    )
    
    REM Wait for graceful shutdown
    set /a attempt=1
    set /a max_attempts=15
    
    :graceful_wait
    if %attempt% gtr %max_attempts% (
        echo ⚠️  Graceful shutdown taking too long, forcing stop...
        goto :force_stop
    )
    
    netstat -an | findstr ":%PORT%" >nul
    if %errorlevel% neq 0 (
        echo ✅ Tomcat stopped gracefully!
        goto :cleanup
    )
    
    echo|set /p="."
    timeout /t 2 /nobreak >nul
    set /a attempt+=1
    goto :graceful_wait
) else (
    echo ❌ Tomcat shutdown script not found: %TOMCAT_HOME%\bin\shutdown.bat
    goto :force_stop
)

:force_stop
echo ⚠️  Force stopping processes on port %PORT%...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":%PORT%"') do (
    echo Found process: %%a
    taskkill /PID %%a /F >nul 2>&1
)

timeout /t 2 /nobreak >nul

REM Check if still running
netstat -an | findstr ":%PORT%" >nul
if %errorlevel% equ 0 (
    echo ❌ Failed to stop all processes on port %PORT%
    pause
    exit /b 1
) else (
    echo ✅ All processes stopped!
)

:cleanup
REM Ask if user wants to clean up deployment
set /p cleanup_choice="Do you want to clean up the deployed application? (y/N): "
if /i "%cleanup_choice%"=="y" (
    echo 🛑 Cleaning up deployment...
    
    if exist "%TOMCAT_HOME%\webapps\%PROJECT_NAME%" (
        echo ⚠️  Removing deployed application directory...
        rmdir /s /q "%TOMCAT_HOME%\webapps\%PROJECT_NAME%"
    )
    
    if exist "%TOMCAT_HOME%\webapps\%PROJECT_NAME%.war" (
        echo ⚠️  Removing WAR file...
        del "%TOMCAT_HOME%\webapps\%PROJECT_NAME%.war"
    )
    
    echo ✅ Deployment cleaned up!
)

REM Display final status
echo.
echo =====================================================
netstat -an | findstr ":%PORT%" >nul
if %errorlevel% equ 0 (
    echo ⚠️  Tomcat is still running on port %PORT%
    echo You may need to force stop it with: taskkill /F /IM java.exe
) else (
    echo ✅ Tomcat has been stopped successfully!
)
echo =====================================================
echo.
pause


