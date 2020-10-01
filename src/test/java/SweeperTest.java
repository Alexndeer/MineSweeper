import org.junit.jupiter.api.Test;
import sweeper.controller.*;
import sweeper.controller.Box;
import sweeper.model.Matrix;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

class SweeperTest {
    View view;
    Matrix matrix;
    Coord coord;

    @Test
    void bombsPlace() throws IOException {
        String[] coords = new String[]{"0,0", "1,1", "5,5"};
        view = new View(3, coords);
        matrix = view.getGame().getBomb().getBombMap();
        assertSame(matrix.get(new Coord(0, 0)), Box.BOMB);
        assertSame(matrix.get(new Coord(1, 1)), Box.BOMB);
        assertSame(matrix.get(new Coord(5, 5)), Box.BOMB);
        assertNotSame(matrix.get(new Coord(5, 4)), Box.BOMB);
    }

    @Test
    void numsAround() throws IOException {
        String[] coords = new String[]{"1,1"};
        view = new View(1, coords);
        matrix = view.getGame().getBomb().getBombMap();
        List<Coord> coordList = Ranges.getCoordsAround(new Coord(1, 1));
        for (Coord coord : coordList) {
            assertSame(matrix.get(coord), Box.NUM1);
        }
    }

    @Test
    void incNums() throws IOException {
        String[] coords = new String[]{"1,1", "1,2"};
        view = new View(2, coords);
        matrix = view.getGame().getBomb().getBombMap();
        assertSame(matrix.get(new Coord(0, 1)), Box.NUM2);
        assertSame(matrix.get(new Coord(2, 1)), Box.NUM2);
    }

    @Test
    void victory() throws IOException {
        String[] coords = new String[]{"0,0", "8,8", "0,8", "8,0"};
        view = new View(4, coords);
        coord = new Coord(5, 5);
        view.getGame().pressLeftButton(coord);
        assertSame(view.getGame().getState(), GameState.VICTORY);
    }

    @Test
    void exploded() throws IOException {
        String[] coords = new String[]{"5,5"};
        view = new View(1, coords);
        coord = new Coord(5, 5);
        view.getGame().pressLeftButton(coord);
        assertSame(view.getGame().getState(), GameState.EXPLODED);
    }
}