import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Random;

/**
 * StandaloneLocalhostSimulator.java
 * 
 * A standalone simulation program that generates realistic localhost packet data
 * without requiring the JPcap library. This version can be compiled and run
 * without external dependencies to test CSV export and Power BI compatibility.
 */
public class StandaloneLocalhostSimulator {
    
    private static final Random random = new Random();
    private static final List<PacketRecord> simulatedPackets = new ArrayList<>();
    
    /**
     * Returns the simulated packets for external access
     */
    public static List<PacketRecord> getSimulatedPackets() {
        return new ArrayList<>(simulatedPackets);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Standalone Localhost Packet Simulation ===");
        System.out.println("Generating realistic localhost traffic patterns...\n");
        
        // Simulate different types of localhost traffic
        simulateWebBrowsing();
        simulateAPICalls();
        simulateDatabaseConnections();
        simulateSystemServices();
        simulateDevelopmentWork();
        
        // Export simulated data to CSV
        exportSimulatedData();
        
        // Display summary
        displaySummary();
        
        System.out.println("\n=== Simulation Complete ===");
        System.out.println("Check output/captured_packets.csv for Power BI import");
    }
    
    /**
     * Simulates web browsing traffic on localhost
     */
    private static void simulateWebBrowsing() {
        System.out.println("Simulating web browsing traffic...");
        
        // Simulate HTTP requests to localhost
        for (int i = 0; i < 15; i++) {
            PacketRecord packet = createPacket(
                "192.168.1.100", // Source (browser)
                "127.0.0.1",     // Destination (localhost server)
                50000 + random.nextInt(1000), // Random source port
                80,              // HTTP port
                "TCP",
                "Outgoing",
                "HTTP"
            );
            packet.setTcpFlags("SYN ACK");
            simulatedPackets.add(packet);
            
            // Response packet
            PacketRecord response = createPacket(
                "127.0.0.1",     // Source (localhost server)
                "192.168.1.100", // Destination (browser)
                80,              // HTTP port
                50000 + random.nextInt(1000), // Random dest port
                "TCP",
                "Incoming",
                "HTTP"
            );
            response.setTcpFlags("ACK PSH");
            simulatedPackets.add(response);
        }
        
        // Simulate HTTPS traffic
        for (int i = 0; i < 10; i++) {
            PacketRecord packet = createPacket(
                "192.168.1.100",
                "127.0.0.1",
                50000 + random.nextInt(1000),
                443,
                "TCP",
                "Outgoing",
                "HTTPS"
            );
            packet.setTcpFlags("SYN");
            simulatedPackets.add(packet);
        }
    }
    
    /**
     * Simulates API calls to localhost services
     */
    private static void simulateAPICalls() {
        System.out.println("Simulating API calls...");
        
        int[] apiPorts = {3000, 4000, 5000, 8000, 8080, 9000};
        
        for (int i = 0; i < 20; i++) {
            int port = apiPorts[random.nextInt(apiPorts.length)];
            PacketRecord packet = createPacket(
                "192.168.1.100",
                "127.0.0.1",
                50000 + random.nextInt(1000),
                port,
                "TCP",
                "Outgoing",
                "Development Server"
            );
            packet.setTcpFlags("SYN ACK");
            simulatedPackets.add(packet);
        }
    }
    
    /**
     * Simulates database connections
     */
    private static void simulateDatabaseConnections() {
        System.out.println("Simulating database connections...");
        
        int[] dbPorts = {3306, 5432, 1433, 1521};
        String[] dbApps = {"MySQL", "PostgreSQL", "MSSQL", "Oracle"};
        
        for (int i = 0; i < 12; i++) {
            int portIndex = random.nextInt(dbPorts.length);
            PacketRecord packet = createPacket(
                "192.168.1.100",
                "127.0.0.1",
                50000 + random.nextInt(1000),
                dbPorts[portIndex],
                "TCP",
                "Outgoing",
                dbApps[portIndex]
            );
            packet.setTcpFlags("SYN ACK");
            simulatedPackets.add(packet);
        }
    }
    
    /**
     * Simulates system services
     */
    private static void simulateSystemServices() {
        System.out.println("Simulating system services...");
        
        // DNS queries
        for (int i = 0; i < 8; i++) {
            PacketRecord packet = createPacket(
                "192.168.1.100",
                "127.0.0.1",
                50000 + random.nextInt(1000),
                53,
                "UDP",
                "Outgoing",
                "DNS"
            );
            simulatedPackets.add(packet);
        }
        
        // SSH connections
        for (int i = 0; i < 5; i++) {
            PacketRecord packet = createPacket(
                "192.168.1.100",
                "127.0.0.1",
                50000 + random.nextInt(1000),
                22,
                "TCP",
                "Outgoing",
                "SSH"
            );
            packet.setTcpFlags("SYN");
            simulatedPackets.add(packet);
        }
        
        // RDP connections
        for (int i = 0; i < 3; i++) {
            PacketRecord packet = createPacket(
                "192.168.1.100",
                "127.0.0.1",
                50000 + random.nextInt(1000),
                3389,
                "TCP",
                "Outgoing",
                "RDP"
            );
            packet.setTcpFlags("SYN ACK");
            simulatedPackets.add(packet);
        }
    }
    
