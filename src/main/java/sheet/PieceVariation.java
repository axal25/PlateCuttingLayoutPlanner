package sheet;

import coords.Coordinate;
import coords.RectangleCorners;
import coords.exceptions.BadCoordinateValueException;
import orientation.Orientation;
import solution.Solution;

import java.util.Objects;
import java.util.TreeSet;

public class PieceVariation implements Comparable<PieceVariation> {
    private final Piece piece;
    private Orientation orientation;
    private Coordinate northWesternCoord;

    public PieceVariation(Piece piece) throws CloneNotSupportedException {
        this.piece = (Piece) piece.clone();
        calculateOrientation();
        this.northWesternCoord = null;
    }

    private PieceVariation(PieceVariation template) throws CloneNotSupportedException {
        this.piece = (Piece) template.piece.clone();
        this.orientation = template.getOrientation();
        if (template.northWesternCoord != null)
            this.northWesternCoord = (Coordinate) template.northWesternCoord.clone();
        else this.northWesternCoord = null;
    }

    public Coordinate[] getCornersInsideOf(PieceVariation other) throws BadCoordinateValueException {
        TreeSet<Coordinate> thisCornersInsideOther = new TreeSet<>();
        RectangleCorners thisRectangleCorners = new RectangleCorners(this);
        RectangleCorners otherRectangleCorners = new RectangleCorners(other);
        return thisRectangleCorners.getCornersInsideOf(otherRectangleCorners);
    }

    public Coordinate[] getXNotOverlappingCornersOfBoth(PieceVariation other, int amountOfNotOverlappingCorners) throws BadCoordinateValueException {
        Coordinate[] xNotOverlappingCorners = new Coordinate[amountOfNotOverlappingCorners];
        Coordinate[] thisXNotOverlappingCorners = this.getXNotOverlappingCornersOfThisOnly(other, amountOfNotOverlappingCorners / 2);
        Coordinate[] otherXNotOverlappingCorners = other.getXNotOverlappingCornersOfThisOnly(this, amountOfNotOverlappingCorners / 2);
        for (int i = 0, j = 0, k = 0; i < thisXNotOverlappingCorners.length && j < otherXNotOverlappingCorners.length; i++, j++, k++) {
            xNotOverlappingCorners[k] = thisXNotOverlappingCorners[i];
            k++;
            xNotOverlappingCorners[k] = otherXNotOverlappingCorners[j];
        }
        return xNotOverlappingCorners;
    }

    public Coordinate[] getXNotOverlappingCornersOfThisOnly(PieceVariation other, int amountOfNotOverlappingCorners) throws BadCoordinateValueException {
        Coordinate[] thisCoords = new RectangleCorners(this).toCoordinateArray();
        Coordinate[] otherCoords = new RectangleCorners(other).toCoordinateArray();
        Coordinate[] xNotOverlappingCorners = new Coordinate[amountOfNotOverlappingCorners];
        for (int i = 0, j = 0; i < thisCoords.length && i < otherCoords.length; i++) {
            if (!thisCoords[i].equals(otherCoords[i])) {
                if (j >= amountOfNotOverlappingCorners)
                    throw new IndexOutOfBoundsException(
                            String.format(
                                    "%s%s",
                                    String.format("Not matching amount of expected not overlapping corners (%d)", amountOfNotOverlappingCorners),
                                    String.format(" to actual amount of not overlapping corners found so far (%d).", j)
                            )
                    );
                xNotOverlappingCorners[j] = thisCoords[i];
                j++;
            }
        }
        return xNotOverlappingCorners;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getArea() {
        return this.piece.getHeight() * this.piece.getWidth();
    }

    public Coordinate getNorthWesternCoord() {
        return northWesternCoord;
    }

    public void setNorthWesternCoord(Coordinate northWesternCoord) {
        this.northWesternCoord = northWesternCoord;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    private void calculateOrientation() {
        if (this.piece.getHeight() > this.piece.getWidth()) this.orientation = Orientation.V;
        else this.orientation = Orientation.H;
    }

    public void rotate() {
        if (this.piece.getWidth() == this.piece.getHeight()) {
            swapOrientation();
        } else {
            this.piece.rotate();
            calculateOrientation();
        }
    }

    private void swapOrientation() {
        if (this.orientation == Orientation.H) this.orientation = Orientation.V;
        else this.orientation = Orientation.H;
    }

    boolean isOverlapping(PieceVariation other) throws BadCoordinateValueException {
        RectangleCorners thisPVRectangleCorners = new RectangleCorners(this);
        RectangleCorners otherPVRectangleCorners = new RectangleCorners(other);
        return thisPVRectangleCorners.isRectangleOverlapping(otherPVRectangleCorners);
    }

    boolean isInsideOther(PieceVariation other) throws BadCoordinateValueException {
        RectangleCorners thisPVRectangleCorners = new RectangleCorners(this);
        RectangleCorners otherPVRectangleCorners = new RectangleCorners(other);
        return thisPVRectangleCorners.isThisRectangleInsideOtherOrOnSides(otherPVRectangleCorners);
    }

    @Override
    public int compareTo(PieceVariation other) {
        int comparison = this.getPiece().compareTo(other.getPiece());
        if (comparison == 0) comparison = this.getNorthWesternCoord().compareTo(other.getNorthWesternCoord());
        return comparison;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PieceVariation)) return false;
        PieceVariation that = (PieceVariation) o;
        return getPiece().equals(that.getPiece()) &&
                getOrientation() == that.getOrientation() &&
                Objects.equals(getNorthWesternCoord(), that.getNorthWesternCoord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPiece(), getOrientation(), getNorthWesternCoord());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new PieceVariation(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("piece=")
                .append(this.piece)
                .append(", orientation=")
                .append(this.orientation)
                .append(", northEasternCoord=")
                .append(this.northWesternCoord)
                .append("}");
        return output.toString();
    }

    public String toString(int level) {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{\n");
        Solution.appendByTab(output, level + 1)
                .append("piece=")
                .append(this.piece)
                .append(",\n");
        Solution.appendByTab(output, level + 1)
                .append("orientation=")
                .append(this.orientation)
                .append(",\n");
        Solution.appendByTab(output, level + 1)
                .append("northEasternCoord=")
                .append(this.northWesternCoord)
                .append("\n");
        Solution.appendByTab(output, level)
                .append("}");
        return output.toString();
    }
}
