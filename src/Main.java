import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Board board = new Board();
        Engine engine = new Engine();
        boolean playerTurn = true;
        boolean gameOver = false;

        while (!gameOver) {
            board.printBoard();

            if (playerTurn) {
                System.out.println("Enter row and column (0-3) seperated by space:");
                int row = in.nextInt();
                int col = in.nextInt();

                System.out.println("Choose a piece (0-15):");
                int pieceId = in.nextInt();

                Piece piece = new Piece(pieceId);
                board.grid[row][col] = piece;
                board.availablePieces.remove(piece);

                if (board.isWinningMove(row, col, piece)) {
                    board.printBoard();
                    System.out.println("Player Wins!");
                    gameOver = true;
                }
            } else {
                System.out.println("Engine is thinking...");
                int[] bestMove = engine.getBestMove(board);
                Piece enginePiece = board.availablePieces.get(0);
                board.grid[bestMove[0]][bestMove[1]] = enginePiece;
                board.availablePieces.remove(enginePiece);

                System.out.printf("Engine placed piece %d at (%d, %d)\n", enginePiece.attributes, bestMove[0], bestMove[1]);

                if (board.isWinningMove(bestMove[0], bestMove[1], enginePiece)) {
                    board.printBoard();
                    System.out.println("Engine Wins!");
                    gameOver = true;
                }
            }
            playerTurn = !playerTurn;
        }
        in.close();
    }
}
