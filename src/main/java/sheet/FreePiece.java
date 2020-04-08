package sheet;

import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.NegativePiecePointsException;
import sheet.exceptions.PieceCanNotFitIntoLayoutException;
import sheet.exceptions.SheetSizeException;

public class FreePiece extends Piece {
    public FreePiece(int id, int width, int height, int points) throws SheetSizeException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        super(id, width, height, points);
    }

    @Override
    public int getPoints() {
        return - super.getPoints();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("id=").append(super.getId())
                .append(", width=").append(super.getWidth())
                .append(", height=").append(super.getHeight())
                .append(", points=").append(this.getPoints())
                .append("}").toString();
    }
}
