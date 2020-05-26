package sheet.cutcase.piece.exceptions;

import sheet.PieceVariation;

public class UnsupportedPieceCutCaseException extends UnsupportedOperationException {
    public UnsupportedPieceCutCaseException(
            String msg, Throwable cause,
            String varName1, PieceVariation pieceVariation1,
            String varName2, PieceVariation pieceVariation2
    ) {
        super(
                String.format("%s%s",
                        msg,
                        genStaticMsgPart(
                                varName1, pieceVariation1,
                                varName2, pieceVariation2
                        )
                ),
                cause
        );
    }

    public UnsupportedPieceCutCaseException(
            String msg,
            String varName1, PieceVariation pieceVariation1,
            String varName2, PieceVariation pieceVariation2
    ) {
        this(msg, null, varName1, pieceVariation1, varName2, pieceVariation2);
    }

    private static String genStaticMsgPart(
            String varName1, PieceVariation pieceVariation1,
            String varName2, PieceVariation pieceVariation2
    ) {
        return String.format(
                "%s%s",
                String.format("\r\n\t%s: %s", varName1, pieceVariation1),
                String.format("\r\n\t%s: %s", varName2, pieceVariation2)
        );
    }
}
