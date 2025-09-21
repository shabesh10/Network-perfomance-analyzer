# Network Interface Lister & Packet Capture

A Java application that lists network interfaces and captures live packets using the JPcap library.

## Features

1. **ListInterfaces.java** - Lists all available network interfaces with index, name, and description
2. **PacketCapture.java** - Captures live packets and displays packet type (TCP, UDP, ARP), source IP, destination IP, port numbers, and timestamp

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
# Compile both programs
compile.bat

# Run the main menu
run.bat

# Or run individual programs
run-list.bat      # List network interfaces
run-capture.bat   # Capture live packets (requires admin)
```

### Linux/Mac:
```bash
# Compile both programs
./compile.sh

# Run the main menu
./run.sh

# Or run individual programs
./run-list.sh     # List network interfaces
./run-capture.sh  # Capture live packets (requires sudo)
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

The program will automatically:
1. Detect all available network interfaces
2. Display each interface with:
   - Index number
   - Name
   - Description
   - Additional details (addresses, MTU, etc.)

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
