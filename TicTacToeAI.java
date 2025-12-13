
import java.util.Scanner;

public class TicTacToeAI {

    static char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    static Scanner scanner = new Scanner(System.in);

    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String BLUE = "\u001B[34m";
    static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        System.out.println(CYAN + "Welcome to Tic-Tac-Toe!" + RESET);
        System.out.println("You are " + RED + "X" + RESET + ", AI is " + BLUE + "O" + RESET + ".");
        printBoard();

        while (true) {
            playerMove();
            printBoard();
            if (isWinner('X')) {
                System.out.println(RED + "Congratulations! You win!" + RESET);
                break;
            }
            if (isDraw()) {
                System.out.println(CYAN + "It's a draw!" + RESET);
                break;
            }

            System.out.println("AI is making a move...");
            aiMove();
            printBoard();
            if (isWinner('O')) {
                System.out.println(BLUE + "AI wins! Better luck next time." + RESET);
                break;
            }
            if (isDraw()) {
                System.out.println(CYAN + "It's a draw!" + RESET);
                break;
            }
        }
    }

    static void printBoard() {
        System.out.println();
        System.out.println(
                CYAN + " " + coloredChar(board[0]) + " | " + coloredChar(board[1]) + " | " + coloredChar(board[2]));
        System.out.println("---+---+---");
        System.out.println(" " + coloredChar(board[3]) + " | " + coloredChar(board[4]) + " | " + coloredChar(board[5]));
        System.out.println("---+---+---");
        System.out.println(
                " " + coloredChar(board[6]) + " | " + coloredChar(board[7]) + " | " + coloredChar(board[8]) + RESET);
        System.out.println();
    }

    static String coloredChar(char c) {
        if (c == 'X') {
            return RED + "X" + CYAN;
        }
        if (c == 'O') {
            return BLUE + "O" + CYAN;
        }
        return " ";
    }

    static void playerMove() {
        int move;
        while (true) {
            System.out.print("Enter your move (1-9): ");
            move = scanner.nextInt() - 1;
            if (move >= 0 && move < 9 && board[move] == ' ') {
                board[move] = 'X';
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    static void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int move = 0;

        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                int score = minimax(board, 0, false);
                board[i] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        board[move] = 'O';
    }

    static int minimax(char[] board, int depth, boolean isMaximizing) {
        if (isWinner('O')) {
            return 1;
        }
        if (isWinner('X')) {
            return -1;
        }
        if (isDraw()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'O';
                    int score = minimax(board, depth + 1, false);
                    board[i] = ' ';
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'X';
                    int score = minimax(board, depth + 1, true);
                    board[i] = ' ';
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    static boolean isWinner(char player) {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };
        for (int[] condition : winConditions) {
            if (board[condition[0]] == player
                    && board[condition[1]] == player
                    && board[condition[2]] == player) {
                return true;
            }
        }
        return false;
    }

    static boolean isDraw() {
        for (char c : board) {
            if (c == ' ') {
                return false;
            }
        }
        return true;
    }
}
