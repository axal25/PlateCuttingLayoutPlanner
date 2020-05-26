package sheet.cutcase.piece;

import coords.Coordinate;
import coords.Coordinates;
import coords.exceptions.BadCoordinateValueException;
import sheet.FreePieceVariation;
import sheet.PieceVariation;
import sheet.exceptions.*;

/**
 * Cuts up pieceVariation to the fragment inside freePieceVariation, so this fragment can be applied to FreePieceCutCase
 */
public class PieceCutter {
    private PieceVariation pieceVariation;
    private FreePieceVariation freePieceVariation;
    private Coordinate[] pvCornersInsideFpv;
    private Coordinate[] fpvCornersInsidePv;
    private PieceCutCaseInterface pieceCutCase;

    public PieceCutter(PieceVariation pieceVariation, FreePieceVariation freePieceVariation) throws BadCoordinateValueException {
        this.pieceVariation = pieceVariation;
        System.out.println(String.format("pieceVariation: %s", pieceVariation));
        this.freePieceVariation = freePieceVariation;
        System.out.println(String.format("freePieceVariation: %s", freePieceVariation));
        this.pvCornersInsideFpv = pieceVariation.getCornersInsideOf(freePieceVariation);
        System.out.println(String.format("pieceVariation.getCornersInsideOf(freePieceVariation).length: %d", this.pvCornersInsideFpv.length));
        System.out.println(String.format("pieceVariation.getCornersInsideOf(freePieceVariation): %s", Coordinates.toString(this.pvCornersInsideFpv)));
        this.pieceCutCase = PieceCutCaseFactory.getNewPieceCutCase(this);
        System.out.println("PieceCutCaseFactory.getNewPieceCutCase(PieceCutter pieceCutter)");
    }

    public PieceVariation getFragmentOfPvInsideFpv() throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, NotAllCornersFoundException, LayoutFactoryNotInitiatedException, SheetSizeException {
        return this.pieceCutCase.getFragmentOfPvInsideFpv(this);
    }

    public static PieceVariation getFragmentOfPvInsideFpv(PieceVariation pieceVariation, FreePieceVariation freePieceVariation) throws BadCoordinateValueException, CloneNotSupportedException, NotAllCornersFoundException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
        PieceCutter pieceCutter = new PieceCutter(pieceVariation, freePieceVariation);
        return pieceCutter.getPieceCutCase().getFragmentOfPvInsideFpv(pieceCutter);
    }

    public PieceVariation getPieceVariation() {
        return pieceVariation;
    }

    public FreePieceVariation getFreePieceVariation() {
        return freePieceVariation;
    }

    public Coordinate[] getPvCornersInsideFpv() {
        return pvCornersInsideFpv;
    }

    public Coordinate[] getFpvCornersInsidePv() {
        return fpvCornersInsidePv;
    }

    public void setFpvCornersInsidePv(Coordinate[] fpvCornersInsidePv) {
        System.out.println("setFpvCornersInsidePv(Coordinate[] fpvCornersInsidePv)");
        this.fpvCornersInsidePv = fpvCornersInsidePv;
    }

    public PieceCutCaseInterface getPieceCutCase() {
        return pieceCutCase;
    }
}
