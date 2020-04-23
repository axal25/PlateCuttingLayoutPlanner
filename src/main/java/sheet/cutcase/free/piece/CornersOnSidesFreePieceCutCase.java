package sheet.cutcase.free.piece;

import coords.Coordinate;
import coords.RectangleCorners;
import coords.exceptions.BadCoordinateValueException;
import sheet.FreePieceVariation;
import sheet.PieceVariation;
import sheet.cutcase.free.piece.exceptions.BadAmountOfCoordinatesFoundException;
import sheet.cutcase.free.piece.exceptions.CornerNotOnSideException;
import sheet.cutcase.free.piece.exceptions.CornersOnSidesShareNoCoordinateException;
import sheet.cutcase.free.piece.exceptions.CutCaseNullArgumentException;
import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.NegativePiecePointsException;
import sheet.exceptions.PieceCanNotFitIntoLayoutException;
import sheet.exceptions.SheetSizeException;
import utils.collection.TreeSetUtils;

import java.util.TreeSet;

import static sheet.cutcase.free.piece.FreePieceCutCase.validateArguments;

public enum CornersOnSidesFreePieceCutCase implements FreePieceCutCaseInterface {
    TWO_CORNERS_ON_ONE_SIDE {
        @Override
        public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) throws BadCoordinateValueException, BadAmountOfCoordinatesFoundException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, CutCaseNullArgumentException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
            validateArguments(overriddenFunctionName, freePieceVariation, pieceVariation);
            Coordinate[] cornersNotOnOneSide = get2CornersNotOnOneSide(this.pvRectangleCorners, this.cornersOnSides);
            TreeSet<FreePieceVariation> cutUpFreePieceVariations = new TreeSet<>();
            cutUpFreePieceVariations.addAll(getNewFreePieceVariations2CornersOnOneSide(this.cornersOnSides, this.fpvRectangleCorners));
            cutUpFreePieceVariations.add(getNewFreePieceVariation2CornersNotOnOneSide(this.cornersOnSides, cornersNotOnOneSide, this.fpvRectangleCorners));
            return cutUpFreePieceVariations;
        }

        private TreeSet<FreePieceVariation> getNewFreePieceVariations2CornersOnOneSide(Coordinate[] cornersOnOneSide, RectangleCorners fpvRectangleCorners) throws BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
            if (cornersOnOneSide[0].getX() == cornersOnOneSide[1].getX())
                return onSameXGetNewFreePieceVariations2CornersOnOneSideIf(cornersOnOneSide, fpvRectangleCorners);
            if (cornersOnOneSide[0].getY() == cornersOnOneSide[1].getY())
                return onSameYGetNewFreePieceVariations2CornersOnOneSideIf(cornersOnOneSide, fpvRectangleCorners);
            else throw new CornersOnSidesShareNoCoordinateException(cornersOnOneSide);
        }

        private TreeSet<FreePieceVariation> onSameXGetNewFreePieceVariations2CornersOnOneSideIf(
                Coordinate[] cornersOnOneSide,
                RectangleCorners fpvRectangleCorners
        ) throws BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException, CornerNotOnSideException {
            int yNorth0Dist = cornersOnOneSide[0].getY() - fpvRectangleCorners.getNWCoord().getY();
            int ySouth0Dist = fpvRectangleCorners.getSECoord().getY() - cornersOnOneSide[0].getY();
            int[] closerFpvYsToCornersOnSide = new int[2];
            if (yNorth0Dist < ySouth0Dist) {
                closerFpvYsToCornersOnSide[0] = fpvRectangleCorners.getNWCoord().getY();
                closerFpvYsToCornersOnSide[1] = fpvRectangleCorners.getSECoord().getY();
            } else {
                closerFpvYsToCornersOnSide[0] = fpvRectangleCorners.getSECoord().getY();
                closerFpvYsToCornersOnSide[1] = fpvRectangleCorners.getNWCoord().getY();
            }
            int differentX;
            if (cornersOnOneSide[0].getX() == fpvRectangleCorners.getNWCoord().getX())
                differentX = fpvRectangleCorners.getSECoord().getX();
            else if (cornersOnOneSide[0].getX() == fpvRectangleCorners.getSECoord().getX())
                differentX = fpvRectangleCorners.getNWCoord().getX();
            else throw new CornerNotOnSideException("X", cornersOnOneSide, fpvRectangleCorners);
            return onSameXGetNewFreePieceVariations2CornersOnOneSideIf(fpvRectangleCorners, cornersOnOneSide, closerFpvYsToCornersOnSide, differentX);
        }

        private TreeSet<FreePieceVariation> onSameXGetNewFreePieceVariations2CornersOnOneSideIf(
                RectangleCorners fpvRectangleCorners,
                Coordinate[] cornersOnOneSide,
                int[] closerFpvYsToCornersOnSide,
                int differentX
        ) throws BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException {
            TreeSet<FreePieceVariation> newFreePieceVariations2CornersOnOneSide = new TreeSet<>();
            for (int i = 0; i < cornersOnOneSide.length && i < closerFpvYsToCornersOnSide.length; i++) {
                newFreePieceVariations2CornersOnOneSide.add(FreePieceVariation.getNewFreePieceVariation(
                        new Coordinate[]{
                                new Coordinate(fpvRectangleCorners.getNWCoord().getX(), closerFpvYsToCornersOnSide[i]),
                                new Coordinate(fpvRectangleCorners.getSECoord().getX(), closerFpvYsToCornersOnSide[i]),
                                cornersOnOneSide[i],
                                new Coordinate(differentX, cornersOnOneSide[i].getY()),
                        }
                ));
            }
            return newFreePieceVariations2CornersOnOneSide;
        }

        private TreeSet<FreePieceVariation> onSameYGetNewFreePieceVariations2CornersOnOneSideIf(
                Coordinate[] cornersOnOneSide,
                RectangleCorners fpvRectangleCorners
        ) throws BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException, CornerNotOnSideException {
            int xNorth0Dist = cornersOnOneSide[0].getX() - fpvRectangleCorners.getNWCoord().getX();
            int xSouth0Dist = fpvRectangleCorners.getSECoord().getX() - cornersOnOneSide[0].getX();
            int[] closerFpvXsToCornersOnSide = new int[2];
            if (xNorth0Dist < xSouth0Dist) {
                closerFpvXsToCornersOnSide[0] = fpvRectangleCorners.getNWCoord().getX();
                closerFpvXsToCornersOnSide[1] = fpvRectangleCorners.getSECoord().getX();
            } else {
                closerFpvXsToCornersOnSide[0] = fpvRectangleCorners.getSECoord().getX();
                closerFpvXsToCornersOnSide[1] = fpvRectangleCorners.getNWCoord().getX();
            }
            int differentY;
            if (cornersOnOneSide[0].getY() == fpvRectangleCorners.getNWCoord().getY())
                differentY = fpvRectangleCorners.getSECoord().getY();
            else if (cornersOnOneSide[0].getY() == fpvRectangleCorners.getSECoord().getY())
                differentY = fpvRectangleCorners.getNWCoord().getY();
            else throw new CornerNotOnSideException("Y", cornersOnOneSide, fpvRectangleCorners);
            return onSameYGetNewFreePieceVariations2CornersOnOneSideIf(fpvRectangleCorners, cornersOnOneSide, closerFpvXsToCornersOnSide, differentY);
        }

        private TreeSet<FreePieceVariation> onSameYGetNewFreePieceVariations2CornersOnOneSideIf(
                RectangleCorners fpvRectangleCorners,
                Coordinate[] cornersOnOneSide,
                int[] closerFpvXsToCornersOnSide,
                int differentY
        ) throws BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException {
            TreeSet<FreePieceVariation> newFreePieceVariations2CornersOnOneSide = new TreeSet<>();
            for (int i = 0; i < cornersOnOneSide.length && i < closerFpvXsToCornersOnSide.length; i++) {
                newFreePieceVariations2CornersOnOneSide.add(FreePieceVariation.getNewFreePieceVariation(
                        new Coordinate[]{
                                new Coordinate(closerFpvXsToCornersOnSide[i], fpvRectangleCorners.getNWCoord().getY()),
                                new Coordinate(closerFpvXsToCornersOnSide[i], fpvRectangleCorners.getSECoord().getY()),
                                cornersOnOneSide[i],
                                new Coordinate(cornersOnOneSide[i].getX(), differentY),
                        }
                ));
            }
            return newFreePieceVariations2CornersOnOneSide;
        }

        private Coordinate[] get2CornersNotOnOneSide(RectangleCorners pvRectangleCorners, Coordinate[] cornersOnOneSide) throws BadAmountOfCoordinatesFoundException, BadCoordinateValueException {
            TreeSet<Coordinate> cornersNotOnOneSide = new TreeSet<>();
            cornersNotOnOneSide.addAll(coordIfNotOnOneSide(pvRectangleCorners.getNWCoord(), cornersOnOneSide));
            cornersNotOnOneSide.addAll(coordIfNotOnOneSide(pvRectangleCorners.getNECoord(), cornersOnOneSide));
            cornersNotOnOneSide.addAll(coordIfNotOnOneSide(pvRectangleCorners.getSECoord(), cornersOnOneSide));
            cornersNotOnOneSide.addAll(coordIfNotOnOneSide(pvRectangleCorners.getSWCoord(), cornersOnOneSide));
            if (cornersNotOnOneSide.size() != 2) throw new BadAmountOfCoordinatesFoundException(cornersNotOnOneSide, 2);
            return TreeSetUtils.treeSetToArray(cornersNotOnOneSide);
        }


        private FreePieceVariation getNewFreePieceVariation2CornersNotOnOneSide(
                Coordinate[] cornersOnOneSide,
                Coordinate[] cornersNotOnOneSide,
                RectangleCorners fpvRectangleCorners
        ) throws BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException {
            if (cornersNotOnOneSide[0].getX() == cornersNotOnOneSide[1].getX() &&
                    cornersOnOneSide[0].getX() == cornersOnOneSide[1].getX()) {
                TreeSet<Coordinate> fpv2CoordsOnNotCommonAxisSide = new TreeSet<>();
                if (cornersOnOneSide[0].getX() != fpvRectangleCorners.getNWCoord().getX())
                    fpv2CoordsOnNotCommonAxisSide.add(fpvRectangleCorners.getNWCoord());
                if (cornersOnOneSide[0].getX() != fpvRectangleCorners.getNECoord().getX())
                    fpv2CoordsOnNotCommonAxisSide.add(fpvRectangleCorners.getNECoord());
                if (cornersOnOneSide[0].getX() != fpvRectangleCorners.getSECoord().getX())
                    fpv2CoordsOnNotCommonAxisSide.add(fpvRectangleCorners.getSECoord());
                if (cornersOnOneSide[0].getX() != fpvRectangleCorners.getSWCoord().getX())
                    fpv2CoordsOnNotCommonAxisSide.add(fpvRectangleCorners.getSWCoord());
                TreeSet<Coordinate> newFreePieceCoords = new TreeSet<>();
                for (Coordinate fpv2Coord : fpv2CoordsOnNotCommonAxisSide) {
                    newFreePieceCoords.add(fpv2Coord);
                    newFreePieceCoords.add(new Coordinate(cornersNotOnOneSide[0].getX(), fpv2Coord.getY()));
                }
                return FreePieceVariation.getNewFreePieceVariation(TreeSetUtils.treeSetToArray(newFreePieceCoords));
            }
            if (cornersNotOnOneSide[0].getY() == cornersNotOnOneSide[1].getY() &&
                    cornersOnOneSide[0].getY() == cornersOnOneSide[1].getY()) {
                TreeSet<Coordinate> fpv2CoordsOnNotCommonAxisSide = new TreeSet<>();
                if (cornersOnOneSide[0].getY() != fpvRectangleCorners.getNWCoord().getY())
                    fpv2CoordsOnNotCommonAxisSide.add(fpvRectangleCorners.getNWCoord());
                if (cornersOnOneSide[0].getY() != fpvRectangleCorners.getNECoord().getY())
                    fpv2CoordsOnNotCommonAxisSide.add(fpvRectangleCorners.getNECoord());
                if (cornersOnOneSide[0].getY() != fpvRectangleCorners.getSECoord().getY())
                    fpv2CoordsOnNotCommonAxisSide.add(fpvRectangleCorners.getSECoord());
                if (cornersOnOneSide[0].getY() != fpvRectangleCorners.getSWCoord().getY())
                    fpv2CoordsOnNotCommonAxisSide.add(fpvRectangleCorners.getSWCoord());
                TreeSet<Coordinate> newFreePieceCoords = new TreeSet<>();
                for (Coordinate fpv2Coord : fpv2CoordsOnNotCommonAxisSide) {
                    newFreePieceCoords.add(fpv2Coord);
                    newFreePieceCoords.add(new Coordinate(fpv2Coord.getX(), cornersNotOnOneSide[0].getY()));
                }
                return FreePieceVariation.getNewFreePieceVariation(TreeSetUtils.treeSetToArray(newFreePieceCoords));
            }
            return null;
        }

        private TreeSet<Coordinate> coordIfNotOnOneSide(Coordinate coordCandidate, Coordinate[] cornersOnOneSide) {
            TreeSet<Coordinate> cornerNotOnOneSide = new TreeSet<>();
            if (!coordCandidate.equals(cornersOnOneSide[0]) && !coordCandidate.equals(cornersOnOneSide[1]))
                cornerNotOnOneSide.add(coordCandidate);
            return cornerNotOnOneSide;
        }
    }, FOUR_CORNERS_ON_TWO_SIDES {
        @Override
        public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) throws BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, CutCaseNullArgumentException, CornerNotOnSideException {
            validateArguments(overriddenFunctionName, freePieceVariation, pieceVariation);
            TreeSet<FreePieceVariation> cutUpFreePieceVariations = new TreeSet<>();
            cutUpFreePieceVariations.addAll(getNewFreePieceVariations4CornersOn2Sides(this.pvRectangleCorners, this.fpvRectangleCorners));
            return cutUpFreePieceVariations;
        }

        private TreeSet<FreePieceVariation> getNewFreePieceVariations4CornersOn2Sides(RectangleCorners pvRectangleCorners, RectangleCorners fpvRectangleCorners) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, CornerNotOnSideException {
            if (pvRectangleCorners.getNWCoord().getY() == fpvRectangleCorners.getNWCoord().getY() &&
                    pvRectangleCorners.getSECoord().getY() == fpvRectangleCorners.getSECoord().getY()) {
                TreeSet<FreePieceVariation> newFreePieceVariations4CornersOn2Sides = new TreeSet<>();
                newFreePieceVariations4CornersOn2Sides.add(FreePieceVariation.getNewFreePieceVariation(
                        new Coordinate[]{
                                fpvRectangleCorners.getNWCoord(),
                                pvRectangleCorners.getNWCoord(),
                                pvRectangleCorners.getSWCoord(),
                                fpvRectangleCorners.getSWCoord()
                        }
                ));
                newFreePieceVariations4CornersOn2Sides.add(FreePieceVariation.getNewFreePieceVariation(
                        new Coordinate[]{
                                pvRectangleCorners.getNECoord(),
                                fpvRectangleCorners.getNECoord(),
                                fpvRectangleCorners.getSECoord(),
                                pvRectangleCorners.getSECoord()
                        }
                ));
                return newFreePieceVariations4CornersOn2Sides;
            } else if (pvRectangleCorners.getNWCoord().getX() == fpvRectangleCorners.getNWCoord().getX() &&
                    pvRectangleCorners.getSECoord().getX() == fpvRectangleCorners.getSECoord().getX()) {
                TreeSet<FreePieceVariation> newFreePieceVariations4CornersOn2Sides = new TreeSet<>();
                newFreePieceVariations4CornersOn2Sides.add(FreePieceVariation.getNewFreePieceVariation(
                        new Coordinate[]{
                                fpvRectangleCorners.getNWCoord(),
                                fpvRectangleCorners.getNECoord(),
                                pvRectangleCorners.getNECoord(),
                                pvRectangleCorners.getNWCoord()
                        }
                ));
                newFreePieceVariations4CornersOn2Sides.add(FreePieceVariation.getNewFreePieceVariation(
                        new Coordinate[]{
                                pvRectangleCorners.getSWCoord(),
                                pvRectangleCorners.getSECoord(),
                                fpvRectangleCorners.getSECoord(),
                                fpvRectangleCorners.getSWCoord(),
                        }
                ));
                return newFreePieceVariations4CornersOn2Sides;
            } else throw new CornerNotOnSideException(pvRectangleCorners, fpvRectangleCorners);
        }
    };

    @Override
    public TreeSet<FreePieceVariation> getCutUpFreePieceVariation(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) throws BadCoordinateValueException, BadAmountOfCoordinatesFoundException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, CutCaseNullArgumentException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        throw new UnsupportedOperationException("Unimplemented");
    }

    public Coordinate[] cornersOnSides;
    public RectangleCorners fpvRectangleCorners, pvRectangleCorners;

    public static CornersOnSidesFreePieceCutCase getNewCornersOnSidesFreePieceCutCase(FreePieceVariation freePieceVariation, PieceVariation pieceVariation) throws CutCaseNullArgumentException, BadCoordinateValueException, BadAmountOfCoordinatesFoundException {
        final String functionName = "getNewCornersOnSides(FreePieceVariation freePieceVariation, PieceVariation pieceVariation)";
        validateArguments(functionName, freePieceVariation, pieceVariation);
        RectangleCorners fpvRectangleCorners = new RectangleCorners(freePieceVariation);
        RectangleCorners pvRectangleCorners = new RectangleCorners(pieceVariation);
        Coordinate[] cornersOnSides = getCornersOnSides(fpvRectangleCorners, pvRectangleCorners);
        CornersOnSidesFreePieceCutCase cornersOnSidesFreePieceCutCase = null;
        if (cornersOnSides.length == 2) cornersOnSidesFreePieceCutCase = TWO_CORNERS_ON_ONE_SIDE;
        else if (cornersOnSides.length == 4) cornersOnSidesFreePieceCutCase = FOUR_CORNERS_ON_TWO_SIDES;
        if (cornersOnSidesFreePieceCutCase != null) {
            cornersOnSidesFreePieceCutCase.cornersOnSides = cornersOnSides;
            cornersOnSidesFreePieceCutCase.fpvRectangleCorners = fpvRectangleCorners;
            cornersOnSidesFreePieceCutCase.pvRectangleCorners = pvRectangleCorners;
        }
        return cornersOnSidesFreePieceCutCase;
    }

    private static Coordinate[] getCornersOnSides(RectangleCorners fpvRectangleCorners, RectangleCorners pvRectangleCorners) throws BadCoordinateValueException, BadAmountOfCoordinatesFoundException {
        TreeSet<Coordinate> cornersOnSides = new TreeSet<>();
        if (
                pvRectangleCorners.getNWCoord().isBetweenCoords(fpvRectangleCorners.getNWCoord(), fpvRectangleCorners.getNECoord()) &&
                        pvRectangleCorners.getNECoord().isBetweenCoords(fpvRectangleCorners.getNWCoord(), fpvRectangleCorners.getNECoord())
        ) {
            cornersOnSides.add(pvRectangleCorners.getNWCoord());
            cornersOnSides.add(pvRectangleCorners.getNECoord());
        }
        if (
                pvRectangleCorners.getNECoord().isBetweenCoords(fpvRectangleCorners.getNECoord(), fpvRectangleCorners.getSECoord()) &&
                        pvRectangleCorners.getSECoord().isBetweenCoords(fpvRectangleCorners.getNECoord(), fpvRectangleCorners.getSECoord())
        ) {
            cornersOnSides.add(pvRectangleCorners.getNECoord());
            cornersOnSides.add(pvRectangleCorners.getSECoord());
        }

        if (
                pvRectangleCorners.getSECoord().isBetweenCoords(fpvRectangleCorners.getSECoord(), fpvRectangleCorners.getSWCoord()) &&
                        pvRectangleCorners.getSWCoord().isBetweenCoords(fpvRectangleCorners.getSECoord(), fpvRectangleCorners.getSWCoord())
        ) {
            cornersOnSides.add(pvRectangleCorners.getSECoord());
            cornersOnSides.add(pvRectangleCorners.getSWCoord());
        }
        if (
                pvRectangleCorners.getNWCoord().isBetweenCoords(fpvRectangleCorners.getNWCoord(), fpvRectangleCorners.getSWCoord()) &&
                        pvRectangleCorners.getSWCoord().isBetweenCoords(fpvRectangleCorners.getNWCoord(), fpvRectangleCorners.getSWCoord())
        ) {
            cornersOnSides.add(pvRectangleCorners.getNWCoord());
            cornersOnSides.add(pvRectangleCorners.getSWCoord());
        }
        if (cornersOnSides.size() != 2 && cornersOnSides.size() != 4) {
            throw new BadAmountOfCoordinatesFoundException(cornersOnSides, 2, 4);
        }
        return TreeSetUtils.treeSetToArray(cornersOnSides);
    }
}
