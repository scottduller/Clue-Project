package clue.model.card;

public enum Room implements CardType {
    KITCHEN("Kitchen"),
    CONSERVATORY("Conservatory"),
    DINING_ROOM("Dining Room"),
    BALLROOM("Ballroom"),
    STUDY("Study"),
    HALL("Hall"),
    LOUNGE("Lounge"),
    LIBRARY("Library"),
    BILLIARD_ROOM("Billiard Room"),
    CELLAR("Cellar");

    private String name;

    Room(String name) {
        this.name = name;
    }

    public String getRoom() {
        return name;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                '}';
    }
}
