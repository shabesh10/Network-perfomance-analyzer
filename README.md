# Network Performance analyzer with BI analytics

A comprehensive Java application that lists network interfaces and captures live packets using the JPcap library, with advanced CSV export functionality for Power BI analysis and network traffic monitoring.

## Quick Start - How to Execute the Project

### Prerequisites
1. **Java Development Kit (JDK) 8 or higher**
2. **WinPcap or Npcap** - Download and install:
   - [Npcap](https://nmap.org/npcap/) (recommended, modern replacement)
   - [WinPcap](https://www.winpcap.org/) (legacy, for older systems)
3. **JPcap JAR file** - Download from [SourceForge](https://sourceforge.net/projects/jpcap/files/) and place in `lib/` directory

### Installation & Setup
1. Clone or download this project
2. Install Npcap/WinPcap on your system
3. Download `jpcap.jar` and place it in the `lib` directory
4. Use the provided scripts to compile and run

### Execution Steps

#### Windows:
```cmd
# Step 1: Compile all programs
compile.bat

# Step 2: Choose your execution method

# Option A - Interactive Menu (Recommended)
run.bat

# Option B - Direct Program Execution
run-list.bat                    # List network interfaces
run-capture.bat                 # Basic packet capture (requires admin)
run-realtime-capture.bat        # Real-time packet capture with CSV export
run-modern-capture.bat          # Modern packet capture with enhanced features
run-standalone-sim.bat          # Standalone localhost traffic simulation
run-csv-test.bat               # Test CSV export functionality
run-experiment.bat             # Experimental packet capture features

# Step 3: Validate CSV output (optional)
validate-csv.bat               # Validate generated CSV files
```

#### Linux/Mac:
```bash
# Step 1: Compile all programs
./compile.sh

# Step 2: Choose your execution method

# Option A - Interactive Menu (Recommended)
./run.sh

# Option B - Direct Program Execution
./run-list.sh                   # List network interfaces
./run-capture.sh                # Basic packet capture (requires sudo)
./run-localhost-sim.sh          # Real-time packet capture with CSV export
./run-standalone-sim.sh         # Standalone localhost traffic simulation
./run-csv-test.sh              # Test CSV export functionality
./run-experiment.sh            # Experimental packet capture features

# Step 3: Validate CSV output (optional)
./validate-csv.sh              # Validate generated CSV files
```

## Project Features & Components

### Core Applications
1. **ListInterfaces.java** - Lists all available network interfaces with detailed information
2. **PacketCapture.java** - Basic packet capture with protocol analysis
3. **RealTimePacketCapture.java** - Real-time packet capture with CSV export to output folder
4. **ModernPacketCapture.java** - **NEW**: Enhanced packet capture with modern filtering and analysis
5. **StandaloneLocalhostSimulator.java** - **NEW**: Standalone traffic simulation for testing
6. **ExperimentalSetup.java** - **NEW**: Experimental packet capture with advanced features
7. **CsvExporter.java** - Power BI-compatible CSV export functionality
8. **CsvExporterTest.java** - **NEW**: CSV export validation and testing
9. **PacketRecord.java** - Data structure for storing detailed packet information

### Output Directories
- `output/` - CSV files for Power BI analysis
- `logs/` - Application logs and debug information
- `classes/` - Compiled Java bytecode

## Manual Compilation (Advanced Users)

If you prefer to compile manually:

1. Download `jpcap.jar` and place it in the `lib` directory
2. Compile all programs:
```bash
javac -cp "lib/jpcap.jar" -d classes src/main/java/*.java
```

3. Run individual programs:
```bash
# List interfaces
java -cp "lib/jpcap.jar:classes" ListInterfaces

# Basic packet capture
java -cp "lib/jpcap.jar:classes" PacketCapture

# Real-time capture with CSV export
java -cp "lib/jpcap.jar:classes" RealTimePacketCapture

# Modern enhanced capture
java -cp "lib/jpcap.jar:classes" ModernPacketCapture

# Standalone traffic simulation
java -cp "lib/jpcap.jar:classes" StandaloneLocalhostSimulator
```

## Detailed Usage Guide

### 1. Network Interface Listing (ListInterfaces)
Automatically detects and displays:
- All available network interfaces
- Index numbers, names, and descriptions
- IP addresses, MTU, and datalink information
- Loopback interface identification

### 2. Real-Time Packet Capture (RealTimePacketCapture)
Captures **live network traffic** with:
- Configurable capture duration (default: 2 minutes)
- Automatic localhost packet filtering
- Real-time protocol analysis (TCP/UDP/ARP)
- CSV export to `output/` directory
- Traffic direction analysis (Incoming/Outgoing)
- Application identification by port numbers

### 3. Modern Packet Capture (ModernPacketCapture) - NEW
Enhanced capture capabilities:
- Advanced packet filtering and analysis
- Improved performance and stability
- Enhanced protocol detection
- Better error handling and logging

### 4. Standalone Traffic Simulation (StandaloneLocalhostSimulator) - NEW
For testing and demonstration:
- Generates controlled localhost traffic
- Simulates various protocols and patterns
- Perfect for testing capture functionality
- No external network dependencies

### 5. Experimental Features (ExperimentalSetup) - NEW
Cutting-edge packet analysis:
- Experimental filtering techniques
- Advanced packet inspection
- Performance optimization testing
- Research and development features

### CSV Output Files (saved to output/ folder)
- `captured_packets.csv` - Main packet data for Power BI
- `packet_summary.csv` - Statistical summary
- `protocol_breakdown.csv` - Protocol analysis
- `traffic_patterns.csv` - Traffic pattern analysis

### Power BI Integration
All CSV files are formatted with:
- UTF-8 BOM encoding for seamless import
- Standardized headers and data types
- Optimized structure for visualization
- Compatible with Power BI data models

## Troubleshooting

### Common Issues
- **"No network interfaces found"**: Ensure Npcap/WinPcap is properly installed and restart your system
- **Permission errors**: Run as administrator on Windows or use sudo on Linux/Mac
- **ClassNotFoundException**: Verify JPcap JAR is in the `lib/` directory and classpath is correct
- **Empty CSV files**: Check if capture duration is sufficient and network traffic exists
- **Compilation errors**: Ensure JDK 8+ is installed and JAVA_HOME is set correctly

### Validation Tools
- Use `validate-csv.bat` (Windows) or `validate-csv.sh` (Linux/Mac) to check CSV file integrity
- Check `logs/` directory for detailed error messages and debug information
- Run `run-csv-test.bat` to test CSV export functionality independently

### Performance Tips
- Use ModernPacketCapture for better performance on high-traffic networks
- Adjust capture duration based on your analysis needs
- Monitor system resources during packet capture operations


