# Network Interface Lister & Real-Time Packet Capture

A Java application that lists network interfaces and captures live packets using the JPcap library, with CSV export functionality for Power BI analysis.

## Features

1. **ListInterfaces.java** - Lists all available network interfaces with index, name, and description
2. **PacketCapture.java** - Captures live packets and displays packet type (TCP, UDP, ARP), source IP, destination IP, port numbers, and timestamp
3. **RealTimePacketCapture.java** - **NEW**: Real-time packet capture that captures actual network traffic and saves to CSV files in output folder
4. **CsvExporter.java** - Exports captured packet data to Power BI-compatible CSV files
5. **PacketRecord.java** - Data structure for storing detailed packet information

## Prerequisites

1. **Java Development Kit (JDK) 8 or higher**
2. **WinPcap or Npcap** - Download and install one of these:
   - [WinPcap](https://www.winpcap.org/) (legacy, for older systems)
   - [Npcap](https://nmap.org/npcap/) (recommended, modern replacement)
3. **JPcap JAR file** - Download from [SourceForge](https://sourceforge.net/projects/jpcap/files/)

## Installation

1. Clone or download this project
2. Install WinPcap or Npcap on your system
3. Download `jpcap.jar` and place it in the `lib` directory
4. Compile and run using the provided scripts

## Quick Start

### Windows:
```cmd
# Compile all programs
compile.bat

# Run the main menu
run.bat

# Or run individual programs
run-list.bat           # List network interfaces
run-capture.bat        # Capture live packets (requires admin)
run-localhost-sim.bat  # Real-time packet capture with CSV export to output folder
```

### Linux/Mac:
```bash
# Compile all programs
./compile.sh

# Run the main menu
./run.sh

# Or run individual programs
./run-list.sh          # List network interfaces
./run-capture.sh       # Capture live packets (requires sudo)
./run-localhost-sim.sh # Real-time packet capture with CSV export to output folder
```

## Manual Compilation

If you prefer to compile manually:

1. Download `jpcap.jar` and place it in the `lib` directory
2. Compile both programs:
```bash
javac -cp "lib/jpcap.jar" -d classes src/main/java/ListInterfaces.java
javac -cp "lib/jpcap.jar" -d classes src/main/java/PacketCapture.java
```

3. Run the programs:
```bash
# List interfaces
java -cp "lib/jpcap.jar:classes" ListInterfaces

# Capture packets
java -cp "lib/jpcap.jar:classes" PacketCapture
```

## Usage

### Network Interface Listing
The ListInterfaces program will automatically:
1. Detect all available network interfaces
2. Display each interface with:
   - Index number
   - Name
   - Description
   - Additional details (addresses, MTU, etc.)

### Real-Time Packet Capture (RealTimePacketCapture)
The RealTimePacketCapture program captures **real network packets** from your system:

1. **Captures live network traffic** for 2 minutes (configurable)
2. **Filters localhost-related packets** automatically
3. **Exports to Power BI-compatible CSV** files in the `output/` directory
4. **Provides detailed analysis** including:
   - Protocol breakdown (TCP/UDP)
   - Traffic direction (Incoming/Outgoing)
   - Application identification by port
   - Timestamp and packet details

#### CSV Output File (saved to output folder):
- `captured_packets.csv` - Single file optimized for Power BI import containing all packet data

#### Power BI Integration:
The CSV file is formatted with UTF-8 BOM encoding and proper headers for seamless Power BI import. Simply import `captured_packets.csv` from the output folder into Power BI for network traffic analysis and visualization.

## Troubleshooting

- **"No network interfaces found"**: Ensure WinPcap/Npcap is installed
- **Permission errors**: Run as administrator on Windows
- **ClassNotFoundException**: Check that JPcap JAR is in the classpath

## Output Example

```
=== Network Interface Lister ===
Listing all available network interfaces...

Found 3 network interface(s):

Interface #0:
  Name: \Device\NPF_{12345678-1234-1234-1234-123456789ABC}
  Description: Intel(R) Ethernet Connection (7) I219-V
  Addresses:
    - 192.168.1.100
  Loopback: false
  Datalink: Ethernet
  MTU: 1500

Interface #1:
  Name: \Device\NPF_Loopback
  Description: Microsoft Loopback Adapter
  Loopback: true
  Datalink: Loopback
  MTU: 65536

=== End of Interface List ===
```
