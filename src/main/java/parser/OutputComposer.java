package parser;

import cutter.Solution;
import parser.exceptions.NullSolutionException;
import sheet.LayoutVariation;
import sheet.PieceVariation;

public class OutputComposer {
    public static String getOutputString(Solution solution) throws NullSolutionException {
        if(solution == null) throw new NullSolutionException(solution);
        int amountOfPieceVariations = 0;
        StringBuilder pieceVariationsOutput = new StringBuilder();
        for (LayoutVariation layoutVariation : solution.getLayoutVariations()) {
            amountOfPieceVariations += layoutVariation.getPieceVariations().size();
            for (PieceVariation pieceVariation : layoutVariation.getPieceVariations()) {
                pieceVariationsOutput.append(pieceVariationToOutputString(pieceVariation));
            }
        }
        return String.format("%d%s", amountOfPieceVariations, pieceVariationsOutput.toString());
    }

    private static String pieceVariationToOutputString(PieceVariation pieceVariation) {
        return String.format(
                "\r\n%d %s %d %d",
                pieceVariation.getPiece().getId(),
                pieceVariation.getOrientation(),
                pieceVariation.getNorthWesternCoord().getX(),
                pieceVariation.getNorthWesternCoord().getY()
        );
    }
}
