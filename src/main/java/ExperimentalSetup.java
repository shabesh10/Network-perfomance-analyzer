import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * ExperimentalSetup.java
 * 
 * A comprehensive experimental setup class that:
 * - Logs system details (OS, RAM, CPU)
 * - Tracks experiment timing (start/end times)
 * - Provides detailed summary reporting
 * - Generates experimental logs for analysis
 */
public class ExperimentalSetup {
    
    // System information
    private static String osName;
    private static String osVersion;
    private static String osArchitecture;
    private static long totalMemory;
    private static long freeMemory;
    private static int availableProcessors;
    private static String javaVersion;
    private static String javaVendor;
    
    // Experiment tracking
    private static Date experimentStartTime;
    private static Date experimentEndTime;
    private static long experimentDurationMs;
    private static List<PacketRecord> capturedPackets = new ArrayList<>();
    private static String experimentId;
    
    // Summary statistics
    private static int totalPacketsCaptured;
    private static Set<String> uniqueSourceIPs = new HashSet<>();
    private static Set<String> uniqueDestinationIPs = new HashSet<>();
    private static Map<String, Integer> protocolDistribution = new HashMap<>();
    private static Map<String, Integer> applicationDistribution = new HashMap<>();
    private static Map<String, Integer> directionDistribution = new HashMap<>();
    
    // Logging
    private static final String LOG_DIR = "logs";
    private static final SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final SimpleDateFormat FILE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");
    
    public static void main(String[] args) {
        // Initialize experiment
        initializeExperiment();
        
        // Log system details
        logSystemDetails();
        
        // Start experiment
        startExperiment();
        
        // Simulate packet capture (replace with actual capture in real scenario)
        simulatePacketCapture();
        
        // End experiment
        endExperiment();
        
        // Generate comprehensive summary
        generateSummary();
        
        // Save experiment log
        saveExperimentLog();
        
        System.out.println("\n=== EXPERIMENT COMPLETE ===");
        System.out.println("Experiment ID: " + experimentId);
        System.out.println("Log file: " + LOG_DIR + "/experiment_" + experimentId + ".log");
        System.out.println("CSV file: output/captured_packets.csv");
    }
    
    /**
     * Initializes the experimental setup
     */
    private static void initializeExperiment() {
        System.out.println("=== EXPERIMENTAL SETUP INITIALIZATION ===");
        
        // Generate unique experiment ID
        experimentId = "EXP_" + FILE_DATE_FORMAT.format(new Date());
        
        // Create logs directory
        try {
            java.io.File logDir = new java.io.File(LOG_DIR);
            if (!logDir.exists()) {
                logDir.mkdirs();
                System.out.println("Created logs directory: " + LOG_DIR);
            }
        } catch (Exception e) {
            System.err.println("Error creating logs directory: " + e.getMessage());
        }
        
        System.out.println("Experiment ID: " + experimentId);
        System.out.println("Initialization complete.\n");
    }
    
