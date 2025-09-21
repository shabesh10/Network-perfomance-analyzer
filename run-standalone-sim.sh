#!/bin/bash

echo "Running Standalone Localhost Simulation (No JPcap Required)..."

# Create classes directory if it doesn't exist
mkdir -p classes

# Compile only the required classes (without JPcap dependency)
echo "Compiling required classes..."
javac -d classes src/main/java/PacketRecord.java
if [ $? -ne 0 ]; then
    echo "Compilation of PacketRecord.java failed!"
    exit 1
fi

javac -cp classes -d classes src/main/java/CsvExporter.java
if [ $? -ne 0 ]; then
    echo "Compilation of CsvExporter.java failed!"
    exit 1
fi

javac -cp classes -d classes src/main/java/StandaloneLocalhostSimulator.java
if [ $? -ne 0 ]; then
    echo "Compilation of StandaloneLocalhostSimulator.java failed!"
    exit 1
fi

echo "Compilation successful!"
echo ""

# Run the standalone simulation
java -cp classes StandaloneLocalhostSimulator

echo ""
echo "Simulation complete! Check output/captured_packets.csv"
echo "This file is ready for Power BI import."
echo ""
echo "To validate the CSV file, run: ./validate-csv.sh"
