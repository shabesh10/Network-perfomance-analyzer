#!/bin/bash

echo "Running PacketCapture program..."

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

echo "WARNING: This program requires administrator privileges to capture packets."
echo "Make sure to run this script with sudo or as root."
echo ""
echo "Press Enter to continue or Ctrl+C to cancel..."
read

# Run the PacketCapture program
java -cp "lib/jpcap.jar:classes" PacketCapture