    /**
     * Logs detailed system information
     */
    private static void logSystemDetails() {
        System.out.println("=== SYSTEM DETAILS ===");
        
        // Operating System Information
        osName = System.getProperty("os.name");
        osVersion = System.getProperty("os.version");
        osArchitecture = System.getProperty("os.arch");
        
        System.out.println("Operating System:");
        System.out.println("  Name: " + osName);
        System.out.println("  Version: " + osVersion);
        System.out.println("  Architecture: " + osArchitecture);
        
        // Memory Information
        Runtime runtime = Runtime.getRuntime();
        totalMemory = runtime.totalMemory();
        freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        System.out.println("\nMemory Information:");
        System.out.println("  Total Memory: " + formatBytes(totalMemory));
        System.out.println("  Free Memory: " + formatBytes(freeMemory));
        System.out.println("  Used Memory: " + formatBytes(usedMemory));
        System.out.println("  Max Memory: " + formatBytes(maxMemory));
        System.out.println("  Memory Usage: " + String.format("%.2f%%", (double) usedMemory / totalMemory * 100));
        
        // CPU Information
        availableProcessors = runtime.availableProcessors();
        System.out.println("\nCPU Information:");
        System.out.println("  Available Processors: " + availableProcessors);
        
        // Java Information
        javaVersion = System.getProperty("java.version");
        javaVendor = System.getProperty("java.vendor");
        String javaHome = System.getProperty("java.home");
        
        System.out.println("\nJava Information:");
        System.out.println("  Version: " + javaVersion);
        System.out.println("  Vendor: " + javaVendor);
        System.out.println("  Home: " + javaHome);
        
        // Network Information
        try {
            java.net.InetAddress localHost = java.net.InetAddress.getLocalHost();
            System.out.println("\nNetwork Information:");
            System.out.println("  Host Name: " + localHost.getHostName());
            System.out.println("  Host Address: " + localHost.getHostAddress());
        } catch (Exception e) {
            System.out.println("\nNetwork Information: Unable to retrieve");
        }
        
        System.out.println();
    }
    
    /**
     * Starts the experiment and logs start time
     */
    private static void startExperiment() {
        experimentStartTime = new Date();
        System.out.println("=== EXPERIMENT STARTED ===");
        System.out.println("Start Time: " + LOG_DATE_FORMAT.format(experimentStartTime));
        System.out.println("Experiment ID: " + experimentId);
        System.out.println();
    }
    
    /**
     * Simulates packet capture (replace with actual capture in real scenario)
     */
    private static void simulatePacketCapture() {
        System.out.println("=== PACKET CAPTURE SIMULATION ===");
        System.out.println("Simulating packet capture for 2 minutes...");
        
        // Generate simulated packets directly
        generateSimulatedPackets();
        
        // Update statistics
        updateStatistics();
        
        System.out.println("Packet capture simulation complete.");
        System.out.println("Total packets captured: " + totalPacketsCaptured);
        System.out.println();
    }
    
    /**
     * Generates simulated packet data
     */
    private static void generateSimulatedPackets() {
        Random random = new Random();
        
        // Simulate web browsing traffic
        for (int i = 0; i < 15; i++) {
            PacketRecord packet = createSimulatedPacket(
                "192.168.1.100", "127.0.0.1", 
                50000 + random.nextInt(1000), 80, 
                "TCP", "Outgoing", "HTTP", random
            );
            packet.setTcpFlags("SYN ACK");
            capturedPackets.add(packet);
        }
        
        // Simulate API calls
        int[] apiPorts = {3000, 4000, 5000, 8000, 8080, 9000};
        for (int i = 0; i < 20; i++) {
            int port = apiPorts[random.nextInt(apiPorts.length)];
            PacketRecord packet = createSimulatedPacket(
                "192.168.1.100", "127.0.0.1",
                50000 + random.nextInt(1000), port,
                "TCP", "Outgoing", "Development Server", random
            );
            packet.setTcpFlags("SYN ACK");
            capturedPackets.add(packet);
        }
        
        // Simulate database connections
        int[] dbPorts = {3306, 5432, 1433, 1521};
        String[] dbApps = {"MySQL", "PostgreSQL", "MSSQL", "Oracle"};
        for (int i = 0; i < 12; i++) {
            int portIndex = random.nextInt(dbPorts.length);
            PacketRecord packet = createSimulatedPacket(
                "192.168.1.100", "127.0.0.1",
                50000 + random.nextInt(1000), dbPorts[portIndex],
                "TCP", "Outgoing", dbApps[portIndex], random
            );
            packet.setTcpFlags("SYN ACK");
            capturedPackets.add(packet);
        }
        
        // Simulate system services
        for (int i = 0; i < 8; i++) {
            PacketRecord packet = createSimulatedPacket(
                "192.168.1.100", "127.0.0.1",
                50000 + random.nextInt(1000), 53,
                "UDP", "Outgoing", "DNS", random
            );
            capturedPackets.add(packet);
        }
        
        // Simulate localhost-to-localhost communication
        for (int i = 0; i < 25; i++) {
            PacketRecord packet = createSimulatedPacket(
                "127.0.0.1", "127.0.0.1",
                3000 + random.nextInt(10), 3000 + random.nextInt(10),
                "TCP", "Outgoing", "Development Server", random
            );
            packet.setTcpFlags("ACK PSH");
            capturedPackets.add(packet);
        }
        
        // Export to CSV
        CsvExporter.exportToCsvForPowerBI(capturedPackets, "captured_packets");
    }
    
