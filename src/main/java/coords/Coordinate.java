package coords;

import parser.BadAmountOfInputArgsException;
import parser.InputParser;

/** Unfortunate name
 * I've decided to avoid class name "Points"
 * because there already exist field int points in Piece class for amount of points given fitting (cutting) operation will yield
 * It is recommended to avoid confusion and mistakes
 */
public class Coordinate {
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
}
