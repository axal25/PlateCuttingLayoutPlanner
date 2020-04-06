package sheet;

import org.junit.jupiter.api.*;
import sheet.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StaticPieceFactoryTest implements StaticPieceFactory.InterfaceTestingStaticPieceFactory, StaticLayoutFactory.InterfaceTestingStaticSheetFactory {
    public static final int DEFAULT_WIDTH = StaticLayoutFactoryTest.DEFAULT_WIDTH;
    public static final int DEFAULT_HEIGHT = StaticLayoutFactoryTest.DEFAULT_HEIGHT;
    public static final int DEFAULT_POINTS = 3;

    public static String getSheetPieceToString(int id, int width, int height, int points) {
        return new StringBuilder()
                .append(Piece.class.getSimpleName())
                .append("{id=")
                .append(id)
                .append(", width=")
                .append(width)
                .append(", height=")
                .append(height)
                .append(", points=")
                .append(points)
                .append("}")
                .toString();
    }

    @BeforeEach
    void setUp() {
        this.resetLayoutFactory();
        this.resetPieceFactory();
    }

    @AfterEach
    void tearDown() {
        this.resetLayoutFactory();
        this.resetPieceFactory();
    }

    @Test
    @Order(1)
    @DisplayName("Single factory - following IDs")
    void singleFactoryFollowingIds() throws SheetSizeException, NegativePiecePointsException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(StaticLayoutFactoryTest.DEFAULT_WIDTH, StaticLayoutFactoryTest.DEFAULT_HEIGHT);
        PieceFactory pieceFactory = StaticPieceFactory.getPieceFactory();
        int currentId = pieceFactory.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS).getId();
        assertEquals(0, currentId);
        Piece[] pieces = new Piece[10];
        for (int i = 0; i < pieces.length; i++) {
            currentId++;
            pieces[i] = pieceFactory.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS);
            String currentPieces1toString = getSheetPieceToString(currentId, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS);
            assertEquals(currentPieces1toString, pieces[i].toString());
        }
    }

    @Test
    @Order(2)
    @DisplayName("Separate factories - following IDs")
    void separateFactoriesFollowingIds() throws SheetSizeException, NegativePiecePointsException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(StaticLayoutFactoryTest.DEFAULT_WIDTH, StaticLayoutFactoryTest.DEFAULT_HEIGHT);
        PieceFactory pieceFactory1 = StaticPieceFactory.getPieceFactory();
        PieceFactory pieceFactory2 = StaticPieceFactory.getPieceFactory();
        Piece[] pieces1 = new Piece[10];
        Piece[] pieces2 = new Piece[10];
        int currentId = pieceFactory1.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS).getId();
        assertEquals(0, currentId);
        for (int i = 0; i < pieces1.length; i++) {
            pieces1[i] = pieceFactory1.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS);
        }

        for (int i = 0; i < pieces2.length; i++) {
            pieces2[i] = pieceFactory2.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS);
        }

        for (int i = 0; i < pieces1.length && i < pieces2.length; i++) {
            assertNotEquals(pieces1[i].toString(), pieces2.toString());
            currentId ++;
            String currentPieces1toString = getSheetPieceToString(currentId, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS);
            assertEquals(currentPieces1toString, pieces1[i].toString());
            String currentPieces2toString = getSheetPieceToString(currentId + pieces1.length, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS);
            assertEquals(currentPieces2toString, pieces2[i].toString());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Separate factories - alternate factories IDs")
    void separateFactoriesAlternateFactoriesIds() throws SheetSizeException, NegativePiecePointsException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(StaticLayoutFactoryTest.DEFAULT_WIDTH, StaticLayoutFactoryTest.DEFAULT_HEIGHT);
        PieceFactory pieceFactory1 = StaticPieceFactory.getPieceFactory();
        PieceFactory pieceFactory2 = StaticPieceFactory.getPieceFactory();
        Piece[] pieces1 = new Piece[10];
        Piece[] pieces2 = new Piece[10];
        int currentId = pieceFactory1.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS).getId();
        assertEquals(0, currentId);
        currentId++;

        for (int i = 0; i < pieces1.length && i < pieces2.length; i++) {
            pieces1[i] = pieceFactory1.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS);
            pieces2[i] = pieceFactory2.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS);
            assertNotEquals(pieces1[i].toString(), pieces2.toString());
            String currentPieces1toString = getSheetPieceToString(currentId++, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS);;
            assertEquals(currentPieces1toString, pieces1[i].toString());
            String currentPieces2toString = getSheetPieceToString(currentId++, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS);;
            assertEquals(currentPieces2toString, pieces2[i].toString());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Width exception")
    void staticPieceFactory_widthException() throws LayoutFactoryAlreadyInitiatedException, SheetSizeException {
        StaticLayoutFactory.initLayoutFactor(StaticLayoutFactoryTest.DEFAULT_WIDTH, StaticLayoutFactoryTest.DEFAULT_HEIGHT);
        PieceFactory pieceFactory = StaticPieceFactory.getPieceFactory();
        assertThrows(SheetSizeException.class, () -> pieceFactory.getSheetPiece(0, 2, 3));
    }

    @Test
    @Order(6)
    @DisplayName("Height exception")
    void staticPieceFactory_heightException() throws LayoutFactoryAlreadyInitiatedException, SheetSizeException {
        StaticLayoutFactory.initLayoutFactor(StaticLayoutFactoryTest.DEFAULT_WIDTH, StaticLayoutFactoryTest.DEFAULT_HEIGHT);
        PieceFactory pieceFactory = StaticPieceFactory.getPieceFactory();
        assertThrows(SheetSizeException.class, () -> pieceFactory.getSheetPiece(1, -2, 3));
    }

    @Test
    @Order(7)
    @DisplayName("Points exception")
    void staticPieceFactory_pointsException() throws LayoutFactoryAlreadyInitiatedException, SheetSizeException {
        StaticLayoutFactory.initLayoutFactor(StaticLayoutFactoryTest.DEFAULT_WIDTH, StaticLayoutFactoryTest.DEFAULT_HEIGHT);
        PieceFactory pieceFactory = StaticPieceFactory.getPieceFactory();
        assertThrows(NegativePiecePointsException.class, () -> pieceFactory.getSheetPiece(1, 2, -3));
    }

    @Test
    @Order(8)
    @DisplayName("width/height larger than Layout's")
    void staticPieceFactory_widthOrHeightLargerThanLayouts() throws LayoutFactoryAlreadyInitiatedException, SheetSizeException {
        StaticLayoutFactory.initLayoutFactor(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        PieceFactory pieceFactory = StaticPieceFactory.getPieceFactory();
        assertThrows(PieceCanNotFitIntoLayoutException.class, () -> pieceFactory.getSheetPiece(DEFAULT_WIDTH+1, DEFAULT_HEIGHT, 1));
        assertThrows(PieceCanNotFitIntoLayoutException.class, () -> pieceFactory.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT+1, 1));
    }

    @Test
    @Order(999)
    @DisplayName("Pieces Sheet Amount Exceeded Limit")
    void piecesSheetAmountExceededLimit() throws SheetAmountExceededLimitException, NegativePiecePointsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(StaticLayoutFactoryTest.DEFAULT_WIDTH, StaticLayoutFactoryTest.DEFAULT_HEIGHT);
        PieceFactory pieceFactory = StaticPieceFactory.getPieceFactory();
        for (
                int i = pieceFactory.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS).getId() + 1;
                i < SheetFactory.MAX_SHEET_AMOUNT+1;
                i++
        ) {
            if(i < SheetFactory.MAX_SHEET_AMOUNT) {
                assertEquals(i, pieceFactory.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS).getId());
            } else assertThrows(SheetAmountExceededLimitException.class, () -> pieceFactory.getSheetPiece(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POINTS));
        }
    }
}
