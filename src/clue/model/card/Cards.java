package clue.model.card;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Cards {
    private final Queue<CardType> cards = new LinkedList<>();
    private final ArrayList<CardType> roomCards = new ArrayList<>();
    private final ArrayList<CardType> weaponCards = new ArrayList<>();
    private final ArrayList<CardType> characterCards = new ArrayList<>();
    private final ArrayList<CardType> cardsUsed = new ArrayList<>();
    private final ArrayList<CardType> roomCardsUsed = new ArrayList<>();
    private final ArrayList<CardType> weaponCardsUsed = new ArrayList<>();
    private final ArrayList<CardType> characterCardsUsed = new ArrayList<>();

    public Cards() {
        for (Room r : Room.values()) {
            cards.add(r);
            roomCards.add(r);
        }
        for (Weapon w : Weapon.values()) {
            cards.add(w);
            weaponCards.add(w);
        }
        for (CharacterCard c : CharacterCard.values()) {
            cards.add(c);
            characterCards.add(c);
        }
    }

    public void removeRoomCard(Room r) {
        if (getCards().contains(r) && getRoomCards().contains(r)) {
            cardsUsed.add(r);
            cards.remove(r);
            roomCardsUsed.add(r);
            roomCards.remove(r);
        }
    }

    public void removeWeaponCard(Weapon r) {
        if (getCards().contains(r) && getWeaponCards().contains(r)) {
            cardsUsed.add(r);
            cards.remove(r);
            weaponCardsUsed.add(r);
            weaponCards.remove(r);
        }
    }

    public void removeCharacterCard(CharacterCard r) {
        if (getCards().contains(r) && getCharacterCards().contains(r)) {
            cardsUsed.add(r);
            cards.remove(r);
            characterCardsUsed.add(r);
            characterCards.remove(r);
        }
    }

    public void shuffle() {
        Collections.shuffle(roomCards);
        Collections.shuffle(weaponCards);
        Collections.shuffle(characterCards);
        ArrayList<CardType> shuffleCards = new ArrayList<>(cards);
        Collections.shuffle(shuffleCards);
        cards.clear();
        cards.addAll(shuffleCards);
    }

    public Queue<CardType> getCards() {
        return cards;
    }

    public ArrayList<CardType> getRoomCards() {
        return roomCards;
    }

    public ArrayList<CardType> getWeaponCards() {
        return weaponCards;
    }

    public ArrayList<CardType> getCharacterCards() {
        return characterCards;
    }

    public ArrayList<CardType> getCardsUsed() {
        return cardsUsed;
    }

    public ArrayList<CardType> getRoomCardsUsed() {
        return roomCardsUsed;
    }

    public ArrayList<CardType> getWeaponCardsUsed() {
        return weaponCardsUsed;
    }

    public ArrayList<CardType> getCharacterCardsUsed() {
        return characterCardsUsed;
    }
}
