package clue.model.card;

/**
 * enum that contains the types of rooms
 */
public enum RoomType implements CardType {
    KITCHEN("Kitchen"),
    CONSERVATORY("Conservatory"),
    DINING_ROOM("Dining RoomType"),
    BALLROOM("Ballroom"),
    STUDY("Study"),
    HALL("Hall"),
    LOUNGE("Lounge"),
    LIBRARY("Library"),
    BILLIARD_ROOM("Billiard RoomType"),
    CELLAR("Cellar");

    private String name;

    RoomType(String name) {
        this.name = name;
    }

    public String getRoom() {
        return name;
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "name='" + name + '\'' +
                '}';
    }
}
