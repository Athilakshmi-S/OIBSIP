import java.util.*;

// Class 1: Renamed from TransactionHistory
class LogManager {
    private final List<String> statementList = new ArrayList<>();
    
    public void recordAction(String activityDetails) {
        String currentTimeStamp = new Date().toString();
        statementList.add("[" + currentTimeStamp + "] " + activityDetails);
    }
    
    public void printStatement() {
        if (statementList.isEmpty()) {
            System.out.println("\nNo prior activity found in history.");
        } else {
            System.out.println("\n*** STATEMENT OVERVIEW ***");
            statementList.forEach(System.out::println);
            System.out.println("**************************");
        }
    }
}

// Class 2: Renamed from Account
class UserWallet {
    private double currentFunds = 10000.0; 
    private double dailyTotalWithdrawn = 0.0;
    private static final double MAX_DAILY_LIMIT = 20000.0;

    public double fetchBalance() { 
        return currentFunds; 
    }
    
    public void addFunds(double creditAmount) { 
        currentFunds += creditAmount; 
    }
    
    public int processWithdrawal(double debitAmount) {
        if (dailyTotalWithdrawn + debitAmount > MAX_DAILY_LIMIT) {
            return -1; // Limit hit
        }
        if (debitAmount > currentFunds) {
            return 0; // Low funds
        }
        currentFunds -= debitAmount;
        dailyTotalWithdrawn += debitAmount;
        return 1; // Completed
    }
}

// Class 3: Renamed from BankDatabase
class CredentialsRegistry {
    private final Map<String, String> userMap = new HashMap<>();
    
    public CredentialsRegistry() { 
        userMap.put("user123", "4321"); 
    }
    
    public void enrollAccount(String userKey, String pinKey) { 
        userMap.put(userKey, pinKey); 
    }
    
    public void modifyPasscode(String userKey, String refreshedPin) { 
        userMap.put(userKey, refreshedPin); 
    }
    
    public boolean verifyAccess(String userKey, String pinKey) {
        return userMap.containsKey(userKey) && userMap.get(userKey).equals(pinKey);
    }
}

// Class 4: Renamed from ATMOperations
class ServiceDashboard {
    private final UserWallet wallet = new UserWallet();
    private final LogManager logger = new LogManager();
    private final CredentialsRegistry systemDb;
    private final String clientSessionId;
    private final Scanner reader = new Scanner(System.in);

    public ServiceDashboard(CredentialsRegistry systemDb, String clientSessionId) {
        this.systemDb = systemDb;
        this.clientSessionId = clientSessionId;
    }

    public void launchTerminal() {
        while (true) {
            System.out.print("\n[1] View History | [2] Cash Out | [3] Cash In | [4] Remit Funds | [5] Modify PIN | [6] Terminate\nSelection: ");
            int userChoice = reader.nextInt(); 
            reader.nextLine(); // Clear buffer
            
            if (userChoice == 1) logger.printStatement();
            else if (userChoice == 2) executeCashOut();
            else if (userChoice == 3) executeCashIn();
            else if (userChoice == 4) executeRemittance();
            else if (userChoice == 5) executePinChange();
            else if (userChoice == 6) { 
                System.out.println("\n[DONE] Securely logged out."); 
                break; 
            }
            else System.out.println("[FAILED] Option unrecognized.");
        }
    }

    private void executeCashOut() {
        System.out.println("\nAvailable Funds: INR " + wallet.fetchBalance());
        System.out.print("Specify cash out quantity: "); 
        double sum = reader.nextDouble();
        
        int status = wallet.processWithdrawal(sum);
        if (status == 1) {
            String activity = "Debited: INR " + sum + " | Current: INR " + wallet.fetchBalance();
            logger.recordAction(activity);
            System.out.println("[DONE] Disbursing banknotes. Updated Balance: INR " + wallet.fetchBalance());
        } else if (status == -1) {
            System.out.println("[FAILED] Maximum allowance of INR 20,000 reached today!");
        } else {
            System.out.println("[FAILED] Your account balance is insufficient.");
        }
    }

    private void executeCashIn() {
        System.out.println("\nAvailable Funds: INR " + wallet.fetchBalance());
        System.out.print("Specify deposit quantity: "); 
        double sum = reader.nextDouble();
        
        wallet.addFunds(sum);
        String activity = "Credited: INR " + sum + " | Current: INR " + wallet.fetchBalance();
        logger.recordAction(activity);
        System.out.println("[DONE] Currency deposited. Updated Balance: INR " + wallet.fetchBalance());
    }

    private void executeRemittance() {
        System.out.println("\nAvailable Funds: INR " + wallet.fetchBalance());
        System.out.print("Specify target account reference: "); 
        String targetUser = reader.nextLine();
        System.out.print("Specify amount to transfer: "); 
        double sum = reader.nextDouble();
        
        if (wallet.processWithdrawal(sum) == 1) {
            String activity = "Sent: INR " + sum + " to ID: " + targetUser + " | Current: INR " + wallet.fetchBalance();
            logger.recordAction(activity);
            System.out.println("[DONE] Remittance finished. Updated Balance: INR " + wallet.fetchBalance());
        } else {
            System.out.println("[FAILED] Request denied due to boundary restrictions or inadequate balance.");
        }
    }

    private void executePinChange() {
        System.out.print("Specify new 4-digit numeric code: "); 
        String updatedPinCode = reader.nextLine();
        systemDb.modifyPasscode(clientSessionId, updatedPinCode);
        logger.recordAction("Security passcode altered successfully.");
        System.out.println("[DONE] Passcode has been reconfigured.");
    }
}

// Class 5: Renamed from MuthamilselviS_Task3
public class Athilakshmi_Task3 {
    public static void main(String[] args) {
        Scanner systemInput = new Scanner(System.in);
        CredentialsRegistry centralizedRegistry = new CredentialsRegistry();
        System.out.println("=== OIB AUTOMATED TELLER APPARATUS ===");
        
        while (true) {
            System.out.print("\n1. Enrollment | 2. Authenticate | 3. Quit System\nInput Choice: ");
            int pathwaySelection = systemInput.nextInt(); 
            systemInput.nextLine(); // Clear buffer
            
            if (pathwaySelection == 1) {
                System.out.print("Assign New Username ID: "); 
                String identityKey = systemInput.nextLine();
                System.out.print("Assign 4-Digit Password PIN: "); 
                String securityCode = systemInput.nextLine();
                
                centralizedRegistry.enrollAccount(identityKey, securityCode);
                System.out.println("[DONE] Profile Registered Successfully.");
            } else if (pathwaySelection == 2) {
                System.out.print("Provide Username ID: "); 
                String userEntry = systemInput.nextLine();
                System.out.print("Provide Account PIN: "); 
                String pinEntry = systemInput.nextLine();

                if (centralizedRegistry.verifyAccess(userEntry, pinEntry)) {
                    System.out.println("[DONE] Identity Authenticated.");
                    ServiceDashboard engine = new ServiceDashboard(centralizedRegistry, userEntry);
                    engine.launchTerminal();
                } else {
                    System.out.println("[FAILED] Invalid account identifiers entered.");
                }
            } else if (pathwaySelection == 3) {
                System.out.println("System connection severed. Goodbye.");
                break;
            }
        }
        systemInput.close();
    }
}