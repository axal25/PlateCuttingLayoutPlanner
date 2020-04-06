package sheet;

import coords.BadCoordinateValueException;
import coords.Coordinate;
import coords.RectangleCorners;
import cutter.Solution;
import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.NegativePiecePointsException;
import sheet.exceptions.PieceCanNotFitIntoLayoutException;
import sheet.exceptions.SheetSizeException;

import java.util.TreeSet;

public class FreePieceVariation extends PieceVariation implements Comparable<FreePieceVariation> {
    private FreePiece piece;

    public static FreePieceVariation getNewFreePieceVariation(int id, int width, int height, int points, int coordX, int coordY) throws SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, BadCoordinateValueException, CloneNotSupportedException {
        FreePiece newFreePiece = new FreePiece(id, width, height, -points);
        return new FreePieceVariation(newFreePiece, coordX, coordY);
    }

    public FreePieceVariation(FreePiece freePiece, int coordX, int coordY) throws BadCoordinateValueException, CloneNotSupportedException {
        super(freePiece);
        this.piece = freePiece;
        setNorthWesternCoord(new Coordinate(coordX, coordY));
    }

    public boolean isOverlapping(PieceVariation pieceVariation) throws BadCoordinateValueException {
        RectangleCorners freePieceVariationRectangleCornersSheetPieceVariation = new RectangleCorners(
                this.getNorthWesternCoord(),
                this.getPiece().getWidth(),
                this.getPiece().getHeight()
        );
        RectangleCorners pieceVariationRectangleCornersSheetPieceVariation = new RectangleCorners(
                pieceVariation.getNorthWesternCoord(),
                pieceVariation.getPiece().getWidth(),
                pieceVariation.getPiece().getHeight()
        );
        return freePieceVariationRectangleCornersSheetPieceVariation.isOverlapping(pieceVariationRectangleCornersSheetPieceVariation);
    }

    public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(PieceVariation pieceVariation) throws BadCoordinateValueException {
        TreeSet<FreePieceVariation> cutUpFreePieceVariations = new TreeSet<>();
        if(this.isOverlapping(pieceVariation)) {
            /**
             * returned cutUpFreePieceVariations are always biggest, and overlapping,
             * so if PieceVariation is overlapping then it fits inside (because it fits inside whole layout)
             * if piece variation is the same size as this free piece variation nothing is left, so we don't add anything
             */
            if(isPieceVariationSmaller(pieceVariation)) {
                // todo two points are not enough, need all pieceVariation's corners not on corners of THIS
                // todo then all combinations of two of those X points are on sides of new FreePieceVariationS
                Coordinate[] twoPieceVariationCornersOnThisSides = findTwoPieceVariationCornersOnThisSides(pieceVariation);
            }
        } else cutUpFreePieceVariations.add(this);
        return cutUpFreePieceVariations;
    }

    private boolean isPieceVariationSmaller(PieceVariation pieceVariation) {
        return this.getPiece().getWidth() > pieceVariation.getPiece().getHeight() && this.getPiece().getHeight() > pieceVariation.getPiece().getHeight();
    }

    private Coordinate[] findTwoPieceVariationCornersOnThisSides(PieceVariation pieceVariation) throws BadCoordinateValueException {
        Coordinate[] twoPieceVariationCornersOnThisSides = new Coordinate[2];
        Coordinate[] pieceVariationCoordinates = get4Corners(pieceVariation);
        Coordinate[] freeVariationCoordinates = get4Corners(this);
        int index = 0;
        for (int i = 0; i < 4; i++) {
            if(!pieceVariationCoordinates[i].equals(freeVariationCoordinates[i])) {
                twoPieceVariationCornersOnThisSides[index] = pieceVariationCoordinates[i];
                index++;
            }
        }
        return twoPieceVariationCornersOnThisSides;
    }

    private Coordinate[] get4Corners(PieceVariation pieceVariation) throws BadCoordinateValueException {
        Coordinate[] pieceVariationCoordinates = new Coordinate[4];
        pieceVariationCoordinates[0] = pieceVariation.getNorthWesternCoord();
        pieceVariationCoordinates[1] = new Coordinate(
                pieceVariation.getNorthWesternCoord().getX() + pieceVariation.getPiece().getWidth(),
                pieceVariation.getNorthWesternCoord().getY()
        );
        pieceVariationCoordinates[2] = new Coordinate(
                pieceVariation.getNorthWesternCoord().getX() + pieceVariation.getPiece().getWidth(),
                pieceVariation.getNorthWesternCoord().getY() + pieceVariation.getPiece().getHeight()
        );
        pieceVariationCoordinates[3] = new Coordinate(
                pieceVariation.getNorthWesternCoord().getX(),
                pieceVariation.getNorthWesternCoord().getY() + pieceVariation.getPiece().getHeight()
        );
        return pieceVariationCoordinates;
    }

    @Override
    public FreePiece getPiece() {
        return this.piece;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("piece=")
                .append(super.getPiece())
                .append(", orientation=")
                .append(super.getOrientation())
                .append(", northEasternCoord=")
                .append(super.getNorthWesternCoord())
                .append("}");
        return output.toString();
    }

    public String toString(int level) {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{\n");
        Solution.appendByTab(output, level+1)
                .append("piece=")
                .append(super.getPiece())
                .append(",\n");
        Solution.appendByTab(output, level+1)
                .append("orientation=")
                .append(super.getOrientation())
                .append(",\n");
        Solution.appendByTab(output, level+1)
                .append("northEasternCoord=")
                .append(super.getNorthWesternCoord())
                .append("\n");
        Solution.appendByTab(output, level)
                .append("}");
        return output.toString();
    }

    @Override
    public int compareTo(FreePieceVariation other) {
        return other.getPiece().getPoints() - this.getPiece().getPoints();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return getNewFreePieceVariation(
                    this.getPiece().getId(),
                    this.getPiece().getWidth(),
                    this.getPiece().getHeight(),
                    this.getPiece().getPoints(),
                    this.getNorthWesternCoord().getX(),
                    this.getNorthWesternCoord().getY()
            );
        } catch (SheetSizeException | PieceCanNotFitIntoLayoutException | NegativePiecePointsException | LayoutFactoryNotInitiatedException | BadCoordinateValueException ex) {
            throw new CloneNotSupportedException(
                    new StringBuilder()
                            .append("Could not copy object of class ")
                            .append(this.getClass().getSimpleName())
                            .append(". Object to be copied: ")
                            .append(this.toString(0))
                            .append("\nCause by: ")
                            .append(ex.getMessage())
                            .toString()
            );
        }
    }
}
