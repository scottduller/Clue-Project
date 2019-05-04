package clue.model.board;

public class Door {
    private Coordinate coordinate;
    private DoorDirection entryDirection;

    public Door(Coordinate coordinate, DoorDirection entryDirection) {
        this.coordinate = coordinate;
        this.entryDirection = entryDirection;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public DoorDirection getEntryDirection() {
        return entryDirection;
    }

    public void setEntryDirection(DoorDirection entryDirection) {
        this.entryDirection = entryDirection;
    }

    @Override
    public String toString() {
        return entryDirection.toString();
    }

    enum DoorDirection {
        UP("up"), DOWN("down"), LEFT("left"), RIGHT("right");

        private String s;

        DoorDirection(String s) {
            this.s = s;
        }

        @Override
        public String toString() {
            return s;
        }
    }
}