    /**
     * Simulates development work traffic
     */
    private static void simulateDevelopmentWork() {
        System.out.println("Simulating development work...");
        
        // Hot reload connections
        for (int i = 0; i < 25; i++) {
            PacketRecord packet = createPacket(
                "127.0.0.1",
                "127.0.0.1",
                3000 + random.nextInt(10),
                3000 + random.nextInt(10),
                "TCP",
                "Outgoing",
                "Development Server"
            );
            packet.setTcpFlags("ACK PSH");
            simulatedPackets.add(packet);
        }
        
        // WebSocket connections
        for (int i = 0; i < 15; i++) {
            PacketRecord packet = createPacket(
                "127.0.0.1",
                "127.0.0.1",
                8080,
                50000 + random.nextInt(1000),
                "TCP",
                "Incoming",
                "WebSocket"
            );
            packet.setTcpFlags("ACK");
            simulatedPackets.add(packet);
        }
        
        // Localhost to localhost communication
        for (int i = 0; i < 20; i++) {
            PacketRecord packet = createPacket(
                "127.0.0.1",
                "127.0.0.1",
                50000 + random.nextInt(1000),
                50000 + random.nextInt(1000),
                "TCP",
                "Outgoing",
                "Localhost Communication"
            );
            packet.setTcpFlags("SYN ACK");
            simulatedPackets.add(packet);
        }
    }
    
    /**
     * Creates a packet record with realistic data
     */
    private static PacketRecord createPacket(String sourceIP, String destIP, 
                                          int sourcePort, int destPort, 
                                          String protocol, String direction, 
                                          String application) {
        PacketRecord packet = new PacketRecord();
        
        // Set timestamp (spread over last 2 minutes)
        long currentTime = System.currentTimeMillis();
        long randomOffset = random.nextInt(120000); // Random offset within 2 minutes
        packet.setTimestamp(new Date(currentTime - randomOffset));
        
        packet.setSourceIP(sourceIP);
        packet.setDestinationIP(destIP);
        packet.setSourcePort(sourcePort);
        packet.setDestinationPort(destPort);
        packet.setProtocol(protocol);
        packet.setDirection(direction);
        packet.setApplicationGuess(application);
        
        // Set realistic packet length based on protocol
        int baseLength = protocol.equals("TCP") ? 64 : 32;
        packet.setPacketLength(baseLength + random.nextInt(1000));
        
        return packet;
    }
    
    /**
     * Exports simulated data to CSV
     */
    private static void exportSimulatedData() {
        System.out.println("\nExporting simulated data to CSV...");
        
        boolean success = CsvExporter.exportToCsvForPowerBI(simulatedPackets, "captured_packets");
        if (success) {
            System.out.println("✓ Simulated data exported successfully!");
            System.out.println("  File: output/captured_packets.csv");
            System.out.println("  Records: " + simulatedPackets.size());
        } else {
            System.out.println("✗ Failed to export simulated data!");
        }
    }
    
    /**
     * Displays summary of simulated data
     */
    private static void displaySummary() {
        System.out.println("\n=== SIMULATION SUMMARY ===");
        System.out.println("Total packets simulated: " + simulatedPackets.size());
        
        // Count by protocol
        long tcpCount = simulatedPackets.stream().filter(p -> "TCP".equals(p.getProtocol())).count();
        long udpCount = simulatedPackets.stream().filter(p -> "UDP".equals(p.getProtocol())).count();
        
        System.out.println("\nBy Protocol:");
        System.out.println("  TCP: " + tcpCount);
        System.out.println("  UDP: " + udpCount);
        
        // Count by direction
        long outgoingCount = simulatedPackets.stream().filter(p -> "Outgoing".equals(p.getDirection())).count();
        long incomingCount = simulatedPackets.stream().filter(p -> "Incoming".equals(p.getDirection())).count();
        
        System.out.println("\nBy Direction:");
        System.out.println("  Outgoing: " + outgoingCount);
        System.out.println("  Incoming: " + incomingCount);
        
        // Count by application
        System.out.println("\nTop Applications:");
        simulatedPackets.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                PacketRecord::getApplicationGuess, 
                java.util.stream.Collectors.counting()))
            .entrySet().stream()
            .sorted(java.util.Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(10)
            .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));
    }
}
