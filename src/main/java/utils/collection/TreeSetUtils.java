package utils.collection;

import coords.Coordinate;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetUtils {
    public static Coordinate[] treeSetToArray(TreeSet<Coordinate> treeCoordinates) {
        Coordinate[] arrayCoordinates = new Coordinate[treeCoordinates.size()];
        Iterator iterator = treeCoordinates.iterator();
        for (int i = 0; i < arrayCoordinates.length && iterator.hasNext(); i++) {
            arrayCoordinates[i] = (Coordinate) iterator.next();
        }
        return arrayCoordinates;
    }
}
