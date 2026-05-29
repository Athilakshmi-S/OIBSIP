import java.util.*;
// Class 1: Renamed blueprint class from Book
class InventoryItem {
    String itemCode, itemTitle, itemGenre, holdUser = "";
    boolean checkedOut = false;
    InventoryItem(String itemCode, String itemTitle, String itemGenre) {
        this.itemCode = itemCode; 
        this.itemTitle = itemTitle; 
        this.itemGenre = itemGenre;
    }
}
public class Athilakshmi_Task5 {
    private static final Scanner systemScanner = new Scanner(System.in);
    private static final Map<String, InventoryItem> archiveStore = new HashMap<>();
    private static final List<String> registeredClients = new ArrayList<>();
    private static double aggregatePenaltiesPaid = 0.0;
    // Static initializer block with fresh names and records
    static {
        archiveStore.put("101", new InventoryItem("101", "Java Programming", "Technical"));
        archiveStore.put("102", new InventoryItem("102", "Data Structures", "Technical"));
        registeredClients.add("Athila"); // Changed initial user context to her name
    }
    public static void main(String[] args) {
        System.out.println("====== VIRTUAL LIBRARY NETWORK SYSTEM ======");
        while (true) {
            System.out.print("\n1. Management Desk | 2. Patron Interface | 3. Close Terminal\nInput: ");
            int coreOption = systemScanner.nextInt(); 
            systemScanner.nextLine(); // Clear scanner buffer       
            if (coreOption == 1) launchAdminModule();
            else if (coreOption == 2) launchPatronModule();
            else if (coreOption == 3) System.exit(0);
            else System.out.println("[FAILED] Choice allocation unrecognized.");
        }
    }
    private static void launchAdminModule() {
        while (true) {
            System.out.print("\n[MGR] 1. Catalog Item | 2. Discard Item | 3. System Metrics | 4. Back\nInput: ");
            int adminChoice = systemScanner.nextInt(); 
            systemScanner.nextLine();       
            if (adminChoice == 1) {
                System.out.print("Assign Item Code: "); String code = systemScanner.nextLine();
                System.out.print("Assign Book Title: "); String name = systemScanner.nextLine();
                System.out.print("Assign Category: "); String category = systemScanner.nextLine();
                archiveStore.put(code, new InventoryItem(code, name, category));
                System.out.println("[DONE] Item successfully cataloged!");
            } else if (adminChoice == 2) {
                System.out.print("Identify Item Code to remove: "); String code = systemScanner.nextLine();
                if (archiveStore.remove(code) != null) {
                    System.out.println("[DONE] Item purged from memory database!");
                } else {
                    System.out.println("[FAILED] Reference missing in archive index.");
                }
            } else if (adminChoice == 3) {
                // Feature 3: Full Administrative Analytics Report (Rewritten stream logic variables)
                System.out.println("\n--- CENTRAL INFRASTRUCTURE ANALYTICS ---");
                long activeLoans = archiveStore.values().stream().filter(item -> item.checkedOut).count();
                System.out.println("Aggregate Inventory Size: " + archiveStore.size());
                System.out.println("Current Active Loans: " + activeLoans);
                System.out.println("Total Penalty Income Earned: INR " + aggregatePenaltiesPaid);
                System.out.println("-----------------------------------------");
            } else if (adminChoice == 4) {
                return;
            }
        }
    }
    private static void launchPatronModule() {
        while (true) {
            System.out.print("\n[PATRON] 1. Search Directory | 2. Rent Document | 3. Return & Penalty Sweep | 4. Advance Hold Reservation | 5. Back\nInput: ");
            int userChoice = systemScanner.nextInt(); 
            systemScanner.nextLine();       
            if (userChoice == 1) {
                System.out.print("Query Genre/Title terms (Blank for all entries): ");
                String searchString = systemScanner.nextLine().toLowerCase();
                for (InventoryItem entry : archiveStore.values()) {
                    if (entry.itemTitle.toLowerCase().contains(searchString) || entry.itemGenre.toLowerCase().contains(searchString)) {
                        String currentAvailability = entry.checkedOut ? "Checked-Out" : "On-Shelf";
                        if (!entry.holdUser.isEmpty()) {
                            currentAvailability += " (Reserved for " + entry.holdUser + ")";
                        }
                        System.out.printf("Code: %s | Title: %s | Category: %s | Status: %s\n", entry.itemCode, entry.itemTitle, entry.itemGenre, currentAvailability);
                    }
                }
            } else if (userChoice == 2) {
                System.out.print("Identify Item Code to rent: "); String code = systemScanner.nextLine();
                if (archiveStore.containsKey(code) && !archiveStore.get(code).checkedOut) {
                    archiveStore.get(code).checkedOut = true;
                    System.out.println("[DONE] Allocation verified. Book checked out!");
                } else {
                    System.out.println("[FAILED] Book is restricted, under loan, or nonexistent.");
                }
            } else if (userChoice == 3) {
                // Feature 1: Automated Fine Calculation Logic (Rewritten mathematical variables)
                System.out.print("Identify Returning Item Code: "); String code = systemScanner.nextLine();
                if (archiveStore.containsKey(code) && archiveStore.get(code).checkedOut) {
                    System.out.print("Declare overdrawn days (0 if returned timely): ");
                    int processingDelayDays = systemScanner.nextInt(); 
                    systemScanner.nextLine();
                    if (processingDelayDays > 0) {
                        double calculatedOverdueFee = processingDelayDays * 5.0; // Flat INR 5/day logic
                        aggregatePenaltiesPaid += calculatedOverdueFee;
                        System.out.println("[ALERT] Late Return! Compensation Required: INR " + calculatedOverdueFee);
                    }
                    archiveStore.get(code).checkedOut = false;
                    archiveStore.get(code).holdUser = ""; // Flush the advance queue on intake processing
                    System.out.println("[DONE] Item restored to shelving units successfully.");
                } else {
                    System.out.println("[FAILED] Unable to match transaction records for return.");
                }
            } else if (userChoice == 4) {
                // Feature 2: Advance Booking / Reservation Mechanism (Rewritten assignment criteria)
                System.out.print("Identify Item Code for future hold request: "); String code = systemScanner.nextLine();
                if (archiveStore.containsKey(code) && archiveStore.get(code).checkedOut) {
                    System.out.print("Provide your registration name to secure priority queue: ");
                    archiveStore.get(code).holdUser = systemScanner.nextLine();
                    System.out.println("[DONE] Advance priority queue placeholder locked in!");
                } else {
                    System.out.println("[FAILED] Item is already sitting on the shelf or not present in database.");
                }
            } else if (userChoice == 5) {
                return;
            }
        }
    }
}