import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        boolean playAgain = true;
        int maxAttempts = 10;

        while (playAgain) {
            int numberToGuess = random.nextInt(100) + 1;
            int numberOfTries = 0;
            boolean win = false;
            int score = 0;

            System.out.println("Welcome to Guess the Number!");
            System.out.println("I have randomly chosen a number between 1 and 100.");
            System.out.println("Try to guess it. You have " + maxAttempts + " attempts.");

            while (numberOfTries < maxAttempts && !win) {
                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();
                numberOfTries++;

                if (guess == numberToGuess) {
                    win = true;
                    score = 100 - (numberOfTries * 10);
                    System.out.println("Congratulations! You guessed the number in " + numberOfTries + " tries.");
                } else if (guess < numberToGuess) {
                    System.out.println("Your guess is too low.");
                } else {
                    System.out.println("Your guess is too high.");
                }
            }

            if (!win) {
                System.out.println("You've used all attempts. The number was: " + numberToGuess);
            }

            System.out.println("Your score is: " + score);
            System.out.print("Do you want to play again? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");
        }
        
        System.out.println("Thank you for playing!");
        scanner.close();
    }
}
