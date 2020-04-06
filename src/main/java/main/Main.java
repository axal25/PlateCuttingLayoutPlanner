package main;

import coords.BadCoordinateValueException;
import cutter.Cutter;
import parser.BadAmountOfInputArgsException;
import parser.InputParser;
import replaced.out.MockSystem;
import sheet.exceptions.*;

public class Main {

    // hides System variable of type System
    // used in MainWithReplacedOut - class used for tests
    public static MockSystem System = new MockSystem(java.lang.System.out);

    public static void main(String[] args) {
        try {
            Cutter cutter = new Cutter(args);
            String output = cutter.cut();
            System.out.println(output);
        } catch (
                LayoutFactoryAlreadyInitiatedException | BadAmountOfInputArgsException | SheetSizeException | NegativePiecePointsException | CalculatedAndInputAmountOfPiecesNotMatchException | SheetAmountExceededLimitException | LayoutFactoryNotInitiatedException | PieceCanNotFitIntoLayoutException | BadCoordinateValueException | CloneNotSupportedException | PieceVariationsNotInitiatedException e
        ) {
            System.out.println(e.getMessage());

            System.out.println();
            e.printStackTrace();
        }
    }
}
