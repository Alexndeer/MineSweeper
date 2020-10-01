package sweeper.model;

import sweeper.controller.Box;
import sweeper.controller.Coord;
import sweeper.controller.Ranges;

import java.util.List;

public class Bomb {
    private Matrix bombMap;
    private int total;

    public Bomb(int total) {
        this.total = total;
        fixBombsCount();
    }

    public void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < total; i++) {
            placeBomb();
        }
    }

    public void start2(List<Coord> coords) {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < total; i++) {
            placeBomb(coords.get(i));
        }
    }

    public Box get(Coord coord) {
        return bombMap.get(coord);
    }

    private void fixBombsCount() {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (total > maxBombs)
            total = maxBombs;
    }

    public void placeBomb(Coord coords) {
        Coord coord = coords;
        bombMap.set(coord, Box.BOMB);
        incNumbersAroundBomb(coord);
    }

    private void placeBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord)) {
                continue;
            }
            bombMap.set(coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }

    }

    private void incNumbersAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (Box.BOMB != bombMap.get(around)) {
                bombMap.set(around, bombMap.get(around).getNextNumber());
            }
        }
    }

    public Matrix getBombMap() {
        return bombMap;
    }

    public int getTotalBombs() {
        return total;
    }
}
