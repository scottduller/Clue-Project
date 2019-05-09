package clue.model.board;

import clue.model.card.RoomType;

/**
 * This class initialises the trapdoor tunnel system between rooms
 */
public class Secret {
    private Coordinate location;
    private RoomType destination;

    /**
     * @param location      location of the trapdoor being entered
     * @param destination   the room the tunnel leads to
     */
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
