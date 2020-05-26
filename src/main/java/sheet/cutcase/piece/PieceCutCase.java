package sheet.cutcase.piece;

import coords.Coordinate;
import coords.Coordinates;
import coords.RectangleCorners;
import coords.exceptions.BadCoordinateValueException;
import sheet.FreePieceVariation;
import sheet.PieceVariation;
import sheet.cutcase.piece.exceptions.UnsupportedPieceCutCaseException;
import sheet.exceptions.*;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Stream;

public enum PieceCutCase implements PieceCutCaseInterface {
    FPV_AROUND_PV_2_CORNERS {
        @Override
        public PieceVariation getFragmentOfPvInsideFpv(PieceCutter pieceCutter) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, NotAllCornersFoundException {
            Coordinate[] overlappingFragmentCorners = getOverlappingFragmentCornersOf2PVs(pieceCutter.getFreePieceVariation(), pieceCutter.getPieceVariation());
            return FreePieceVariation.getNewFreePieceVariation(overlappingFragmentCorners);
        }
    },
    ALL_FPV_CORNERS_INSIDE_PV {
        @Override
        public PieceVariation getFragmentOfPvInsideFpv(PieceCutter pieceCutter) throws BadCoordinateValueException, SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NotAllCornersFoundException {
            RectangleCorners fragmentRC = new RectangleCorners(pieceCutter.getFreePieceVariation());
            return FreePieceVariation.getNewFreePieceVariation(new Coordinate[]{
                    fragmentRC.getNWCoord(),
                    fragmentRC.getNECoord(),
                    fragmentRC.getSWCoord(),
                    fragmentRC.getSECoord(),
            });
        }
    };


    private static Coordinate[] getOverlappingFragmentCornersOf2PVs(PieceVariation insidePieceVariation, PieceVariation outsidePieceVariation) throws BadCoordinateValueException {
        Coordinate[] outPvCornersInsideInPv = outsidePieceVariation.getCornersInsideOf(insidePieceVariation);
        TreeSet<Coordinate> fragmentCorners = new TreeSet<>();
        RectangleCorners inPvRectangleCorners = new RectangleCorners(insidePieceVariation);
        RectangleCorners outPvRectangleCorners = new RectangleCorners(outsidePieceVariation);
        Arrays.sort(outPvCornersInsideInPv);
        Coordinate[][] suspectedCoords = new Coordinate[2][2];
        if (outPvCornersInsideInPv[0].getX() == outPvCornersInsideInPv[1].getX()) {
            suspectedCoords[0][0] = new Coordinate(inPvRectangleCorners.getNWCoord().getX(), outPvCornersInsideInPv[0].getY());
            suspectedCoords[0][1] = new Coordinate(inPvRectangleCorners.getNWCoord().getX(), outPvCornersInsideInPv[1].getY());
            suspectedCoords[1][0] = new Coordinate(inPvRectangleCorners.getSECoord().getX(), outPvCornersInsideInPv[0].getY());
            suspectedCoords[1][1] = new Coordinate(inPvRectangleCorners.getSECoord().getX(), outPvCornersInsideInPv[1].getY());
            get2RemainingOverlappingFragmentCornerOf2PVs(
                    fragmentCorners,
                    outPvRectangleCorners,
                    suspectedCoords
            );
        } else if (outPvCornersInsideInPv[0].getY() == outPvCornersInsideInPv[1].getY()) {
            suspectedCoords[0][0] = new Coordinate(outPvCornersInsideInPv[0].getX(), inPvRectangleCorners.getNWCoord().getY());
            suspectedCoords[0][1] = new Coordinate(outPvCornersInsideInPv[1].getX(), inPvRectangleCorners.getNWCoord().getY());
            suspectedCoords[1][0] = new Coordinate(outPvCornersInsideInPv[0].getX(), inPvRectangleCorners.getSECoord().getY());
            suspectedCoords[1][1] = new Coordinate(outPvCornersInsideInPv[1].getX(), inPvRectangleCorners.getSECoord().getY());
            get2RemainingOverlappingFragmentCornerOf2PVs(
                    fragmentCorners,
                    outPvRectangleCorners,
                    suspectedCoords
            );
        } else throw new UnsupportedPieceCutCaseException(
                new StringBuilder()
                        .append("It is not possible for 2 insidePieceVariation corners inside another to not have one common")
                        .append(" coordinate (X or Y). Or those 2 corners are not enveloped by this.")
                        .toString(),
                "insidePieceVariation", insidePieceVariation,
                "outsidePieceVariation", outsidePieceVariation
        );
        fragmentCorners.add(outPvCornersInsideInPv[0]);
        fragmentCorners.add(outPvCornersInsideInPv[1]);
        return Coordinates.toArray(fragmentCorners);
    }

