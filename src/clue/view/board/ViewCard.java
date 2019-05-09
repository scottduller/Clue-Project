package clue.view.board;

import clue.model.card.CardType;
import clue.model.card.CharacterType;
import clue.model.card.RoomType;
import clue.model.card.WeaponType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewCard extends ImageView {
    private final CardType cardType;

    public ViewCard(CardType cardType) {
        this.cardType = cardType;
        if (cardType instanceof RoomType) {
            switch ((RoomType)cardType) {
                case STUDY:
                    this.setImage(new Image("clue/view/image/cards/rooms/studyp.png"));
                    break;
                case LOUNGE:
                    this.setImage(new Image("clue/view/image/cards/rooms/loungep.png"));
                    break;
                case KITCHEN:
                    this.setImage(new Image("clue/view/image/cards/rooms/kitchenp.png"));
                    break;
                case CONSERVATORY:
                    this.setImage(new Image("clue/view/image/cards/rooms/conservatoryp.png"));
                    break;
                case HALL:
                    this.setImage(new Image("clue/view/image/cards/rooms/hallp.png"));
                    break;
                case LIBRARY:
                    this.setImage(new Image("clue/view/image/cards/rooms/libraryp.png"));
                    break;
                case BALLROOM:
                    this.setImage(new Image("clue/view/image/cards/rooms/ballroomp.png"));
                    break;
                case DINING_ROOM:
                    this.setImage(new Image("clue/view/image/cards/rooms/diningroomp.png"));
                    break;
                case BILLIARD_ROOM:
                    this.setImage(new Image("clue/view/image/cards/rooms/billiardroomp.png"));
                    break;
                case CELLAR:
                    this.setImage(new Image("clue/view/image/cards/rooms/cellarp.png"));
                    break;
            }
        } else if (cardType instanceof CharacterType) {
            switch ((CharacterType) cardType) {
                case MISS_SCARLET:
                    this.setImage(new Image("clue/view/image/cards/characters/missscarlettp.png"));
                    break;
                case MR_GREEN:
                    this.setImage(new Image("clue/view/image/cards/characters/revgreenp.png"));
                    break;
                case MRS_WHITE:
                    this.setImage(new Image("clue/view/image/cards/characters/mrswhitep.png"));
                    break;
                case MRS_PEACOCK:
                    this.setImage(new Image("clue/view/image/cards/characters/mrspeacockp.png"));
                    break;
                case PROFESSOR_PLUM:
                    this.setImage(new Image("clue/view/image/cards/characters/profplump.png"));
                    break;
                case COLONEL_MUSTARD:
                    this.setImage(new Image("clue/view/image/cards/characters/colmustardp.png"));
                    break;
            }
        } else {
            switch ((WeaponType) cardType) {
                case CANDLESTICK:
                    this.setImage(new Image("clue/view/image/cards/weapon/candlestickp.png"));
                    break;
                case ROPE:
                    this.setImage(new Image("clue/view/image/cards/weapon/ropep.png"));
                    break;
                case DAGGER:
                    this.setImage(new Image("clue/view/image/cards/weapon/daggerp.png"));
                    break;
                case SPANNER:
                    this.setImage(new Image("clue/view/image/cards/weapon/spannerp.png"));
                    break;
                case REVOLVER:
                    this.setImage(new Image("clue/view/image/cards/weapon/revolverp.png"));
                    break;
                case LEAD_PIPE:
                    this.setImage(new Image("clue/view/image/cards/weapon/leadpipep.png"));
                    break;
            }
        }
        this.setFitHeight(105);
        this.setFitWidth(75);
    }

    public CardType getCardType() {
        return cardType;
    }
}
