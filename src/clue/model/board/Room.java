package clue.model.board;

import java.util.HashMap;


class Room {
    private HashMap<clue.model.card.Room, Coordinate[]> rooms = new HashMap<>();
    private HashMap<clue.model.card.Room, Door[]> doors = new HashMap<>();
    private HashMap<clue.model.card.Room, Secret> secrets = new HashMap<>();

    Room() {
        rooms.put(clue.model.card.Room.BALLROOM, new Coordinate[]{new Coordinate(17, 8), new Coordinate(24, 15)});
        doors.put(clue.model.card.Room.BALLROOM, new Door[]{new Door(new Coordinate(17, 9), Door.DoorDirection.UP), new Door(new Coordinate(19, 8), Door.DoorDirection.LEFT),
                new Door(new Coordinate(17, 14), Door.DoorDirection.UP), new Door(new Coordinate(19, 15), Door.DoorDirection.RIGHT)});

        rooms.put(clue.model.card.Room.BILLIARD_ROOM, new Coordinate[]{new Coordinate(12, 0), new Coordinate(16, 5)});
        doors.put(clue.model.card.Room.BILLIARD_ROOM, new Door[]{new Door(new Coordinate(12, 1), Door.DoorDirection.UP), new Door(new Coordinate(15, 5), Door.DoorDirection.RIGHT)});

        rooms.put(clue.model.card.Room.CONSERVATORY, new Coordinate[]{new Coordinate(19, 0), new Coordinate(24, 5)});
        doors.put(clue.model.card.Room.CONSERVATORY, new Door[]{new Door(new Coordinate(19, 4), Door.DoorDirection.RIGHT)});
        secrets.put(clue.model.card.Room.CONSERVATORY, new Secret(new Coordinate(19, 1), new Coordinate(5, 17)));

        rooms.put(clue.model.card.Room.DINING_ROOM, new Coordinate[]{new Coordinate(9, 16), new Coordinate(15, 23)});
        doors.put(clue.model.card.Room.DINING_ROOM, new Door[]{new Door(new Coordinate(9, 17), Door.DoorDirection.UP), new Door(new Coordinate(12, 16), Door.DoorDirection.LEFT)});

        rooms.put(clue.model.card.Room.HALL, new Coordinate[]{new Coordinate(0, 9), new Coordinate(6, 14)});
        doors.put(clue.model.card.Room.HALL, new Door[]{new Door(new Coordinate(4, 9), Door.DoorDirection.LEFT), new Door(new Coordinate(6, 11), Door.DoorDirection.DOWN),
                new Door(new Coordinate(6, 12), Door.DoorDirection.DOWN)});

        rooms.put(clue.model.card.Room.KITCHEN, new Coordinate[]{new Coordinate(18, 18), new Coordinate(24, 23)});
        doors.put(clue.model.card.Room.KITCHEN, new Door[]{new Door(new Coordinate(18, 19), Door.DoorDirection.UP)});
        secrets.put(clue.model.card.Room.KITCHEN, new Secret(new Coordinate(23, 18), new Coordinate(3, 6)));

        rooms.put(clue.model.card.Room.LIBRARY, new Coordinate[]{new Coordinate(6, 0), new Coordinate(10, 6)});
        doors.put(clue.model.card.Room.LIBRARY, new Door[]{new Door(new Coordinate(8, 6), Door.DoorDirection.RIGHT), new Door(new Coordinate(10, 3), Door.DoorDirection.DOWN)});

        rooms.put(clue.model.card.Room.LOUNGE, new Coordinate[]{new Coordinate(0, 17), new Coordinate(5, 23)});
        doors.put(clue.model.card.Room.LOUNGE, new Door[]{new Door(new Coordinate(5, 17), Door.DoorDirection.DOWN)});
        secrets.put(clue.model.card.Room.LOUNGE, new Secret(new Coordinate(5, 23), new Coordinate(19, 4)));

        rooms.put(clue.model.card.Room.STUDY, new Coordinate[]{new Coordinate(0, 0), new Coordinate(3, 6)});
        doors.put(clue.model.card.Room.STUDY, new Door[]{new Door(new Coordinate(3, 6), Door.DoorDirection.DOWN)});
        secrets.put(clue.model.card.Room.STUDY, new Secret(new Coordinate(3, 0), new Coordinate(18, 19)));

        rooms.put(clue.model.card.Room.CELLAR, new Coordinate[]{new Coordinate(8, 9), new Coordinate(14, 13)});
    }

    HashMap<clue.model.card.Room, Coordinate[]> getRooms() {
        return rooms;
    }

    Coordinate getRoomTileCoord(clue.model.card.Room r, int row) {
        return this.getRooms().get(r)[row];
    }

    HashMap<clue.model.card.Room, Door[]> getDoors() {
        return doors;
    }

    Coordinate getDoorTileCoord(clue.model.card.Room r, int row) {
        return this.getDoors().get(r)[row].getCoordinate();
    }

    HashMap<clue.model.card.Room, Secret> getSecret() {
        return secrets;
    }

    Secret getSecretObj(clue.model.card.Room r) {
        return secrets.get(r);
    }

    Coordinate getSecretLocation(clue.model.card.Room r) {
        return this.getSecret().get(r).getLocation();
    }

    Coordinate getSecretDestination(clue.model.card.Room r) {
        return this.getSecret().get(r).getDestination();
    }
}
