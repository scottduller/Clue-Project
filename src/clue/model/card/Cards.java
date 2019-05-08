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
        for (int i = 0; i < 9; i++) {
            cards.add(Room.values()[i]);
            roomCards.add(Room.values()[i]);
        }
        for (Weapon w : Weapon.values()) {
            cards.add(w);
            weaponCards.add(w);
        }
        for (CharacterCard c : CharacterCard.values()) {
            cards.add(c);
            characterCards.add(c);
        }
        System.out.print(cards);
        shuffle();
    }

    public Room removeRoomCard(Room r) {
        if (getCards().contains(r) && getRoomCards().contains(r)) {
            cardsUsed.add(r);
            cards.remove(r);
            roomCardsUsed.add(r);
            roomCards.remove(r);

        }
        return r;
    }

    public Weapon removeWeaponCard(Weapon r) {
        if (getCards().contains(r) && getWeaponCards().contains(r)) {
            cardsUsed.add(r);
            cards.remove(r);
            weaponCardsUsed.add(r);
            weaponCards.remove(r);
        }
        return r;
    }

    public CharacterCard removeCharacterCard(CharacterCard r) {
        if (getCards().contains(r) && getCharacterCards().contains(r)) {
            cardsUsed.add(r);
            cards.remove(r);
            characterCardsUsed.add(r);
            characterCards.remove(r);
        }
        return r;
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
