package clue.view.board;

import clue.model.ClueText;
import clue.model.Command;
import clue.model.CommandWord;
import clue.model.board.Coordinate;
import clue.model.board.Tile;
import clue.view.Clue;
import javafx.scene.layout.StackPane;

public class ViewTile extends StackPane {
    private final Tile tile;
    private final Coordinate coordinate;

    public ViewTile(Tile tile, Coordinate coordinate) {
        this.tile = tile;
        this.coordinate = coordinate;

    }

    public Tile getTile() {
        return tile;
    }

    @Override
    public String toString() {
        return coordinate.toString();
    }
}
