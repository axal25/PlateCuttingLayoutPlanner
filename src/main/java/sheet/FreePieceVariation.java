package sheet;

import coords.Coordinate;
import coords.exceptions.BadCoordinateValueException;
import solution.Solution;
import sheet.cutcase.free.piece.FreePieceCutCase;
import sheet.cutcase.free.piece.exceptions.BadAmountOfCoordinatesFoundException;
import sheet.cutcase.free.piece.exceptions.CornerNotOnSideException;
import sheet.cutcase.free.piece.exceptions.CornersOnSidesShareNoCoordinateException;
import sheet.cutcase.free.piece.exceptions.CutCaseNullArgumentException;
import sheet.cutcase.piece.PieceCutter;
import sheet.exceptions.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.TreeSet;

public class FreePieceVariation extends PieceVariation {
    private FreePiece piece;

    public FreePieceVariation(FreePiece freePiece, int coordX, int coordY) throws BadCoordinateValueException, CloneNotSupportedException {
        super(freePiece);
        this.piece = freePiece;
        setNorthWesternCoord(new Coordinate(coordX, coordY));
    }

    public static FreePieceVariation getNewFreePieceVariation(int id, int width, int height, int points, int coordX, int coordY) throws SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, BadCoordinateValueException, CloneNotSupportedException {
        FreePiece newFreePiece = new FreePiece(id, width, height, -points);
        return new FreePieceVariation(newFreePiece, coordX, coordY);
    }

    public static FreePieceVariation getNewFreePieceVariation(Coordinate[] overLappingFragmentCorners) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, NotAllCornersFoundException {
        int expectedAmountOfCorners = 4;
        if(overLappingFragmentCorners.length != expectedAmountOfCorners) throw new NotAllCornersFoundException(overLappingFragmentCorners, expectedAmountOfCorners);
        Arrays.sort(overLappingFragmentCorners);
        int width = overLappingFragmentCorners[3].getX() - overLappingFragmentCorners[0].getX();
        int height = overLappingFragmentCorners[3].getY() - overLappingFragmentCorners[0].getY();
        return FreePieceVariation.getNewFreePieceVariation(
                0,
                width,
                height,
                -width * height,
                overLappingFragmentCorners[0].getX(),
                overLappingFragmentCorners[0].getY()
        );
    }

    /**
     * if piece variation is the same size as this free piece variation - nothing is left, so we don't add anything
     * if PieceVariation is overlapping one of freePieceVariations but doesn't fit inside
     * it fits completely inside another, but only partially inside this one
     */
    public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(PieceVariation pieceVariation) throws BadCoordinateValueException, CutCaseNullArgumentException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        TreeSet<FreePieceVariation> cutUpFreePieceVariations = new TreeSet<>();
        if (pieceVariation.isOverlapping(this)) {
            if (!pieceVariation.isInsideOther(this)) {
                System.out.println(String.format("pieceCutCase.getFragmentInsideOther(pieceVariation, this)\n\t\t\t>>>pieceVariation: %s\n\t\t\t>>> this: %s", pieceVariation.toString(3), this.toString(3)));
                pieceVariation = PieceCutter.getFragmentOfPvInsideFpv(pieceVariation, this);
            }
            FreePieceCutCase freePieceCutCase = FreePieceCutCase.getNewFreePieceCutCase(this, pieceVariation);
            cutUpFreePieceVariations.addAll(freePieceCutCase.getCutUpFreePieceVariation(this, pieceVariation));
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
        Solution.appendByTab(output, level + 1)
                .append("piece=")
                .append(this.getPiece())
                .append(",\n");
        Solution.appendByTab(output, level + 1)
                .append("orientation=")
                .append(super.getOrientation())
                .append(",\n");
        Solution.appendByTab(output, level + 1)
                .append("northEasternCoord=")
                .append(super.getNorthWesternCoord())
                .append("\n");
        Solution.appendByTab(output, level)
                .append("}");
        return output.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FreePieceVariation)) return false;
        if (!super.equals(o)) return false;
        FreePieceVariation that = (FreePieceVariation) o;
        return getPiece().equals(that.getPiece()) &&
                getOrientation() == that.getOrientation() &&
                Objects.equals(getNorthWesternCoord(), that.getNorthWesternCoord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPiece());
    }

    @Override
    public int compareTo(PieceVariation other) {
        int comparison = this.getPiece().compareTo(other.getPiece());
        if (comparison == 0) comparison = this.getNorthWesternCoord().compareTo(other.getNorthWesternCoord());
        return comparison;
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
