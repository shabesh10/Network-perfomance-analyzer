#!/bin/bash

echo "Choose which program to run:"
echo ""
echo "1. ListInterfaces - List all network interfaces"
echo "2. PacketCapture - Capture live packets (2 minutes)"
echo "3. CsvExporterTest - Test CSV export functionality"
echo "4. LocalhostSimulator - Simulate localhost traffic"
echo "5. ExperimentalSetup - Full experimental setup with logging"
echo "6. Exit"
echo ""
read -p "Enter your choice (1-6): " choice

case $choice in
    1)
        ./run-list.sh
        ;;
    2)
        ./run-capture.sh
        ;;
    3)
        ./run-csv-test.sh
        ;;
    4)
        ./run-localhost-sim.sh
        ;;
    5)
        ./run-experiment.sh
        ;;
    6)
        echo "Goodbye!"
        exit 0
        ;;
    *)
        echo "Invalid choice. Please run the script again."
        exit 1
        ;;
esac
