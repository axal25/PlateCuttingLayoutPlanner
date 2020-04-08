package sheet;

import coords.exceptions.BadCoordinateValueException;
import coords.Coordinate;
import coords.RectangleCorners;
import cutter.Solution;
import orientation.Orientation;
import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.NegativePiecePointsException;
import sheet.exceptions.PieceCanNotFitIntoLayoutException;
import sheet.exceptions.SheetSizeException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public class PieceVariation {
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
        if(template.northWesternCoord != null) this.northWesternCoord = (Coordinate) template.northWesternCoord.clone();
        else this.northWesternCoord = null;
    }

    public Coordinate[] getXNotOverlappingCornersOfBoth(PieceVariation other, int amountOfNotOverlappingCorners) throws BadCoordinateValueException {
        Coordinate[] xNotOverlappingCorners = new Coordinate[amountOfNotOverlappingCorners];
        Coordinate[] thisXNotOverlappingCorners = this.getXNotOverlappingCornersOfThisOnly(other, amountOfNotOverlappingCorners/2);
        Coordinate[] otherXNotOverlappingCorners = other.getXNotOverlappingCornersOfThisOnly(this, amountOfNotOverlappingCorners/2);
        for (int i = 0, j = 0, k = 0; i < thisXNotOverlappingCorners.length && j < otherXNotOverlappingCorners.length; i++, j++, k++) {
            xNotOverlappingCorners[k] = thisXNotOverlappingCorners[i];
            k++;
            xNotOverlappingCorners[k] = otherXNotOverlappingCorners[j];
        }
        return xNotOverlappingCorners;
    }

    public Coordinate[] getXNotOverlappingCornersOfThisOnly(PieceVariation other, int amountOfNotOverlappingCorners) throws BadCoordinateValueException {
        Coordinate[] xNotOverlappingCorners = new Coordinate[amountOfNotOverlappingCorners];
        int index = 0;
        RectangleCorners otherRC = new RectangleCorners(other);
        RectangleCorners thisRC = new RectangleCorners(this);
        if(!otherRC.getNWCoord().equals(thisRC.getNWCoord())) {
            xNotOverlappingCorners[index] = thisRC.getNWCoord();
            index++;
        }
        if(!otherRC.getNECoord().equals(thisRC.getNECoord())) {
            xNotOverlappingCorners[index] = thisRC.getNECoord();
            index++;
        }
        if(!otherRC.getSECoord().equals(thisRC.getSECoord())) {
            xNotOverlappingCorners[index] = thisRC.getSECoord();
            index++;
        }
        if(!otherRC.getSWCoord().equals(thisRC.getSWCoord())) {
            xNotOverlappingCorners[index] = thisRC.getSWCoord();
            index++;
        }
        return xNotOverlappingCorners;
    }

    PieceVariation getFragmentInsideOther(PieceVariation other) throws BadCoordinateValueException, SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, CloneNotSupportedException {
        Coordinate[] cornersInsideOther = getCornersInsideOther(other);
        switch (cornersInsideOther.length) {
            case 0:
                return getPieceVariationInsideOtherWith0Corners(other);
            case 1:
                throw new UnsupportedOperationException("1");
            case 2:
                throw new UnsupportedOperationException("2");
            default:
                throw new UnsupportedOperationException(
                        new StringBuilder()
                                .append("It is not possible for this PieceVariation to be partially inside another")
                                .append(" PieceVariation with more than 2 corners. Amount of corners inside other: ")
                                .append(cornersInsideOther)
                                .toString()
                );
        }
    }

    private Coordinate[] getCornersInsideOther(PieceVariation other) throws BadCoordinateValueException {
        TreeSet<Coordinate> thisCornersInsideOther = new TreeSet<>();
        RectangleCorners thisRectangleCorners = new RectangleCorners(this);
        RectangleCorners otherRectangleCorners = new RectangleCorners(other);
        if(otherRectangleCorners.isInside(thisRectangleCorners.getNWCoord())) thisCornersInsideOther.add(thisRectangleCorners.getNWCoord());
        if(otherRectangleCorners.isInside(thisRectangleCorners.getSWCoord())) thisCornersInsideOther.add(thisRectangleCorners.getSWCoord());
        if(otherRectangleCorners.isInside(thisRectangleCorners.getNECoord())) thisCornersInsideOther.add(thisRectangleCorners.getNECoord());
        if(otherRectangleCorners.isInside(thisRectangleCorners.getSECoord())) thisCornersInsideOther.add(thisRectangleCorners.getSECoord());
        return treeSetToArray(thisCornersInsideOther);
    }

    private static Coordinate[] treeSetToArray(TreeSet<Coordinate> treeCoordinates) {
        Coordinate[] arrayCoordinates = new Coordinate[treeCoordinates.size()];
        Iterator iterator = treeCoordinates.iterator();
        for (int i = 0; i < arrayCoordinates.length && iterator.hasNext(); i++) {
            arrayCoordinates[i] = (Coordinate) iterator.next();
        }
        return arrayCoordinates;
    }

    private PieceVariation getPieceVariationInsideOtherWith0Corners(PieceVariation other) throws BadCoordinateValueException, SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, CloneNotSupportedException {
        Coordinate[] corners = getCornersInsideOtherWith0Corners(other);
        Arrays.sort(corners);
        int width = corners[3].getX() - corners[0].getX();
        int height = corners[3].getY() - corners[0].getY();
        return FreePieceVariation.getNewFreePieceVariation(
                0,
                width,
                height,
                -width*height,
                corners[0].getX(),
                corners[0].getY()
        );
    }

    private Coordinate[] getCornersInsideOtherWith0Corners(PieceVariation other) throws BadCoordinateValueException {
        Coordinate[] otherCornersInsideThis = other.getCornersInsideOther(this);
        TreeSet<Coordinate> fragmentCorners = new TreeSet<>();
        RectangleCorners thisRectangleCorners = new RectangleCorners(this);
        Arrays.sort(otherCornersInsideThis);
        Coordinate suspectedCoord = null;
        if(
                otherCornersInsideThis[0].getX() == otherCornersInsideThis[1].getX() &&
                thisRectangleCorners.getNWCoord().getY() < otherCornersInsideThis[0].getY() &&
                thisRectangleCorners.getSECoord().getY() > otherCornersInsideThis[0].getY()
        ) {
            suspectedCoord = new Coordinate(thisRectangleCorners.getNWCoord().getX(), otherCornersInsideThis[0].getY());
            if(thisRectangleCorners.isInside(suspectedCoord)) {
                fragmentCorners.add(suspectedCoord);
                suspectedCoord = new Coordinate(thisRectangleCorners.getNWCoord().getX(), otherCornersInsideThis[1].getY());
                fragmentCorners.add(suspectedCoord);
            } else {
                suspectedCoord = new Coordinate(thisRectangleCorners.getSECoord().getX(), otherCornersInsideThis[0].getY());
                if (thisRectangleCorners.isInside(suspectedCoord)) {
                    fragmentCorners.add(suspectedCoord);
                    suspectedCoord = new Coordinate(thisRectangleCorners.getSECoord().getX(), otherCornersInsideThis[1].getY());
                    fragmentCorners.add(suspectedCoord);
                }
            }
        } else if(
                otherCornersInsideThis[0].getY() == otherCornersInsideThis[1].getY() &&
                thisRectangleCorners.getNWCoord().getX() < otherCornersInsideThis[0].getX() &&
                thisRectangleCorners.getSECoord().getX() > otherCornersInsideThis[0].getX()
        ) {
            suspectedCoord = new Coordinate(otherCornersInsideThis[0].getX(), thisRectangleCorners.getNWCoord().getY());
            if(thisRectangleCorners.isInside(suspectedCoord)) {
                fragmentCorners.add(suspectedCoord);
                suspectedCoord = new Coordinate(otherCornersInsideThis[1].getX(), thisRectangleCorners.getNWCoord().getY());
                fragmentCorners.add(suspectedCoord);
            } else {
                suspectedCoord = new Coordinate(otherCornersInsideThis[0].getX(), thisRectangleCorners.getSECoord().getY());
                if (thisRectangleCorners.isInside(suspectedCoord)) {
                    fragmentCorners.add(suspectedCoord);
                    suspectedCoord = new Coordinate(otherCornersInsideThis[1].getX(), thisRectangleCorners.getSECoord().getY());
                    fragmentCorners.add(suspectedCoord);
                }
            }
        } else throw new UnsupportedOperationException(
                new StringBuilder()
                .append("It is not possible for 2 PieceVariation corners inside another to not have one common")
                .append(" coordinate (X or Y). Or those 2 corners are not enveloped by this.")
                .toString()
        );
        fragmentCorners.add(otherCornersInsideThis[0]);
        fragmentCorners.add(otherCornersInsideThis[1]);
        return treeSetToArray(fragmentCorners);
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
        if(this.piece.getHeight() > this.piece.getWidth()) this.orientation = Orientation.V;
        else this.orientation = Orientation.H;
    }

    public void rotate() {
        if(this.piece.getWidth() == this.piece.getHeight()) {
            swapOrientation();
        } else {
            this.piece.rotate();
            calculateOrientation();
        }
    }

    private void swapOrientation() {
        if(this.orientation == Orientation.H) this.orientation = Orientation.V;
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
        return thisPVRectangleCorners.isRectangleInsideOther(otherPVRectangleCorners);
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
                .append("{");
        Solution.appendByTab(output, level+1)
                .append("piece=")
                .append(this.piece)
                .append(",\n");
        Solution.appendByTab(output, level+1)
                .append("orientation=")
                .append(this.orientation)
                .append(",\n");
        Solution.appendByTab(output, level+1)
                .append("northEasternCoord=")
                .append(this.northWesternCoord)
                .append("\n");
        Solution.appendByTab(output, level)
                .append("}");
        return output.toString();
    }
}
