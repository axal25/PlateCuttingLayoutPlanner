package sheet;

import coords.RectangleCorners;
import coords.exceptions.BadCoordinateValueException;
import coords.Coordinate;
import cutter.CutCase;
import cutter.Solution;
import cutter.exceptions.CutCaseNullArgumentException;
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

    /**
     * if piece variation is the same size as this free piece variation - nothing is left, so we don't add anything
     * if PieceVariation is overlapping one of freePieceVariations but doesn't fit inside
     * it fits completely inside another, but only partially inside this one
     */
    public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(PieceVariation pieceVariation) throws BadCoordinateValueException, CutCaseNullArgumentException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException {
        TreeSet<FreePieceVariation> cutUpFreePieceVariations = new TreeSet<>();
        if(pieceVariation.isOverlapping(this)) {
            if(!pieceVariation.isInsideOther(this)) {
                pieceVariation = pieceVariation.getFragmentInsideOther(this);
            }
            CutCase cutCase = CutCase.getCutCase(this, pieceVariation);
            cutUpFreePieceVariations.addAll(cutCase.getCutUpFreePieceVariation(this, pieceVariation));
        } else cutUpFreePieceVariations.add(this);
        return cutUpFreePieceVariations;
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
                .append(this.getPiece())
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
                .append(this.getPiece())
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
