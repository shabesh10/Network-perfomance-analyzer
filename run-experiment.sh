#!/bin/bash

echo "Running Experimental Setup..."

# Create classes directory if it doesn't exist
mkdir -p classes

# Compile required classes
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

javac -cp classes -d classes src/main/java/ExperimentalSetup.java
if [ $? -ne 0 ]; then
    echo "Compilation of ExperimentalSetup.java failed!"
    exit 1
fi

echo "Compilation successful!"
echo ""

# Run the experimental setup
java -cp classes ExperimentalSetup

echo ""
echo "Experimental setup complete!"
echo "Check the logs/ directory for detailed experiment logs."
