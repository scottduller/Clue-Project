package clue.model.board;

import clue.model.card.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static clue.model.card.Room.*;


public class Room {
    private static final HashMap<clue.model.card.Room, Coordinate[]> ROOMS = new HashMap<>();
    private static final HashMap<clue.model.card.Room, Door[]> DOORS = new HashMap<>();
    private static final HashMap<clue.model.card.Room, Secret> SECRETS = new HashMap<>();
    private static final HashMap<clue.model.card.Room, Coordinate> NON_PLAYERS = new HashMap<>();
    private static final HashMap<clue.model.card.Room, clue.model.card.Weapon> ROOM_WEAPON = new HashMap<>();
    private static final HashMap<clue.model.card.Room, Coordinate[]> ROOM_POSITIONS = new HashMap<>();

    Room() {
        ROOMS.put(BALLROOM, new Coordinate[]{new Coordinate(17, 8), new Coordinate(24, 15)});
        DOORS.put(BALLROOM, new Door[]{new Door(new Coordinate(17, 9), Door.DoorDirection.UP), new Door(new Coordinate(19, 8), Door.DoorDirection.LEFT),
                new Door(new Coordinate(17, 14), Door.DoorDirection.UP), new Door(new Coordinate(19, 15), Door.DoorDirection.RIGHT)});
        NON_PLAYERS.put(BALLROOM, new Coordinate(19, 11));
        ROOM_POSITIONS.put(BALLROOM, new Coordinate[]{new Coordinate(18, 9), new Coordinate(21, 14)});

        ROOMS.put(BILLIARD_ROOM, new Coordinate[]{new Coordinate(12, 0), new Coordinate(16, 5)});
        DOORS.put(BILLIARD_ROOM, new Door[]{new Door(new Coordinate(12, 1), Door.DoorDirection.UP), new Door(new Coordinate(15, 5), Door.DoorDirection.RIGHT)});
        NON_PLAYERS.put(BILLIARD_ROOM, new Coordinate(14, 2));
        ROOM_POSITIONS.put(BILLIARD_ROOM, new Coordinate[]{new Coordinate(18, 9), new Coordinate(21, 14)});

        ROOMS.put(CONSERVATORY, new Coordinate[]{new Coordinate(19, 0), new Coordinate(24, 5)});
        DOORS.put(CONSERVATORY, new Door[]{new Door(new Coordinate(19, 4), Door.DoorDirection.RIGHT)});
        SECRETS.put(CONSERVATORY, new Secret(new Coordinate(19, 1), new Coordinate(5, 17)));
        NON_PLAYERS.put(CONSERVATORY, new Coordinate(21, 2));
        ROOM_POSITIONS.put(CONSERVATORY, new Coordinate[]{new Coordinate(20, 1), new Coordinate(23, 4)});

        ROOMS.put(DINING_ROOM, new Coordinate[]{new Coordinate(9, 16), new Coordinate(15, 23)});
        DOORS.put(DINING_ROOM, new Door[]{new Door(new Coordinate(9, 17), Door.DoorDirection.UP), new Door(new Coordinate(12, 16), Door.DoorDirection.LEFT)});
        NON_PLAYERS.put(DINING_ROOM, new Coordinate(19, 11));
        ROOM_POSITIONS.put(DINING_ROOM, new Coordinate[]{new Coordinate(10, 17), new Coordinate(13, 22)});

        ROOMS.put(HALL, new Coordinate[]{new Coordinate(0, 9), new Coordinate(6, 14)});
        DOORS.put(HALL, new Door[]{new Door(new Coordinate(4, 9), Door.DoorDirection.LEFT), new Door(new Coordinate(6, 11), Door.DoorDirection.DOWN),
                new Door(new Coordinate(6, 12), Door.DoorDirection.DOWN)});
        NON_PLAYERS.put(HALL, new Coordinate(3, 11));
        ROOM_POSITIONS.put(HALL, new Coordinate[]{new Coordinate(1, 10), new Coordinate(5, 13)});

        ROOMS.put(KITCHEN, new Coordinate[]{new Coordinate(18, 18), new Coordinate(24, 23)});
        DOORS.put(KITCHEN, new Door[]{new Door(new Coordinate(18, 19), Door.DoorDirection.UP)});
        SECRETS.put(KITCHEN, new Secret(new Coordinate(23, 18), new Coordinate(3, 6)));
        NON_PLAYERS.put(KITCHEN, new Coordinate(21, 21));
        ROOM_POSITIONS.put(KITCHEN, new Coordinate[]{new Coordinate(19, 9), new Coordinate(23, 22)});

        ROOMS.put(LIBRARY, new Coordinate[]{new Coordinate(6, 0), new Coordinate(10, 6)});
        DOORS.put(LIBRARY, new Door[]{new Door(new Coordinate(8, 6), Door.DoorDirection.RIGHT), new Door(new Coordinate(10, 3), Door.DoorDirection.DOWN)});
        NON_PLAYERS.put(LIBRARY, new Coordinate(8, 2));
        ROOM_POSITIONS.put(LIBRARY, new Coordinate[]{new Coordinate(7, 1), new Coordinate(9, 5)});

        ROOMS.put(LOUNGE, new Coordinate[]{new Coordinate(0, 17), new Coordinate(5, 23)});
        DOORS.put(LOUNGE, new Door[]{new Door(new Coordinate(5, 17), Door.DoorDirection.DOWN)});
        SECRETS.put(LOUNGE, new Secret(new Coordinate(5, 23), new Coordinate(19, 4)));
        NON_PLAYERS.put(LOUNGE, new Coordinate(2, 12));
        ROOM_POSITIONS.put(LOUNGE, new Coordinate[]{new Coordinate(1, 18), new Coordinate(4, 22)});

        ROOMS.put(STUDY, new Coordinate[]{new Coordinate(0, 0), new Coordinate(3, 6)});
        DOORS.put(STUDY, new Door[]{new Door(new Coordinate(3, 6), Door.DoorDirection.DOWN)});
        SECRETS.put(STUDY, new Secret(new Coordinate(3, 0), new Coordinate(18, 19)));
        NON_PLAYERS.put(STUDY, new Coordinate(3, 2));
        ROOM_POSITIONS.put(STUDY, new Coordinate[]{new Coordinate(1, 1), new Coordinate(2, 5)});

        ROOMS.put(CELLAR, new Coordinate[]{new Coordinate(8, 9), new Coordinate(14, 13)});

        for (int i = 0; i < values().length-1; i++) {
            Coordinate[] coordinates = ROOM_POSITIONS.get(clue.model.card.Room.values()[i]);
            Coordinate init = coordinates[0];
            Coordinate fin = coordinates[1];
            ROOM_POSITIONS.remove(clue.model.card.Room.values()[i]);
            System.out.println(init + " " + fin + " " + (fin.getCol()-init.getCol()+1)*(fin.getRow()-init.getRow()+1));
            int PTR = 0;
            Coordinate[] positionCoordinates = new Coordinate[(fin.getCol()-init.getCol()+1)*(fin.getRow()-init.getRow()+1)];
            for (int row = init.getRow(); row <= fin.getRow(); row++) {
                for (int col = init.getCol(); col <= fin.getCol(); col++) {
                    positionCoordinates[PTR] = new Coordinate(row,col);
                    System.out.println(new Coordinate(row,col));
                    PTR++;
                }
            }

            ROOM_POSITIONS.put(clue.model.card.Room.values()[i], positionCoordinates);
        }
    }