    private static void get2RemainingOverlappingFragmentCornerOf2PVs(
            TreeSet<Coordinate> fragmentCorners,
            RectangleCorners outPvRectangleCorners,
            Coordinate[][] suspectedCoords
    ) {
        for (int i = 0; i < suspectedCoords.length; i++)
            for (int j = 0; j < suspectedCoords[i].length; j++)
                if (outPvRectangleCorners.isCoordinateInside(suspectedCoords[i][j]))
                    fragmentCorners.add(suspectedCoords[i][j]);
    }

    public enum ZERO_PV_CORNERS_INSIDE_FPV implements PieceCutCaseInterface {
        INTERSECTING_PV_FPV {
            @Override
            public PieceVariation getFragmentOfPvInsideFpv(PieceCutter pieceCutter) throws BadCoordinateValueException, SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NotAllCornersFoundException {
                RectangleCorners pvRectangleCorners = new RectangleCorners(pieceCutter.getPieceVariation());
                RectangleCorners fpvRectangleCorners = new RectangleCorners(pieceCutter.getFreePieceVariation());
                Coordinate[] overlappingFragmentCorners;
                if (isFirstRcOverBothVerticalSidesOfSecondRc(fpvRectangleCorners, pvRectangleCorners)) {
                    overlappingFragmentCorners = getCornersOfFragmentOverlapping(fpvRectangleCorners, pvRectangleCorners);
                    return FreePieceVariation.getNewFreePieceVariation(overlappingFragmentCorners);
                } else if (isFirstRcOverBothVerticalSidesOfSecondRc(pvRectangleCorners, fpvRectangleCorners)) {
                    overlappingFragmentCorners = getCornersOfFragmentOverlapping(pvRectangleCorners, fpvRectangleCorners);
                } else throw new UnsupportedPieceCutCaseException(
                        String.format(
                                "%s%s",
                                String.format("Neither PieceVariation is over both vertical sides of FreePieceVariation "),
                                String.format("nor is FreePieceVariation over both vertical sides of PieceVariation."),
                                String.format("\r\n\tpieceVariation: %s", pieceCutter.getPieceVariation().toString()),
                                String.format("\r\n\tfreePieceVariation: %s", pieceCutter.getFreePieceVariation().toString())
                        ),
                        "pieceVariation", pieceCutter.getPieceVariation(),
                        "freePieceVariation", pieceCutter.getFreePieceVariation()
                );
                System.out.println(String.format("overlappingFragmentCorners: %s", Coordinates.toString(overlappingFragmentCorners)));
                return FreePieceVariation.getNewFreePieceVariation(overlappingFragmentCorners);
            }

            private boolean isFirstRcOverBothVerticalSidesOfSecondRc(RectangleCorners rc1, RectangleCorners rc2) {
                return rc1.getNWCoord().getY() > rc2.getNWCoord().getY() &&
                        rc1.getSECoord().getY() < rc2.getSECoord().getY() &&
                        rc1.getNWCoord().getX() < rc2.getNWCoord().getX() &&
                        rc1.getSECoord().getX() > rc2.getSECoord().getX();
            }

            private Coordinate[] getCornersOfFragmentOverlapping(RectangleCorners pvRcOverVertical, RectangleCorners pvRcOverHorizontal) throws BadCoordinateValueException {
                Coordinate[] coordinates = new Coordinate[4];
                int westX = pvRcOverHorizontal.getNWCoord().getX();
                int eastX = pvRcOverHorizontal.getSECoord().getX();
                int northY = pvRcOverVertical.getNWCoord().getY();
                int southY = pvRcOverVertical.getSECoord().getY();
                coordinates[0] = new Coordinate(westX, northY);
                coordinates[1] = new Coordinate(eastX, northY);
                coordinates[2] = new Coordinate(eastX, southY);
                coordinates[3] = new Coordinate(westX, southY);
                return coordinates;
            }
        },
        PV_AROUND_FPV_2_CORNERS {
            @Override
            public PieceVariation getFragmentOfPvInsideFpv(PieceCutter pieceCutter) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, NotAllCornersFoundException {
                Coordinate[] overlappingFragmentCorners = getOverlappingFragmentCornersOf2PVs(pieceCutter.getPieceVariation(), pieceCutter.getFreePieceVariation());
                return FreePieceVariation.getNewFreePieceVariation(overlappingFragmentCorners);
            }

        };
    }

