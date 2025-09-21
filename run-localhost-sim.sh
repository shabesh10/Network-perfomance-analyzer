#!/bin/bash

echo "Running Localhost Packet Simulation..."

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

# Run the localhost simulation
java -cp "lib/jpcap.jar:classes" LocalhostSimulator

echo ""
echo "Simulation complete! Check output/captured_packets.csv"
echo "This file is ready for Power BI import."
