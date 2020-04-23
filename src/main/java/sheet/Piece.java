package sheet;

import sheet.exceptions.*;
import sheet.sort.strategy.PieceSortStrategy;

import java.util.Objects;

import static sheet.StaticLayoutFactory.getLayoutFactory;

public class Piece extends Sheet implements Comparable<Piece> {
    public static PieceSortStrategy DEFAULT_PIECE_SORT_STRATEGY = PieceSortStrategy.LONGEST_SIDE_DESC;
    static PieceSortStrategy pieceSortStrategy = DEFAULT_PIECE_SORT_STRATEGY;
    private int points;

    Piece(int id, int width, int height, int points) throws SheetSizeException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        super(id, width, height);
        validate();
        setPoints(points);
    }

    private Piece(Piece template) throws SheetSizeException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        this(template.getId(), template.getWidth(), template.getHeight(), template.points);
    }

    public static PieceSortStrategy getPieceSortStrategy() throws PieceSortStrategyNotInitiatedException {
        if (pieceSortStrategy == null)
            throw new PieceSortStrategyNotInitiatedException(
                    StaticPieceFactory.class.getSimpleName(),
                    "getPieceSortStrategy()",
                    new StringBuilder()
                            .append("StaticPieceFactory.pieceSortStrategy field should never be null. ")
                            .append("It must be first initiated to be used.")
                            .toString()
            );
        return pieceSortStrategy;
    }

    private boolean isCanFitIntoSheetLayout() throws LayoutFactoryNotInitiatedException {
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

    private double getPointsToLongestSideRatioDesc(Piece piece) {
        final int pieceLongestSide = Math.max(piece.getHeight(), piece.getWidth());
        return ((double) piece.points) / ((double) pieceLongestSide);
    }

    private int getPointsToLongestSideRatioDescDifference(Piece piece1, Piece piece2) {
        final double answerLongestSideDouble = getPointsToLongestSideRatioDesc(piece2) - getPointsToLongestSideRatioDesc(piece1);
        if (answerLongestSideDouble >= 0) return (int) Math.ceil(answerLongestSideDouble);
        else return (int) Math.floor(answerLongestSideDouble);
    }

    private double getPointsToAreaRatioDesc(Piece piece) {
        final int pieceArea = piece.getHeight() * piece.getWidth();
        return ((double) piece.points) / ((double) pieceArea);
    }

    private int getPointsToAreaRatioDescDifference(Piece piece1, Piece piece2) {
        final double answerAreaDouble = getPointsToAreaRatioDesc(piece2) - getPointsToAreaRatioDesc(piece1);
        if (answerAreaDouble >= 0) return (int) Math.ceil(answerAreaDouble);
        else return (int) Math.floor(answerAreaDouble);
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
                "Piece cannot fit into into Layout."
        );
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
        PieceSortStrategy pieceSortStrategy;
        final int defaultAnswer = other.getId() - this.getId();
        try {
            pieceSortStrategy = getPieceSortStrategy();
        } catch (PieceSortStrategyNotInitiatedException e) {
            return defaultAnswer;
        }
        switch (pieceSortStrategy) {
            case HEIGHT_DESC:
                return other.getHeight() - this.getHeight();

            case WIDTH_DESC:
                return other.getWidth() - this.getWidth();

            case LONGEST_SIDE_DESC:
                final int thisLongestSide = Math.max(this.getHeight(), this.getWidth());
                final int otherLongestSide = Math.max(other.getHeight(), other.getWidth());
                return otherLongestSide - thisLongestSide;

            case AREA_DESC:
                final int thisArea = this.getHeight() * this.getWidth();
                final int otherArea = other.getHeight() * other.getWidth();
                return otherArea - thisArea;

            case POINTS_TO_LONGEST_SIDE_RATIO_DESC:
                return getPointsToLongestSideRatioDescDifference(this, other);

            case POINTS_TO_AREA_RATIO_DESC:
                return getPointsToAreaRatioDescDifference(this, other);

            default:
                return defaultAnswer;
        }
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
