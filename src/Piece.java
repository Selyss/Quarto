public class Piece {
    int attributes; // Represented as a 4-bit integer (e.g., 1101 for tall, dark, solid, square)
    // 1st -> size
    // 2nd -> color
    // 4th -> hole
    // 3rd -> shape

    public Piece(int attributes) {
        this.attributes = attributes;
        System.out.println("Piece created with attributes: " + Integer.toBinaryString(attributes));

    }

    public boolean isTall() { return (attributes & 0b1000) != 0; }  // 2nd bit: 1 = tall, 0 = short
    public boolean isBlack() { return (attributes & 0b0100) != 0; }  // 1st bit: 1 = white, 0 = black
    public boolean hasHole() { return (attributes & 0b0010) != 0; } // 4th bit: 1 = hole, 0 = solid
    public boolean isRound() { return (attributes & 0b0001) != 0; } // 3rd bit: 1 = round, 0 = square

    public String getFormattedPiece() {
        String shape = isRound() ? "o" : "x";
        String size = isTall() ? shape.toUpperCase() : shape.toLowerCase();
        String color = isBlack() ? "\033[30m" : "\033[97m"; // Black or White
        String background = hasHole() ? "\033[45m" : "\033[46m"; // Magenta for hole, Cyan for solid
        return background + color + " " + size + " \033[0m";
    }

    public boolean sharesAttribute(Piece other) {
        return (this.attributes & other.attributes) != 0;
    }
}

// color -> color (black is black, white is white)
// size -> case (upper is tall, lower is short)
// shape -> o, x
// hole -> background (magenta hole, solid is cyan)
