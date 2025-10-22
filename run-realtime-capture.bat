@echo off
echo Running Real-Time Packet Capture...

REM Check if classes directory exists
if not exist "classes" (
    echo ERROR: Classes directory not found!
    echo Please run compile.bat first to compile the programs.
    pause
    exit /b 1
)

REM Check if JPcap library exists
if not exist "lib\jpcap.jar" (
    echo ERROR: JPcap library not found!
    echo Please download jpcap.jar and place it in the lib directory.
    echo Download from: https://sourceforge.net/projects/jpcap/files/
    pause
    exit /b 1
)

echo.
echo *** IMPORTANT: This program requires administrator privileges ***
echo *** Please run this batch file as administrator ***
echo.
echo Starting real-time packet capture for 2 minutes...
echo Real packet data will be saved to the output folder.
echo.

REM Run the real-time packet capture (requires JPcap native libs)
java -cp "lib/jpcap.jar;classes" RealTimePacketCapture

echo.
echo Real-time capture complete! Check output folder for CSV files:
echo - real_time_packets.csv (main file for Power BI)
echo - Additional filtered files by protocol and direction
echo.
echo These files contain REAL network packet data captured from your system.
pause