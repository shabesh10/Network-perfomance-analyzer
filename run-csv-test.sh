#!/bin/bash

echo "Running CSV Exporter Test..."

# Check if jpcap.jar exists
if [ ! -f "lib/jpcap.jar" ]; then
    echo "ERROR: jpcap.jar not found in lib directory!"
    echo "Please download jpcap.jar and place it in the lib folder."
    echo "See lib/README.txt for download instructions."
    exit 1
fi

# Check if classes directory exists
if [ ! -d "classes" ]; then
    echo "ERROR: Classes directory not found!"
    echo "Please run ./compile.sh first to compile the programs."
    exit 1
fi

# Run the CSV test program
java -cp "lib/jpcap.jar:classes" CsvExporterTest
