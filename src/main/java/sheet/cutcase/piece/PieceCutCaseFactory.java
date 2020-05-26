package sheet.cutcase.piece;

import coords.Coordinates;
import coords.exceptions.BadCoordinateValueException;
import sheet.cutcase.piece.exceptions.PieceCutCaseFactoryInterface;
import sheet.cutcase.piece.exceptions.UnsupportedPieceCutCaseException;

public enum PieceCutCaseFactory implements PieceCutCaseFactoryInterface {
    ZERO_PV_CORNERS_INSIDE_FPV {
        public PieceCutCaseInterface getPieceCutCase(PieceCutter pieceCutter) throws BadCoordinateValueException {
            pieceCutter.setFpvCornersInsidePv(pieceCutter.getFreePieceVariation().getCornersInsideOf(pieceCutter.getPieceVariation()));
            switch (pieceCutter.getFpvCornersInsidePv().length) {
                case 0:
                    return PieceCutCase.ZERO_PV_CORNERS_INSIDE_FPV.INTERSECTING_PV_FPV;
                case 2:
                    return PieceCutCase.ZERO_PV_CORNERS_INSIDE_FPV.PV_AROUND_FPV_2_CORNERS;
                case 4:
                    return PieceCutCase.ALL_FPV_CORNERS_INSIDE_PV;
                default:
                    throw new UnsupportedPieceCutCaseException(
                            String.format(
                                    "%s%s%s%s%s%s%s",
                                    String.format("It is not possible for this PieceVariation to be partially inside other"),
                                    String.format(" FreePieceVariation with 0 corners and for the other FreePieceVariation to be "),
                                    String.format("inside this PieceVariation with neither 0 nor 2 nor 4 corners."),
                                    String.format("\r\n\tAmount of PieceVariation corners inside FreePieceVariation: %d", pieceCutter.getPvCornersInsideFpv().length),
                                    String.format("\r\n\tPieceVariation corners inside FreePieceVariation: %s", Coordinates.toString(pieceCutter.getPvCornersInsideFpv())),
                                    String.format("\r\n\tAmount of FreePieceVariation corners inside PieceVariation: %d", pieceCutter.getFpvCornersInsidePv().length),
                                    String.format("\r\n\tFreePieceVariation corners inside PieceVariation: %s", Coordinates.toString(pieceCutter.getFpvCornersInsidePv()))
                            ),
                            "pieceVariation", pieceCutter.getPieceVariation(),
                            "freePieceVariation", pieceCutter.getFreePieceVariation()
                    );
            }
        }
    }, ONE_PV_CORNER_INSIDE_FPV {
        @Override
        public PieceCutCaseInterface getPieceCutCase(PieceCutter pieceCutter) throws BadCoordinateValueException {
            pieceCutter.setFpvCornersInsidePv(pieceCutter.getFreePieceVariation().getCornersInsideOf(pieceCutter.getPieceVariation()));
            switch (pieceCutter.getFpvCornersInsidePv().length) {
                case 1:
                case 2:
                    return PieceCutCase.ONE_PV_CORNER_INSIDE_FPV.ONE_OR_TWO_FPV_CORNERS_INSIDE_PV;
                case 4:
                    return PieceCutCase.ALL_FPV_CORNERS_INSIDE_PV;
                default:
                    throw new UnsupportedPieceCutCaseException(
                            String.format(
                                    "%s%s%s%s%s%s%s",
                                    String.format("It is not possible for this PieceVariation to be partially inside other"),
                                    String.format(" FreePieceVariation with 1 corner and for the other FreePieceVariation to be "),
                                    String.format("inside this PieceVariation with neither 1 nor 4 corners."),
                                    String.format("\r\n\tAmount of PieceVariation corners inside FreePieceVariation: %d", pieceCutter.getPvCornersInsideFpv().length),
                                    String.format("\r\n\tPieceVariation corners inside FreePieceVariation: %s", Coordinates.toString(pieceCutter.getPvCornersInsideFpv())),
                                    String.format("\r\n\tAmount of FreePieceVariation corners inside PieceVariation: %d", pieceCutter.getFpvCornersInsidePv().length),
                                    String.format("\r\n\tFreePieceVariation corners inside PieceVariation: %s", Coordinates.toString(pieceCutter.getFpvCornersInsidePv()))
                            ),
                            "pieceVariation", pieceCutter.getPieceVariation(),
                            "freePieceVariation", pieceCutter.getFreePieceVariation()
                    );
            }
        }
    };

    public static PieceCutCaseInterface getNewPieceCutCase(PieceCutter pieceCutter) throws BadCoordinateValueException {
        switch (pieceCutter.getPvCornersInsideFpv().length) {
            case 0:
                System.out.println("pvCornersInsideFpv.length == 0");
                return PieceCutCaseFactory.ZERO_PV_CORNERS_INSIDE_FPV.getPieceCutCase(pieceCutter);
            case 1:
                System.out.println("pvCornersInsideFpv.length == 1");
                return PieceCutCaseFactory.ONE_PV_CORNER_INSIDE_FPV.getPieceCutCase(pieceCutter);
            case 2:
                System.out.println("pvCornersInsideFpv.length == 2");
                return PieceCutCase.FPV_AROUND_PV_2_CORNERS;
            default:
                System.out.println(String.format("pvCornersInsideFpv.length == default (%d)", pieceCutter.getPvCornersInsideFpv().length));
                throw new UnsupportedPieceCutCaseException(
                        String.format(
                                "%s%s%s%s",
                                String.format("It is not possible for this PieceVariation to be partially inside "),
                                String.format("other FreePieceVariation with more than 2 corners. "),
                                String.format("\r\n\tAmount of PieceVariation corners inside FreePieceVariation: %d", pieceCutter.getPvCornersInsideFpv().length),
                                String.format("\r\n\tPieceVariation corners inside FreePieceVariation: %d", Coordinates.toString(pieceCutter.getPvCornersInsideFpv()))
                        ),
                        "pieceVariation", pieceCutter.getPieceVariation(),
                        "freePieceVariation", pieceCutter.getFreePieceVariation()
                );
        }
    }
}
