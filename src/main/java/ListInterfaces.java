import net.sourceforge.jpcap.capture.PacketCapture;

/**
 * ListInterfaces.java
 * 
 * A simple Java program that lists all available network interfaces using JPcap library.
 * For each interface, it displays:
 * - Index number
 * - Name
 * - Description
 */
public class ListInterfaces {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== Network Interface Lister ===");
            System.out.println("Listing all available network interfaces...\n");
            
            // Get all available network interfaces
            String[] devices = PacketCapture.lookupDevices();
            
            if (devices == null || devices.length == 0) {
                System.out.println("No network interfaces found!");
                System.out.println("Make sure WinPcap or Npcap is installed on your system.");
                return;
            }
            
            System.out.println("Found " + devices.length + " network interface(s):\n");
            
            // Display each interface
            for (int i = 0; i < devices.length; i++) {
                System.out.println("Index: " + i);
                System.out.println("Name: " + devices[i]);
                System.out.println("Description: " + devices[i]);
                System.out.println();
            }
            
            System.out.println("=== End of Interface List ===");
            
        } catch (Exception e) {
            System.err.println("Error occurred while listing network interfaces:");
            System.err.println("Exception: " + e.getClass().getSimpleName());
            System.err.println("Message: " + e.getMessage());
            System.err.println("\nTroubleshooting:");
            System.err.println("1. Make sure WinPcap or Npcap is installed");
            System.err.println("2. Run the program as administrator (Windows)");
            System.err.println("3. Check if JPcap library is properly configured");
            e.printStackTrace();
        }
    }
}
