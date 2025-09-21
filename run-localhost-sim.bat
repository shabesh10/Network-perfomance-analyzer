@echo off
echo Running Localhost Packet Simulation...

REM Check if jpcap.jar exists
if not exist "lib\jpcap.jar" (
    echo ERROR: jpcap.jar not found in lib directory!
    echo Please download jpcap.jar and place it in the lib folder.
    echo See lib\README.txt for download instructions.
    pause
    exit /b 1
)

REM Check if classes directory exists
if not exist "classes" (
    echo ERROR: Classes directory not found!
    echo Please run compile.bat first to compile the programs.
    pause
    exit /b 1
)

REM Run the localhost simulation
java -cp "lib\jpcap.jar;classes" LocalhostSimulator

echo.
echo Simulation complete! Check output/captured_packets.csv
echo This file is ready for Power BI import.
pause
