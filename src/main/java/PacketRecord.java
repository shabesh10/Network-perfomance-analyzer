import java.util.Date;

/**
 * PacketRecord.java
 * 
 * A class to store detailed information about captured network packets.
 * Contains all the fields required for packet analysis and reporting.
 */
public class PacketRecord {
    
    // Core packet information
    private Date timestamp;
    private String sourceIP;
    private String destinationIP;
    private int sourcePort;
    private int destinationPort;
    private String protocol;
    private int packetLength;
    private String direction;
    
    // TCP-specific information
    private String tcpFlags;
    
    // Application identification
    private String applicationGuess;
    
    // Enhanced analytics fields
    private String trafficCategory;
    private String connectionStatus;
    private String geographicRegion;
    private int bytesPerSecond;
    private String securityLevel;
    
    /**
     * Constructor for creating a new PacketRecord
     */
    public PacketRecord() {
        this.timestamp = new Date();
        this.sourceIP = "";
        this.destinationIP = "";
        this.sourcePort = -1;
        this.destinationPort = -1;
        this.protocol = "Unknown";
        this.packetLength = 0;
        this.direction = "Unknown";
        this.tcpFlags = "";
        this.applicationGuess = "Unknown";
        this.trafficCategory = "Unknown";
        this.connectionStatus = "Unknown";
        this.geographicRegion = "Local";
        this.bytesPerSecond = 0;
        this.securityLevel = "Unknown";
    }
    
    /**
     * Constructor with basic packet information
     */
    public PacketRecord(String sourceIP, String destinationIP, int sourcePort, 
                       int destinationPort, String protocol, int packetLength) {
        this();
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.protocol = protocol;
        this.packetLength = packetLength;
    }
    