    /**
     * Creates a simulated packet record
     */
    private static PacketRecord createSimulatedPacket(String sourceIP, String destIP, 
                                                    int sourcePort, int destPort, 
                                                    String protocol, String direction, 
                                                    String application, Random random) {
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
     * Ends the experiment and logs end time
     */
    private static void endExperiment() {
        experimentEndTime = new Date();
        experimentDurationMs = experimentEndTime.getTime() - experimentStartTime.getTime();
        
        System.out.println("=== EXPERIMENT ENDED ===");
        System.out.println("End Time: " + LOG_DATE_FORMAT.format(experimentEndTime));
        System.out.println("Duration: " + formatDuration(experimentDurationMs));
        System.out.println();
    }
    
    /**
     * Generates comprehensive summary statistics
     */
    private static void generateSummary() {
        System.out.println("=== EXPERIMENT SUMMARY ===");
        
        // Basic statistics
        System.out.println("Basic Statistics:");
        System.out.println("  Total Packets Captured: " + totalPacketsCaptured);
        System.out.println("  Unique Source IPs: " + uniqueSourceIPs.size());
        System.out.println("  Unique Destination IPs: " + uniqueDestinationIPs.size());
        System.out.println("  Total Unique IPs: " + (uniqueSourceIPs.size() + uniqueDestinationIPs.size()));
        
        // Protocol distribution
        System.out.println("\nProtocol Distribution:");
        protocolDistribution.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> {
                double percentage = (double) entry.getValue() / totalPacketsCaptured * 100;
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + 
                                 " (" + String.format("%.1f", percentage) + "%)");
            });
        
