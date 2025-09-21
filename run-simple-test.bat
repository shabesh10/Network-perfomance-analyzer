@echo off
echo Running Simple CSV Test (No JPcap Required)...

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

javac -cp classes -d classes src\main\java\CsvExporterTest.java
if %errorlevel% neq 0 (
    echo Compilation of CsvExporterTest.java failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the CSV test
java -cp classes CsvExporterTest

echo.
echo Test complete!
pause
