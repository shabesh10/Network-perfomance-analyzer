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

REM Prefer a user-specified Java (e.g., 32-bit JRE) if provided
set "JAVA_EXE=%JAVA_EXE%"
if "%JAVA_EXE%"=="" set "JAVA_EXE=java"

REM Add native DLL directory to PATH for this process
set "DLL_DIR=lib\jpcap-0.01.16-win32\lib"
if exist "%DLL_DIR%" set "PATH=%CD%\%DLL_DIR%;%PATH%"

REM Run the PacketCapture program
"%JAVA_EXE%" -cp "lib\net.sourceforge.jpcap-0.01.16.jar;classes" PacketCapture

pause
