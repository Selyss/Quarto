import java.util.ArrayList;

public class Engine {
    private static final int MAX_DEPTH = 3;

    private Board cloneBoard(Board board) {
        Board newBoard = new Board();
        for (int i = 0; i < 4; i++) {
            System.arraycopy(board.grid[i], 0, newBoard.grid[i], 0, 4);
        }
        newBoard.availablePieces = new ArrayList<>(board.availablePieces);
        return newBoard;
    }

    private int negamax(Board board, int alpha, int beta, int player, int depth) {
        if (depth == 0 || board.getAvailableMoves().isEmpty()) {
            return evaluate(board, player); // Use evaluation function at depth limit
        }

        int best = Integer.MIN_VALUE;
        for (int[] move : board.getAvailableMoves()) {
            for (Piece piece : board.availablePieces) {
                Board simulatedBoard = cloneBoard(board);
                simulatedBoard.grid[move[0]][move[1]] = piece;

                int score = -negamax(simulatedBoard, -beta, -alpha, -player, depth - 1);
                best = Math.max(best, score);
                alpha = Math.max(alpha, best);
                if (alpha >= beta) break; // prune
            }
        }
        return best * player;
    }

    private int evaluate(Board board, int player) {
        // Basic heuristic: count potential winning lines for the player
        int score = 0;
        for (int i = 0; i < 4; i++) {
            score += countPotentialLines(board, i, player);
        }
        return score;
    }


    private int countPotentialLines(Board board, int index, int player) {
        // Simple scoring system (more sophisticated evaluation can be added)
        int score = 0;
        if (board.checkLine(board.grid[index][0], board.grid[index][1], board.grid[index][2], board.grid[index][3])) {
            score += 10;
        }
        if (board.checkLine(board.grid[0][index], board.grid[1][index], board.grid[2][index], board.grid[3][index])) {
            score += 10;
        }
        return score * player;
    }

    public int[] getBestMove(Board board) {
        int[] bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        for (int[] move : board.getAvailableMoves()) {
            for (Piece piece : board.availablePieces) {
                Board simulatedBoard = cloneBoard(board);
                simulatedBoard.grid[move[0]][move[1]] = piece;
                int score = -negamax(simulatedBoard, -Integer.MAX_VALUE, Integer.MAX_VALUE, -1, MAX_DEPTH);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
        }
        return bestMove;
    }
}
