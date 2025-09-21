#!/bin/bash

echo "Running CSV Validation for Power BI Compatibility..."

# Check if Python is available
if ! command -v python3 &> /dev/null; then
    if ! command -v python &> /dev/null; then
        echo "ERROR: Python not found!"
        echo "Please install Python to run the validation script."
        echo "Alternatively, you can manually check the CSV file."
        exit 1
    else
        PYTHON_CMD="python"
    fi
else
    PYTHON_CMD="python3"
fi

# Check if CSV file exists
if [ ! -f "output/captured_packets.csv" ]; then
    echo "ERROR: CSV file not found!"
    echo "Please run the simulation or packet capture first."
    echo "Run: ./run-localhost-sim.sh or ./run-capture.sh"
    exit 1
fi

# Run validation
$PYTHON_CMD validate-csv.py

echo ""
echo "Validation complete!"
