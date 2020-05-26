package parser;

import parser.exceptions.BadAmountOfInputArgsException;
import sheet.*;
import sheet.exceptions.*;

public class InputParser {

    public static void initSheetLayoutFactory(String[] args) throws BadAmountOfInputArgsException, LayoutFactoryAlreadyInitiatedException, NumberFormatException, SheetSizeException {
        validateLengthArgsForSheetLayoutFactory(args);
        parseAndInitSheetLayoutFactoryData(args);
    }

    private static void validateLengthArgsForSheetLayoutFactory(String[] args) throws BadAmountOfInputArgsException {
        if(args == null) return;
        final String functionName = "validateLengthArgsForSheetLayoutFactory(String[] args)";
        final int modulo = args.length % 3;
        final String division = "args.length % 3";
        if(args.length != 2 && modulo != 0) throw new BadAmountOfInputArgsException(
                InputParser.class.getSimpleName(),
                functionName,
                new StringBuilder()
                        .append("Amount of arguments must be equal to 2 (args.length == 2) or be divisible by 3 (")
                        .append(division)
                        .append(" == 0). Amount of arguments detected: ")
                        .append(args.length)
                        .append(". So ")
                        .append(division)
                        .append(" = ")
                        .append(modulo)
                        .append(".")
                        .toString()
        );
    }

    private static void parseAndInitSheetLayoutFactoryData(String[] args) throws LayoutFactoryAlreadyInitiatedException, NumberFormatException, SheetSizeException {
        if(args == null || args.length == 0) return ;
        int sheetLayoutX, sheetLayoutY;
        try {
            sheetLayoutX = Integer.parseInt(args[0]);
            sheetLayoutY = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(
                    new StringBuilder().append("Could not parse sheet layout data. ").append(e.getMessage()).toString()
            );
        }
        StaticLayoutFactory.initLayoutFactor(sheetLayoutX, sheetLayoutY);
    }

    public static Piece[] getSheetPieces(String[] args) throws BadAmountOfInputArgsException, NegativePiecePointsException, SheetSizeException, SheetAmountExceededLimitException, CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        validateLengthArgsForSheetPieces(args);
        return parseSheetPieces(args);
    }

    private static void validateLengthArgsForSheetPieces(String[] args) throws BadAmountOfInputArgsException {
        if(args == null) return;
        final String functionName = "validateLengthArgsForSheetPieces(String[] args)";
        final int modulo = args.length % 3;
        final String moduloOperation = "args.length % 3";
        if(modulo != 0) throw new BadAmountOfInputArgsException(
                InputParser.class.getSimpleName(),
                functionName,
                new StringBuilder()
                        .append("Amount of arguments must be divisible by 3. (")
                        .append(moduloOperation)
                        .append(" == 0). Amount of arguments detected: ")
                        .append(args.length)
                        .append(". So ")
                        .append(moduloOperation)
                        .append(" = ")
                        .append(modulo)
                        .append(".")
                        .toString()
        );
    }

    /**
     * Parses args arguments with indexes greater than 3 for Piece(s) constructor(s) data.
     * @param args program input.
     * @return array of all SheetPieces from program input.
     * @throws NumberFormatException Integer parsing from String exception. All of parsing operations are in separate line for debugging ease.
     * @throws NegativePiecePointsException
     * @throws SheetSizeException
     */
    private static Piece[] parseSheetPieces(String[] args) throws NumberFormatException, NegativePiecePointsException, SheetSizeException, SheetAmountExceededLimitException, CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        if(args == null || args.length == 0 || args.length == 3) return new Piece[0];
        final int amountOfSheetPieces = getAmountOfSheetPieces(args);
        Piece[] pieces = new Piece[amountOfSheetPieces];
        int sheetPieceIWidth, sheetPieceIHeight, sheetPieceIPoints;
        for (int i = 3, j = 0; i < args.length && j < pieces.length; i+=3, j++) {
            try {
                sheetPieceIWidth = Integer.parseInt(args[i]);
                sheetPieceIHeight = Integer.parseInt(args[i + 1]);
                sheetPieceIPoints = Integer.parseInt(args[i + 2]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException(
                        new StringBuilder().append("Could not parse sheet piece. ").append(e.getMessage()).toString()
                );
            }
            pieces[j] = StaticPieceFactory.getPieceFactory().getPiece(
                    sheetPieceIWidth,
                    sheetPieceIHeight,
                    sheetPieceIPoints
            );
        }
        return pieces;
    }

    private static int getAmountOfSheetPieces(String[] args) throws CalculatedAndInputAmountOfPiecesNotMatchException, NumberFormatException {
        final String calculateAmountOfSheetPiecesFormula = "(args.length-3)/3";
        final int calculatedAmountOfSheetPieces = (args.length-3)/3;
        int inputAmountOfSheetPieces;
        try{
            inputAmountOfSheetPieces = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(
                    new StringBuilder().append("Could not parse input amount of sheet pieces. ").append(e.getMessage()).toString()
            );
        }
        if(calculatedAmountOfSheetPieces != inputAmountOfSheetPieces) {
            throw new CalculatedAndInputAmountOfPiecesNotMatchException(
                    InputParser.class.getSimpleName(),
                    "getAmountOfSheetPieces(String[] args)",
                    new StringBuilder()
                            .append("3rd argument (input amount of sheet pieces) and amount of arguments needed for initiation of sheet pieces (calculated) is not equal. ")
                            .append("Detected input amount of sheet pieces: ")
                            .append(inputAmountOfSheetPieces)
                            .append(". Detected calculated amount of sheet pieces: ")
                            .append(calculatedAmountOfSheetPieces)
                            .append(". Calculation formula: ")
                            .append(calculateAmountOfSheetPiecesFormula)
                            .append(".")
                            .toString()
            );
        }
        return calculatedAmountOfSheetPieces;
    }
}
