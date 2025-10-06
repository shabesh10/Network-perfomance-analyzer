@echo off
echo Compiling Java programs with JPcap library...

REM Check if JPcap JAR exists
if not exist "lib\net.sourceforge.jpcap-0.01.16.jar" (
    echo ERROR: JPcap JAR not found in lib directory!
    echo Please ensure net.sourceforge.jpcap-0.01.16.jar is in the lib folder.
    pause
    exit /b 1
)

REM Create classes directory if it doesn't exist
if not exist "classes" mkdir classes

REM Set compile classpath to include compiled classes and JPcap JAR
set "CP=classes;lib\net.sourceforge.jpcap-0.01.16.jar"

REM Compile all Java files
echo Compiling PacketRecord.java...
javac -cp "%CP%" -d classes src\main\java\PacketRecord.java

if %errorlevel% neq 0 (
    echo Compilation of PacketRecord.java failed!
    pause
    exit /b 1
)

echo Compiling CsvExporter.java...
javac -cp "%CP%" -d classes src\main\java\CsvExporter.java

if %errorlevel% neq 0 (
    echo Compilation of CsvExporter.java failed!
    pause
    exit /b 1
)

echo Compiling ListInterfaces.java...
javac -cp "%CP%" -d classes src\main\java\ListInterfaces.java

if %errorlevel% neq 0 (
    echo Compilation of ListInterfaces.java failed!
    pause
    exit /b 1
)

echo Compiling PacketCapture.java...
javac -cp "%CP%" -d classes src\main\java\PacketCapture.java

if %errorlevel% neq 0 (
    echo Compilation of PacketCapture.java failed!
    pause
    exit /b 1
)

echo Compiling CsvExporterTest.java...
javac -cp "%CP%" -d classes src\main\java\CsvExporterTest.java

if %errorlevel% neq 0 (
    echo Compilation of CsvExporterTest.java failed!
    pause
    exit /b 1
)

echo Compiling LocalhostSimulator.java...
javac -cp "%CP%" -d classes src\main\java\LocalhostSimulator.java

if %errorlevel% neq 0 (
    echo Compilation of LocalhostSimulator.java failed!
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo You can now run the programs using:
echo   - run-list.bat (for ListInterfaces)
echo   - run-capture.bat (for PacketCapture)
echo   - run.bat (to choose which program to run)

pause
