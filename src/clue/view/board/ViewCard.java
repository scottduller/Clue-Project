package clue.view.board;

import clue.model.card.CardType;
import clue.model.card.CharacterCard;
import clue.model.card.Room;
import clue.model.card.Weapon;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewCard extends ImageView {
    private CardType cardType;
    private Image image;

    public ViewCard(Room cardType, boolean isDetectiveCard) {
        this.cardType = cardType;
        switch (cardType) {
            case STUDY:
                this.image = new Image("clue/view/image/cards/rooms/studyp.png");
                this.setImage(this.image);
                break;
            case LOUNGE:
                this.image = (new Image("clue/view/image/cards/rooms/loungep.png"));
                this.setImage(this.image);
                break;
            case KITCHEN:
                this.image = (new Image("clue/view/image/cards/rooms/kitchenp.png"));
                this.setImage(this.image);
                break;
            case CONSERVATORY:
                this.image = (new Image("clue/view/image/cards/rooms/conservatoryp.png"));
                this.setImage(this.image);
                break;
            case HALL:
                this.image = (new Image("clue/view/image/cards/rooms/hallp.png"));
                this.setImage(this.image);
                break;
            case LIBRARY:
                this.image = (new Image("clue/view/image/cards/rooms/libraryp.png"));
                this.setImage(this.image);
                break;
            case BALLROOM:
                this.image = (new Image("clue/view/image/cards/rooms/ballroomp.png"));
                this.setImage(this.image);
                break;
            case DINING_ROOM:
                this.image = (new Image("clue/view/image/cards/rooms/diningroomp.png"));
                this.setImage(this.image);
                break;
            case BILLIARD_ROOM:
                this.image = (new Image("clue/view/image/cards/rooms/billiardroomp.png"));
                this.setImage(this.image);
                break;
        }

        if (isDetectiveCard) {
            this.setFitHeight(70);
            this.setFitWidth(70);
            this.setOnMouseClicked(e -> {
                if (getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    setEffect(colorAdjust);
                } else {
                    setEffect(null);
                }
            });
        }
    }

    public ViewCard(Weapon cardType, boolean isDetectiveCard) {
        this.cardType = cardType;
        switch (cardType) {
            case DAGGER:
                this.image = (new Image("clue/view/image/cards/weapon/daggerp.png"));
                this.setImage(this.image);
                break;
            case CANDLESTICK:
                this.image = (new Image("clue/view/image/cards/weapon/candlestickp.png"));
                this.setImage(this.image);
                break;
            case REVOLVER:
                this.image = (new Image("clue/view/image/cards/weapon/revolverp.png"));
                this.setImage(this.image);
                break;
            case LEAD_PIPE:
                this.image = (new Image("clue/view/image/cards/weapon/leadpipep.png"));
                this.setImage(this.image);
                break;
            case ROPE:
                this.image = (new Image("clue/view/image/cards/weapon/ropep.png"));
                this.setImage(this.image);
                break;
            case SPANNER:
                this.image = (new Image("clue/view/image/cards/weapon/spannerp.png"));
                this.setImage(this.image);
                break;
        }

        if (isDetectiveCard) {
            this.setFitHeight(70);
            this.setFitWidth(70);
            this.setOnMouseClicked(e -> {
                if (getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    setEffect(colorAdjust);
                } else {
                    setEffect(null);
                }
            });
        }
    }

    public ViewCard(CharacterCard cardType, boolean isDetectiveCard) {
        this.cardType = cardType;
        switch (cardType) {
            case COLONEL_MUSTARD:
                this.image = (new Image("clue/view/image/cards/characters/colmustardp.png"));
                this.setImage(this.image);
                break;
            case MISS_SCARLET:
                this.image = (new Image("clue/view/image/cards/characters/missscarlettp.png"));
                this.setImage(this.image);
                break;
            case PROFESSOR_PLUM:
                this.image = (new Image("clue/view/image/cards/characters/profplump.png"));
                this.setImage(this.image);
                break;
            case MR_GREEN:
                this.image = (new Image("clue/view/image/cards/characters/revgreenp.png"));
                this.setImage(this.image);
                break;
            case MRS_WHITE:
                this.image = (new Image("clue/view/image/cards/characters/mrswhitep.png"));
                this.setImage(this.image);
                break;
            case MRS_PEACOCK:
                this.image = (new Image("clue/view/image/cards/characters/mrspeacockp.png"));
                this.setImage(this.image);
                break;
        }

        if (isDetectiveCard) {
            this.setFitHeight(70);
            this.setFitWidth(70);
            this.setOnMouseClicked(e -> {
                if (getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    setEffect(colorAdjust);
                } else {
                    setEffect(null);
                }
            });
        }
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return cardType.toString();
    }
}
