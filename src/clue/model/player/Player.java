package clue.model.player;

import clue.model.board.Coordinate;
import clue.model.card.CardType;
import clue.model.card.CharacterCard;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private final String name;
    private Coordinate coordinate;
    private CharacterCard character;
    private Boolean playing;
    private ArrayList<CardType> cardHand = new ArrayList<>();

    public Player(String name, String character, Boolean playing) {
        this.name = name;
        for (CharacterCard c : CharacterCard.values()) {
            if (c.toString() == character) {
                this.character = c;
                break;
            }
        }
        this.playing = playing;
    }


    public void addCard(CardType card) {
        cardHand.add(card);
    }

    public ArrayList<CardType> getCardHand() {
        return cardHand;
    }

    public void setCardHand(ArrayList<CardType> cardHand) {
        this.cardHand = cardHand;
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

    public void setCharacter(CharacterCard character) {
        this.character = character;
    }

    public Boolean getPlaying() {
        return playing;
    }

    public void setPlaying(Boolean playing) {
        this.playing = playing;
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
