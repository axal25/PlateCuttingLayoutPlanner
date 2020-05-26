package sheet.cutcase.piece.exceptions;

import coords.exceptions.BadCoordinateValueException;
import sheet.cutcase.piece.PieceCutCaseInterface;
import sheet.cutcase.piece.PieceCutter;

public interface PieceCutCaseFactoryInterface {
    public PieceCutCaseInterface getPieceCutCase(PieceCutter pieceCutter) throws BadCoordinateValueException;
}
