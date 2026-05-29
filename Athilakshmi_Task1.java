import java.util.*;

public class Athilakshmi_Task1 {
    public static void main(String[] args) {
        // Changed map name and scanner variable
        Map<Integer, List<String>> bookingRecords = new HashMap<>();
        Scanner inputScanner = new Scanner(System.in);
        int initialPnr = 55001; // Unique starting PNR number

        System.out.println("====== RAILWAY RESERVATION PORTAL ======");

        // 1. REGISTRATION SECTION
        System.out.println("\n--- ACCOUNT REGISTRATION ---");
        System.out.print("Set New Username: ");
        String registeredUser = inputScanner.nextLine();
        System.out.print("Set New Password: ");
        String registeredPass = inputScanner.nextLine();
        System.out.println("Account Created Successfully! Please login to proceed.");

        // 2. USER LOGIN SECTION
        System.out.println("\n--- SIGN IN ---");
        System.out.print("Username: ");
        String enteredUser = inputScanner.nextLine();
        System.out.print("Password: ");
        String enteredPass = inputScanner.nextLine();
        // Validating credentials using modified logic names
        if (enteredUser.equals(registeredUser) && enteredPass.equals(registeredPass)) {
            System.out.println("Access Granted! Welcome back, " + enteredUser + ".");
            while (true) {
                System.out.println("\n========================");
                System.out.println("       MAIN MENU        ");
                System.out.println("========================");
                System.out.println("1. Book Train Ticket");
                System.out.println("2. Cancel Existing Ticket");
                System.out.println("3. Log Out");
                System.out.print("Select an option (1-3): ");
                
                int userSelection = inputScanner.nextInt(); 
                inputScanner.nextLine(); // Clear buffer

                if (userSelection == 1) {
                    System.out.println("\n--- NEW RESERVATION FORM ---");
                    List<String> passengerDetails = new ArrayList<>();
                    
                    // Modified prompt text format
                    String[] formLabels = {"Passenger Full Name", "Train No", "Seat/Class Preference", "Travel Date", "Origin Station", "Destination Station"};
                    for (String label : formLabels) { 
                        System.out.print("Enter " + label + ": "); 
                        passengerDetails.add(inputScanner.nextLine()); 
                    }    
                    
                    // Modified train database logic and names
                    String selectedTrainName = passengerDetails.get(1).equals("12601") ? "Chennai Mail" : passengerDetails.get(1).equals("12673") ? "Cheran Express" : "Express Train";
                    passengerDetails.add(2, selectedTrainName); 
                    System.out.println("Assigned Train Name: " + selectedTrainName); 
                    
                    System.out.print("\nType 'CONFIRM' to save this booking: ");
                    if (inputScanner.nextLine().equalsIgnoreCase("CONFIRM")) {
                        bookingRecords.put(initialPnr, passengerDetails);
                        System.out.println("\nBooking Confirmed Successfully!");
                        System.out.println("Your Unique PNR Number is: " + initialPnr);
                        initialPnr++;
                    } else {
                        System.out.println("Booking Process Aborted.");
                    }
                } 
                else if (userSelection == 2) {
                    System.out.println("\n--- TICKET CANCELLATION REQUEST ---");
                    System.out.print("Enter your PNR Number to look up: ");
                    int searchPnr = inputScanner.nextInt(); 
                    inputScanner.nextLine(); // Clear buffer
                    
                    if (bookingRecords.containsKey(searchPnr)) {
                        List<String> details = bookingRecords.get(searchPnr);
                        // Modified layout of the ticket display output
                        System.out.println("\n--- TICKET MATCH FOUND ---");
                        System.out.println("PNR Reference: " + searchPnr);
                        System.out.println("Passenger: " + details.get(0));
                        System.out.println("Train Service: " + details.get(1) + " - " + details.get(2));
                        System.out.println("Class Type: " + details.get(3) + " | Date: " + details.get(4));
                        System.out.println("Route: " + details.get(5) + " ---> " + details.get(6));
                        
                        System.out.print("\nType 'YES' to delete this reservation permanently: ");
                        if (inputScanner.nextLine().equalsIgnoreCase("YES")) { 
                            bookingRecords.remove(searchPnr); 
                            System.out.println("Success: PNR " + searchPnr + " has been cancelled."); 
                        } else {
                            System.out.println("Cancellation process cancelled."); 
                        }
                    } else {
                        System.out.println("Error: PNR not found in our database.");
                    }
                } 
                else if (userSelection == 3) { 
                    System.out.println("Exiting system. Have a safe journey!"); 
                    break; 
                }
                else {
                    System.out.println("Invalid input! Select numbers between 1 and 3.");
                }
            }
        } else {
            System.out.println("Authentication Failed! Incorrect credentials.");
        }
    }
}