import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * CsvExporterTest.java
 * 
 * A simple test program to demonstrate the CSV export functionality.
 * Creates sample packet records and exports them to CSV files.
 */
public class CsvExporterTest {
    
    public static void main(String[] args) {
        System.out.println("=== CSV Exporter Test ===");
        
        // Create sample packet records
        List<PacketRecord> samplePackets = createSamplePackets();
        
        System.out.println("Created " + samplePackets.size() + " sample packet records.");
        
        // Test basic CSV export
        System.out.println("\nTesting basic CSV export...");
        boolean success = CsvExporter.exportToCsv(samplePackets, "test_packets");
        if (success) {
            System.out.println("✓ Basic CSV export successful!");
        } else {
            System.out.println("✗ Basic CSV export failed!");
        }
        
        // Test export by protocol
        System.out.println("\nTesting export by protocol...");
        CsvExporter.exportToCsvByProtocol(samplePackets, "TCP");
        CsvExporter.exportToCsvByProtocol(samplePackets, "UDP");
        CsvExporter.exportToCsvByProtocol(samplePackets, "ARP");
        
        // Test export by direction
        System.out.println("Testing export by direction...");
        CsvExporter.exportToCsvByDirection(samplePackets, "Incoming");
        CsvExporter.exportToCsvByDirection(samplePackets, "Outgoing");
        
        // List generated files
        System.out.println("\nGenerated CSV files:");
        String[] csvFiles = CsvExporter.listCsvFiles();
        for (String file : csvFiles) {
            System.out.println("  - " + CsvExporter.getOutputDirectory() + "/" + file);
        }
        
        System.out.println("\n=== Test Complete ===");
    }
    
    /**
     * Creates sample packet records for testing
     */
    private static List<PacketRecord> createSamplePackets() {
        List<PacketRecord> packets = new ArrayList<>();
        
        // Sample TCP packet
        PacketRecord tcpPacket = new PacketRecord();
        tcpPacket.setTimestamp(new Date());
        tcpPacket.setSourceIP("192.168.1.100");
        tcpPacket.setDestinationIP("93.184.216.34");
        tcpPacket.setSourcePort(52341);
        tcpPacket.setDestinationPort(443);
        tcpPacket.setProtocol("TCP");
        tcpPacket.setPacketLength(64);
        tcpPacket.setDirection("Outgoing");
        tcpPacket.setTcpFlags("SYN");
        tcpPacket.setApplicationGuess("HTTPS");
        packets.add(tcpPacket);
        
        // Sample UDP packet
        PacketRecord udpPacket = new PacketRecord();
        udpPacket.setTimestamp(new Date());
        udpPacket.setSourceIP("192.168.1.1");
        udpPacket.setDestinationIP("192.168.1.100");
        udpPacket.setSourcePort(53);
        udpPacket.setDestinationPort(12345);
        udpPacket.setProtocol("UDP");
        udpPacket.setPacketLength(84);
        udpPacket.setDirection("Incoming");
        udpPacket.setTcpFlags("");
        udpPacket.setApplicationGuess("DNS");
        packets.add(udpPacket);
        
        // Sample ARP packet
        PacketRecord arpPacket = new PacketRecord();
        arpPacket.setTimestamp(new Date());
        arpPacket.setSourceIP("192.168.1.50");
        arpPacket.setDestinationIP("192.168.1.100");
        arpPacket.setSourcePort(-1);
        arpPacket.setDestinationPort(-1);
        arpPacket.setProtocol("ARP");
        arpPacket.setPacketLength(42);
        arpPacket.setDirection("Incoming");
        arpPacket.setTcpFlags("");
        arpPacket.setApplicationGuess("Unknown");
        packets.add(arpPacket);
        
        // Sample HTTP packet
        PacketRecord httpPacket = new PacketRecord();
        httpPacket.setTimestamp(new Date());
        httpPacket.setSourceIP("192.168.1.100");
        httpPacket.setDestinationIP("216.58.194.174");
        httpPacket.setSourcePort(45678);
        httpPacket.setDestinationPort(80);
        httpPacket.setProtocol("TCP");
        httpPacket.setPacketLength(1024);
        httpPacket.setDirection("Outgoing");
        httpPacket.setTcpFlags("ACK PSH");
        httpPacket.setApplicationGuess("HTTP");
        packets.add(httpPacket);
        
        return packets;
    }
}
