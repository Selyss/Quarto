public class Piece {
    int attributes;

    public Piece(int attributes) {
        this.attributes = attributes;
    }

    public boolean sharesAttribute(Piece other) {
        return (this.attributes & other.attributes) != 0;
    }
}
