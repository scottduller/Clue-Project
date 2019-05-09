package clue.model.board;

import clue.model.card.RoomType;

public class Secret {
    private Coordinate location;
    private RoomType destination;

    Secret(Coordinate location, RoomType destination) {
        this.location = location;
        this.destination = destination;
    }

    public RoomType getDestination() {
        return destination;
    }

    public Coordinate getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "loc: " + location + " dest: " + destination;
    }
}
