package clueTest;

import clue.model.board.Coordinate;
import clue.model.board.Grid;
import clue.model.card.CharacterType;
import clue.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GridTest {

    private String testGridInit =
            "-------#--------P-------" + System.lineSeparator() +
                    "-------##------##-------" + System.lineSeparator() +
                    "-------##------##-------" + System.lineSeparator() +
                    "T-----D##------##-------" + System.lineSeparator() +
                    "-########D-----##-------" + System.lineSeparator() +
                    "P########------##D-----T" + System.lineSeparator() +
                    "------###--DD--########-" + System.lineSeparator() +
                    "-------################P" + System.lineSeparator() +
                    "------D##-----#########-" + System.lineSeparator() +
                    "-------##-----##-D------" + System.lineSeparator() +
                    "---D--###-----##--------" + System.lineSeparator() +
                    "-########-----##--------" + System.lineSeparator() +
                    "-D----###-----##D-------" + System.lineSeparator() +
                    "------###-----##--------" + System.lineSeparator() +
                    "------###-----##--------" + System.lineSeparator() +
                    "-----D#############-----" + System.lineSeparator() +
                    "------#################-" + System.lineSeparator() +
                    "-#######-D----D-########" + System.lineSeparator() +
                    "P#######--------##-D----" + System.lineSeparator() +
                    "-T--D###D------D##------" + System.lineSeparator() +
                    "------##--------##------" + System.lineSeparator() +
                    "------##--------##------" + System.lineSeparator() +
                    "------##--------##------" + System.lineSeparator() +
                    "-------###----###-T-----" + System.lineSeparator() +
                    "---------P----P---------" + System.lineSeparator();
    private Player[] p;
    private Grid g;

    @Before
    public void setUp() throws Exception {
        p = new Player[]{
                new Player("1", CharacterType.MISS_SCARLET.toString(), true), new Player("2", CharacterType.MRS_PEACOCK.toString(), true),
                new Player("3", CharacterType.COLONEL_MUSTARD.toString(), true), new Player("4", CharacterType.MRS_WHITE.toString(), true),
                new Player("4", CharacterType.MR_GREEN.toString(), true), new Player("6", CharacterType.PROFESSOR_PLUM.toString(), true)
        };
        g = new Grid(p, 6);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void initTest() {
        assertEquals(testGridInit, g.toString());
    }

    @Test
    public void playableTest() {
        Coordinate playable = new Coordinate(7, 18);
        Coordinate nonPlayable = new Coordinate(2, 2);
        assertTrue(g.playable(playable));
        assertFalse(g.playable(nonPlayable));
    }

    @Test
    public void distanceTest() {
        assertTrue(g.distance(3, new Coordinate(0, 0), new Coordinate(1, 1)));
        assertFalse(g.distance(3, new Coordinate(0, 0), new Coordinate(2, 2)));
    }

    @Test
    public void movePositionsTest() {
        int row = 7;
        int col = 18;

        g.getTile(new Coordinate(row, col)).setPlayer(p[0]);
        List<Coordinate> movePositions = g.movePositions(1, row, col);
        List<Coordinate> testPositions = new ArrayList<>();
        testPositions.add(new Coordinate(row - 1, col));
        testPositions.add(new Coordinate(row, col - 1));
        testPositions.add(new Coordinate(row, col + 1));
        testPositions.add(new Coordinate(row + 1, col));

        assertEquals(testPositions, movePositions);

        row = 0;
        col = 7;

        g.getTile(new Coordinate(row, col)).setPlayer(p[0]);
        movePositions = g.movePositions(2, row, col);
        testPositions = new ArrayList<>();
        testPositions.add(new Coordinate(row + 1, col));
        testPositions.add(new Coordinate(row + 1, col + 1));
        testPositions.add(new Coordinate(row + 2, col));

        assertEquals(testPositions, movePositions);

        row = 6;
        col = 17;

        g.getTile(new Coordinate(row, col)).setPlayer(p[0]);
        movePositions = g.movePositions(1, row, col);

        assertEquals(true, movePositions.contains(new Coordinate(5, 17)));

        row = 5;
        col = 16;

        g.getTile(new Coordinate(row, col)).setPlayer(p[0]);
        movePositions = g.movePositions(1, row, col);

        assertEquals(false, movePositions.contains(new Coordinate(5, 17)));
    }

    @Test
    public void teleportTest() {
        Coordinate c = new Coordinate(15, 11);

        assertEquals(true, g.teleport(p[0], c));
        assertEquals(true, g.getTile(c).hasPlayer());
        assertEquals(p[0], g.getTile(c).getPlayer());

        assertEquals(false, g.teleport(p[1], c));
        assertEquals(p[0], g.getTile(c).getPlayer());
    }

    @Test
    public void moveTest() {
        Coordinate c = new Coordinate(15, 11);

        g.teleport(p[0], new Coordinate(15, 14));

        assertEquals(true, g.move(p[0], c, 3));
        assertEquals(false, g.move(p[0], c, 2));
    }

    public void print(Object s) {
        System.out.println(s.toString());
    }
}