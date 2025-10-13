################################################################################
# System Resource Monitor for K6 Load Testing (Windows PowerShell)
#
# This script monitors CPU, Memory, Network, and Disk I/O while the K6
# load test is running. It logs the data to a file for later analysis.
#
# Usage:
#   .\monitor-system.ps1 [output_file]
#
# Default output: system-metrics-YYYYMMDD-HHMMSS.log
#
# Requirements: Run PowerShell as Administrator for best results
#
# Author: Spring MVC Load Testing
# Version: 1.0
################################################################################

param(
    [string]$OutputFile = ('system-metrics-' + (Get-Date -Format 'yyyyMMdd-HHmmss') + '.log')
)

# Colors for console output
$colors = @{
    Green = 'Green'
    Yellow = 'Yellow'
    Red = 'Red'
    Blue = 'Cyan'
    White = 'White'
}

# Tomcat process name
$TomcatProcessName = "java"
$TomcatIdentifier = "catalina"

# Function to get Tomcat process
function Get-TomcatProcess {
    Get-Process -Name $TomcatProcessName -ErrorAction SilentlyContinue | 
        Where-Object { $_.CommandLine -like "*$TomcatIdentifier*" -or $_.MainWindowTitle -like "*Tomcat*" } |
        Select-Object -First 1
}

# Function to format bytes to KB/s
function Format-BytesToKB {
    param([double]$bytes)
    return [math]::Round($bytes / 1KB, 2)
}

# Function to format bytes to MB
function Format-BytesToMB {
    param([double]$bytes)
    return [math]::Round($bytes / 1MB, 2)
}

# Display header
Write-Host "=====================================================================" -ForegroundColor $colors.Blue
Write-Host "System Resource Monitor for K6 Load Testing (Windows)" -ForegroundColor $colors.Blue
Write-Host "=====================================================================" -ForegroundColor $colors.Blue
Write-Host "Output file: " -NoNewline
Write-Host $OutputFile -ForegroundColor $colors.Green
Write-Host "Monitoring interval: " -NoNewline
Write-Host "5 seconds" -ForegroundColor $colors.Yellow
Write-Host "Press " -NoNewline
Write-Host "Ctrl+C" -ForegroundColor $colors.Red -NoNewline
Write-Host " to stop monitoring"
Write-Host "=====================================================================" -ForegroundColor $colors.Blue
Write-Host ""

# Initialize output file with headers
$header = @'
System Resource Monitoring - K6 Load Test
Started: {0}
Hostname: {1}
OS: {2}
====================================================================

Timestamp | CPU% | Memory% | MemoryMB | Network RX (KB/s) | Network TX (KB/s) | Disk Read (KB/s) | Disk Write (KB/s) | Tomcat CPU% | Tomcat Mem MB
'@ -f (Get-Date), $env:COMPUTERNAME, ([System.Environment]::OSVersion.VersionString)

$header | Out-File -FilePath $OutputFile -Encoding UTF8

Write-Host "✓ Monitoring started..." -ForegroundColor $colors.Green
Write-Host ""
Write-Host 'Timestamp               | Overall CPU | Memory Used | Tomcat CPU | Tomcat Mem  | Network (RX/TX)      | Disk (R/W)' -ForegroundColor $colors.Yellow
Write-Host "-------------------------------------------------------------------------------------------------------------------"

# Counter
$count = 0

# Store previous network stats for delta calculation
$prevNetworkStats = $null

# Store previous disk stats for delta calculation
$prevDiskStats = $null
$prevTime = Get-Date

