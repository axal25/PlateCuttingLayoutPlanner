package sheet.cutcase.piece;

import coords.exceptions.BadCoordinateValueException;
import sheet.FreePieceVariation;
import sheet.PieceVariation;
import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.NegativePiecePointsException;
import sheet.exceptions.PieceCanNotFitIntoLayoutException;
import sheet.exceptions.SheetSizeException;

public interface PieceCutCaseInterface {
    public PieceVariation getFragmentInsideOther(PieceVariation pieceVariation, FreePieceVariation freePieceVariation) throws BadCoordinateValueException, SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, CloneNotSupportedException;
}
