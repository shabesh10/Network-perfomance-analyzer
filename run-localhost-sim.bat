@echo off
echo Running Real-Time Localhost Packet Capture...

REM Check if classes directory exists
if not exist "classes" (
    echo ERROR: Classes directory not found!
    echo Please run compile.bat first to compile the programs.
    pause
    exit /b 1
)

REM Run the real-time packet capture (requires JPcap library)
java -Djava.library.path="lib/jpcap-0.01.16-win32/lib" -cp "lib/net.sourceforge.jpcap-0.01.16.jar;classes" RealTimePacketCapture

echo.
echo Real-time capture complete!
echo Single CSV file created: output/captured_packets.csv
echo This file contains REAL network packet data and is ready for Power BI import.
pause
