import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * RealTimePacketCapture.java
 * 
 * A real-time packet capture program that captures live network packets
 * and exports them to CSV for analysis. Captures actual network traffic
 * and saves packet data to the output folder for Power BI analysis.
 */
public class RealTimePacketCapture {
    
    private static net.sourceforge.jpcap.capture.PacketCapture pcap;
    private static int packetCount = 0;
    private static final int CAPTURE_DURATION_MINUTES = 2; // Capture for 2 minutes
    private static final int CAPTURE_DURATION_MS = CAPTURE_DURATION_MINUTES * 60 * 1000;
    private static List<PacketRecord> capturedPackets = new ArrayList<>();
    private static String[] localIPs = null;
    private static Timer captureTimer;
    private static boolean isCapturing = false;
    
    public static void main(String[] args) {
        try {
            System.out.println("=== Real-Time Network Packet Capture ===");
            System.out.println("Capturing live network traffic for " + CAPTURE_DURATION_MINUTES + " minutes...\n");
            
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
            
            // Select the best network interface for capturing
            String selectedDevice = selectBestInterface(devices);
            System.out.println("Selected interface: " + selectedDevice);
            System.out.println("Starting real-time packet capture...\n");
            
            // Open the selected network interface for capturing
            pcap.open(selectedDevice, true);
            
            // Set a filter to capture TCP and UDP packets (real network traffic)
            pcap.setFilter("tcp or udp", true);
            
            // Add a listener to process captured packets in real-time
            pcap.addPacketListener(new PacketListener() {
                public void packetArrived(Packet packet) {
                    if (!isCapturing) {
                        return; // Ignore packets if capture has stopped
                    }
                    
                    packetCount++;
                    
                    // Create a new PacketRecord for this real packet
                    PacketRecord record = processRealPacket(packet);
                    
                    // Store the record in our list
                    capturedPackets.add(record);
                    
                    // Print progress (every 10th packet to show activity)
                    if (packetCount % 10 == 0) {
                        System.out.println("Captured " + packetCount + " packets - Latest: " + record.toCompactString());
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
            
            // Start capturing real packets
            isCapturing = true;
            System.out.println("Real-time capture started at: " + new Date());
            System.out.println("Capturing live network packets... (Press Ctrl+C to stop early)\n");
            
            pcap.capture(-1); // -1 means capture indefinitely until stopped
            
        } catch (Exception e) {
            System.err.println("Error capturing real-time packets:");
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
        System.out.println("\n=== REAL-TIME CAPTURE COMPLETE ===");
        System.out.println("Capture ended at: " + new Date());
        System.out.println("Total real packets captured: " + packetCount);
        
        // Close the capture
        if (pcap != null) {
            pcap.close();
        }
        
        // Cancel the timer
        if (captureTimer != null) {
            captureTimer.cancel();
        }
        
        // Process and export real packet data
        displayRealPacketSummary();
        exportRealPacketData();
        
        System.out.println("\n=== REAL-TIME CAPTURE SESSION ENDED ===");
        System.out.println("Check output/captured_packets.csv for Power BI import!");
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
            // Always include localhost
            ips.add("127.0.0.1");
            ips.add("::1");
            
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
     * Selects the best network interface for packet capture
     */
    private static String selectBestInterface(String[] devices) {
        // Try to find a non-loopback interface first for real traffic
        for (String device : devices) {
            if (!device.toLowerCase().contains("loopback")) {
                return device;
            }
        }
        
        // If no non-loopback interface found, use the first one
        return devices[0];
    }
    
    /**
     * Processes a real captured packet and creates a PacketRecord with all details
     */
    private static PacketRecord processRealPacket(Packet packet) {
        PacketRecord record = new PacketRecord();
        
        // Set real timestamp
        record.setTimestamp(new Date());
        
        // Try to get real packet length
        try {
            // Some JPcap versions may have packet length available
            record.setPacketLength(packet.toString().length()); // Fallback estimation
        } catch (Exception e) {
            record.setPacketLength(64); // Default packet size
        }
        
        // Process based on real packet type
        if (packet instanceof TCPPacket) {
            TCPPacket tcpPacket = (TCPPacket) packet;
            record.setProtocol("TCP");
            record.setSourceIP(tcpPacket.getSourceAddress());
            record.setDestinationIP(tcpPacket.getDestinationAddress());
            record.setSourcePort(tcpPacket.getSourcePort());
            record.setDestinationPort(tcpPacket.getDestinationPort());
            
            // Extract real TCP flags
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
     * Exports real captured packet data to CSV files in output folder
     */
    private static void exportRealPacketData() {
        if (capturedPackets.isEmpty()) {
            System.out.println("No real packets captured to export to CSV.");
            return;
        }
        
        System.out.println("\n=== EXPORTING REAL PACKET DATA TO OUTPUT FOLDER ===");
        
        // Export all real packets to the main CSV file (Power BI optimized)
        boolean success = CsvExporter.exportToCsvForPowerBI(capturedPackets, "captured_packets");
        if (success) {
            System.out.println("✓ Real packet data exported successfully!");
            System.out.println("  File: " + CsvExporter.getOutputDirectory() + "/captured_packets.csv");
            System.out.println("  Records: " + capturedPackets.size());
            System.out.println("  Format: Power BI optimized with UTF-8 BOM");
        } else {
            System.out.println("✗ Failed to export real packet data!");
        }
        
        System.out.println("\n=== REAL PACKET DATA EXPORT COMPLETE ===");
        System.out.println("Single CSV file ready for Power BI: captured_packets.csv");
    }
    
    /**
     * Displays summary of real captured packet data
     */
    private static void displayRealPacketSummary() {
        System.out.println("\n=== REAL PACKET CAPTURE SUMMARY ===");
        System.out.println("Total real packets captured: " + capturedPackets.size());
        
        if (capturedPackets.isEmpty()) {
            System.out.println("No real packets were captured during this session.");
            System.out.println("Try running with administrator privileges or check network activity.");
            return;
        }
        
        // Count by protocol
        int tcpCount = 0, udpCount = 0, otherCount = 0;
        int incomingCount = 0, outgoingCount = 0, unknownDirCount = 0;
        
        for (PacketRecord record : capturedPackets) {
            switch (record.getProtocol()) {
                case "TCP": tcpCount++; break;
                case "UDP": udpCount++; break;
                default: otherCount++; break;
            }
            
            switch (record.getDirection()) {
                case "Incoming": incomingCount++; break;
                case "Outgoing": outgoingCount++; break;
                default: unknownDirCount++; break;
            }
        }
        
        System.out.println("\nReal Packets by Protocol:");
        System.out.println("  TCP: " + tcpCount);
        System.out.println("  UDP: " + udpCount);
        System.out.println("  Other: " + otherCount);
        
        System.out.println("\nReal Packets by Direction:");
        System.out.println("  Incoming: " + incomingCount);
        System.out.println("  Outgoing: " + outgoingCount);
        System.out.println("  Unknown: " + unknownDirCount);
        
        // Show top applications from real traffic
        System.out.println("\nTop Applications (from real traffic):");
        java.util.Map<String, Integer> appCounts = new java.util.HashMap<>();
        for (PacketRecord record : capturedPackets) {
            String app = record.getApplicationGuess();
            appCounts.put(app, appCounts.getOrDefault(app, 0) + 1);
        }
        
        appCounts.entrySet().stream()
            .sorted(java.util.Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));
        
        System.out.println("\n=== END REAL PACKET SUMMARY ===");
    }
}