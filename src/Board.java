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
}