try {
    while ($true) {
        $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
        $currentTime = Get-Date
        
        # Calculate time difference for rate calculations
        $timeDiff = ($currentTime - $prevTime).TotalSeconds
        if ($timeDiff -eq 0) { $timeDiff = 1 }
        
        # ============================================================
        # CPU Usage (Overall)
        # ============================================================
        $cpuUsage = (Get-Counter '\Processor(_Total)\% Processor Time' -ErrorAction SilentlyContinue).CounterSamples.CookedValue
        $cpuUsage = [math]::Round($cpuUsage, 2)
        
        # ============================================================
        # Memory Usage (Overall)
        # ============================================================
        $os = Get-CimInstance Win32_OperatingSystem
        $totalMemory = $os.TotalVisibleMemorySize
        $freeMemory = $os.FreePhysicalMemory
        $usedMemory = $totalMemory - $freeMemory
        $memPercent = [math]::Round(($usedMemory / $totalMemory) * 100, 2)
        $memUsedMB = Format-BytesToMB ($usedMemory * 1KB)
        
        # ============================================================
        # Network Usage
        # ============================================================
        $networkAdapters = Get-Counter '\Network Interface(*)\Bytes Received/sec', '\Network Interface(*)\Bytes Sent/sec' -ErrorAction SilentlyContinue
        $bytesReceived = ($networkAdapters.CounterSamples | Where-Object { $_.Path -like "*Bytes Received/sec*" } | Measure-Object -Property CookedValue -Sum).Sum
        $bytesSent = ($networkAdapters.CounterSamples | Where-Object { $_.Path -like "*Bytes Sent/sec*" } | Measure-Object -Property CookedValue -Sum).Sum
        
        $networkRxKBps = Format-BytesToKB $bytesReceived
        $networkTxKBps = Format-BytesToKB $bytesSent
        
        # ============================================================
        # Disk I/O
        # ============================================================
        $diskReadBytes = (Get-Counter '\PhysicalDisk(_Total)\Disk Read Bytes/sec' -ErrorAction SilentlyContinue).CounterSamples.CookedValue
        $diskWriteBytes = (Get-Counter '\PhysicalDisk(_Total)\Disk Write Bytes/sec' -ErrorAction SilentlyContinue).CounterSamples.CookedValue
        
        $diskReadKBps = Format-BytesToKB $diskReadBytes
        $diskWriteKBps = Format-BytesToKB $diskWriteBytes
        
        # ============================================================
        # Tomcat-specific metrics
        # ============================================================
        $tomcatProcess = Get-TomcatProcess
        
        if ($null -eq $tomcatProcess) {
            # Try to find any Java process as fallback
            $tomcatProcess = Get-Process -Name $TomcatProcessName -ErrorAction SilentlyContinue | Select-Object -First 1
        }
        
        if ($null -eq $tomcatProcess) {
            $tomcatCpu = "N/A"
            $tomcatMemMB = "N/A"
            $tomcatStatus = "NOT RUNNING"
        } else {
            # Get Tomcat CPU (this is an approximation)
            $tomcatCpu = [math]::Round($tomcatProcess.CPU, 2)
            
            # Get Tomcat Memory in MB
            $tomcatMemMB = Format-BytesToMB $tomcatProcess.WorkingSet64
            $tomcatStatus = "RUNNING (PID: " + $tomcatProcess.Id + ")"
        }
        
        # Format values with error handling
        $cpuUsageStr = if ($cpuUsage) { $cpuUsage } else { "N/A" }
        $memPercentStr = if ($memPercent) { $memPercent } else { "N/A" }
        $memUsedMBStr = if ($memUsedMB) { $memUsedMB } else { "N/A" }
        $networkRxStr = if ($networkRxKBps) { $networkRxKBps } else { "0" }
        $networkTxStr = if ($networkTxKBps) { $networkTxKBps } else { "0" }
        $diskReadStr = if ($diskReadKBps) { $diskReadKBps } else { "0" }
        $diskWriteStr = if ($diskWriteKBps) { $diskWriteKBps } else { "0" }
        
        # ============================================================
        # Log to file
        # ============================================================
        $logLine = @($timestamp, $cpuUsageStr, $memPercentStr, $memUsedMBStr, $networkRxStr, $networkTxStr, $diskReadStr, $diskWriteStr, $tomcatCpu, $tomcatMemMB) -join ' | '
        $logLine | Out-File -FilePath $OutputFile -Append -Encoding UTF8
        
        # ============================================================
        # Display to console (formatted)
        # ============================================================
        Write-Host ($timestamp + ' | ') -NoNewline
        Write-Host ("{0,6}%" -f $cpuUsageStr) -ForegroundColor $colors.Yellow -NoNewline
        Write-Host ' | ' -NoNewline
        Write-Host ("{0,6}% ({1,6}MB)" -f $memPercentStr, $memUsedMBStr) -ForegroundColor $colors.Yellow -NoNewline
        Write-Host ' | ' -NoNewline
        Write-Host ("{0,8}" -f $tomcatCpu) -ForegroundColor $colors.Red -NoNewline
        Write-Host ' | ' -NoNewline
        Write-Host ("{0,8}MB" -f $tomcatMemMB) -ForegroundColor $colors.Red -NoNewline
        Write-Host ' | ' -NoNewline
        Write-Host ("{0,6}/{1,6}KB/s" -f $networkRxStr, $networkTxStr) -ForegroundColor $colors.Blue -NoNewline
        Write-Host ' | ' -NoNewline
        Write-Host ("{0,6}/{1,6}KB/s" -f $diskReadStr, $diskWriteStr) -ForegroundColor $colors.Green
        
        # Increment counter
        $count++
        
        # Every 12th iteration (1 minute), show summary
        if ($count % 12 -eq 0) {
            Write-Host "-------------------------------------------------------------------------------------------------------------------"
            Write-Host ("Monitoring for " + ($count * 5) + " seconds... (" + ($count / 12) + " minutes) | Tomcat Status: " + $tomcatStatus) -ForegroundColor $colors.Blue
            Write-Host "-------------------------------------------------------------------------------------------------------------------"
        }
        
        # Update previous time
        $prevTime = $currentTime
        
        # Sleep for 5 seconds
        Start-Sleep -Seconds 5
    }
}
catch {
    Write-Host "`nMonitoring stopped." -ForegroundColor $colors.Yellow
    Write-Host ("Log saved to: " + $OutputFile) -ForegroundColor $colors.Green
    Write-Host ("Error: " + $_) -ForegroundColor $colors.Red
}
finally {
    # Final message
    Write-Host "`n=====================================================================" -ForegroundColor $colors.Blue
    Write-Host ("Monitoring session ended at " + (Get-Date)) -ForegroundColor $colors.Blue
    Write-Host ("Log file: " + $OutputFile) -ForegroundColor $colors.Green
    Write-Host "=====================================================================" -ForegroundColor $colors.Blue
}


