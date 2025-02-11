import java.util.ArrayList;

public class Engine {
    private Board cloneBoard(Board board) {
        Board newBoard = new Board();
        for (int i = 0; i < 4; i++) {
            System.arraycopy(board.grid[i], 0, newBoard.grid[i], 0, 4);
        }
        newBoard.availablePieces = new ArrayList<>(board.availablePieces);
        return newBoard;
    }

    private int negamax(Board board, int alpha, int beta, int player) {
        if (board.getAvailableMoves().isEmpty()) return 0;
        int best = Integer.MIN_VALUE;
        for (int[] move : board.getAvailableMoves()) {
            for (Piece piece : board.availablePieces) {
                Board simulatedBoard = cloneBoard(board);
                simulatedBoard.grid[move[0]][move[1]] = piece;
                int score = -negamax(simulatedBoard, -beta, -alpha, -player);
                best = Math.max(best, score);
                alpha = Math.max(alpha, best);
                if (alpha >= beta) break;
            }
        }
        return best * player;
    }

    public int[] getBestMove(Board board) {
        int[] bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        for (int[] move : board.getAvailableMoves()) {
            for (Piece piece : board.availablePieces) {
                Board simulatedBoard = cloneBoard(board);
                simulatedBoard.grid[move[0]][move[1]] = piece;
                int score = -negamax(simulatedBoard, -Integer.MAX_VALUE, Integer.MAX_VALUE, -1);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
        }
        return bestMove;
    }
}
