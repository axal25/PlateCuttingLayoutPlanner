package sheet;

import org.junit.jupiter.api.*;
import sheet.exceptions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PieceVariationTest implements StaticLayoutFactory.InterfaceTestingStaticSheetFactory, StaticPieceFactory.InterfaceTestingStaticPieceFactory {

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
    @DisplayName("rotate Sheet Piece")
    void rotateSheetPiece() throws LayoutFactoryAlreadyInitiatedException, SheetSizeException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, CloneNotSupportedException {
        StaticLayoutFactory.initLayoutFactor(StaticLayoutFactoryTest.DEFAULT_WIDTH, StaticLayoutFactoryTest.DEFAULT_HEIGHT);
        Piece piece = StaticPieceFactory.getPieceFactory().getPiece(StaticPieceFactoryTest.DEFAULT_WIDTH, StaticPieceFactoryTest.DEFAULT_HEIGHT, StaticPieceFactoryTest.DEFAULT_POINTS);
        PieceVariation pieceVariation1 = new PieceVariation(piece);
        PieceVariation pieceVariation2 = new PieceVariation(piece);
        int previousWidth = piece.getWidth();
        int previousHeight = piece.getHeight();
        piece.rotate();
        assertEquals(previousWidth, piece.getHeight());
        assertEquals(previousHeight, piece.getWidth());
        assertEquals(previousWidth, pieceVariation1.getPiece().getWidth());
        assertEquals(previousHeight, pieceVariation1.getPiece().getHeight());
        assertEquals(previousWidth, pieceVariation2.getPiece().getWidth());
        assertEquals(previousHeight, pieceVariation2.getPiece().getHeight());
    }

    @Test
    @Order(2)
    @DisplayName("rotate Sheet Piece Variation 1")
    void rotateSheetPieceVariation1() throws LayoutFactoryAlreadyInitiatedException, SheetSizeException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, CloneNotSupportedException {
        StaticLayoutFactory.initLayoutFactor(StaticLayoutFactoryTest.DEFAULT_WIDTH, StaticLayoutFactoryTest.DEFAULT_HEIGHT);
        Piece piece = StaticPieceFactory.getPieceFactory().getPiece(StaticPieceFactoryTest.DEFAULT_WIDTH, StaticPieceFactoryTest.DEFAULT_HEIGHT, StaticPieceFactoryTest.DEFAULT_POINTS);
        PieceVariation pieceVariation1 = new PieceVariation(piece);
        PieceVariation pieceVariation2 = new PieceVariation(piece);
        int previousWidth = piece.getWidth();
        int previousHeight = piece.getHeight();
        pieceVariation1.rotate();
        assertEquals(previousWidth, piece.getWidth());
        assertEquals(previousHeight, piece.getHeight());
        assertEquals(previousWidth, pieceVariation1.getPiece().getHeight());
        assertEquals(previousHeight, pieceVariation1.getPiece().getWidth());
        assertEquals(previousWidth, pieceVariation2.getPiece().getWidth());
        assertEquals(previousHeight, pieceVariation2.getPiece().getHeight());
    }

    @Test
    @Order(3)
    @DisplayName("rotate Sheet Piece Variation 2")
    void rotateSheetPieceVariation2() throws LayoutFactoryAlreadyInitiatedException, SheetSizeException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, CloneNotSupportedException {
        StaticLayoutFactory.initLayoutFactor(StaticLayoutFactoryTest.DEFAULT_WIDTH, StaticLayoutFactoryTest.DEFAULT_HEIGHT);
        Piece piece = StaticPieceFactory.getPieceFactory().getPiece(StaticPieceFactoryTest.DEFAULT_WIDTH, StaticPieceFactoryTest.DEFAULT_HEIGHT, StaticPieceFactoryTest.DEFAULT_POINTS);
        PieceVariation pieceVariation1 = new PieceVariation(piece);
        PieceVariation pieceVariation2 = new PieceVariation(piece);
        int previousWidth = piece.getWidth();
        int previousHeight = piece.getHeight();
        pieceVariation2.rotate();
        assertEquals(previousWidth, piece.getWidth());
        assertEquals(previousHeight, piece.getHeight());
        assertEquals(previousWidth, pieceVariation1.getPiece().getWidth());
        assertEquals(previousHeight, pieceVariation1.getPiece().getHeight());
        assertEquals(previousWidth, pieceVariation2.getPiece().getHeight());
        assertEquals(previousHeight, pieceVariation2.getPiece().getWidth());
    }
}
