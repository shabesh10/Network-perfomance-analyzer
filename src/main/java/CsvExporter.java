import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/**
 * CsvExporter.java
 * 
 * A utility class for exporting packet data to CSV format.
 * Takes a list of PacketRecord objects and writes them to a CSV file
 * in the output/ directory with proper formatting and headers.
 */
public class CsvExporter {
    
    private static final String OUTPUT_DIR = "output";
    private static final String CSV_HEADER = "Timestamp,SourceIP,DestinationIP,SourcePort,DestinationPort,Protocol,PacketLength,Direction,TCPFlags,ApplicationGuess,TrafficCategory,ConnectionStatus,GeographicRegion,BytesPerSecond,SecurityLevel";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
    /**
     * Exports a list of PacketRecord objects to a CSV file with Power BI compatibility
     * @param packetRecords List of PacketRecord objects to export
     * @param filename The name of the CSV file (without extension)
     * @return true if export was successful, false otherwise
     */
    public static boolean exportToCsv(List<PacketRecord> packetRecords, String filename) {
        if (packetRecords == null || packetRecords.isEmpty()) {
            System.out.println("No packet records to export.");
            return false;
        }
        
        // Ensure output directory exists
        if (!createOutputDirectory()) {
            return false;
        }
        
        String fullFilename = OUTPUT_DIR + "/" + filename + ".csv";
        
        try (FileWriter writer = new FileWriter(fullFilename, java.nio.charset.StandardCharsets.UTF_8)) {
            // Write CSV header
            writer.write(CSV_HEADER);
            writer.write("\n");
            
            // Write each packet record
            for (PacketRecord record : packetRecords) {
                writer.write(formatRecordForCsv(record));
                writer.write("\n");
            }
            
            System.out.println("Successfully exported " + packetRecords.size() + " packet records to " + fullFilename);
            return true;
            
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Exports packet records with a timestamp-based filename
     * @param packetRecords List of PacketRecord objects to export
     * @return true if export was successful, false otherwise
     */
    public static boolean exportToCsv(List<PacketRecord> packetRecords) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "packet_capture_" + timestamp;
        return exportToCsv(packetRecords, filename);
    }
    
    /**
     * Creates the output directory if it doesn't exist
     * @return true if directory exists or was created successfully, false otherwise
     */
    private static boolean createOutputDirectory() {
        try {
            java.io.File outputDir = new java.io.File(OUTPUT_DIR);
            if (!outputDir.exists()) {
                boolean created = outputDir.mkdirs();
                if (created) {
                    System.out.println("Created output directory: " + OUTPUT_DIR);
                } else {
                    System.err.println("Failed to create output directory: " + OUTPUT_DIR);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error creating output directory: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Formats a PacketRecord object for CSV output
     * @param record The PacketRecord to format
     * @return Formatted CSV line
     */
    private static String formatRecordForCsv(PacketRecord record) {
        StringBuilder csvLine = new StringBuilder();
        
        // Timestamp
        csvLine.append("\"").append(DATE_FORMAT.format(record.getTimestamp())).append("\",");
        
        // Source IP
        csvLine.append("\"").append(escapeCsvField(record.getSourceIP())).append("\",");
        
        // Destination IP
        csvLine.append("\"").append(escapeCsvField(record.getDestinationIP())).append("\",");
        
        // Source Port
        csvLine.append(record.getSourcePort()).append(",");
        
        // Destination Port
        csvLine.append(record.getDestinationPort()).append(",");
        
        // Protocol
        csvLine.append("\"").append(escapeCsvField(record.getProtocol())).append("\",");
        
        // Packet Length
        csvLine.append(record.getPacketLength()).append(",");
        
        // Direction
        csvLine.append("\"").append(escapeCsvField(record.getDirection())).append("\",");
        
        // TCP Flags
        csvLine.append("\"").append(escapeCsvField(record.getTcpFlags())).append("\",");
        
        // Application Guess
        csvLine.append("\"").append(escapeCsvField(record.getApplicationGuess())).append("\",");
        
        // Traffic Category
        csvLine.append("\"").append(escapeCsvField(record.getTrafficCategory())).append("\",");
        
        // Connection Status
        csvLine.append("\"").append(escapeCsvField(record.getConnectionStatus())).append("\",");
        
        // Geographic Region
        csvLine.append("\"").append(escapeCsvField(record.getGeographicRegion())).append("\",");
        
        // Bytes Per Second
        csvLine.append(record.getBytesPerSecond()).append(",");
        
        // Security Level
        csvLine.append("\"").append(escapeCsvField(record.getSecurityLevel())).append("\"");
        
        return csvLine.toString();
    }
    
    /**
     * Escapes special characters in CSV fields
     * @param field The field to escape
     * @return Escaped field
     */
    private static String escapeCsvField(String field) {
        if (field == null) {
            return "";
        }
        
        // Escape double quotes by doubling them
        return field.replace("\"", "\"\"");
    }
    
    /**
     * Exports packet records with custom filename and additional options
     * @param packetRecords List of PacketRecord objects to export
     * @param filename The name of the CSV file (without extension)
     * @param includeHeader Whether to include the CSV header row
     * @return true if export was successful, false otherwise
     */
    public static boolean exportToCsv(List<PacketRecord> packetRecords, String filename, boolean includeHeader) {
        if (packetRecords == null || packetRecords.isEmpty()) {
            System.out.println("No packet records to export.");
            return false;
        }
        
        // Ensure output directory exists
        if (!createOutputDirectory()) {
            return false;
        }
        
        String fullFilename = OUTPUT_DIR + "/" + filename + ".csv";
        
        try (FileWriter writer = new FileWriter(fullFilename)) {
            // Write CSV header if requested
            if (includeHeader) {
                writer.write(CSV_HEADER);
                writer.write("\n");
            }
            
            // Write each packet record
            for (PacketRecord record : packetRecords) {
                writer.write(formatRecordForCsv(record));
                writer.write("\n");
            }
            
            System.out.println("Successfully exported " + packetRecords.size() + " packet records to " + fullFilename);
            return true;
            
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Exports packet records filtered by protocol
     * @param packetRecords List of PacketRecord objects to export
     * @param protocol The protocol to filter by (TCP, UDP, ARP, etc.)
     * @return true if export was successful, false otherwise
     */
    public static boolean exportToCsvByProtocol(List<PacketRecord> packetRecords, String protocol) {
        if (packetRecords == null || packetRecords.isEmpty()) {
            System.out.println("No packet records to export.");
            return false;
        }
        
        // Filter records by protocol
        List<PacketRecord> filteredRecords = packetRecords.stream()
            .filter(record -> protocol.equalsIgnoreCase(record.getProtocol()))
            .collect(java.util.stream.Collectors.toList());
        
        if (filteredRecords.isEmpty()) {
            System.out.println("No packets found with protocol: " + protocol);
            return false;
        }
        
        String filename = "packet_capture_" + protocol.toLowerCase() + "_" + 
                         new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        
        return exportToCsv(filteredRecords, filename);
    }
    
    /**
     * Exports packet records filtered by direction
     * @param packetRecords List of PacketRecord objects to export
     * @param direction The direction to filter by (Incoming, Outgoing)
     * @return true if export was successful, false otherwise
     */
    public static boolean exportToCsvByDirection(List<PacketRecord> packetRecords, String direction) {
        if (packetRecords == null || packetRecords.isEmpty()) {
            System.out.println("No packet records to export.");
            return false;
        }
        
        // Filter records by direction
        List<PacketRecord> filteredRecords = packetRecords.stream()
            .filter(record -> direction.equalsIgnoreCase(record.getDirection()))
            .collect(java.util.stream.Collectors.toList());
        
        if (filteredRecords.isEmpty()) {
            System.out.println("No packets found with direction: " + direction);
            return false;
        }
        
        String filename = "packet_capture_" + direction.toLowerCase() + "_" + 
                         new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        
        return exportToCsv(filteredRecords, filename);
    }
    
    /**
     * Exports packet records optimized for Power BI with specific formatting
     * @param packetRecords List of PacketRecord objects to export
     * @param filename The name of the CSV file (without extension)
     * @return true if export was successful, false otherwise
     */
    public static boolean exportToCsvForPowerBI(List<PacketRecord> packetRecords, String filename) {
        if (packetRecords == null || packetRecords.isEmpty()) {
            System.out.println("No packet records to export.");
            return false;
        }
        
        // Ensure output directory exists
        if (!createOutputDirectory()) {
            return false;
        }
        
        String fullFilename = OUTPUT_DIR + "/" + filename + ".csv";
        
        try (FileWriter writer = new FileWriter(fullFilename, java.nio.charset.StandardCharsets.UTF_8)) {
            // Write UTF-8 BOM for better Power BI compatibility
            writer.write('\ufeff');
            
            // Write CSV header
            writer.write(CSV_HEADER);
            writer.write("\n");
            
            // Write each packet record with Power BI optimized formatting
            for (PacketRecord record : packetRecords) {
                writer.write(formatRecordForPowerBI(record));
                writer.write("\n");
            }
            
            System.out.println("Successfully exported " + packetRecords.size() + " packet records to " + fullFilename + " (Power BI optimized)");
            return true;
            
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Formats a PacketRecord object for Power BI CSV output
     * @param record The PacketRecord to format
     * @return Formatted CSV line optimized for Power BI
     */
    private static String formatRecordForPowerBI(PacketRecord record) {
        StringBuilder csvLine = new StringBuilder();
        
        // Timestamp - Power BI friendly format
        csvLine.append("\"").append(DATE_FORMAT.format(record.getTimestamp())).append("\",");
        
        // Source IP
        csvLine.append("\"").append(escapeCsvField(record.getSourceIP())).append("\",");
        
        // Destination IP
        csvLine.append("\"").append(escapeCsvField(record.getDestinationIP())).append("\",");
        
        // Source Port - handle -1 for non-TCP/UDP packets
        int sourcePort = record.getSourcePort();
        csvLine.append(sourcePort == -1 ? "" : String.valueOf(sourcePort)).append(",");
        
        // Destination Port - handle -1 for non-TCP/UDP packets
        int destPort = record.getDestinationPort();
        csvLine.append(destPort == -1 ? "" : String.valueOf(destPort)).append(",");
        
        // Protocol
        csvLine.append("\"").append(escapeCsvField(record.getProtocol())).append("\",");
        
        // Packet Length
        csvLine.append(record.getPacketLength()).append(",");
        
        // Direction
        csvLine.append("\"").append(escapeCsvField(record.getDirection())).append("\",");
        
        // TCP Flags - empty string if no flags
        String tcpFlags = record.getTcpFlags();
        csvLine.append("\"").append(escapeCsvField(tcpFlags.isEmpty() ? "" : tcpFlags)).append("\",");
        
        // Application Guess
        csvLine.append("\"").append(escapeCsvField(record.getApplicationGuess())).append("\",");
        
        // Traffic Category
        csvLine.append("\"").append(escapeCsvField(record.getTrafficCategory())).append("\",");
        
        // Connection Status
        csvLine.append("\"").append(escapeCsvField(record.getConnectionStatus())).append("\",");
        
        // Geographic Region
        csvLine.append("\"").append(escapeCsvField(record.getGeographicRegion())).append("\",");
        
        // Bytes Per Second
        csvLine.append(record.getBytesPerSecond()).append(",");
        
        // Security Level
        csvLine.append("\"").append(escapeCsvField(record.getSecurityLevel())).append("\"");
        
        return csvLine.toString();
    }
    
    /**
     * Gets the output directory path
     * @return The output directory path
     */
    public static String getOutputDirectory() {
        return OUTPUT_DIR;
    }
    
    /**
     * Lists all CSV files in the output directory
     * @return Array of CSV filenames, or empty array if none found
     */
    public static String[] listCsvFiles() {
        try {
            java.io.File outputDir = new java.io.File(OUTPUT_DIR);
            if (!outputDir.exists()) {
                return new String[0];
            }
            
            return outputDir.list((dir, name) -> name.toLowerCase().endsWith(".csv"));
        } catch (Exception e) {
            System.err.println("Error listing CSV files: " + e.getMessage());
            return new String[0];
        }
    }
}
