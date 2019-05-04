package clue.model.card;

public class Card {
    private final Class cardType;
    private final CardType card;

    Card(CardType card) {
        this.cardType = card.getClass();
        this.card = card;
    }

    public Class getCardType() {
        return cardType;
    }

    public CardType getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardType=" + cardType +
                ", card=" + card +
                '}';
    }
}
