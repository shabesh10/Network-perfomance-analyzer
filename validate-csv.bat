@echo off
echo Running CSV Validation for Power BI Compatibility...

REM Check if Python is available
python --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Python not found!
    echo Please install Python to run the validation script.
    echo Alternatively, you can manually check the CSV file.
    pause
    exit /b 1
)

REM Check if CSV file exists
if not exist "output\captured_packets.csv" (
    echo ERROR: CSV file not found!
    echo Please run the simulation or packet capture first.
    echo Run: run-localhost-sim.bat or run-capture.bat
    pause
    exit /b 1
)

REM Run validation
python validate-csv.py

echo.
echo Validation complete!
pause
