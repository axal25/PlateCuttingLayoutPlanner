package sheet;

import coords.exceptions.BadCoordinateValueException;
import org.junit.jupiter.api.*;
import sheet.exceptions.SheetAmountExceededLimitException;
import sheet.exceptions.LayoutFactoryAlreadyInitiatedException;
import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.SheetSizeException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StaticLayoutFactoryTest implements StaticLayoutFactory.InterfaceTestingStaticSheetFactory {
    public static final int DEFAULT_WIDTH = 1;
    public static final int DEFAULT_HEIGHT = 2;

    public static String getSheetLayoutToString(int id, int width, int height) {
        return new StringBuilder()
                .append(Layout.class.getSimpleName())
                .append("{id=")
                .append(id)
                .append(", width=")
                .append(width)
                .append(", height=")
                .append(height)
                .append("}")
                .toString();
    }

    @BeforeEach
    void setUp() {
        this.resetLayoutFactory();
    }

    @AfterEach
    void tearDown() {
        this.resetLayoutFactory();
    }

    @Test
    @Order(1)
    @DisplayName("Initiation StaticLayoutFactory Exception")
    void staticSheetLayoutFactoryInit() {
        assertThrows(LayoutFactoryNotInitiatedException.class, () -> StaticLayoutFactory.getLayoutFactory());
    }

    @Test
    @Order(2)
    @DisplayName("Already Initiated StaticLayoutFactory Exception")
    void staticSheetLayoutFactoryAlreadyInit() throws LayoutFactoryAlreadyInitiatedException, SheetSizeException {
        StaticLayoutFactory.initLayoutFactor(1, 1);
        assertThrows(LayoutFactoryAlreadyInitiatedException.class, () -> StaticLayoutFactory.initLayoutFactor(1, 1));
    }

    @Test
    @Order(3)
    @DisplayName("Single factory - following IDs")
    void singleFactoryFollowingIds() throws SheetAmountExceededLimitException, SheetSizeException, LayoutFactoryNotInitiatedException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        LayoutFactory layoutFactory = StaticLayoutFactory.getLayoutFactory();
        int currentId = layoutFactory.getLayout().getId();
        assertEquals(0, currentId);
        Layout[] pieces = new Layout[10];
        for (int i = 0; i < pieces.length; i++) {
            currentId++;
            pieces[i] = layoutFactory.getLayout();
            String currentPieces1toString = getSheetLayoutToString(currentId, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            assertEquals(currentPieces1toString, pieces[i].toString());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Separate factories - following IDs")
    void separateFactoriesFollowingIds() throws SheetSizeException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        LayoutFactory sheetPieceFactory1 = StaticLayoutFactory.getLayoutFactory();
        LayoutFactory sheetPieceFactory2 = StaticLayoutFactory.getLayoutFactory();
        Layout[] pieces1 = new Layout[10];
        Layout[] pieces2 = new Layout[10];
        int currentId = sheetPieceFactory1.getLayout().getId();
        assertEquals(0, currentId);
        for (int i = 0; i < pieces1.length; i++) {
            pieces1[i] = sheetPieceFactory1.getLayout();
        }

        for (int i = 0; i < pieces2.length; i++) {
            pieces2[i] = sheetPieceFactory2.getLayout();
        }

        for (int i = 0; i < pieces1.length && i < pieces2.length; i++) {
            assertNotEquals(pieces1[i].toString(), pieces2.toString());
            currentId ++;
            String currentPieces1toString = getSheetLayoutToString(currentId, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            assertEquals(currentPieces1toString, pieces1[i].toString());
            String currentPieces2toString = getSheetLayoutToString(currentId + pieces1.length, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            assertEquals(currentPieces2toString, pieces2[i].toString());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Separate factories - alternate factories IDs")
    void separateFactoriesAlternateFactoriesIds() throws SheetSizeException, SheetAmountExceededLimitException, BadCoordinateValueException, LayoutFactoryNotInitiatedException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        LayoutFactory sheetPieceFactory1 = StaticLayoutFactory.getLayoutFactory();
        LayoutFactory sheetPieceFactory2 = StaticLayoutFactory.getLayoutFactory();
        Layout[] pieces1 = new Layout[10];
        Layout[] pieces2 = new Layout[10];
        int currentId = sheetPieceFactory1.getLayout().getId();
        assertEquals(0, currentId);
        currentId++;

        for (int i = 0; i < pieces1.length && i < pieces2.length; i++) {
            pieces1[i] = sheetPieceFactory1.getLayout();
            pieces2[i] = sheetPieceFactory2.getLayout();
            assertNotEquals(pieces1[i].toString(), pieces2.toString());
            String currentPieces1toString = getSheetLayoutToString(currentId++, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            assertEquals(currentPieces1toString, pieces1[i].toString());
            String currentPieces2toString = getSheetLayoutToString(currentId++, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            assertEquals(currentPieces2toString, pieces2[i].toString());
        }
    }

    @Test
    @Order(6)
    @DisplayName("Width exception")
    void staticLayoutFactory_widthException() {
        assertThrows(SheetSizeException.class, () -> StaticLayoutFactory.initLayoutFactor(0, 2));
        assertThrows(SheetSizeException.class, () -> StaticLayoutFactory.initLayoutFactor(-1, 2));
    }

    @Test
    @Order(7)
    @DisplayName("Height exception")
    void staticLayoutFactory_heightException() {
        assertThrows(SheetSizeException.class, () -> StaticLayoutFactory.initLayoutFactor(1, 0));
        assertThrows(SheetSizeException.class, () -> StaticLayoutFactory.initLayoutFactor(1, -2));
    }

    @Test
    @Order(999)
    @DisplayName("Pieces Sheet Amount Exceeded Limit")
    void piecesSheetAmountExceededLimit() throws SheetAmountExceededLimitException, SheetSizeException, LayoutFactoryAlreadyInitiatedException, LayoutFactoryNotInitiatedException {
        StaticLayoutFactory.initLayoutFactor(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        LayoutFactory sheetPieceFactory = StaticLayoutFactory.getLayoutFactory();
        for (
                int i = sheetPieceFactory.getLayout().getId() + 1;
                i < SheetFactory.MAX_SHEET_AMOUNT+1;
                i++
        ) {
            if(i < SheetFactory.MAX_SHEET_AMOUNT) {
                assertEquals(i, sheetPieceFactory.getLayout().getId());
            } else assertThrows(SheetAmountExceededLimitException.class, sheetPieceFactory::getLayout);
        }
    }
}
