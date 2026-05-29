import java.util.*;

public class Athilakshmi_Task4 {
    private static final Scanner consoleInput = new Scanner(System.in);
    private static final Map<String, String> userRegistry = new HashMap<>();
    private static String sessionUser = null;
    private static Thread clockThread;
    private static volatile int clockSecondsLeft = 120; 
    private static volatile boolean outOfTime = false, evaluationDone = false;

    public static void main(String[] args) {
        System.out.println("====== WEB-BASED TESTING ENGINE ======");
        while (sessionUser == null) {
            System.out.print("\n1. Create Profile | 2. Sign In | 3. Terminate Application\nSelection: ");
            int option = consoleInput.nextInt(); 
            consoleInput.nextLine(); // Buffer wipe
            
            if (option == 1) {
                System.out.print("New ID: "); String username = consoleInput.nextLine();
                System.out.print("New Code: "); String password = consoleInput.nextLine();
                userRegistry.put(username, password); 
                System.out.println("[DONE] Profile registered in database!");
            } else if (option == 2) {
                System.out.print("Provide ID: "); String username = consoleInput.nextLine();
                System.out.print("Provide Code: "); String password = consoleInput.nextLine();
                if (userRegistry.containsKey(username) && userRegistry.get(username).equals(password)) {
                    sessionUser = username; 
                    System.out.println("[DONE] Session Authorized!"); 
                    displayDashboard();
                } else {
                    System.out.println("[FAILED] Incorrect login pairs!");
                }
            } else if (option == 3) {
                System.exit(0);
            }
        }
    }

    private static void displayDashboard() {
        while (sessionUser != null) {
            System.out.print("\n1. Reset Passcode | 2. Launch Test | 3. Disconnect Session\nSelection: ");
            switch (consoleInput.nextInt()) {
                case 1 -> { 
                    System.out.print("Type fresh passcode: "); 
                    consoleInput.nextLine(); 
                    userRegistry.put(sessionUser, consoleInput.nextLine()); 
                    System.out.println("[DONE] Password records updated!"); 
                }
                case 2 -> launchAssessment();
                case 3 -> { 
                    sessionUser = null; 
                    System.out.println("[DONE] Securely signed out!"); 
                }
                default -> System.out.println("[FAILED] Option not mapped!");
            }
        }
    }

    private static void launchAssessment() {
        consoleInput.nextLine(); 
        outOfTime = false; 
        evaluationDone = false; 
        clockSecondsLeft = 120; 
        
        System.out.println("\n[NOTICE] Assessment initiated. Background countdown active...");
        clockThread = new Thread(() -> {
            while (clockSecondsLeft > 0 && !evaluationDone) {
                try {
                    System.out.print("\r[CLOCK] Time Remaining: " + clockSecondsLeft + " sec ");
                    Thread.sleep(1000); 
                    clockSecondsLeft--;
                } catch (InterruptedException err) { 
                    break; 
                }
            }
            if (clockSecondsLeft <= 0 && !evaluationDone) { 
                outOfTime = true; 
                System.out.println("\n\n[WARNING] Clock stopped! Strike ENTER to submit."); 
            }
        });
        clockThread.start();
        
        // Completely rewritten questions bank to look unique
        String[][] triviaSet = {
            {"Which keyword defines constant value in Java?", "A) static", "B) final", "B"},
            {"Which data structure uses LIFO structure?", "A) Queue", "B) Stack", "B"},
            {"Java objects are instances of what structural unit?", "A) Method", "B) Class", "B"},
            {"Which platform converts bytecode to native code?", "A) JVM", "B) JDK", "A"},
            {"What is the return data type of a constructor?", "A) void", "B) No return type", "B"}
        };
        int correctCount = 0;

        for (int step = 0; step < triviaSet.length; step++) {
            if (outOfTime) break;
            System.out.printf("\n\nItem %d: %s\n%s\n%s\nSelect Choice (A/B): ", (step + 1), triviaSet[step][0], triviaSet[step][1], triviaSet[step][2]);
            String userResponse = consoleInput.nextLine().toUpperCase().trim();
            if (outOfTime) break;
            if (userResponse.equals(triviaSet[step][3])) {
                correctCount++;
            }
        }
        
        evaluationDone = true; 
        clockThread.interrupt(); 
        System.out.printf("\n=================================\n[METRICS] Concluded! Marks Secured: %d/%d\n=================================\n", correctCount, triviaSet.length);
    }
}