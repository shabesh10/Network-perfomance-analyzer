@echo off
echo Running Standalone Localhost Simulation (No JPcap Required)...

REM Create classes directory if it doesn't exist
if not exist "classes" mkdir classes

REM Compile only the required classes (without JPcap dependency)
echo Compiling required classes...
javac -d classes src\main\java\PacketRecord.java
if %errorlevel% neq 0 (
    echo Compilation of PacketRecord.java failed!
    pause
    exit /b 1
)

javac -cp classes -d classes src\main\java\CsvExporter.java
if %errorlevel% neq 0 (
    echo Compilation of CsvExporter.java failed!
    pause
    exit /b 1
)

javac -cp classes -d classes src\main\java\StandaloneLocalhostSimulator.java
if %errorlevel% neq 0 (
    echo Compilation of StandaloneLocalhostSimulator.java failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the standalone simulation
java -cp classes StandaloneLocalhostSimulator

echo.
echo Simulation complete! Check output/captured_packets.csv
echo This file is ready for Power BI import.
echo.
echo To validate the CSV file, run: validate-csv.bat
pause
