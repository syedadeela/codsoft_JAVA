import java.util.Random;
import java.util.Scanner;

public class UniqueNumberGuessingGame {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        Random randomGenerator = new Random();
        
        int lb = 1;
        int ub= 100;
        int MAttempts = 10;
        int totalScore = 0;

        System.out.println("Welcome to the Unique Number Guessing Challenge!");

        do {
            int secretNumber = randomGenerator.nextInt(ub - lb + 1) + lb;
            int attempts = 0;
            int userAttempt;

            System.out.println("\nI've chosen a number between " + lb + " and " + ub + ". Your task is to guess it!");

            while (true) {
                System.out.print("Enter your guess: ");
                userAttempt = inputScanner.nextInt();
                attempts++;

                if (userAttempt == secretNumber) {
                    System.out.println("Congratulations! You correctly guessed the number in " + attempts + " attempts.");
                    totalScore += maxAttempts - attempts + 1;
                    break;
                } else if (userAttempt < secretNumber) {
                    System.out.println("Too low! Give it another shot.");
                } else {
                    System.out.println("Too high! Try a lower number.");
                }

                if (attempts == maxAttempts) {
                    System.out.println("Sorry, you've exhausted your attempts. The correct number was: " + secretNumber);
                    break;
                }
            }

            System.out.print("Would you like to play again? (yes/no): ");
        } while (inputScanner.next().equalsIgnoreCase("yes"));

        System.out.println("Thanks for participating! Your total score is: " + totalScore);
        inputScanner.close();
    }
}
