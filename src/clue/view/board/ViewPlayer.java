package clue.view.board;

import clue.model.player.Player;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ViewPlayer extends Circle {
    Player player;

    public ViewPlayer(Player player) {
        super(10);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(5);
        this.player = player;
        this.setFill(player.getCharacter().getColour());
    }
}
