import java.util.ArrayList;
import java.util.List;

public class Engine {

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
        for (int[] move : getOrderedMoves(board)) {
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
//        if (board.isWinningState()) return player * 1000; // TODO: implement
        // NOTE: implement eval if the move would win
        if (board.checkLine(board.grid[0][0], board.grid[1][1], board.grid[2][2], board.grid[3][3])) {
            return 1000 * player;
        }

        int score = 0;
        for (int i = 0; i < 4; i++) {
            score += countThreats(board, i, player);
        }
        return score;
    }

    private int countThreats(Board board, int index, int player) {
        int score = 0;
        if (board.checkLine(board.grid[index][0], board.grid[index][1], board.grid[index][2], board.grid[index][3])) {
            score += 50; // good line
        }
        if (board.checkLine(board.grid[0][index], board.grid[1][index], board.grid[2][index], board.grid[3][index])) {
            score += 50;
        }
        return score * player;
    }


    public int[] getBestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;
        int depth = getDynamicDepth(board);

        for (int[] move : board.getAvailableMoves()) {
            for (Piece piece : board.availablePieces) {
                Board simulatedBoard = cloneBoard(board);
                simulatedBoard.grid[move[0]][move[1]] = piece;

                int score = -negamax(simulatedBoard, -Integer.MAX_VALUE, Integer.MAX_VALUE, -1, depth);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
        }
        return bestMove;
    }

    private int getDynamicDepth(Board board) {
        int remainingMoves = board.getAvailableMoves().size();
        if (remainingMoves > 10) return 3;
        if (remainingMoves > 6) return 4;
        return 5;
    }

    private List<int[]> getOrderedMoves(Board board) {
        List<int[]> moves = board.getAvailableMoves();
        moves.sort((a, b) -> evaluateMove(b) - evaluateMove(a));
        return moves;
    }

    private int evaluateMove(int[] move) {
        return (move[0] == 1 || move[0] == 2) && (move[1] == 1 || move[1] == 2) ? 10 : 5; // prioritize center moves
    }

}