    public static void addRoomWeapon() {
        Random r = new Random();
        ROOM_WEAPON.put(clue.model.card.Room.values()[r.nextInt(9)], Weapon.values()[r.nextInt(6)]);
    }

    public static HashMap<clue.model.card.Room, Coordinate[]> getRoomPositions() {
        return ROOM_POSITIONS;
    }

    public static HashMap<clue.model.card.Room, Coordinate[]> getROOMS() {
        return ROOMS;
    }

    public static HashMap<clue.model.card.Room, Door[]> getDOORS() {
        return DOORS;
    }

    public static HashMap<clue.model.card.Room, Secret> getSECRETS() {
        return SECRETS;
    }

    public static HashMap<clue.model.card.Room, Coordinate> getNonPlayers() {
        return NON_PLAYERS;
    }

    public static HashMap<clue.model.card.Room, Weapon> getRoomWeapon() {
        return ROOM_WEAPON;
    }

    public HashMap<clue.model.card.Room, Coordinate[]> getRooms() {
        return ROOMS;
    }

    public Coordinate getRoomTileCoord(clue.model.card.Room r, int row) {
        return this.getRooms().get(r)[row];
    }

    public HashMap<clue.model.card.Room, Door[]> getDoors() {
        return DOORS;
    }

    public Coordinate getDoorTileCoord(clue.model.card.Room r, int row) {
        return this.getDoors().get(r)[row].getCoordinate();
    }

    public HashMap<clue.model.card.Room, Secret> getSecret() {
        return SECRETS;
    }

    public Secret getSecretObj(clue.model.card.Room r) {
        return SECRETS.get(r);
    }

    public Coordinate getSecretLocation(clue.model.card.Room r) {
        return this.getSecret().get(r).getLocation();
    }

    public Coordinate getSecretDestination(clue.model.card.Room r) {
        return this.getSecret().get(r).getDestination();
    }
}
