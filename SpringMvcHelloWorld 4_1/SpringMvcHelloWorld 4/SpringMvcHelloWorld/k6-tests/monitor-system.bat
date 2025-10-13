@echo off
REM ============================================================================
REM System Resource Monitor Launcher for Windows
REM ============================================================================

echo Starting System Resource Monitor...
echo.

REM Check if PowerShell is available
where powershell >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERROR: PowerShell is not available on this system.
    echo Please install PowerShell to use this monitoring script.
    pause
    exit /b 1
)

REM Run the PowerShell script
powershell -ExecutionPolicy Bypass -File "%~dp0monitor-system.ps1" %*

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: The monitoring script encountered an error.
    pause
)

