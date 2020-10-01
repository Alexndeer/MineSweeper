package sweeper.model;

import sweeper.controller.Ranges;
import sweeper.controller.Box;
import sweeper.controller.Coord;

public class Flag {
    private Matrix flagMap;
    private int countClosedBoxes;

    public void start() {
        flagMap = new Matrix((Box.CLOSED));
        countClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    public Box get(Coord coord) {
        return flagMap.get(coord);
    }

    public void setOpenedBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countClosedBoxes--;
    }

    public void switchFlaggedBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED:
                setClosedBox(coord);
                break;
            case CLOSED:
                setFlaggedBox(coord);
                break;
        }
    }

    private void setClosedBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    void setFlaggedBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
    }

    public int getCountClosedBoxes() {
        return countClosedBoxes;
    }

    public void setBombedBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }


    public void setClosed(Coord coord) {
        if (flagMap.get(coord) == Box.CLOSED) {
            flagMap.set(coord, Box.OPENED);
        }

    }

    public void setSafeBox(Coord coord) {
        if (flagMap.get(coord) == Box.FLAGED) {
            flagMap.set(coord, Box.NOBOMB);
        }
    }

    public int getAroundCount(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (flagMap.get(around) == Box.FLAGED) {
                count++;
            }
        }
        return count;
    }

}
