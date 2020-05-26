package coords;

import coords.Coordinate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Coordinates {
    public static String toString(Coordinate[][] coordinates) {
        StringBuilder stringBuilder = new StringBuilder().append("[");
        for (int i = 0; i < coordinates.length; i++) {
            if(i!=0) stringBuilder.append(", ");
            stringBuilder.append(toString(coordinates[i]));
        }
        return stringBuilder.append("]").toString();
    }

    public static String toString(Collection<Coordinate> coordinates) {
        return toString(toArray(coordinates));
    }

    public static String toString(Coordinate[] coordinates) {
        return Arrays.toString(coordinates);
    }

    public static Coordinate[] toArray(Collection<Coordinate> coordinates) {
        Coordinate[] arrayCoordinates = new Coordinate[coordinates.size()];
        Iterator iterator = coordinates.iterator();
        for (int i = 0; i < arrayCoordinates.length && iterator.hasNext(); i++) {
            arrayCoordinates[i] = (Coordinate) iterator.next();
        }
        return arrayCoordinates;
    }
}
