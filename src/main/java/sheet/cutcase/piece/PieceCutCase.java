package sheet.cutcase.piece;

import coords.Coordinate;
import coords.RectangleCorners;
import coords.exceptions.BadCoordinateValueException;
import sheet.FreePieceVariation;
import sheet.PieceVariation;
import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.NegativePiecePointsException;
import sheet.exceptions.PieceCanNotFitIntoLayoutException;
import sheet.exceptions.SheetSizeException;
import utils.collection.TreeSetUtils;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Cuts up pieceVariation to the fragment inside freePieceVariation, so this fragment can be applied to FreePieceCutCase
 */
public enum PieceCutCase implements PieceCutCaseInterface {
    PV_AROUND_FPV_2_CORNERS {

        @Override
        public PieceVariation getFragmentInsideOther(PieceVariation pieceVariation, FreePieceVariation freePieceVariation) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
            Coordinate[] overlappingFragmentCorners = getCornersOfFragmentOf1stPVInside2ndPV(pieceVariation, freePieceVariation);
            return FreePieceVariation.getNewFreePieceVariation(overlappingFragmentCorners);
        }

    }, FPV_AROUND_PV_2_CORNERS {

        @Override
        public PieceVariation getFragmentInsideOther(PieceVariation pieceVariation, FreePieceVariation freePieceVariation) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
            Coordinate[] overlappingFragmentCorners = getCornersOfFragmentOf1stPVInside2ndPV(freePieceVariation, pieceVariation);
            return FreePieceVariation.getNewFreePieceVariation(overlappingFragmentCorners);
        }

    }, ONE_CORNER_INSIDE_EACH {

        @Override
        public PieceVariation getFragmentInsideOther(PieceVariation pieceVariation, FreePieceVariation freePieceVariation) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
            Coordinate[] overlappingFragmentCorners = getCornersOfFragmentOverlapping(pieceVariation, freePieceVariation);
            return FreePieceVariation.getNewFreePieceVariation(overlappingFragmentCorners);
        }

        private Coordinate[] getCornersOfFragmentOverlapping(PieceVariation pieceVariation, FreePieceVariation freePieceVariation) throws BadCoordinateValueException {
            Coordinate[] pvCornerInsideFpv = pieceVariation.getCornersInsideOf(freePieceVariation);
            Coordinate[] fpvCornerInsidePv = freePieceVariation.getCornersInsideOf(pieceVariation);
            Coordinate[] fragmentCorners = new Coordinate[4];
            fragmentCorners[0] = pvCornerInsideFpv[0];
            fragmentCorners[1] = fpvCornerInsidePv[0];
            fragmentCorners[2] = new Coordinate(pvCornerInsideFpv[0].getX(), fpvCornerInsidePv[0].getY());
            fragmentCorners[3] = new Coordinate(fpvCornerInsidePv[0].getX(), pvCornerInsideFpv[0].getY());
            return fragmentCorners;
        }

    };

    public static PieceCutCase getNewPieceCutCase(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) throws BadCoordinateValueException {
        Coordinate[] pvCornersInsideFpv = pieceVariation.getCornersInsideOf(freePieceVariation);
        switch (pvCornersInsideFpv.length) {
            case 0:
                return PV_AROUND_FPV_2_CORNERS;
            case 1:
                return ONE_CORNER_INSIDE_EACH;
            case 2:
                return FPV_AROUND_PV_2_CORNERS;
            default:
                throw new UnsupportedOperationException(
                        new StringBuilder()
                                .append("It is not possible for this PieceVariation to be partially inside another")
                                .append(" PieceVariation with more than 2 corners. Amount of PieceVariation corners inside FreePieceVariation: ")
                                .append(pvCornersInsideFpv)
                                .toString()
                );
        }
    }

    private static Coordinate[] getCornersOfFragmentOf1stPVInside2ndPV(PieceVariation insidePieceVariation, PieceVariation outsidePieceVariation) throws BadCoordinateValueException {
        Coordinate[] outPvCornersInsideInPv = outsidePieceVariation.getCornersInsideOf(insidePieceVariation);
        TreeSet<Coordinate> fragmentCorners = new TreeSet<>();
        RectangleCorners inPvRectangleCorners = new RectangleCorners(insidePieceVariation);
        RectangleCorners outPvRectangleCorners = new RectangleCorners(outsidePieceVariation);
        Arrays.sort(outPvCornersInsideInPv);
        Coordinate suspectedCoord = null;
        if( outPvCornersInsideInPv[0].getX() == outPvCornersInsideInPv[1].getX() ) {
            suspectedCoord = new Coordinate(inPvRectangleCorners.getNWCoord().getX(), outPvCornersInsideInPv[0].getY());
            if(outPvRectangleCorners.isCoordinateInside(suspectedCoord)) {
                fragmentCorners.add(suspectedCoord);
                suspectedCoord = new Coordinate(inPvRectangleCorners.getNWCoord().getX(), outPvCornersInsideInPv[1].getY());
                fragmentCorners.add(suspectedCoord);
            } else {
                suspectedCoord = new Coordinate(inPvRectangleCorners.getSECoord().getX(), outPvCornersInsideInPv[0].getY());
                if (outPvRectangleCorners.isCoordinateInside(suspectedCoord)) {
                    fragmentCorners.add(suspectedCoord);
                    suspectedCoord = new Coordinate(inPvRectangleCorners.getSECoord().getX(), outPvCornersInsideInPv[1].getY());
                    fragmentCorners.add(suspectedCoord);
                }
            }
        } else if( outPvCornersInsideInPv[0].getY() == outPvCornersInsideInPv[1].getY() ) {
            suspectedCoord = new Coordinate(outPvCornersInsideInPv[0].getX(), inPvRectangleCorners.getNWCoord().getY());
            if(outPvRectangleCorners.isCoordinateInside(suspectedCoord)) {
                fragmentCorners.add(suspectedCoord);
                suspectedCoord = new Coordinate(outPvCornersInsideInPv[1].getX(), inPvRectangleCorners.getNWCoord().getY());
                fragmentCorners.add(suspectedCoord);
            } else {
                suspectedCoord = new Coordinate(outPvCornersInsideInPv[0].getX(), inPvRectangleCorners.getSECoord().getY());
                if (outPvRectangleCorners.isCoordinateInside(suspectedCoord)) {
                    fragmentCorners.add(suspectedCoord);
                    suspectedCoord = new Coordinate(outPvCornersInsideInPv[1].getX(), inPvRectangleCorners.getSECoord().getY());
                    fragmentCorners.add(suspectedCoord);
                }
            }
        } else throw new UnsupportedOperationException(
                new StringBuilder()
                        .append("It is not possible for 2 insidePieceVariation corners inside another to not have one common")
                        .append(" coordinate (X or Y). Or those 2 corners are not enveloped by this.")
                        .toString()
        );
        fragmentCorners.add(outPvCornersInsideInPv[0]);
        fragmentCorners.add(outPvCornersInsideInPv[1]);
        return TreeSetUtils.treeSetToArray(fragmentCorners);
    }
}
