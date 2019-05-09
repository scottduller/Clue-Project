package clue.model.board;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Door door = (Door) o;
        return Objects.equals(coordinate, door.coordinate) &&
                entryDirection == door.entryDirection;
    }



    @Override
    public int hashCode() {

        return Objects.hash(coordinate, entryDirection);
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
