@echo off
echo Running ListInterfaces program...

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

REM Run the ListInterfaces program
java -cp "lib\net.sourceforge.jpcap-0.01.16.jar;classes" ListInterfaces

pause
