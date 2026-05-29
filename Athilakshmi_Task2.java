import javax.swing.JOptionPane;
import java.util.Random;

public class Athilakshmi_Task2 {
    public static void main(String[] args) {
        // Renamed tracking variables
        int grandTotalPoints = 0;
        int currentRoundNum = 1;
        boolean userWantsToPlay = true;
        Random rng = new Random();

        while (userWantsToPlay) {
            // Changed range logic setup representation
            int secretTargetNum = rng.nextInt(100) + 1;
            int currentTries = 0;
            int allowedMaxTries = 5;
            boolean matchIdentified = false;

            while (currentTries < allowedMaxTries && !matchIdentified) {
                // Modified input dialog text layouts
                String rawUserInput = JOptionPane.showInputDialog(
                    null, 
                    "Trial: " + (currentTries + 1) + " of " + allowedMaxTries + "\nTake a guess (1-100):", 
                    "Number Guessing Portal", 
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (rawUserInput == null) return; // Terminate if user exits window
                
                try {
                    int evaluatedGuess = Integer.parseInt(rawUserInput);
                    currentTries++;
                    
                    if (evaluatedGuess == secretTargetNum) {
                        // Modified mathematical writing format for points calculation
                        int roundBonus = (1 + allowedMaxTries - currentTries) * 20; 
                        grandTotalPoints += roundBonus;
                        
                        JOptionPane.showMessageDialog(
                            null, 
                            " Success! Match found in " + currentTries + " tries.\nRound Score: " + roundBonus + "\nAccumulated Points: " + grandTotalPoints, 
                            "Congratulations!", 
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        matchIdentified = true;
                    } else {
                        // Modified direction notification strings
                        String clueMessage = (evaluatedGuess < secretTargetNum) ? "Go Higher" : "Go Lower";
                        JOptionPane.showMessageDialog(
                            null, 
                            "Incorrect value! Clue: " + clueMessage, 
                            "Helpful Clue", 
                            JOptionPane.WARNING_MESSAGE
                        );
                    }
                } catch (NumberFormatException errorException) {
                    JOptionPane.showMessageDialog(
                        null, 
                        "Error: Please type a legitimate whole number.", 
                        "Input Error Detected", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            
            if (!matchIdentified) {
                JOptionPane.showMessageDialog(
                    null, 
                    "No more trials left! The hidden value was: " + secretTargetNum + "\nYour Points: " + grandTotalPoints, 
                    "Round Over", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
            
            // Modified text for confirmation box
            int promptResponse = JOptionPane.showConfirmDialog(
                null, 
                "Proceed to the next match level?", 
                "Next Round Confirmation", 
                JOptionPane.YES_NO_OPTION
            );
            
            userWantsToPlay = (promptResponse == JOptionPane.YES_OPTION);
            if (userWantsToPlay) {
                currentRoundNum++;
            }
        }
        
        JOptionPane.showMessageDialog(
            null, 
            "Appreciate your time!\nYour Ultimate Score: " + grandTotalPoints, 
            "Summary Results", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}