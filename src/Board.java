import java.util.ArrayList;
import java.util.List;

public class Board {
    Piece[][] grid = new Piece[4][4];
    List<Piece> availablePieces = new ArrayList<>();

    public Board() {
        for (int i = 0; i < 16; i++) {
            availablePieces.add(new Piece(i));
        }
    }

    private boolean checkLine(Piece a, Piece b, Piece c, Piece d) {
        if (a == null || b == null || c == null || d == null) return false;
        return (a.attributes & b.attributes & c.attributes & d.attributes) != 0;
    }

    public boolean isWinningMove(int row, int col, Piece piece) {
        grid[row][col] = piece;

        for (int i = 0; i < 4; i++) {
            if (checkLine(grid[i][0], grid[i][1], grid[i][2], grid[i][3]) ||
                    checkLine(grid[0][i], grid[1][i], grid[2][i], grid[3][i])) {
                return true;
            }
        }
        return checkLine(grid[0][0], grid[1][1], grid[2][2], grid[3][3]) ||
                checkLine(grid[0][3], grid[1][2], grid[2][1], grid[3][0]);
    }

    public List<int[]> getAvailableMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == null) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    public void printBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == null) {
                    System.out.print(" . ");
                } else {
                    System.out.printf("%02d  ", grid[i][j].attributes); // Print piece ID
                }
            }
            System.out.println();
        }
    }
}
