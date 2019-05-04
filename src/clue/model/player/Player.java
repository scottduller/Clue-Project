package clue.model.player;

import clue.model.board.Coordinate;
import clue.model.card.CharacterCard;

import java.util.Objects;

public class Player {
    private final String name;
    private Coordinate coordinate;
    private CharacterCard character;

    public Player(String name, String character) {
        this.name = name;
        for (CharacterCard c : CharacterCard.values()) {
            if (c.toString() == character) {
                this.character = c;
                break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getRow() {
        return coordinate.getRow();
    }

    public int getCol() {
        return coordinate.getCol();
    }

    public CharacterCard getCharacter() {
        return character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return character == player.character;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character);
    }
}
