# !/bin/bash

###############################################################################
# System Resource Monitor for K6 Load Testing
#
# This script monitors CPU, Memory, Network, and Disk I/O while the K6
# load test is running. It logs the data to a file for later analysis.
#
# Usage:
#   ./monitor-system.sh [output_file]
#
# Default output: system-metrics-$(date +%Y%m%d-%H%M%S).log
#
# Author: Spring MVC Load Testing
# Version: 1.0
###############################################################################

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Output file
OUTPUT_FILE="${1:-system-metrics-$(date +%Y%m%d-%H%M%S).log}"

# Tomcat process name
TOMCAT_PROCESS="org.apache.catalina.startup.Bootstrap"

# Get Tomcat PID
get_tomcat_pid() {
    pgrep -f "$TOMCAT_PROCESS" | head -1
}

echo -e "${BLUE}=====================================================================${NC}"
echo -e "${BLUE}System Resource Monitor for K6 Load Testing${NC}"
echo -e "${BLUE}=====================================================================${NC}"
echo -e "Output file: ${GREEN}$OUTPUT_FILE${NC}"
echo -e "Monitoring interval: ${YELLOW}5 seconds${NC}"
echo -e "Press ${RED}Ctrl+C${NC} to stop monitoring"
echo -e "${BLUE}=====================================================================${NC}\n"

# Initialize output file with headers
cat > "$OUTPUT_FILE" <<EOF
System Resource Monitoring - K6 Load Test
Started: $(date)
Hostname: $(hostname)
OS: $(uname -s)
====================================================================

Timestamp | CPU% | Memory% | MemoryMB | Network RX (KB/s) | Network TX (KB/s) | Disk Read (KB/s) | Disk Write (KB/s) | Tomcat CPU% | Tomcat Mem%
EOF

echo -e "${GREEN}✓ Monitoring started...${NC}\n"
echo -e "${YELLOW}Timestamp          | Overall CPU | Memory Used | Tomcat CPU | Tomcat Mem | Network (RX/TX)${NC}"
echo "--------------------------------------------------------------------------------------------"

# Counter
COUNT=0

# Monitoring loop
while true; do
    TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')

    # Get Tomcat PID
    TOMCAT_PID=$(get_tomcat_pid)

    if [ -z "$TOMCAT_PID" ]; then
        echo -e "${RED}⚠️  Tomcat is not running!${NC}"
        TOMCAT_CPU="N/A"
        TOMCAT_MEM="N/A"
    else
        # Get Tomcat-specific metrics (macOS)
        if [[ "$OSTYPE" == "darwin"* ]]; then
            TOMCAT_CPU=$(ps -p $TOMCAT_PID -o %cpu | tail -1 | xargs)
            TOMCAT_MEM=$(ps -p $TOMCAT_PID -o %mem | tail -1 | xargs)
        else
            # Linux
            TOMCAT_CPU=$(ps -p $TOMCAT_PID -o %cpu --no-headers)
            TOMCAT_MEM=$(ps -p $TOMCAT_PID -o %mem --no-headers)
        fi
    fi

    # Overall system metrics (macOS)
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # CPU usage (macOS)
        CPU_USAGE=$(top -l 1 | grep "CPU usage" | awk '{print $3}' | sed 's/%//')

        # Memory usage (macOS)
        MEM_INFO=$(vm_stat | perl -ne '/page size of (\d+)/ and $size=$1; /Pages\s+([^:]+)[^\d]+(\d+)/ and printf("%-16s % 16.2f MB\n", "$1:", $2 * $size / 1048576);')
        MEM_USED=$(echo "$MEM_INFO" | grep "active:" | awk '{print $2}')
        MEM_PERCENT=$(ps -A -o %mem | awk '{s+=$1} END {print s}')

        # Network usage (basic - macOS doesn't have easy real-time network stats)
        NETWORK_RX="N/A"
        NETWORK_TX="N/A"

        # Disk I/O (macOS)
        DISK_READ="N/A"
        DISK_WRITE="N/A"
    else
        # Linux alternatives
        CPU_USAGE=$(top -bn1 | grep "Cpu(s)" | sed "s/.*, *\([0-9.]*\)%* id.*/\1/" | awk '{print 100 - $1}')
        MEM_PERCENT=$(free | grep Mem | awk '{print ($3/$2) * 100.0}')
        MEM_USED=$(free -m | grep Mem | awk '{print $3}')

        # Network
        NETWORK_RX=$(cat /sys/class/net/eth0/statistics/rx_bytes 2>/dev/null || echo "0")
        NETWORK_TX=$(cat /sys/class/net/eth0/statistics/tx_bytes 2>/dev/null || echo "0")

        # Disk I/O
        DISK_READ=$(iostat -d -x 1 2 | tail -1 | awk '{print $6}')
        DISK_WRITE=$(iostat -d -x 1 2 | tail -1 | awk '{print $7}')
    fi

    # Format values
    CPU_USAGE=${CPU_USAGE:-"N/A"}
    MEM_PERCENT=${MEM_PERCENT:-"N/A"}
    MEM_USED=${MEM_USED:-"N/A"}
    TOMCAT_CPU=${TOMCAT_CPU:-"N/A"}
    TOMCAT_MEM=${TOMCAT_MEM:-"N/A"}

    # Log to file
    echo "$TIMESTAMP | $CPU_USAGE | $MEM_PERCENT | $MEM_USED | $NETWORK_RX | $NETWORK_TX | $DISK_READ | $DISK_WRITE | $TOMCAT_CPU | $TOMCAT_MEM" >> "$OUTPUT_FILE"

    # Display to console (formatted)
    printf "${GREEN}%s${NC} | ${YELLOW}%6s%%${NC} | ${YELLOW}%6sMB${NC} | ${RED}%6s%%${NC} | ${RED}%6s%%${NC} | ${BLUE}%s/%s${NC}\n" \
        "$TIMESTAMP" \
        "$CPU_USAGE" \
        "$MEM_USED" \
        "$TOMCAT_CPU" \
        "$TOMCAT_MEM" \
        "$NETWORK_RX" \
        "$NETWORK_TX"

    # Increment counter
    ((COUNT++))

    # Every 12th iteration (1 minute), show summary
    if [ $((COUNT % 12)) -eq 0 ]; then
        echo "--------------------------------------------------------------------------------------------"
        echo -e "${BLUE}Monitoring for $((COUNT * 5)) seconds... ($(($COUNT / 12)) minutes)${NC}"
        echo "--------------------------------------------------------------------------------------------"
    fi

    # Sleep for 5 seconds
    sleep 5
done

