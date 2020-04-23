package sheet.cutcase.free.piece;

import coords.exceptions.BadCoordinateValueException;
import sheet.cutcase.free.piece.exceptions.BadAmountOfCoordinatesFoundException;
import sheet.cutcase.free.piece.exceptions.CornerNotOnSideException;
import sheet.cutcase.free.piece.exceptions.CornersOnSidesShareNoCoordinateException;
import sheet.cutcase.free.piece.exceptions.CutCaseNullArgumentException;
import sheet.FreePieceVariation;
import sheet.PieceVariation;
import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.NegativePiecePointsException;
import sheet.exceptions.PieceCanNotFitIntoLayoutException;
import sheet.exceptions.SheetSizeException;

import java.util.TreeSet;

public interface FreePieceCutCaseInterface {
    String overriddenFunctionName = "getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation)";

    public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) throws CutCaseNullArgumentException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException;
}
