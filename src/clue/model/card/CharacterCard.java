package clue.model.card;


import javafx.scene.paint.Color;

public enum CharacterCard implements CardType {
    COLONEL_MUSTARD("Colonel Mustard", Color.YELLOW),
    MISS_SCARLET("Miss Scarlet", Color.CRIMSON),
    PROFESSOR_PLUM("Professor Plum", Color.BLUEVIOLET),
    MR_GREEN("Rev. Green", Color.SEAGREEN),
    MRS_WHITE("Mrs. White", Color.WHITE),
    MRS_PEACOCK("Mrs. Peacock", Color.BLUE);

    private String name;
    private Color colour;

    CharacterCard(String name, Color colour) {
        this.name = name;
        this.colour = colour;
    }

    public String getCharacter() {
        return name;
    }

    public Color getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return name;
    }
}