    public enum ONE_PV_CORNER_INSIDE_FPV implements PieceCutCaseInterface {
        ONE_OR_TWO_FPV_CORNERS_INSIDE_PV {
            @Override
            public PieceVariation getFragmentOfPvInsideFpv(PieceCutter pieceCutter) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, NotAllCornersFoundException {
                Coordinate[] overlappingFragmentCorners = getCornersOfFragmentOverlapping(pieceCutter.getPieceVariation(), pieceCutter.getFreePieceVariation());
                try {
                    return FreePieceVariation.getNewFreePieceVariation(overlappingFragmentCorners);
                } catch (SheetSizeException e) {
                    System.out.println(String.format("overlappingFragmentCorners: %s", Coordinates.toString(overlappingFragmentCorners)));
                    throw e;
                }
            }

            private Coordinate[] getCornersOfFragmentOverlapping(PieceVariation pieceVariation, FreePieceVariation freePieceVariation) throws BadCoordinateValueException {
                Coordinate[] pvCornersInsidesFpv = pieceVariation.getCornersInsideOf(freePieceVariation);
                Coordinate[] fpvCornersInsidesPv = freePieceVariation.getCornersInsideOf(pieceVariation);
                System.out.println(String.format("pvCornersInsidesFpv: %s", Coordinates.toString(pvCornersInsidesFpv)));
                System.out.println(String.format("fpvCornersInsidesPv: %s", Coordinates.toString(fpvCornersInsidesPv)));
                int westX, eastX, northY, southY;
                westX = getConcatStream(pvCornersInsidesFpv, fpvCornersInsidesPv)
                        .map(Coordinate::getX)
                        .min(Integer::compare)
                        .get();
                eastX = getConcatStream(pvCornersInsidesFpv, fpvCornersInsidesPv)
                        .map(Coordinate::getX)
                        .max(Integer::compare)
                        .get();
                northY = getConcatStream(pvCornersInsidesFpv, fpvCornersInsidesPv)
                        .map(Coordinate::getY)
                        .min(Integer::compare)
                        .get();
                southY = getConcatStream(pvCornersInsidesFpv, fpvCornersInsidesPv)
                        .map(Coordinate::getY)
                        .max(Integer::compare)
                        .get();
                Coordinate[] fragmentCorners = new Coordinate[4];
                fragmentCorners[0] = new Coordinate(westX, northY);
                fragmentCorners[1] = new Coordinate(eastX, northY);
                fragmentCorners[2] = new Coordinate(westX, southY);
                fragmentCorners[3] = new Coordinate(eastX, southY);
                return fragmentCorners;
            }

            private Stream<Coordinate> getConcatStream(Coordinate[] pvCornersInsidesFpv, Coordinate[] fpvCornersInsidesPv) {
                return Stream.concat(
                        Arrays.stream(pvCornersInsidesFpv),
                        Arrays.stream(fpvCornersInsidesPv)
                );
            }
        };
    }
}
