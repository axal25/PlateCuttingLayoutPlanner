package sheet;

import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.NegativePiecePointsException;
import sheet.exceptions.PieceCanNotFitIntoLayoutException;
import sheet.exceptions.SheetSizeException;
import sheet.sort.strategy.PieceSortStrategy;

import java.util.Objects;

import static sheet.StaticLayoutFactory.getLayoutFactory;

public class Piece extends Sheet implements Comparable<Piece> {
    public static final PieceSortStrategy DEFAULT_PIECE_SORT_STRATEGY = PieceSortStrategy.POINTS_TO_LONGEST_SIDE_RATIO_DESC;
    private static PieceSortStrategy pieceSortStrategy = Piece.DEFAULT_PIECE_SORT_STRATEGY;
    private int points;

    Piece(int id, int width, int height, int points) throws SheetSizeException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        super(id, width, height);
        validate();
        setPoints(points);
    }

    private Piece(Piece template) throws SheetSizeException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        this(template.getId(), template.getWidth(), template.getHeight(), template.points);
    }

    public static PieceSortStrategy getPieceSortStrategy() {
        return pieceSortStrategy;
    }

    void setPoints(int points) throws NegativePiecePointsException {
        if (points < 0) throw new NegativePiecePointsException(
                this.getClass().getSimpleName(),
                "<init>",
                new StringBuilder()
                        .append("Points can't be less than 0 (Points < 0). Points detected: ")
                        .append(points)
                        .append(". Program should not be punished for executing task it was suppose to complete.")
                        .toString()
        );
        this.points = points;
    }

    public int getPoints() {
        return this.points;
    }

    public void rotate() {
        int prevWidth = this.getWidth();
        this.setWidth(this.getHeight());
        this.setHeight(prevWidth);
    }

    void validate() throws LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        if (!isCanFitIntoSheetLayout()) throw new PieceCanNotFitIntoLayoutException(
                Piece.class.getSimpleName(),
                "validate()",
                String.format("Piece cannot fit into Layout, because Piece is bigger than Layout. \r\nPiece width: %d, height: %d. \r\nLayout width: %d, height: %d.", getWidth(), getHeight(), getLayoutFactory().getWidth(), getLayoutFactory().getHeight())
        );
    }

    private boolean isCanFitIntoSheetLayout() throws LayoutFactoryNotInitiatedException {
        // todo: clean up
        final int pieceLongestSide = Math.max(getWidth(), getHeight());
        final int pieceShorterSide = Math.min(getWidth(), getHeight());
        /**
         * Because sides of piece and layout are always diagonal to each other (height to height, width to width)
         * Meaning piece can not be rotated by arbitrary number of degrees, only by 90 degrees or its multiplicity (180, 270, 360...)
         * Diagonal check is not enough to determinate if one rectangle can fit into another
         * Diagonal check - is diagonal of layout larger or equal to piece diagonal
         */
//        final int pieceArea = getWidth() * getHeight();
//        final double pieceDiagonal = Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2));
        final int layoutLongestSide = Math.max(getLayoutFactory().getWidth(), getLayoutFactory().getHeight());
        final int layoutShorterSide = Math.min(getLayoutFactory().getWidth(), getLayoutFactory().getHeight());
//        final int layoutArea = getLayoutFactory().getWidth() * getLayoutFactory().getHeight();
//        final double layoutDiagonal = Math.sqrt(Math.pow(getLayoutFactory().getWidth(), 2) + Math.pow(getLayoutFactory().getHeight(), 2));
        if (/*pieceArea <= layoutArea && pieceDiagonal <= layoutDiagonal &&*/ pieceLongestSide <= layoutLongestSide && pieceShorterSide <= layoutShorterSide)
            return true;
        return false;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (!(otherObject instanceof Piece)) return false;
        Piece otherPiece = (Piece) otherObject;
        return this.getHeight() == otherPiece.getHeight() &&
                this.getWidth() == otherPiece.getWidth() &&
                this.getId() == otherPiece.getId() &&
                this.points == otherPiece.getPoints();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPoints());
    }

    @Override
    public int compareTo(Piece other) {
        return Piece.getPieceSortStrategy().compareTo(this, other);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            try {
                return new Piece(this);
            } catch (SheetSizeException | NegativePiecePointsException | LayoutFactoryNotInitiatedException | PieceCanNotFitIntoLayoutException ex) {
                throw new CloneNotSupportedException(
                        new StringBuilder()
                                .append("Could not copy object of class ")
                                .append(this.getClass().getSimpleName())
                                .append(". Object to be copied: ")
                                .append(this.toString())
                                .append("\nCause by: ")
                                .append(ex.getMessage())
                                .toString()
                );
            }
        }
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

    // ONLY FOR SORTING TESTING PURPOSES
    public interface InterfaceTestingPieceSortStrategy {
        default void setPieceSortStrategy(PieceSortStrategy pieceSortStrategy) {
            Piece.setPieceSortStrategy(pieceSortStrategy);
        }

        default PieceSortStrategy getPieceSortStrategy() {
            return Piece.pieceSortStrategy;
        }
    }

    // ONLY FOR SORTING TESTING PURPOSES
    private static void setPieceSortStrategy(PieceSortStrategy pieceSortStrategy) {
        Piece.pieceSortStrategy = pieceSortStrategy;
    }
}
