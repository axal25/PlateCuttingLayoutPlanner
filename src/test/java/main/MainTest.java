package main;

import examples.Example;
import org.junit.jupiter.api.*;
import orientation.Orientation;
import replaced.out.MainWithReplacedOut;
import sheet.*;
import sheet.exceptions.*;
import solution.Solution;
import test.utils.StringComparison;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest implements StaticPieceFactory.InterfaceTestingStaticPieceFactory, StaticLayoutFactory.InterfaceTestingStaticSheetFactory, Piece.InterfaceTestingPieceSortStrategy {
    /**
     * String[] args:
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
            "6", "6", "9", // id: 0
            "7", "4", "3", // id: 1
            "2", "5", "5", // id: 2
            "2", "3", "7", // id: 3
            "5", "8", "4", // id: 4
            "3", "2", "1", // id: 5
            "4", "2", "1", // id: 6
            "4", "2", "1"  // id: 7
    };
    /**
     * String output:
     * 5       - mamy 5 blach wynikowych
     * 1 H 0 0 - w punkcie(x,y): 0,0 umieszczamy horyzontalnie płytę o id 1, wymiary 7x4 za 3 punkty
     * 0 H 0 4 - w punkcie(x,y): 0,4 umieszczamy horyzontalnie płytę o id 0, wymiary 6x6 za 9 punktów
     * 3 V 7 3 - w punkcie(x,y): 7,3 umieszczamy wertykalnie płytę o id 3, wymiary 2x3 za 7 punktów
     * 6 H 6 6 - w punkcie(x,y): 6,6 umieszczamy horyzontalnie płytę o id 6, wymiary 2x4 za 1 punkt
     * 7 H 6 8 - w punkcie(x,y): 6,8 umieszczamy horyzontalnie płytę o id 7, wymiary 2x4 za 1 punkt
     */
    public static String EXPECTED_OUTPUT = new StringBuilder()
            .append("5\r\n")
            .append("1 H 0 0\r\n")
            .append("0 H 0 4\r\n")
            .append("3 V 7 3\r\n")
            .append("6 H 6 6\r\n")
            .append("7 H 6 8")
            .toString();


    public static final Example shortExample = new Example(
            new Example.InputData(
                    new Example.InputData.LayoutData(3, 2),
                    3,
                    new Example.InputData.PieceInputData(1, 2, 1),
                    new Example.InputData.PieceInputData(2, 1, 2),
                    new Example.InputData.PieceInputData(1, 2, 0)
            ),
            new Example.OutputData(
                    3,
                    new Example.OutputData.PieceOutputData(1, Orientation.V, 0, 0),
                    new Example.OutputData.PieceOutputData(0, Orientation.V, 1, 0),
                    new Example.OutputData.PieceOutputData(2, Orientation.V, 2, 0)
            )
    );

    @BeforeEach
    void setUp() {
        this.resetLayoutFactory();
        this.resetPieceFactory();
        this.setPieceSortStrategy(Piece.DEFAULT_PIECE_SORT_STRATEGY);
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
        assertNotNull(byteArrayOutputStream);
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
        assertNotNull(byteArrayOutputStream);
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
        assertNotNull(byteArrayOutputStream);
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
        assertNotNull(main.getByteArrayOutputStream());
        assertFalse(main.getByteArrayOutputStream().toString().isEmpty());
    }

    @Test
    @Order(9)
    @DisplayName("new MainWithReplacedOut().main(new String[0])")
    void new_MainWithReplacedOut_no_args_mainTest_length_0() {
        MainWithReplacedOut main = new MainWithReplacedOut();
        main.main(new String[0]);
        assertNotNull(main.getByteArrayOutputStream());
        assertFalse(main.getByteArrayOutputStream().toString().isEmpty());
    }

    @Test
    @Order(10)
    @DisplayName("new MainWithReplacedOut().main(new String[0])")
    void new_MainWithReplacedOut_no_args_mainTest_1() {
        MainWithReplacedOut main = new MainWithReplacedOut();
        String[] args = {""};
        main.main(args);
        assertNotNull(main.getByteArrayOutputStream());
        assertFalse(main.getByteArrayOutputStream().toString().isEmpty());
    }

    @Test
    @Order(11)
    @DisplayName("StaticPieceFactory, PieceFactory, Piece initiation exception")
    void Main_StaticSheetPieceFactory_initiation_exception() {
        PieceFactory pieceFactory = StaticPieceFactory.getPieceFactory();
        assertThrows(LayoutFactoryNotInitiatedException.class, () -> {
            Piece piece = pieceFactory.getPiece(
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
        Piece piece = pieceFactory.getPiece(
                StaticPieceFactoryTest.DEFAULT_WIDTH,
                StaticPieceFactoryTest.DEFAULT_HEIGHT,
                StaticPieceFactoryTest.DEFAULT_POINTS
        );
    }

    @Test
    @Order(13)
    @DisplayName("StaticLayoutFactory, LayoutFactory, Layout visibility")
    void Main_SheetLayout_visibility() throws SheetSizeException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(StaticPieceFactoryTest.DEFAULT_WIDTH + 1, StaticPieceFactoryTest.DEFAULT_HEIGHT + 1);
        LayoutFactory layoutFactory = StaticLayoutFactory.getLayoutFactory();
        Layout layout = layoutFactory.getLayout();
    }

    @Test
    @Order(14)
    @DisplayName("Catch BadAmountOfInputArgsException")
    void Main_catch_badAmountOfInputArgsException() throws SheetSizeException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, LayoutFactoryAlreadyInitiatedException {
        MainWithReplacedOut main = new MainWithReplacedOut();
        String[] args = {"1"};
        main.main(args);
        assertNotNull(main.getByteArrayOutputStream());
        assertFalse(main.getByteArrayOutputStream().toString().isEmpty());
        final String expectedExceptionMessage = "InputParser.validateLengthArgsForSheetLayoutFactory(String[] args) Amount of arguments must be equal to 2 (args.length == 2) or be divisible by 3 (args.length % 3 == 0). Amount of arguments detected: 1. So args.length % 3 = 1.\n";
        assertEquals(expectedExceptionMessage, main.getByteArrayOutputStream().toString());
    }

    @Test
    @Order(15)
    @DisplayName("Catch runtime exception")
    void Main_catch_runtime_exception() throws SheetSizeException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, LayoutFactoryAlreadyInitiatedException {
        MainWithReplacedOut main = new MainWithReplacedOut();
        String[] args = {"A", "1", "2"};
        main.main(args);
        assertNotNull(main.getByteArrayOutputStream());
        assertFalse(main.getByteArrayOutputStream().toString().isEmpty());
        final String expectedExceptionMessage = "Could not parse sheet layout data. For input string: \"A\"\n";
        assertEquals(expectedExceptionMessage, main.getByteArrayOutputStream().toString());
    }

    @Test
    @Order(16)
    @DisplayName("Bad behaviour - not sure of result")
    void Main_causing_error() {
//        String[] args = new String[2 + 1 + 3 * 50];
//        int layoutWidth = 100;
//        int layoutHeight = 100;
//        int amountOfPieces = 50;
//        args[0] = String.valueOf(layoutWidth);
//        args[1] = String.valueOf(layoutHeight);
//        args[2] = String.valueOf(amountOfPieces);
//        for (int i = 1, j = 3; i <= amountOfPieces && j + 2 < args.length; i++, j += 3) {
//            int pieceWidth = i;
//            int pieceHeight = i;
//            int piecePoints = i;
//            args[j] = String.valueOf(pieceWidth);
//            args[j + 1] = String.valueOf(pieceHeight);
//            args[j + 2] = String.valueOf(piecePoints);
//        }
//        MainWithReplacedOut main = new MainWithReplacedOut();
//        main.main(args);
//        assertNotNull(main.getByteArrayOutputStream());
//        assertFalse(main.getByteArrayOutputStream().toString().isEmpty());
//        assertEquals("Not an error", main.getByteArrayOutputStream().toString());
        assertFalse(true);
    }

    @Test
    @Order(17)
    @DisplayName("Pieces not fitting inside layout (too big pieces for layout)")
    void Main_piece_not_fitting_inside_layout() {
        String[] args = new String[2 + 1 + 3 * 50];
        int layoutWidth = 10;
        int layoutHeight = 10;
        int amountOfPieces = 50;
        args[0] = String.valueOf(layoutWidth);
        args[1] = String.valueOf(layoutHeight);
        args[2] = String.valueOf(amountOfPieces);
        for (int i = 1, j = 3; i <= amountOfPieces && j + 2 < args.length; i++, j += 3) {
            int pieceWidth = i;
            int pieceHeight = i;
            int piecePoints = i;
            args[j] = String.valueOf(pieceWidth);
            args[j + 1] = String.valueOf(pieceHeight);
            args[j + 2] = String.valueOf(piecePoints);
        }
        MainWithReplacedOut main = new MainWithReplacedOut();
        main.main(args);
        assertNotNull(main.getByteArrayOutputStream());
        assertFalse(main.getByteArrayOutputStream().toString().isEmpty());
        String expectedOutput = String.format("%s%s%s",
                "Piece.validate() Piece cannot fit into Layout, because Piece is bigger than Layout. \r\n",
                "Piece width: 11, height: 11. \r\n",
                "Layout width: 10, height: 10.\n"
        );
        System.out.println(StringComparison.compare(expectedOutput, main.getByteArrayOutputStream().toString()));
        assertEquals(
                expectedOutput,
                main.getByteArrayOutputStream().toString()
        );
    }

    @Test
    @Order(18)
    @DisplayName("short example (input & output data)")
    void new_MainWithReplacedOut_no_args_constructor_main_shortExample() {
        MainWithReplacedOut main = new MainWithReplacedOut();
        main.main(shortExample.inputData.toArgs());
        assertEquals(Piece.DEFAULT_PIECE_SORT_STRATEGY, Piece.getPieceSortStrategy());
        assertEquals(Piece.DEFAULT_PIECE_SORT_STRATEGY, this.getPieceSortStrategy());
        System.out.println(
                String.format(
                        "StringComparison.compare: \r\n%s",
                        StringComparison.compare(
                                shortExample.outputData.toOutputString(),
                                main.getByteArrayOutputStream().toString()
                        )
                )
        );
        assertEquals(shortExample.outputData.toOutputString(), main.getByteArrayOutputStream().toString());
    }

    @Test
    @Order(99)
    @DisplayName("Example from PDF (input & output data)")
    void new_MainWithReplacedOut_no_args_constructor_main_PDF_example_args_and_output() {
//        MainWithReplacedOut main = new MainWithReplacedOut();
//        main.main(ARGS);
//        assertEquals(Piece.DEFAULT_PIECE_SORT_STRATEGY, Piece.getPieceSortStrategy());
//        assertEquals(Piece.DEFAULT_PIECE_SORT_STRATEGY, this.getPieceSortStrategy());
//        assertEquals(EXPECTED_OUTPUT, main.getByteArrayOutputStream().toString());
        assertFalse(true);
    }

    public static int getAmountOfPieceVariations(Solution solution) {
        int amountOfPieceVariations = 0;
        for (LayoutVariation layoutVariation : solution.getLayoutVariations()) {
            amountOfPieceVariations += layoutVariation.getPieceVariations().size();
        }
        return amountOfPieceVariations;
    }
}
