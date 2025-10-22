@echo off
echo Running Modern Network Activity Monitor...

REM Check if classes directory exists
if not exist "classes" (
    echo ERROR: Classes directory not found!
    echo Please run compile.bat first to compile the programs.
    pause
    exit /b 1
)

echo.
echo *** Modern Network Activity Monitor ***
echo *** No native libraries required - works on any Java system ***
echo.
echo Starting network activity monitoring for 2 minutes...
echo Network activity data will be saved to the output folder.
echo.

REM Run the modern network monitor (no native libs required)
java -cp "classes" ModernPacketCapture

echo.
echo Network monitoring complete! 
echo Single CSV file created: output/captured_packets.csv
echo This file is ready for Power BI import and contains all network activity data.
pause