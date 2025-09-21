@echo off
echo Running Experimental Setup...

REM Create classes directory if it doesn't exist
if not exist "classes" mkdir classes

REM Compile required classes
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

javac -cp classes -d classes src\main\java\ExperimentalSetup.java
if %errorlevel% neq 0 (
    echo Compilation of ExperimentalSetup.java failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the experimental setup
java -cp classes ExperimentalSetup

echo.
echo Experimental setup complete!
echo Check the logs/ directory for detailed experiment logs.
pause
