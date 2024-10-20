import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Battle_ship {
    static final int SIZE = 5; // Size of the board
    static char[][] player1Board = new char[SIZE][SIZE];
    static char[][] player2Board = new char[SIZE][SIZE];
    static char[][] player1Guesses = new char[SIZE][SIZE];
    static char[][] player2Guesses = new char[SIZE][SIZE];

    // Queues to store the history of guesses
    static Queue<String> player1History = new LinkedList<>();
    static Queue<String> player2History = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize boards
        initializeBoard(player1Board);
        initializeBoard(player2Board);
        initializeBoard(player1Guesses);
        initializeBoard(player2Guesses);

        // Players place their ships
        System.out.println("Player 1, place your ships.");
        placeShips(player1Board, scanner);

        System.out.println("Player 2, place your ships.");
        placeShips(player2Board, scanner);

        // Play the game
        boolean gameOver = false;
        while (!gameOver) {
            // Player 1's turn to guess
            System.out.println("\nPlayer 1, it's your turn to guess!");
            System.out.println("Your guesses:");
            displayBoard(player1Guesses);
            gameOver = makeGuess(player1Guesses, player2Board, scanner, "Player 1", player1History);
            if (gameOver) break;
            displayGuessHistory(player1History, "Player 1");

            // Player 2's turn to guess
            System.out.println("\nPlayer 2, it's your turn to guess!");
            System.out.println("Your guesses:");
            displayBoard(player2Guesses);
            gameOver = makeGuess(player2Guesses, player1Board, scanner, "Player 2", player2History);
            displayGuessHistory(player2History, "Player 2");
        }

        // Display final boards after game is over
        System.out.println("\nGame Over! Final Boards:");
        System.out.println("Player 1's Board:");
        displayBoard(player1Board);
        System.out.println("Player 2's Board:");
        displayBoard(player2Board);

        scanner.close();
    }

    public static void initializeBoard(char[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = '~'; // Water
            }
        }
    }

    public static void displayBoard(char[][] board) {
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(" " + i + " ");
        }
        System.out.println();

        for (int row = 0; row < SIZE; row++) {
            System.out.print(row + " "); // Print row number
            for (int col = 0; col < SIZE; col++) {
                System.out.print("|" + board[row][col] + "|");
            }
            System.out.println();
        }
    }

    public static void placeShips(char[][] board, Scanner scanner) {
        int shipsToPlace = 3;
        for (int i = 0; i < shipsToPlace; i++) {
            boolean placed = false;
            while (!placed) {
                System.out.println("Enter coordinates for ship " + (i + 1) + " (row and column): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == '~') {
                    board[row][col] = 'S'; // Place ship
                    placed = true;
                    System.out.println("Ship placed at (" + row + ", " + col + ")");
                    displayBoard(board);
                } else {
                    System.out.println("Invalid position or ship already placed there. Try again.");
                }
            }
        }
    }

    public static boolean makeGuess(char[][] guessBoard, char[][] opponentBoard, Scanner scanner, String player, Queue<String> history) {
        boolean validGuess = false;
        while (!validGuess) {
            System.out.println("Enter your guess (row and column): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && guessBoard[row][col] == '~') {
                if (opponentBoard[row][col] == 'S') {
                    System.out.println(player + " hit a ship!");
                    guessBoard[row][col] = 'X'; // Hit
                    opponentBoard[row][col] = 'X';
                } else {
                    System.out.println(player + " missed.");
                    guessBoard[row][col] = 'O'; // Miss
                }
                history.add("(" + row + ", " + col + ")"); // Only store coordinates
                validGuess = true;
            } else {
                System.out.println("Invalid guess or already guessed. Try again.");
            }
        }

        return checkVictory(opponentBoard, player);
    }

    public static boolean checkVictory(char[][] board, String player) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 'S') {
                    return false;
                }
            }
        }
        System.out.println("Congratulations " + player + "! You have sunk all ships!");
        return true;
    }

    public static void displayGuessHistory(Queue<String> history, String player) {
        System.out.println("\n" + player + " Move History:");
        for (String move : history) {
            System.out.println(move);
        }
    }
}
