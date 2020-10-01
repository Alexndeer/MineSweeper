package sweeper.model;

import sweeper.controller.Ranges;
import sweeper.controller.Box;
import sweeper.controller.Coord;

public class Matrix {
    private Box[][] matrix;

    public Matrix(Box defaultField) {
        matrix = new Box[Ranges.getSize().x]
                [Ranges.getSize().y];
        for (Coord coord : Ranges.getAllCoords()) {
            matrix[coord.x][coord.y] = defaultField;
        }

    }

    public Box get(Coord coord) {
        if (Ranges.inRange(coord))
            return matrix[coord.x][coord.y];
        return null;
    }

    public void set(Coord coord, Box box) {
        if (Ranges.inRange(coord))
            matrix[coord.x][coord.y] = box;
    }
}
    