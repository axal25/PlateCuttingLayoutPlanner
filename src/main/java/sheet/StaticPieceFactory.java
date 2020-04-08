package sheet;

public class StaticPieceFactory {
    private static PieceFactory pieceFactory = new PieceFactory();

    public static PieceFactory getPieceFactory() {
        return pieceFactory;
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

}
