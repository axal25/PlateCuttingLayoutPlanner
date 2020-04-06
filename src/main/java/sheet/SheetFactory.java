package sheet;

import sheet.exceptions.SheetAmountExceededLimitException;

public abstract class SheetFactory {
    public static final int MAX_SHEET_AMOUNT = 10000;
    int sheetCount = 0;

    void checkIfSheetCountExceededLimit() throws SheetAmountExceededLimitException {
        if(MAX_SHEET_AMOUNT <= sheetCount) throw new SheetAmountExceededLimitException(
                this.getClass().getSimpleName(),
                "checkIfSheetCountExceededLimit(int sheetCount)",
                new StringBuilder()
                        .append("Sheet count exceeded maximum allowed amount of sheets (MAX_SHEET_AMOUNT <= sheetCount). SheetCount detected: ")
                        .append(sheetCount)
                        .append(".")
                        .toString()
        );
    }
}
