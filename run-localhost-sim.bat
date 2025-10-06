@echo off
echo Running Localhost Packet Simulation...

REM Check if classes directory exists
if not exist "classes" (
    echo ERROR: Classes directory not found!
    echo Please run compile.bat first to compile the programs.
    pause
    exit /b 1
)

REM Run the localhost simulation (no native libs required)
java -cp "classes" LocalhostSimulator

echo.
echo Simulation complete! Check output/captured_packets.csv
echo This file is ready for Power BI import.
pause
