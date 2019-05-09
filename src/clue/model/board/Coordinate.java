package clue.model.board;

/**
 * This class is used to show a coordinate on the main grid.
 * This includes a row and a column value
 */
public class Coordinate {
    private final int col;
    private final int row;

    public Coordinate(final int row, final int col) {
        this.col = col;
        this.row = row;
    }

    public String toString() {
        return String.format("%d/%d", this.row, this.col);
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(final Object o) {
        return this.equals((Coordinate) o);
    }

    private boolean equals(final Coordinate c) {
        return this.col == c.col && this.row == c.row;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}