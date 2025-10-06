import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

/**
 * PacketCapture.java
 * 
 * A Java program that captures live packets using JPcap library.
 * The program:
 * 1. Selects a network interface
 * 2. Captures live packets using JPcap
 * 3. Prints packet type (TCP, UDP, ARP), source IP, destination IP, port numbers, and timestamp
 */
public class PacketCapture {
    
    private static net.sourceforge.jpcap.capture.PacketCapture pcap;
    private static int packetCount = 0;
    private static final int CAPTURE_DURATION_MINUTES = 2; // Capture for 2 minutes
    private static final int CAPTURE_DURATION_MS = CAPTURE_DURATION_MINUTES * 60 * 1000; // Convert to milliseconds
    private static List<PacketRecord> packetRecords = new ArrayList<>();
    private static String[] localIPs = null;
    private static Timer captureTimer;
    private static boolean isCapturing = false;
    
    public static void main(String[] args) {
        try {
            System.out.println("=== Live Packet Capture ===");
            System.out.println("Starting packet capture for " + CAPTURE_DURATION_MINUTES + " minutes...\n");
            
            // Get local machine IP addresses for direction detection
            localIPs = getLocalIPAddresses();
            System.out.println("Local IP addresses detected:");
            for (String ip : localIPs) {
                System.out.println("  - " + ip);
            }
            System.out.println();
            
            // Initialize PacketCapture instance
            pcap = new net.sourceforge.jpcap.capture.PacketCapture();
            
            // Retrieve the list of network interfaces
            String[] devices = net.sourceforge.jpcap.capture.PacketCapture.lookupDevices();
            
            if (devices == null || devices.length == 0) {
                System.out.println("No network interfaces found!");
                System.out.println("Make sure WinPcap or Npcap is installed on your system.");
                return;
            }
            
            // Display available interfaces
            System.out.println("Available network interfaces:");
            for (int i = 0; i < devices.length; i++) {
                System.out.println("  " + i + ": " + devices[i]);
            }
            System.out.println();
            
            // Select the first non-loopback interface (or first available)
            String selectedDevice = null;
            for (String device : devices) {
                if (!device.toLowerCase().contains("loopback")) {
                    selectedDevice = device;
                    break;
                }
            }
            
            // If no non-loopback interface found, use the first one
            if (selectedDevice == null) {
                selectedDevice = devices[0];
            }
            
            System.out.println("Selected interface: " + selectedDevice);
            System.out.println("Capturing packets (Press Ctrl+C to stop)...\n");
            
            // Open the selected network interface for capturing
            pcap.open(selectedDevice, true);
            
            // Set a filter to capture only TCP, UDP, and ARP packets
            pcap.setFilter("tcp or udp or arp", true);
            
            // Add a listener to process captured packets
            pcap.addPacketListener(new PacketListener() {
                public void packetArrived(Packet packet) {
                    if (!isCapturing) {
                        return; // Ignore packets if capture has stopped
                    }
                    
                    packetCount++;
                    
                    // Create a new PacketRecord for this packet
                    PacketRecord record = processPacket(packet);
                    
                    // Store the record in our list
                    packetRecords.add(record);
                    
                    // Print the packet details (every 10th packet to reduce spam)
                    if (packetCount % 10 == 0) {
                        System.out.println("--- Packet #" + packetCount + " ---");
                        System.out.println(record.toCompactString());
                    }
                }
            });
            
            // Set up timer to stop capture after specified duration
            captureTimer = new Timer();
            captureTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    stopCapture();
                }
            }, CAPTURE_DURATION_MS);
            
            // Start capturing packets
            isCapturing = true;
            System.out.println("Capture started at: " + new Date());
            System.out.println("Capturing packets... (Press Ctrl+C to stop early)\n");
            
            pcap.capture(-1); // -1 means capture indefinitely
            
        } catch (Exception e) {
            System.err.println("Error capturing packets:");
            System.err.println("Exception: " + e.getClass().getSimpleName());
            System.err.println("Message: " + e.getMessage());
            System.err.println("\nTroubleshooting:");
            System.err.println("1. Make sure WinPcap or Npcap is installed");
            System.err.println("2. Run the program as administrator (Windows)");
            System.err.println("3. Check if JPcap library is properly configured");
            System.err.println("4. Ensure the selected interface is available and not in use");
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }
    
    /**
     * Stops the packet capture and processes the results
     */
    private static void stopCapture() {
        if (!isCapturing) {
            return; // Already stopped
        }
        
        isCapturing = false;
        System.out.println("\n=== CAPTURE COMPLETE ===");
        System.out.println("Capture ended at: " + new Date());
        System.out.println("Total packets captured: " + packetCount);
        
        // Close the capture
        if (pcap != null) {
            pcap.close();
        }
        
        // Cancel the timer
        if (captureTimer != null) {
            captureTimer.cancel();
        }
        
        // Process and export results
        printSummary();
        exportToCsv();
        
        System.out.println("\n=== CAPTURE SESSION ENDED ===");
        System.exit(0);
    }
    
    /**
     * Cleanup resources
     */
    private static void cleanup() {
        if (pcap != null) {
            pcap.close();
        }
        if (captureTimer != null) {
            captureTimer.cancel();
        }
    }
    
    /**
     * Gets all local machine IP addresses for direction detection
     */
    private static String[] getLocalIPAddresses() {
        List<String> ips = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        if (!address.isLoopbackAddress() && address.getAddress().length == 4) { // IPv4 only
                            ips.add(address.getHostAddress());
                        }
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Error getting local IP addresses: " + e.getMessage());
        }
        return ips.toArray(new String[0]);
    }
    
    /**
     * Processes a captured packet and creates a PacketRecord with all details
     */
    private static PacketRecord processPacket(Packet packet) {
        PacketRecord record = new PacketRecord();
        
        // Set timestamp (best effort; some packet APIs may not expose timeval)
        record.setTimestamp(new Date());
        
        // Set packet length (may not be available across all builds)
        record.setPacketLength(0);
        
        // Process based on packet type
        if (packet instanceof TCPPacket) {
            TCPPacket tcpPacket = (TCPPacket) packet;
            record.setProtocol("TCP");
            record.setSourceIP(tcpPacket.getSourceAddress());
            record.setDestinationIP(tcpPacket.getDestinationAddress());
            record.setSourcePort(tcpPacket.getSourcePort());
            record.setDestinationPort(tcpPacket.getDestinationPort());
            
            // Extract TCP flags
            StringBuilder flags = new StringBuilder();
            if (tcpPacket.isSyn()) flags.append("SYN ");
            if (tcpPacket.isAck()) flags.append("ACK ");
            if (tcpPacket.isFin()) flags.append("FIN ");
            if (tcpPacket.isRst()) flags.append("RST ");
            if (tcpPacket.isPsh()) flags.append("PSH ");
            if (tcpPacket.isUrg()) flags.append("URG ");
            record.setTcpFlags(flags.toString().trim());
            
        } else if (packet instanceof UDPPacket) {
            UDPPacket udpPacket = (UDPPacket) packet;
            record.setProtocol("UDP");
            record.setSourceIP(udpPacket.getSourceAddress());
            record.setDestinationIP(udpPacket.getDestinationAddress());
            record.setSourcePort(udpPacket.getSourcePort());
            record.setDestinationPort(udpPacket.getDestinationPort());
            
        } else {
            record.setProtocol("Other");
            // For other packet types, we can't easily extract IP/port info
            record.setSourceIP("Unknown");
            record.setDestinationIP("Unknown");
            record.setSourcePort(-1);
            record.setDestinationPort(-1);
        }
        
        // Determine direction based on local IPs
        record.determineDirection(localIPs);
        
        // Guess application based on port
        record.guessApplication();
        
        return record;
    }
    
    /**
     * Prints a summary of captured packets
     */
    private static void printSummary() {
        System.out.println("\n=== CAPTURE SUMMARY ===");
        System.out.println("Total packets captured: " + packetRecords.size());
        
        // Count by protocol
        int tcpCount = 0, udpCount = 0, arpCount = 0, otherCount = 0;
        int incomingCount = 0, outgoingCount = 0;
        
        for (PacketRecord record : packetRecords) {
            switch (record.getProtocol()) {
                case "TCP": tcpCount++; break;
                case "UDP": udpCount++; break;
                case "ARP": arpCount++; break;
                default: otherCount++; break;
            }
            
            switch (record.getDirection()) {
                case "Incoming": incomingCount++; break;
                case "Outgoing": outgoingCount++; break;
            }
        }
        
        System.out.println("\nBy Protocol:");
        System.out.println("  TCP: " + tcpCount);
        System.out.println("  UDP: " + udpCount);
        System.out.println("  ARP: " + arpCount);
        System.out.println("  Other: " + otherCount);
        
        System.out.println("\nBy Direction:");
        System.out.println("  Incoming: " + incomingCount);
        System.out.println("  Outgoing: " + outgoingCount);
        
        // Show top applications
        System.out.println("\nTop Applications (by frequency):");
        java.util.Map<String, Integer> appCounts = new java.util.HashMap<>();
        for (PacketRecord record : packetRecords) {
            String app = record.getApplicationGuess();
            appCounts.put(app, appCounts.getOrDefault(app, 0) + 1);
        }
        
        appCounts.entrySet().stream()
            .sorted(java.util.Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));
        
        System.out.println("\n=== END SUMMARY ===");
    }
    
    /**
     * Exports captured packets to CSV file with Power BI compatibility
     */
    private static void exportToCsv() {
        if (packetRecords.isEmpty()) {
            System.out.println("No packets to export to CSV.");
            return;
        }
        
        System.out.println("\n=== EXPORTING TO CSV ===");
        
        // Export all packets to the main CSV file (Power BI optimized)
        boolean success = CsvExporter.exportToCsvForPowerBI(packetRecords, "captured_packets");
        if (success) {
            System.out.println("✓ All packets exported to captured_packets.csv successfully.");
            System.out.println("  File location: " + CsvExporter.getOutputDirectory() + "/captured_packets.csv");
            System.out.println("  Format: Power BI optimized with UTF-8 BOM");
        } else {
            System.out.println("✗ Failed to export packets to CSV.");
        }
        
        // Export additional filtered files for analysis
        System.out.println("\nExporting filtered data for analysis...");
        
        // Export by protocol
        CsvExporter.exportToCsvByProtocol(packetRecords, "TCP");
        CsvExporter.exportToCsvByProtocol(packetRecords, "UDP");
        CsvExporter.exportToCsvByProtocol(packetRecords, "ARP");
        
        // Export by direction
        CsvExporter.exportToCsvByDirection(packetRecords, "Incoming");
        CsvExporter.exportToCsvByDirection(packetRecords, "Outgoing");
        
        // List all generated CSV files
        String[] csvFiles = CsvExporter.listCsvFiles();
        if (csvFiles.length > 0) {
            System.out.println("\nGenerated CSV files:");
            for (String file : csvFiles) {
                System.out.println("  - " + CsvExporter.getOutputDirectory() + "/" + file);
            }
        }
        
        System.out.println("\n=== CSV EXPORT COMPLETE ===");
        System.out.println("Main file for Power BI: captured_packets.csv");
    }
}
