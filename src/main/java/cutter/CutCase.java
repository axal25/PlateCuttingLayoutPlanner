package cutter;

import coords.exceptions.BadCoordinateValueException;
import coords.Coordinate;
import coords.RectangleCorners;
import cutter.exceptions.CutCaseNullArgumentException;
import sheet.FreePieceVariation;
import sheet.PieceVariation;
import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.NegativePiecePointsException;
import sheet.exceptions.PieceCanNotFitIntoLayoutException;
import sheet.exceptions.SheetSizeException;

import java.util.Arrays;
import java.util.TreeSet;

public enum CutCase implements CutCaseInterface {
    FOUR_OVERLAPPING_CORNERS{
        @Override
        public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) {
            return new TreeSet<>();
        }
    }, TWO_OVERLAPPING_CORNERS{
        @Override
        public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) throws CutCaseNullArgumentException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException {
            validateArguments(overriddenFunctionName, freePieceVariation, pieceVariation);
            Coordinate[] pv4NotOverlappingCorners = pieceVariation.getXNotOverlappingCornersOfBoth(freePieceVariation, 4);
            TreeSet<FreePieceVariation> cutUpFreePieceVariations = new TreeSet<>();
            cutUpFreePieceVariations.add(getFreePieceVariationFromFourNotOverlappingCorners(pv4NotOverlappingCorners, freePieceVariation));
            return cutUpFreePieceVariations;
        }

        private FreePieceVariation getFreePieceVariationFromFourNotOverlappingCorners(Coordinate[] fourNotOverlappingCorners, FreePieceVariation freePieceVariation) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
            return getNewFreePieceVariation(fourNotOverlappingCorners, freePieceVariation.getPiece().getId());
        }
    }, ONE_OVERLAPPING_CORNER{
        @Override
        public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) throws BadCoordinateValueException, CutCaseNullArgumentException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException {
            validateArguments(overriddenFunctionName, freePieceVariation, pieceVariation);
            Coordinate[] fpv3NotOverlappingCorners = freePieceVariation.getXNotOverlappingCornersOfThisOnly(pieceVariation, 3);
            Coordinate[] pv3NotOverlappingCorners = pieceVariation.getXNotOverlappingCornersOfThisOnly(freePieceVariation, 3);
            return getFreePieceVariationsFromThreeNotOverlappingCorners(fpv3NotOverlappingCorners, pv3NotOverlappingCorners, freePieceVariation);
        }

        private TreeSet<FreePieceVariation> getFreePieceVariationsFromThreeNotOverlappingCorners(
                Coordinate[] fpv3NotOverlappingCorners,
                Coordinate[] pv3NotOverlappingCorners,
                FreePieceVariation freePieceVariation
        ) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
            TreeSet<FreePieceVariation> cutUpFreePieceVariations = new TreeSet<>();
            Coordinate[] fpvNotNextToEachOtherNotOverlapping = get2CornersNotNextToEachOther(fpv3NotOverlappingCorners);
            Coordinate[] pvNotNextToEachOtherNotOverlapping = get2CornersNotNextToEachOther(pv3NotOverlappingCorners);
            Coordinate fpvMiddleNotOverlapping = get1CornerBetween2Others(fpv3NotOverlappingCorners);
            Coordinate[] fpv1Corners = new Coordinate[4];
            Coordinate[] fpv2Corners = new Coordinate[4];
            fpv1Corners[0] = fpvMiddleNotOverlapping;
            fpv2Corners[0] = fpvMiddleNotOverlapping;
            fpv1Corners[1] = pvNotNextToEachOtherNotOverlapping[0];
            fpv2Corners[1] = pvNotNextToEachOtherNotOverlapping[1];
            fpv1Corners = set3rdCornerWithXorYEqualToFPVNotNextToEachOther(fpvNotNextToEachOtherNotOverlapping, fpv1Corners);
            fpv2Corners = set3rdCornerWithXorYEqualToFPVNotNextToEachOther(fpvNotNextToEachOtherNotOverlapping, fpv2Corners);
            fpv1Corners = set4thCorner(fpv1Corners);
            fpv2Corners = set4thCorner(fpv2Corners);
            Arrays.sort(fpv1Corners);
            Arrays.sort(fpv2Corners);
            FreePieceVariation fpv1 = getNewFreePieceVariation(fpv1Corners, freePieceVariation.getPiece().getId());
            cutUpFreePieceVariations.add(fpv1);
            FreePieceVariation fpv2 = getNewFreePieceVariation(fpv2Corners, freePieceVariation.getPiece().getId()+1);
            cutUpFreePieceVariations.add(fpv2);
            return cutUpFreePieceVariations;
        }

        private Coordinate[] get2CornersNotNextToEachOther(Coordinate[] pv3NotOverlappingCorners) {
            for (int i = 0; i < pv3NotOverlappingCorners.length; i++) {
                for (int j = i+1; j < pv3NotOverlappingCorners.length; j++) {
                    if(
                            pv3NotOverlappingCorners[i].getX() != pv3NotOverlappingCorners[j].getX() &&
                            pv3NotOverlappingCorners[i].getY() != pv3NotOverlappingCorners[j].getY()
                    ) {
                        Coordinate[] cornersNotNextToEachOther = new Coordinate[2];
                        cornersNotNextToEachOther[0] = pv3NotOverlappingCorners[i];
                        cornersNotNextToEachOther[1] = pv3NotOverlappingCorners[j];
                        return cornersNotNextToEachOther;
                    }
                }
            }
            return null;
        }

        private Coordinate get1CornerBetween2Others(Coordinate[] pv3NotOverlappingCorners) {
            for (int i = 0; i < pv3NotOverlappingCorners.length; i++) {
                boolean hasThisCornerHaveXorYEqualToAnother = true;
                for (int j = i+1; j < pv3NotOverlappingCorners.length; j++) {
                    if(
                            pv3NotOverlappingCorners[i].getX() != pv3NotOverlappingCorners[j].getX() &&
                            pv3NotOverlappingCorners[i].getY() != pv3NotOverlappingCorners[j].getY()
                    ) {
                        hasThisCornerHaveXorYEqualToAnother = false;
                    }
                }
                if(hasThisCornerHaveXorYEqualToAnother) return pv3NotOverlappingCorners[i];
            }
            return null;
        }

        private Coordinate[] set3rdCornerWithXorYEqualToFPVNotNextToEachOther(Coordinate[] fpvNotNextToEachOtherNotOverlapping, Coordinate[] fpvCorners) {
            if( fpvCorners[1].getX() == fpvNotNextToEachOtherNotOverlapping[0].getX() || fpvCorners[1].getY() == fpvNotNextToEachOtherNotOverlapping[0].getY() ) {
                fpvCorners[2] = fpvNotNextToEachOtherNotOverlapping[0];
                return fpvCorners;
            }
            if( fpvCorners[1].getX() == fpvNotNextToEachOtherNotOverlapping[1].getX() || fpvCorners[1].getY() == fpvNotNextToEachOtherNotOverlapping[1].getY() ) {
                fpvCorners[2] = fpvNotNextToEachOtherNotOverlapping[1];
                return fpvCorners;
            }
            for (int i = 0; i < fpvCorners.length; i++) fpvCorners[i] = null;
            return null;
        }

        private Coordinate[] set4thCorner(Coordinate[] fpvCorners) throws BadCoordinateValueException {
            int x = -1;
            int y = -1;
            if(fpvCorners[1].getX() == fpvCorners[2].getX()) {
                x = fpvCorners[0].getX();
                if(fpvCorners[1].getY() == fpvCorners[0].getY()) y = fpvCorners[2].getY();
                else y = fpvCorners[1].getY();
            }
            if(fpvCorners[1].getY() == fpvCorners[2].getY()) {
                y = fpvCorners[0].getY();
                if(fpvCorners[1].getX() == fpvCorners[0].getX()) x = fpvCorners[2].getX();
                else x = fpvCorners[1].getX();
            }
            Coordinate corner4 = new Coordinate(x, y);
            fpvCorners[3] = corner4;
            return fpvCorners;
        }
    }, TWO_CORNERS_ON_ONE_SIDE{
        @Override
        public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) {
            throw new UnsupportedOperationException("Could not find CutCase for this combination of arguments.");
        }
    }, CORNERS_INSIDE{
        @Override
        public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) {
            throw new UnsupportedOperationException("Could not find CutCase for this combination of arguments.");
        }
    };

    private static final String overriddenFunctionName = "getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation)";

    public static CutCase getCutCase(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) throws BadCoordinateValueException, CutCaseNullArgumentException, UnsupportedOperationException {
        final String functionName = "getCutCase(FreePieceVariation freePieceVariation, PieceVariation pieceVariation)";
        validateArguments(functionName, freePieceVariation, pieceVariation);
        RectangleCorners fpvRectangleCorners = new RectangleCorners(freePieceVariation);
        RectangleCorners pvRectangleCorners = new RectangleCorners(pieceVariation);
        if(is4CornersOverlapping(fpvRectangleCorners, pvRectangleCorners)) return CutCase.FOUR_OVERLAPPING_CORNERS;
        if(is2CornersOverlapping(fpvRectangleCorners, pvRectangleCorners)) return CutCase.TWO_OVERLAPPING_CORNERS;
        if(is1CornerOverlapping(fpvRectangleCorners, pvRectangleCorners)) return CutCase.ONE_OVERLAPPING_CORNER;
        if(is2CornersOnOneSide(fpvRectangleCorners, pvRectangleCorners)) return CutCase.TWO_CORNERS_ON_ONE_SIDE;
        if(isCornersInside(fpvRectangleCorners, pvRectangleCorners)) return CutCase.CORNERS_INSIDE;
        throw new UnsupportedOperationException("Could not find CutCase for this combination of arguments.");
    }

    private static FreePieceVariation getNewFreePieceVariation(Coordinate[] coordinates, int id) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
        Arrays.sort(coordinates);
        Coordinate northWestern = coordinates[0];
        Coordinate southEastern = coordinates[3];
        int width = southEastern.getX() - northWestern.getX();
        int height = southEastern.getY() - northWestern.getY();
        return FreePieceVariation.getNewFreePieceVariation(
                id,
                width,
                height,
                -width * height,
                northWestern.getX(),
                northWestern.getY()
        );
    }

    private static void validateArguments(final String functionName, FreePieceVariation freePieceVariation, PieceVariation pieceVariation) throws CutCaseNullArgumentException {
        if(freePieceVariation == null) throw new CutCaseNullArgumentException(
                CutCase.class.getSimpleName(),
                functionName,
                FreePieceVariation.class.getSimpleName(),
                null
        );
        if(pieceVariation == null) throw new CutCaseNullArgumentException(
                CutCase.class.getSimpleName(),
                functionName,
                PieceVariation.class.getSimpleName(),
                null
        );
    }

    private static boolean is4CornersOverlapping(RectangleCorners fpvRectangleCorners, RectangleCorners pvRectangleCorners) {
        return fpvRectangleCorners.equals(pvRectangleCorners);
    }

    /**
     * WARNING: Will state true also for 4 Corners!!!
     */
    private static boolean is2CornersOverlapping(RectangleCorners fpvRectangleCorners, RectangleCorners pvRectangleCorners) throws BadCoordinateValueException {
        int overlappingFlag = 0;
        if(fpvRectangleCorners.getNWCoord().equals(pvRectangleCorners.getNWCoord())) overlappingFlag++;
        if(fpvRectangleCorners.getSECoord().equals(pvRectangleCorners.getSECoord())) overlappingFlag++;
        if(overlappingFlag >= 2) return true;
        if(overlappingFlag >= 1) {
            if(fpvRectangleCorners.getNECoord().equals(pvRectangleCorners.getNECoord())) overlappingFlag++;
            if(overlappingFlag >= 2) return true;
            if(fpvRectangleCorners.getSWCoord().equals(pvRectangleCorners.getSWCoord())) overlappingFlag++;
        }
        if(overlappingFlag >= 2) return true;
        return false;
    }

    private static boolean is1CornerOverlapping(RectangleCorners fpvRectangleCorners, RectangleCorners pvRectangleCorners) throws BadCoordinateValueException {
        if(fpvRectangleCorners.getNWCoord().equals(pvRectangleCorners.getNWCoord())) return true;
        if(fpvRectangleCorners.getSECoord().equals(pvRectangleCorners.getSECoord())) return true;
        if(fpvRectangleCorners.getNECoord().equals(pvRectangleCorners.getNECoord())) return true;
        if (fpvRectangleCorners.getSWCoord().equals(pvRectangleCorners.getSWCoord())) return true;
        return false;
    }

    private static boolean is2CornersOnOneSide(RectangleCorners fpvRectangleCorners, RectangleCorners pvRectangleCorners) throws BadCoordinateValueException {
        if(
                (
                        pvRectangleCorners.getNWCoord().isBetweenCoords(fpvRectangleCorners.getNWCoord(), fpvRectangleCorners.getNECoord()) &&
                        pvRectangleCorners.getNECoord().isBetweenCoords(fpvRectangleCorners.getNWCoord(), fpvRectangleCorners.getNECoord())
                ) || (
                        pvRectangleCorners.getNECoord().isBetweenCoords(fpvRectangleCorners.getNECoord(), fpvRectangleCorners.getSECoord()) &&
                        pvRectangleCorners.getSECoord().isBetweenCoords(fpvRectangleCorners.getNECoord(), fpvRectangleCorners.getSECoord())
                ) || (
                        pvRectangleCorners.getSECoord().isBetweenCoords(fpvRectangleCorners.getSECoord(), fpvRectangleCorners.getSWCoord()) &&
                        pvRectangleCorners.getSWCoord().isBetweenCoords(fpvRectangleCorners.getSECoord(), fpvRectangleCorners.getSWCoord())
                ) || (
                        pvRectangleCorners.getNWCoord().isBetweenCoords(fpvRectangleCorners.getNWCoord(), fpvRectangleCorners.getSWCoord()) &&
                        pvRectangleCorners.getSWCoord().isBetweenCoords(fpvRectangleCorners.getNWCoord(), fpvRectangleCorners.getSWCoord())
                )
        ) return true;
        return false;
    }

    private static boolean isCornersInside(RectangleCorners fpvRectangleCorners, RectangleCorners pvRectangleCorners) throws BadCoordinateValueException {
        if(
                pvRectangleCorners.getNWCoord().getX() > fpvRectangleCorners.getNWCoord().getX() &&
                pvRectangleCorners.getNWCoord().getY() > fpvRectangleCorners.getNWCoord().getY() &&

                pvRectangleCorners.getNECoord().getX() < fpvRectangleCorners.getNECoord().getX() &&
                pvRectangleCorners.getNECoord().getY() > fpvRectangleCorners.getNECoord().getY() &&

                pvRectangleCorners.getSECoord().getX() < fpvRectangleCorners.getSECoord().getX() &&
                pvRectangleCorners.getSECoord().getY() < fpvRectangleCorners.getSECoord().getY() &&

                pvRectangleCorners.getSWCoord().getX() > fpvRectangleCorners.getSWCoord().getX() &&
                pvRectangleCorners.getSWCoord().getY() < fpvRectangleCorners.getSWCoord().getY()
        ) return true;
        return false;
    }


}
