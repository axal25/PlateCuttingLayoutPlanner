package sheet;

import sheet.exceptions.*;

public class PieceFactory extends SheetFactory {

    PieceFactory() {
        super.sheetCount = 0;
    }

    public Piece getPiece(int width, int height, int points) throws SheetSizeException, NegativePiecePointsException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        try {
            checkIfSheetCountExceededLimit();
            return new Piece(super.sheetCount++, width, height, points);
        } catch(SheetSizeException | NegativePiecePointsException | SheetAmountExceededLimitException | PieceCanNotFitIntoLayoutException | LayoutFactoryNotInitiatedException e) {
            super.sheetCount--;
            throw e;
        }
    }
}
