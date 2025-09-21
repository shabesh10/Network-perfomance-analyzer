#!/bin/bash

echo "Compiling Java programs with JPcap library..."

# Check if jpcap.jar exists
if [ ! -f "lib/jpcap.jar" ]; then
    echo "ERROR: jpcap.jar not found in lib directory!"
    echo "Please download jpcap.jar and place it in the lib folder."
    echo "See lib/README.txt for download instructions."
    exit 1
fi

# Create classes directory if it doesn't exist
mkdir -p classes

# Compile all Java files
echo "Compiling PacketRecord.java..."
javac -cp "lib/jpcap.jar" -d classes src/main/java/PacketRecord.java

if [ $? -ne 0 ]; then
    echo "Compilation of PacketRecord.java failed!"
    exit 1
fi

echo "Compiling CsvExporter.java..."
javac -cp "lib/jpcap.jar" -d classes src/main/java/CsvExporter.java

if [ $? -ne 0 ]; then
    echo "Compilation of CsvExporter.java failed!"
    exit 1
fi

echo "Compiling ListInterfaces.java..."
javac -cp "lib/jpcap.jar" -d classes src/main/java/ListInterfaces.java

if [ $? -ne 0 ]; then
    echo "Compilation of ListInterfaces.java failed!"
    exit 1
fi

echo "Compiling PacketCapture.java..."
javac -cp "lib/jpcap.jar" -d classes src/main/java/PacketCapture.java

if [ $? -ne 0 ]; then
    echo "Compilation of PacketCapture.java failed!"
    exit 1
fi

echo "Compiling CsvExporterTest.java..."
javac -cp "lib/jpcap.jar" -d classes src/main/java/CsvExporterTest.java

if [ $? -ne 0 ]; then
    echo "Compilation of CsvExporterTest.java failed!"
    exit 1
fi

echo "Compiling LocalhostSimulator.java..."
javac -cp "lib/jpcap.jar" -d classes src/main/java/LocalhostSimulator.java

if [ $? -ne 0 ]; then
    echo "Compilation of LocalhostSimulator.java failed!"
    exit 1
fi

echo ""
echo "Compilation successful!"
echo "You can now run the programs using:"
echo "  - ./run-list.sh (for ListInterfaces)"
echo "  - ./run-capture.sh (for PacketCapture)"
echo "  - ./run.sh (to choose which program to run)"
