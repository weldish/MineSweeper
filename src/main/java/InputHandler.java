import java.util.Scanner;
import java.util.InputMismatchException;
public class InputHandler {

    private Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    // This validates the userinput
    public UserInput getUserInput(int numRows, int numCols) {
        while (true) {
            System.out.println("Enter ('r' to reveal, 'f' to flag) followed by row and column numbers separated by a space: ");
            scanner.nextLine(); // getting rid any newlines from before
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+"); // Split by one or more spaces

            if (parts.length != 3) {
                System.out.println("Error: You have entered invalid number of arguments.");
                continue;
            }
            String moveType = parts[0].toLowerCase();
            if (!moveType.equals("r") && !moveType.equals("f")) {
                System.out.println("Error: Invalid move type. Use 'r' to reveal or 'f' to flag.");
                continue;
            }

            try {
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);

                if (row < 0 || row >= numRows|| col < 0 || col >= numCols) {
                    System.out.println("Error: Row or column out of bounds.");
                    continue;
                }

                return new UserInput(moveType, row, col);
            } catch (NumberFormatException e) {
                System.out.println("Error: Row and column must be integers.");
            }

        }

    }

    // this validates the difficulty level
    public String getDifficultyLevel() {
        while (true) {
            System.out.print("Select difficulty level (1 for easy, 2 for medium, 3 for hard): ");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
                scanner.next(); // getting rid of the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    return "easy";
                case 2:
                    return "medium";
                case 3:
                    return "hard";
                default:
                    System.out.println("Invalid choice. Please enter a number (1, 2, or 3).");
                    break;
            }
        }
    }

}

