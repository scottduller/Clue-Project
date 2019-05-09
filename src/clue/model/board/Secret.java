package clue.model.board;

public class Secret {
    private Coordinate location;
    private Coordinate destination;

    Secret(Coordinate location, Coordinate destination) {
        this.location = location;
        this.destination = destination;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public Coordinate getLocation() {
        return location;
    }
}
