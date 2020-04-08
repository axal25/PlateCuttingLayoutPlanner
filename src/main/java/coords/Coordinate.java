package coords;

import coords.exceptions.BadCoordinateValueException;

/** Unfortunate name
 * I've decided to avoid class name "Points"
 * because there already exist field int points in Piece class for amount of points given fitting (cutting) operation will yield
 * It is recommended to avoid confusion and mistakes
 */
public class Coordinate implements Comparable<Coordinate> {
    private int x, y;

    private Coordinate() {
        throw new SecurityException("Not for use");
    }

    public Coordinate(int x, int y) throws BadCoordinateValueException {
        final String functionName = "<init>";
        if(x < 0) throw new BadCoordinateValueException(
                this.getClass().getSimpleName(),
                functionName,
                "x",
                x
        );
        if(y < 0) throw new BadCoordinateValueException(
                this.getClass().getSimpleName(),
                functionName,
                "y",
                y
        );
        this.x = x;
        this.y = y;
    }

    private Coordinate(Coordinate template) throws BadCoordinateValueException {
        this(template.getX(), template.getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isBetweenCoords(Coordinate coord1, Coordinate coord2) {
        int vectorXThisTo1 = this.x - coord1.x;
        int vectorYThisTo1 = this.y - coord1.y;

        int vectorX2to1 = coord2.x - coord1.x;
        int vectorY2to1 = coord2.y - coord1.y;

        int crossProductOfVectors = vectorXThisTo1 * vectorY2to1 - vectorYThisTo1 * vectorX2to1;
        if(crossProductOfVectors != 0) return false;

        // is x part of vector (2 to 1) bigger or equal than y part of the same vector
        // is more horizontal than vertical
        if(Math.abs(vectorX2to1) >= Math.abs(vectorY2to1)) {
            if(vectorX2to1 > 0) return coord1.getX() < this.getX() && this.getX() < coord2.getX();
            else return coord2.getX() < this.getX() && this.getX() < coord1.getX();
        } else {
            if(vectorY2to1 > 0) return coord1.getY() < this.getY() && this.getY() < coord2.getY();
            else return coord2.getY() < this.getY() && this.getY() < coord1.getY();
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("x=").append(this.x)
                .append(", y=").append(this.y)
                .append("}").toString();
    }

    @Override
    public boolean equals(Object unKnownTypeOther) {
        if(this == unKnownTypeOther) return true;
        if(unKnownTypeOther == null || this.getClass() != unKnownTypeOther.getClass()) return false;
        Coordinate other = (Coordinate) unKnownTypeOther;
        if(this.getX() == other.getX() && this.getY() == other.getY()) return true;
        return false;
    }

    @Override
    public int hashCode() {
        final int primeSeed =  67;
        int hashCode = primeSeed + this.getX();
        hashCode = primeSeed * hashCode + this.getY();
        return hashCode;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            try {
                return new Coordinate(this);
            } catch (BadCoordinateValueException ex) {
                throw new CloneNotSupportedException(
                        new StringBuilder()
                                .append("Could not copy object of class ")
                                .append(this.getClass().getSimpleName())
                                .append(". Object to be copied: ")
                                .append(this.toString())
                                .append("\nCause by: ")
                                .append(ex.getMessage())
                                .toString()
                );
            }
        }
    }


    @Override
    public int compareTo(Coordinate other) {
        return ( this.getX() != other.getX() ) ? this.getX() - other.getX() : this.getY() - other.getY();
    }
}
