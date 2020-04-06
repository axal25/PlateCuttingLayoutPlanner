package parser;

import main.MainTest;
import org.junit.jupiter.api.*;
import sheet.*;
import sheet.exceptions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InputParserTest {
    public static String[] ARGS = MainTest.ARGS;
    public static String EXPECTED_OUTPUT = MainTest.EXPECTED_OUTPUT;

    public static String getSheetPieceToString(int id, int width, int height, int points) {
        return StaticPieceFactoryTest.getSheetPieceToString(id, width, height, points);
    }

    public static String getSheetLayoutToString(int id, int width, int height) {
        return StaticLayoutFactoryTest.getSheetLayoutToString(id, width, height);
    }

    @Test
    @Order(1)
    @DisplayName("getSheetLayouts PDF Example")
    void InputParser_getSheetLayouts_PDFExample() throws SheetSizeException, BadAmountOfInputArgsException, SheetAmountExceededLimitException, LayoutFactoryAlreadyInitiatedException, LayoutFactoryNotInitiatedException {
        InputParser.initSheetLayoutFactory(ARGS);
        Layout layout = StaticLayoutFactory.getLayoutFactory().getLayout();
        assertEquals(getSheetLayoutToString(0, 10, 10), layout.toString());
    }

    @Test
    @Order(2)
    @DisplayName("getPieces PDF Example")
    void InputParser_getSheetPieces_PDFExample() throws NegativePiecePointsException, SheetSizeException, BadAmountOfInputArgsException, SheetAmountExceededLimitException, CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        Piece[] pieces = InputParser.getSheetPieces(ARGS);
        assertEquals(8, pieces.length);
        assertEquals(getSheetPieceToString(0,6,6, 9), pieces[0].toString());
        assertEquals(getSheetPieceToString(1,7,4, 3), pieces[1].toString());
        assertEquals(getSheetPieceToString(2,2,5, 5), pieces[2].toString());
        assertEquals(getSheetPieceToString(3,2,3, 7), pieces[3].toString());
        assertEquals(getSheetPieceToString(4,5,8, 4), pieces[4].toString());
        assertEquals(getSheetPieceToString(5,3,2, 1), pieces[5].toString());
        assertEquals(getSheetPieceToString(6,4,2, 1), pieces[6].toString());
        assertEquals(getSheetPieceToString(7,4,2, 1), pieces[7].toString());
    }
}
