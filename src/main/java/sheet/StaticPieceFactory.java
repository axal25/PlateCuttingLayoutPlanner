package sheet;

import sheet.exceptions.PieceSortStrategyNotInitiatedException;
import sheet.sort.strategy.PieceSortStrategy;

public class StaticPieceFactory {
    private static PieceSortStrategy pieceSortStrategy = PieceSortStrategy.LONGEST_SIDE_DESC;
    private static PieceFactory pieceFactory = new PieceFactory();

    public static PieceFactory getPieceFactory() {
        return pieceFactory;
    }

    public static PieceSortStrategy getPieceSortStrategy() throws PieceSortStrategyNotInitiatedException {
        if(StaticPieceFactory.pieceSortStrategy == null)
            throw new PieceSortStrategyNotInitiatedException(
                    StaticPieceFactory.class.getSimpleName(),
                    "getPieceSortStrategy()",
                    new StringBuilder()
                            .append("StaticPieceFactory.pieceSortStrategy field should never be null. ")
                            .append("It must be first initiated to be used.")
                            .toString()
            );
        return StaticPieceFactory.pieceSortStrategy;
    }

    // ONLY FOR EXCEPTION (SheetAmountExceededLimitException) TESTING PURPOSES inside StaticPieceFactory
    private static void resetSPieceFactory(InterfaceTestingStaticPieceFactory caller) {
        pieceFactory = new PieceFactory();
    }

    // ONLY FOR EXCEPTION (SheetAmountExceededLimitException) TESTING PURPOSES inside StaticPieceFactory
    public interface InterfaceTestingStaticPieceFactory {
        default void resetPieceFactory() {
            StaticPieceFactory.resetSPieceFactory(this);
        }
    }

    // ONLY FOR SORTING TESTING PURPOSES
    private static void setPieceSortStrategy(PieceSortStrategy pieceSortStrategy) {
        StaticPieceFactory.pieceSortStrategy = pieceSortStrategy;
    }

    // ONLY FOR SORTING TESTING PURPOSES
    public interface InterfaceTestingPieceSortStrategy {
        default void setPieceSortStrategy(PieceSortStrategy pieceSortStrategy) {
            StaticPieceFactory.setPieceSortStrategy(pieceSortStrategy);
        }
        default PieceSortStrategy getPieceSortStrategy() {
            return pieceSortStrategy;
        }
    }
}
