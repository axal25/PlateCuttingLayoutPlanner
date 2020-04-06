package sheet;

import sheet.exceptions.SheetAmountExceededLimitException;
import sheet.exceptions.SheetSizeException;

public class LayoutFactory extends SheetFactory {
    private int width, height;

    LayoutFactory(int width, int height) throws SheetSizeException {
        if(width <= 0) throw new SheetSizeException(
                this.getClass().getSimpleName(),
                "<init>",
                "Width",
                width
        );
        if(height <= 0) throw new SheetSizeException(
                this.getClass().getSimpleName(),
                "<init>",
                "Height",
                height
        );
        this.width = width;
        this.height = height;
        super.sheetCount = 0;
    }

    public Layout getLayout() throws SheetSizeException, SheetAmountExceededLimitException {
        try {
            checkIfSheetCountExceededLimit();
            return new Layout(super.sheetCount++, this.width, this.height);
        } catch(SheetSizeException | SheetAmountExceededLimitException e ) {
            super.sheetCount--;
            throw e;
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
