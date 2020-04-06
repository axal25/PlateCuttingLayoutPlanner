package sheet;

import sheet.exceptions.*;
import sheet.sort.strategy.PieceSortStrategy;

import static sheet.StaticLayoutFactory.*;

public class Piece extends Sheet implements Comparable<Piece> {
    private int points;

    Piece(int id, int width, int height, int points) throws SheetSizeException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        super(id, width, height);
        validate();
        setPoints(points);
    }

    private Piece(Piece template) throws SheetSizeException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        this(template.getId(), template.getWidth(), template.getHeight(), template.points);
    }

    void validate() throws LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        if(!isCanFitIntoSheetLayout()) throw new PieceCanNotFitIntoLayoutException(
                Piece.class.getSimpleName(),
                "validate()",
                "Piece cannot fit into into Layout."
        );
    }

    private boolean isCanFitIntoSheetLayout() throws LayoutFactoryNotInitiatedException {
        final int pieceLongestSide = Math.max(getWidth(), getHeight());
        final int pieceShorterSide = Math.min(getWidth(), getHeight());
        /**
         * Because sides of piece and layout are always diagonal to each other (height to height, width to width)
         * Meaning piece can be rotated by arbitrary number of degrees, only by 90 degrees or its multiplicity (180, 270, 360...)
         * Diagonal check is not enough to determinate if one rectangle can fit into another
         * Diagonal check - is diagonal of layout larger or equal to piece diagonal
         */
//        final int pieceArea = getWidth() * getHeight();
//        final double pieceDiagonal = Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2));
        final int layoutLongestSide = Math.max(getLayoutFactory().getWidth(), getLayoutFactory().getHeight());
        final int layoutShorterSide = Math.min(getLayoutFactory().getWidth(), getLayoutFactory().getHeight());
//        final int layoutArea = getLayoutFactory().getWidth() * getLayoutFactory().getHeight();
//        final double layoutDiagonal = Math.sqrt(Math.pow(getLayoutFactory().getWidth(), 2) + Math.pow(getLayoutFactory().getHeight(), 2));
        if(/*pieceArea <= layoutArea && pieceDiagonal <= layoutDiagonal &&*/ pieceLongestSide <= layoutLongestSide && pieceShorterSide <= layoutShorterSide) return true;
        return false;
    }

    void setPoints(int points) throws NegativePiecePointsException {
        if(points < 0) throw new NegativePiecePointsException(
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

    @Override
    public int compareTo(Piece other) {
        PieceSortStrategy pieceSortStrategy;
        final int defaultAnswer = other.getId() - this.getId();
        try {
            pieceSortStrategy = StaticPieceFactory.getPieceSortStrategy();
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
                final int thisArea = this.getHeight()*this.getWidth();
                final int otherArea = other.getHeight()*other.getWidth();
                return otherArea - thisArea;

            case POINTS_TO_LONGEST_SIDE_RATIO_DESC:
                return getPointsToLongestSideRatioDescDifference(this, other);

            case POINTS_TO_AREA_RATIO_DESC:
                return getPointsToAreaRatioDescDifference(this, other);

            default:
                return defaultAnswer;
        }
    }

    private double getPointsToLongestSideRatioDesc(Piece piece) {
        final int pieceLongestSide = Math.max(piece.getHeight(), piece.getWidth());
        return ((double) piece.points)/((double) pieceLongestSide);
    }

    private int getPointsToLongestSideRatioDescDifference(Piece piece1, Piece piece2) {
        final double answerLongestSideDouble = getPointsToLongestSideRatioDesc(piece2) - getPointsToLongestSideRatioDesc(piece1);
        if(answerLongestSideDouble >= 0) return (int) Math.ceil(answerLongestSideDouble);
        else return (int) Math.floor(answerLongestSideDouble);
    }

    private double getPointsToAreaRatioDesc(Piece piece) {
        final int pieceArea = piece.getHeight() * piece.getWidth();
        return ((double) piece.points)/((double) pieceArea);
    }

    private int getPointsToAreaRatioDescDifference(Piece piece1, Piece piece2) {
        final double answerAreaDouble = getPointsToAreaRatioDesc(piece2) - getPointsToAreaRatioDesc(piece1);
        if(answerAreaDouble >= 0) return (int) Math.ceil(answerAreaDouble);
        else return (int) Math.floor(answerAreaDouble);
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

    public void rotate() {
        int prevWidth = this.getWidth();
        this.setWidth(this.getHeight());
        this.setHeight(prevWidth);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("id=").append(super.getId())
                .append(", width=").append(super.getWidth())
                .append(", height=").append(super.getHeight())
                .append(", points=").append(this.points)
                .append("}").toString();
    }
}
