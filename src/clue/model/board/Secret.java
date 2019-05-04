package clue.model.board;

class Secret {
    private Coordinate location;
    private Coordinate destination;

    Secret(Coordinate location, Coordinate destination) {
        this.location = location;
        this.destination = destination;
    }

    Coordinate getDestination() {
        return destination;
    }

    Coordinate getLocation() {
        return location;
    }
}
