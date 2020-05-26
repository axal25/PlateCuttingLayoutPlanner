package sheet.cutcase.piece;

import coords.exceptions.BadCoordinateValueException;
import sheet.FreePieceVariation;
import sheet.PieceVariation;
import sheet.exceptions.*;

public interface PieceCutCaseInterface {
    public PieceVariation getFragmentOfPvInsideFpv(PieceCutter pieceCutter) throws BadCoordinateValueException, SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NotAllCornersFoundException;
}
