package clue.view.board;

import clue.model.board.Coordinate;
import clue.model.board.Tile;
import javafx.scene.layout.StackPane;

public class ViewTile extends StackPane {
    private final Tile tile;
    private final Coordinate coordinate;

    public ViewTile(Tile tile, Coordinate coordinate) {
        this.tile = tile;
        this.coordinate = coordinate;
    }

    private Tile getTile() {
        return tile;
    }
}