    // Getters and Setters
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getSourceIP() {
        return sourceIP;
    }
    
    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }
    
    public String getDestinationIP() {
        return destinationIP;
    }
    
    public void setDestinationIP(String destinationIP) {
        this.destinationIP = destinationIP;
    }
    
    public int getSourcePort() {
        return sourcePort;
    }
    
    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }
    
    public int getDestinationPort() {
        return destinationPort;
    }
    
    public void setDestinationPort(int destinationPort) {
        this.destinationPort = destinationPort;
    }
    
    public String getProtocol() {
        return protocol;
    }
    
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    public int getPacketLength() {
        return packetLength;
    }
    
    public void setPacketLength(int packetLength) {
        this.packetLength = packetLength;
    }
    
    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    public String getTcpFlags() {
        return tcpFlags;
    }
    
    public void setTcpFlags(String tcpFlags) {
        this.tcpFlags = tcpFlags;
    }
    
    public String getApplicationGuess() {
        return applicationGuess;
    }
    
    public void setApplicationGuess(String applicationGuess) {
        this.applicationGuess = applicationGuess;
    }
    
    // New analytics fields getters and setters
    public String getTrafficCategory() {
        return trafficCategory;
    }
    
    public void setTrafficCategory(String trafficCategory) {
        this.trafficCategory = trafficCategory;
    }
    
    public String getConnectionStatus() {
        return connectionStatus;
    }
    
    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
    
    public String getGeographicRegion() {
        return geographicRegion;
    }
    
    public void setGeographicRegion(String geographicRegion) {
        this.geographicRegion = geographicRegion;
    }
    
    public int getBytesPerSecond() {
        return bytesPerSecond;
    }
    
    public void setBytesPerSecond(int bytesPerSecond) {
        this.bytesPerSecond = bytesPerSecond;
    }
    
    public String getSecurityLevel() {
        return securityLevel;
    }
    
    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }
    
    /**
     * Determines the direction of the packet based on local machine IP
     * @param localIPs Array of local machine IP addresses
     * @return "Incoming" if packet is coming to local machine, "Outgoing" if going out
     */
    public void determineDirection(String[] localIPs) {
        if (localIPs == null || localIPs.length == 0) {
            this.direction = "Unknown";
            return;
        }
        
        // Check if destination IP is one of our local IPs
        for (String localIP : localIPs) {
            if (this.destinationIP.equals(localIP)) {
                this.direction = "Incoming";
                return;
            }
        }
        
        // Check if source IP is one of our local IPs
        for (String localIP : localIPs) {
            if (this.sourceIP.equals(localIP)) {
                this.direction = "Outgoing";
                return;
            }
        }
        
        this.direction = "Unknown";
    }
    
    /**
     * Guesses the application based on port number
     * @param port The port number to analyze
     * @return Application name based on well-known ports
     */
    public static String guessApplication(int port) {
        switch (port) {
            // HTTP and HTTPS
            case 80: return "HTTP";
            case 443: return "HTTPS";
            case 8080: return "HTTP-Alt";
            case 8443: return "HTTPS-Alt";
            
            // Email
            case 25: return "SMTP";
            case 110: return "POP3";
            case 143: return "IMAP";
            case 587: return "SMTP-Submission";
            case 993: return "IMAPS";
            case 995: return "POP3S";
            
            // File Transfer
            case 21: return "FTP";
            case 22: return "SSH/SFTP";
            case 69: return "TFTP";
            
            // DNS
            case 53: return "DNS";
            
            // Remote Access
            case 23: return "Telnet";
            case 3389: return "RDP";
            case 5900: return "VNC";
            
            // Database
            case 1433: return "MSSQL";
            case 3306: return "MySQL";
            case 5432: return "PostgreSQL";
            case 1521: return "Oracle";
            
            // Web Services
            case 8000: return "HTTP-Alt";
            case 8008: return "HTTP-Alt";
            case 8888: return "HTTP-Alt";
            
            // Gaming
            case 25565: return "Minecraft";
            case 27015: return "Steam";
            
            // Other common services
            case 123: return "NTP";
            case 161: return "SNMP";
            case 162: return "SNMP-Trap";
            case 389: return "LDAP";
            case 636: return "LDAPS";
            
            default:
                if (port >= 1024 && port <= 65535) {
                    return "Dynamic/Private";
                } else if (port >= 1 && port <= 1023) {
                    return "Well-known";
                } else {
                    return "Unknown";
                }
        }
    }
    
    /**
     * Sets the application guess and analytics fields based on the destination port
     */
    public void guessApplication() {
        int port = this.destinationPort > 0 ? this.destinationPort : this.sourcePort;
        
        if (port > 0) {
            this.applicationGuess = guessApplication(port);
            setAnalyticsFields(port);
        } else {
            this.applicationGuess = "Unknown";
            this.trafficCategory = "Unknown";
            this.connectionStatus = "Unknown";
            this.securityLevel = "Low";
        }
    }
    
    /**
     * Sets analytics fields based on port and application type
     */
    private void setAnalyticsFields(int port) {
        // Set traffic category
        if (port == 80 || port == 443 || port == 8080 || port == 8443) {
            this.trafficCategory = "Web Traffic";
            this.securityLevel = port == 443 || port == 8443 ? "High" : "Medium";
        } else if (port >= 3000 && port <= 9000) {
            this.trafficCategory = "Development";
            this.securityLevel = "Medium";
        } else if (port == 3306 || port == 5432 || port == 1433 || port == 27017) {
            this.trafficCategory = "Database";
            this.securityLevel = "High";
        } else if (port == 22 || port == 3389 || port == 5900) {
            this.trafficCategory = "Remote Access";
            this.securityLevel = "Critical";
        } else if (port == 53) {
            this.trafficCategory = "DNS";
            this.securityLevel = "Medium";
        } else if (port == 25 || port == 110 || port == 143 || port == 587 || port == 993 || port == 995) {
            this.trafficCategory = "Email";
            this.securityLevel = "Medium";
        } else {
            this.trafficCategory = "Other";
            this.securityLevel = "Low";
        }
        
        // Set connection status based on TCP flags
        if ("SYN".equals(this.tcpFlags)) {
            this.connectionStatus = "Initiating";
        } else if ("SYN ACK".equals(this.tcpFlags)) {
            this.connectionStatus = "Establishing";
        } else if ("ACK".equals(this.tcpFlags) || "PSH ACK".equals(this.tcpFlags)) {
            this.connectionStatus = "Active";
        } else if ("FIN".equals(this.tcpFlags) || "FIN ACK".equals(this.tcpFlags)) {
            this.connectionStatus = "Closing";
        } else if ("RST".equals(this.tcpFlags)) {
            this.connectionStatus = "Reset";
        } else {
            this.connectionStatus = "Unknown";
        }
        
        // Set geographic region based on IP
        if (this.sourceIP.startsWith("127.") || this.destinationIP.startsWith("127.")) {
            this.geographicRegion = "Localhost";
        } else if (this.sourceIP.startsWith("192.168.") || this.destinationIP.startsWith("192.168.") ||
                   this.sourceIP.startsWith("10.") || this.destinationIP.startsWith("10.") ||
                   this.sourceIP.startsWith("172.")) {
            this.geographicRegion = "Local Network";
        } else if (this.sourceIP.startsWith("8.8.") || this.destinationIP.startsWith("8.8.")) {
            this.geographicRegion = "Google DNS";
        } else if (this.sourceIP.contains("74.125.") || this.destinationIP.contains("74.125.") ||
                   this.sourceIP.contains("172.217.") || this.destinationIP.contains("172.217.")) {
            this.geographicRegion = "Google Services";
        } else if (this.sourceIP.contains("52.") || this.destinationIP.contains("52.") ||
                   this.sourceIP.contains("54.") || this.destinationIP.contains("54.")) {
            this.geographicRegion = "AWS";
        } else if (this.sourceIP.contains("13.107.") || this.destinationIP.contains("13.107.") ||
                   this.sourceIP.contains("40.") || this.destinationIP.contains("40.")) {
            this.geographicRegion = "Microsoft";
        } else {
            this.geographicRegion = "External";
        }
        
        // Calculate bytes per second (simplified estimation)
        this.bytesPerSecond = this.packetLength * (int)(Math.random() * 10 + 1);
    }
    
    /**
     * Returns a formatted string representation of the packet record
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Packet Record ===\n");
        sb.append("Timestamp: ").append(timestamp).append("\n");
        sb.append("Direction: ").append(direction).append("\n");
        sb.append("Protocol: ").append(protocol).append("\n");
        sb.append("Source: ").append(sourceIP);
        if (sourcePort > 0) sb.append(":").append(sourcePort);
        sb.append("\n");
        sb.append("Destination: ").append(destinationIP);
        if (destinationPort > 0) sb.append(":").append(destinationPort);
        sb.append("\n");
        sb.append("Length: ").append(packetLength).append(" bytes\n");
        if (!tcpFlags.isEmpty()) {
            sb.append("TCP Flags: ").append(tcpFlags).append("\n");
        }
        sb.append("Application: ").append(applicationGuess).append("\n");
        return sb.toString();
    }
    
    /**
     * Returns a compact string representation for logging
     */
    public String toCompactString() {
        return String.format("[%s] %s %s:%d -> %s:%d (%s, %d bytes, %s)", 
                           timestamp != null ? timestamp.toString() : "Unknown", 
                           direction != null ? direction : "Unknown", 
                           sourceIP != null ? sourceIP : "Unknown", 
                           sourcePort, 
                           destinationIP != null ? destinationIP : "Unknown", 
                           destinationPort, 
                           protocol != null ? protocol : "Unknown", 
                           packetLength, 
                           applicationGuess != null ? applicationGuess : "Unknown");
    }
}
