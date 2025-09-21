@echo off
echo Running PacketCapture program...

REM Check if JPcap JAR exists
if not exist "lib\net.sourceforge.jpcap-0.01.16.jar" (
    echo ERROR: JPcap JAR not found in lib directory!
    echo Please ensure net.sourceforge.jpcap-0.01.16.jar is in the lib folder.
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

echo WARNING: This program requires administrator privileges to capture packets.
echo Make sure to run this command prompt as administrator.
echo.
echo Press any key to continue or Ctrl+C to cancel...
pause >nul

REM Run the PacketCapture program
java -cp "lib\net.sourceforge.jpcap-0.01.16.jar;classes" PacketCapture

pause
