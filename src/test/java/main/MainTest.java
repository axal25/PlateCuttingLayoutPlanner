package main;

import coords.BadCoordinateValueException;
import org.junit.jupiter.api.*;
import replaced.out.MainWithReplacedOut;
import sheet.*;
import sheet.exceptions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest implements StaticPieceFactory.InterfaceTestingStaticPieceFactory, StaticLayoutFactory.InterfaceTestingStaticSheetFactory {
    /** String[] args:
     * 10 10 - płyta o wymiarach 10 x 10
     * 8     - 8 blach
     * 6 6 9 - blacha 0, wymar 6x6 za 9 punktów
     * 7 4 3 - blacha 1, wymar 7x4 za 3 punkty
     * 2 5 5 - blacha 2, wymar 2x5 za 5 punktów
     * 2 3 7 - blacha 3, wymar 2x3 za 7 punktów
     * 5 8 4 - blacha 4, wymar 5x8 za 4 punkty
     * 3 2 1 - blacha 5, wymar 3x2 za 1 punkt
     * 4 2 1 - blacha 6, wymar 4x2 za 1 punkt
     * 4 2 1 - blacha 7, wymar 4x2 za 1 punkt
     */
    public static String[] ARGS = {
            "10", "10",
            "8",
            "6", "6", "9",
            "7", "4", "3",
            "2", "5", "5",
            "2", "3", "7",
            "5", "8", "4",
            "3", "2", "1",
            "4", "2", "1",
            "4", "2", "1"
    };
    /** String output:
     * 5       - mamy 5 blach wynikowych
     * 1 H 0 0 - w punkcie(x,y): 0,0 umieszczamy horyzontalnie płytę o id 1, wymiary 7x4 za 3 punkty
     * 0 H 0 4 - w punkcie(x,y): 0,4 umieszczamy horyzontalnie płytę o id 0, wymiary 6x6 za 9 punktów
     * 3 V 7 3 - w punkcie(x,y): 7,3 umieszczamy wertykalnie płytę o id 3, wymiary 2x3 za 7 punktów
     * 6 H 6 6 - w punkcie(x,y): 6,6 umieszczamy horyzontalnie płytę o id 6, wymiary 2x4 za 1 punkt
     * 7 H 6 8 - w punkcie(x,y): 6,8 umieszczamy horyzontalnie płytę o id 7, wymiary 2x4 za 1 punkt
     */
    public static String EXPECTED_OUTPUT = new StringBuilder()
            .append("5\n")
            .append("1 H 0 0\n")
            .append("0 H 0 4\n")
            .append("3 V 7 3\n")
            .append("6 H 6 6\n")
            .append("7 H 6 8")
            .toString();

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
    @DisplayName("Main.main(null)")
    void Main_mainTest_null() {
        Main.main(null);
    }

    @Test
    @Order(2)
    @DisplayName("Main.main(new String[0])")
    void Main_mainTest_length_0() {
        Main.main(new String[0]);
    }

    @Test
    @Order(3)
    @DisplayName("Main.main({\"\"})")
    void Main_mainTest_1() {
        String[] args = {""};
        Main.main(args);
    }

    @Test
    @Order(4)
    @DisplayName("new MainWithReplacedOut(replaced.out).main(null)")
    void new_MainWithReplacedOut_args_mainTest_null() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);
        MainWithReplacedOut main = new MainWithReplacedOut(out);
        main.main(null);
        assertNotNull(byteArrayOutputStream.toString());
        assertFalse(byteArrayOutputStream.toString().isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("new MainWithReplacedOut(replaced.out).main(new String[0])")
    void new_MainWithReplacedOut_args_mainTest_length_0() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);
        MainWithReplacedOut main = new MainWithReplacedOut(out);
        main.main(new String[0]);
        assertNotNull(byteArrayOutputStream.toString());
        assertFalse(byteArrayOutputStream.toString().isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName("new MainWithReplacedOut(replaced.out).main(new String[0])")
    void new_MainWithReplacedOut_args_mainTest_1() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);
        MainWithReplacedOut main = new MainWithReplacedOut(out);
        String[] args = {""};
        main.main(args);
        assertNotNull(byteArrayOutputStream.toString());
        assertFalse(byteArrayOutputStream.toString().isEmpty());
    }

    @Test
    @Order(7)
    @DisplayName("NullPointerException: new MainWithReplacedOut(replaced.out).getByteArrayOutputStream()")
    void new_MainWithReplacedOut_args_constructor() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);
        MainWithReplacedOut main = new MainWithReplacedOut(out);
        assertThrows(NullPointerException.class, main::getByteArrayOutputStream);
    }

    @Test
    @Order(8)
    @DisplayName("new MainWithReplacedOut().main(null)")
    void new_MainWithReplacedOut_no_args_mainTest_null() {
        MainWithReplacedOut main = new MainWithReplacedOut();
        main.main(null);
        assertNotNull(main.getByteArrayOutputStream().toString());
        assertFalse(main.getByteArrayOutputStream().toString().isEmpty());
    }

    @Test
    @Order(9)
    @DisplayName("new MainWithReplacedOut().main(new String[0])")
    void new_MainWithReplacedOut_no_args_mainTest_length_0() {
        MainWithReplacedOut main = new MainWithReplacedOut();
        main.main(new String[0]);
        assertNotNull(main.getByteArrayOutputStream().toString());
        assertFalse(main.getByteArrayOutputStream().toString().isEmpty());
    }

    @Test
    @Order(10)
    @DisplayName("new MainWithReplacedOut().main(new String[0])")
    void new_MainWithReplacedOut_no_args_mainTest_1() {
        MainWithReplacedOut main = new MainWithReplacedOut();
        String[] args = {""};
        main.main(args);
        assertNotNull(main.getByteArrayOutputStream().toString());
        assertFalse(main.getByteArrayOutputStream().toString().isEmpty());
    }

    @Test
    @Order(11)
    @DisplayName("StaticPieceFactory, PieceFactory, Piece initiation exception")
    void Main_StaticSheetPieceFactory_initiation_exception() {
        PieceFactory pieceFactory = StaticPieceFactory.getPieceFactory();
        assertThrows(LayoutFactoryNotInitiatedException.class, () -> {
            Piece piece = pieceFactory.getSheetPiece(
                    StaticPieceFactoryTest.DEFAULT_WIDTH,
                    StaticPieceFactoryTest.DEFAULT_HEIGHT,
                    StaticPieceFactoryTest.DEFAULT_POINTS
            );
        });
    }

    @Test
    @Order(12)
    @DisplayName("StaticPieceFactory, PieceFactory, Piece visibility")
    void Main_SheetPiece_visibility() throws SheetSizeException, NegativePiecePointsException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(StaticPieceFactoryTest.DEFAULT_WIDTH + 1, StaticPieceFactoryTest.DEFAULT_HEIGHT + 1);
        PieceFactory pieceFactory = StaticPieceFactory.getPieceFactory();
        Piece piece = pieceFactory.getSheetPiece(
                StaticPieceFactoryTest.DEFAULT_WIDTH,
                StaticPieceFactoryTest.DEFAULT_HEIGHT,
                StaticPieceFactoryTest.DEFAULT_POINTS
        );
    }

    @Test
    @Order(13)
    @DisplayName("StaticLayoutFactory, LayoutFactory, Layout visibility")
    void Main_SheetLayout_visibility() throws SheetSizeException, SheetAmountExceededLimitException, BadCoordinateValueException, LayoutFactoryNotInitiatedException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(StaticPieceFactoryTest.DEFAULT_WIDTH + 1, StaticPieceFactoryTest.DEFAULT_HEIGHT + 1);
        LayoutFactory layoutFactory = StaticLayoutFactory.getLayoutFactory();
        Layout layout = layoutFactory.getLayout();
    }

    @Test
    @Order(99)
    @DisplayName("Example from PDF (input & output data)")
    void new_MainWithReplacedOut_no_args_constructor_main_PDF_example_args_and_output() {
        MainWithReplacedOut main = new MainWithReplacedOut();
        main.main(ARGS);
//        assertEquals(EXPECTED_OUTPUT, main.getByteArrayOutputStream().toString());
    }
}
