package sweeper.controller;

import sweeper.controller.Box;
import sweeper.controller.Coord;
import sweeper.controller.GameState;
import sweeper.controller.Ranges;
import sweeper.model.Bomb;
import sweeper.model.Flag;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public GameState getState() {
        return state;
    }

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.INGAME;
    }

    public void start2(List<Coord> coords) {
        bomb.start2(coords);
        flag.start();
        state = GameState.INGAME;
    }

    public Box getBox(Coord coord) {
        if (flag.get(coord) == Box.OPENED) {
            return bomb.get(coord);
        } else {
            return flag.get(coord);
        }
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) return;
        openBox(coord);
        victory();
    }

    private void victory() {
        if (state == GameState.INGAME) {
            if (flag.getCountClosedBoxes() == bomb.getTotalBombs()) {
                state = GameState.VICTORY;
            }
        }
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED:
                setOpenedNumber(coord);
                return;
            case FLAGED:
                return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        openBoxAround(coord);
                        return;
                    case BOMB:
                        openBombs(coord);
                        return;
                    default:
                        flag.setOpenedBox(coord);
                        return;
                }
        }
    }

    private void setOpenedNumber(Coord coord) {
        if (bomb.get(coord) != Box.BOMB) {
            if (flag.getAroundCount(coord) == bomb.get(coord).getNumber()) ;
            for (Coord around : Ranges.getCoordsAround(coord)) {
                if (flag.get(around) == Box.BOMB) {
                    openBox(around);
                }
            }
        }
    }


    private void openBombs(Coord bombs) {
        state = GameState.EXPLODED;
        flag.setBombedBox(bombs);
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB) {
                flag.setClosed(coord);
            } else
                flag.setSafeBox(coord);
        }
    }

    private void openBoxAround(Coord coord) {
        flag.setOpenedBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord)) {
            openBox(around);
        }
    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) return;
        flag.switchFlaggedBox(coord);
    }

    private boolean gameOver() {
        if (state == GameState.INGAME) {
            return false;
        }
        start();
        return true;
    }

    public Bomb getBomb() {
        return bomb;
    }
}
