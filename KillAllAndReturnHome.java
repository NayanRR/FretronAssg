import java.util.Random;

// A Java program to solve the problem of killing all the soldiers and returning home
// https://en.wikipedia.org/wiki/Kill_all_and_return_home
// https://www.geeksforgeeks.org/killing-all-soldiers-and-returning-home/

// Time Complexity: O(2^(N^2))
// Space Complexity: O(N^2)
//

// Explore all possible moves:
// Move forward if the next cell is within bounds.
// Move left after killing a soldier (only if the current cell contains a soldier).
// If a valid move is found, recursively call solve() to explore further.
// If all soldiers are killed and the castle can move back to the start, return true.
// If no further moves are possible, backtrack by undoing the last move.
// If no valid move is found, return false.

//Google search-- How to solve the problem of killing all the soldiers and returning home
//Google searcg--How to approach and find the solution using recursion

//If going down and if we kill we have to move right
//If going up and if we kill we have to move left
//If going right and if we kill we have to move up
//If going left and if we kill we have to move down

public class KillAllAndReturnHome {

    static final int N = 5; // Board size
    static char[][] board = new char[N][N];
    static int startX, startY;

    public static void main(String[] args) {
        initializeBoard();
        System.out.println("Initial Board:");
        printBoard();

        if (solve(startX, startY)) {
            System.out.println("\nSolved: All soldiers killed and castle returned home.");
        } else {
            System.out.println("\nNo Solution: Could not kill all soldiers and return home.");
        }
        printBoard();
    }

    // Initialize the board with soldiers 'S' and castle 'C'
    static void initializeBoard() {
        Random rand = new Random();

        // Place soldiers randomly
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (rand.nextInt(4) == 0) { // 25% chance to place a soldier
                    board[i][j] = 'S';
                } else {
                    board[i][j] = '.';
                }
            }
        }

        // Place the castle randomly
        startX = rand.nextInt(N);
        startY = rand.nextInt(N);
        board[startX][startY] = 'C';
    }

    // Recursive backtracking function
    static boolean solve(int posX, int posY) {
        // Base case: Check if all soldiers are killed and castle returned to start position
        if (allSoldiersKilled() && posX == startX && posY == startY) {
            return true;
        }

        // Move forward
        if (canMoveForward(posX, posY)) {
            int newX = posX + 1; // Moving downwards
            moveCastle(posX, posY, newX, posY);
            if (solve(newX, posY)) {
                return true;
            }
            // Backtrack
            moveCastle(newX, posY, posX, posY);
        }

        // Move left after killing a soldier
        if (canKillSoldier(posX, posY)) {
            int newY = posY - 1; // Move left
            char temp = board[posX][newY]; // Store soldier for backtracking
            killSoldier(posX, newY);
            moveCastle(posX, posY, posX, newY);
            if (solve(posX, newY)) {
                return true;
            }
            // Backtrack
            moveCastle(posX, newY, posX, posY);
            restoreSoldier(posX, newY, temp);
        }

        return false; // No solution found
    }

    // Check if castle can move forward
    static boolean canMoveForward(int x, int y) {
        return (x + 1 < N);
    }

    // Check if there is a soldier to kill
    static boolean canKillSoldier(int x, int y) {
        return (y - 1 >= 0 && board[x][y - 1] == 'S');
    }

    // Move the castle
    static void moveCastle(int oldX, int oldY, int newX, int newY) {
        board[oldX][oldY] = '.';
        board[newX][newY] = 'C';
    }

    // Kill the soldier
    static void killSoldier(int x, int y) {
        board[x][y] = '.';
    }

    // Restore soldier for backtracking
    static void restoreSoldier(int x, int y, char soldier) {
        board[x][y] = soldier;
    }

    // Check if all soldiers are killed
    static boolean allSoldiersKilled() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 'S') {
                    return false;
                }
            }
        }
        return true;
    }

    // Print the board
    static void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
