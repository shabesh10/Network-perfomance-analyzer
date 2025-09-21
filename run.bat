@echo off
echo Choose which program to run:
echo.
echo 1. ListInterfaces - List all network interfaces (REQUIRES JPcap)
echo 2. PacketCapture - Capture live packets (REQUIRES JPcap)
echo 3. CsvExporterTest - Test CSV export functionality
echo 4. LocalhostSimulator - Simulate localhost traffic
echo 5. ExperimentalSetup - Full experimental setup with logging (RECOMMENDED)
echo 6. Exit
echo.
echo NOTE: Options 1-2 require JPcap library. Option 5 works without JPcap.
echo.
set /p choice="Enter your choice (1-6): "

if "%choice%"=="1" (
    call run-list.bat
) else if "%choice%"=="2" (
    call run-capture.bat
) else if "%choice%"=="3" (
    call run-simple-test.bat
) else if "%choice%"=="4" (
    call run-standalone-sim.bat
) else if "%choice%"=="5" (
    call run-experiment.bat
) else if "%choice%"=="6" (
    echo Goodbye!
    exit /b 0
) else (
    echo Invalid choice. Please run the script again.
    pause
    exit /b 1
)