        // Application distribution
        System.out.println("\nApplication Distribution:");
        applicationDistribution.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .forEach(entry -> {
                double percentage = (double) entry.getValue() / totalPacketsCaptured * 100;
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + 
                                 " (" + String.format("%.1f", percentage) + "%)");
            });
        
        // Direction distribution
        System.out.println("\nDirection Distribution:");
        directionDistribution.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> {
                double percentage = (double) entry.getValue() / totalPacketsCaptured * 100;
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + 
                                 " (" + String.format("%.1f", percentage) + "%)");
            });
        
        // Top IP addresses
        System.out.println("\nTop Source IPs:");
        Map<String, Integer> sourceIPCounts = new HashMap<>();
        for (PacketRecord packet : capturedPackets) {
            sourceIPCounts.put(packet.getSourceIP(), 
                sourceIPCounts.getOrDefault(packet.getSourceIP(), 0) + 1);
        }
        sourceIPCounts.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));
        
        System.out.println("\nTop Destination IPs:");
        Map<String, Integer> destIPCounts = new HashMap<>();
        for (PacketRecord packet : capturedPackets) {
            destIPCounts.put(packet.getDestinationIP(), 
                destIPCounts.getOrDefault(packet.getDestinationIP(), 0) + 1);
        }
        destIPCounts.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));
        
        // Performance metrics
        System.out.println("\nPerformance Metrics:");
        double packetsPerSecond = (double) totalPacketsCaptured / (experimentDurationMs / 1000.0);
        System.out.println("  Packets per Second: " + String.format("%.2f", packetsPerSecond));
        System.out.println("  Average Packet Size: " + String.format("%.2f", 
            capturedPackets.stream().mapToInt(PacketRecord::getPacketLength).average().orElse(0)) + " bytes");
        
        System.out.println();
    }
    
    /**
     * Updates statistics from captured packets
     */
    private static void updateStatistics() {
        totalPacketsCaptured = capturedPackets.size();
        
        for (PacketRecord packet : capturedPackets) {
            // Collect unique IPs
            uniqueSourceIPs.add(packet.getSourceIP());
            uniqueDestinationIPs.add(packet.getDestinationIP());
            
            // Count protocols
            protocolDistribution.put(packet.getProtocol(), 
                protocolDistribution.getOrDefault(packet.getProtocol(), 0) + 1);
            
            // Count applications
            applicationDistribution.put(packet.getApplicationGuess(), 
                applicationDistribution.getOrDefault(packet.getApplicationGuess(), 0) + 1);
            
            // Count directions
            directionDistribution.put(packet.getDirection(), 
                directionDistribution.getOrDefault(packet.getDirection(), 0) + 1);
        }
    }
    
    /**
     * Saves detailed experiment log to file
     */
    private static void saveExperimentLog() {
        String logFilename = LOG_DIR + "/experiment_" + experimentId + ".log";
        
        try (FileWriter writer = new FileWriter(logFilename)) {
            writer.write("=== EXPERIMENT LOG ===\n");
            writer.write("Experiment ID: " + experimentId + "\n");
            writer.write("Generated: " + LOG_DATE_FORMAT.format(new Date()) + "\n\n");
            
            // System details
            writer.write("=== SYSTEM DETAILS ===\n");
            writer.write("OS Name: " + osName + "\n");
            writer.write("OS Version: " + osVersion + "\n");
            writer.write("OS Architecture: " + osArchitecture + "\n");
            writer.write("Total Memory: " + formatBytes(totalMemory) + "\n");
            writer.write("Free Memory: " + formatBytes(freeMemory) + "\n");
            writer.write("Available Processors: " + availableProcessors + "\n");
            writer.write("Java Version: " + javaVersion + "\n");
            writer.write("Java Vendor: " + javaVendor + "\n\n");
            
            // Experiment details
            writer.write("=== EXPERIMENT DETAILS ===\n");
            writer.write("Start Time: " + LOG_DATE_FORMAT.format(experimentStartTime) + "\n");
            writer.write("End Time: " + LOG_DATE_FORMAT.format(experimentEndTime) + "\n");
            writer.write("Duration: " + formatDuration(experimentDurationMs) + "\n\n");
            
            // Summary statistics
            writer.write("=== SUMMARY STATISTICS ===\n");
            writer.write("Total Packets: " + totalPacketsCaptured + "\n");
            writer.write("Unique Source IPs: " + uniqueSourceIPs.size() + "\n");
            writer.write("Unique Destination IPs: " + uniqueDestinationIPs.size() + "\n\n");
            
            // Protocol distribution
            writer.write("Protocol Distribution:\n");
            for (Map.Entry<String, Integer> entry : protocolDistribution.entrySet()) {
                double percentage = (double) entry.getValue() / totalPacketsCaptured * 100;
                writer.write("  " + entry.getKey() + ": " + entry.getValue() + 
                           " (" + String.format("%.1f", percentage) + "%)\n");
            }
            
            writer.write("\nExperiment log saved to: " + logFilename + "\n");
            
        } catch (IOException e) {
            System.err.println("Error saving experiment log: " + e.getMessage());
        }
    }
    
    /**
     * Formats bytes into human-readable format
     */
    private static String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.2f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
    
    /**
     * Formats duration in milliseconds to human-readable format
     */
    private static String formatDuration(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        if (hours > 0) {
            return String.format("%d hours, %d minutes, %d seconds", hours, minutes % 60, seconds % 60);
        } else if (minutes > 0) {
            return String.format("%d minutes, %d seconds", minutes, seconds % 60);
        } else {
            return String.format("%d seconds", seconds);
        }
    }
}
