package clue.model.card;

import java.util.ArrayList;
import java.util.Collections;

public class Cards {
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> roomCards = new ArrayList<>(),
            weaponCards = new ArrayList<>(),
            characterCards = new ArrayList<>();

    public Cards() {
        Card currentCard;

        for (Room r : Room.values()) {
            currentCard = new Card(r);

            cards.add(currentCard);
            roomCards.add(currentCard);
        }
        for (Weapon w : Weapon.values()) {
            currentCard = new Card(w);

            cards.add(currentCard);
            weaponCards.add(currentCard);
        }
        for (CharacterCard c : CharacterCard.values()) {
            currentCard = new Card(c);

            cards.add(currentCard);
            characterCards.add(currentCard);
        }
    }

    private void shuffle() {
        Collections.shuffle(roomCards);
        Collections.shuffle(weaponCards);
        Collections.shuffle(characterCards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Card> getRoomCards() {
        return roomCards;
    }

    public ArrayList<Card> getWeaponCards() {
        return weaponCards;
    }

    public ArrayList<Card> getCharacterCards() {
        return characterCards;
    }
}
