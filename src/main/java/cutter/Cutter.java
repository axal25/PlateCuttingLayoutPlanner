package cutter;

import coords.exceptions.BadCoordinateValueException;
import cutter.exceptions.LayoutIdDoNotMatchPreparedLayoutsIndexException;
import cutter.exceptions.SolutionLayoutVariationLimitExceededException;
import parser.InputParser;
import parser.OutputComposer;
import parser.exceptions.BadAmountOfInputArgsException;
import parser.exceptions.NullSolutionException;
import sheet.*;
import sheet.cutcase.free.piece.exceptions.BadAmountOfCoordinatesFoundException;
import sheet.cutcase.free.piece.exceptions.CornerNotOnSideException;
import sheet.cutcase.free.piece.exceptions.CornersOnSidesShareNoCoordinateException;
import sheet.cutcase.free.piece.exceptions.CutCaseNullArgumentException;
import sheet.exceptions.*;
import solution.Solution;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class Cutter {
    private Piece[] pieces;
    private TreeSet<Solution> solutions;
    private Solution bestSolution;
    private Layout[] layouts;
    public static final Long SOLUTION_LIMIT = 1000000L;
    private Long curSolutionsChecked = 0L;

    public Cutter(String[] args) throws LayoutFactoryAlreadyInitiatedException, BadAmountOfInputArgsException, SheetSizeException, NegativePiecePointsException, CalculatedAndInputAmountOfPiecesNotMatchException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutIdDoNotMatchPreparedLayoutsIndexException {
        InputParser.initSheetLayoutFactory(args);
        this.pieces = InputParser.getSheetPieces(args);
        Arrays.sort(this.pieces);
        Arrays.asList(this.pieces).forEach(piece -> System.out.println(piece));
        this.solutions = new TreeSet<>();
        this.layouts = new Layout[pieces.length];
        for (int i = 0; i < this.layouts.length; i++) {
            this.layouts[i] = StaticLayoutFactory.getLayoutFactory().getLayout();
            if (this.layouts[i].getId() != i)
                throw new LayoutIdDoNotMatchPreparedLayoutsIndexException(this.layouts[i].getId(), i);
        }
        Arrays.asList(this.layouts).forEach(layout -> System.out.println(layout));
    }

    public String cut() throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NullSolutionException, NotAllCornersFoundException, SolutionLayoutVariationLimitExceededException {
        this.bestSolution = generateBestSolutionInit();
        return OutputComposer.getOutputString(this.bestSolution);
    }

    private Solution generateBestSolutionInit() throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException, SolutionLayoutVariationLimitExceededException {
        System.out.println("generateBestSolutionInit()");
        LinkedHashSet<Piece> sortedPiecesToCut = new LinkedHashSet<>();
        sortedPiecesToCut.addAll(Arrays.asList(this.pieces));
        Solution previousLevelBestSolution = new Solution();
        return generateBestSolutionIter(sortedPiecesToCut, new LinkedHashSet<>(), previousLevelBestSolution, 0);
    }

    private Solution generateBestSolutionIter(LinkedHashSet<Piece> sortedPiecesToCutFirst, LinkedHashSet<Piece> sortedPiecesToCutLater, Solution previousLevelBestSolution, int level) throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException, SolutionLayoutVariationLimitExceededException {
        System.out.println("\tgenerateBestSolutionIter(LinkedHashSet<Piece> sortedPiecesToCutFirst, LinkedHashSet<Piece> sortedPiecesToCutLater, Solution previousLevelBestSolution) - level: " + level);
        sortedPiecesToCutFirst = (LinkedHashSet<Piece>) sortedPiecesToCutFirst.clone();
        sortedPiecesToCutLater = (LinkedHashSet<Piece>) sortedPiecesToCutLater.clone();
        Piece bestPieceToCut = selectBestPieceToCutAndRemoveFromPiecesToCut(previousLevelBestSolution, sortedPiecesToCutFirst, sortedPiecesToCutLater);
        if (bestPieceToCut == null) return previousLevelBestSolution;
        TreeSet<Solution> solutionVariations = getSolutionVariations(bestPieceToCut, previousLevelBestSolution);
        solutionVariations = addSolutionVariationsWithAdditionalLayoutVariation(previousLevelBestSolution, solutionVariations, bestPieceToCut, level);
        return getBestSolutionVariationFromNextLevels(sortedPiecesToCutFirst, sortedPiecesToCutLater, solutionVariations, level);
    }

    private TreeSet<Solution> addSolutionVariationsWithAdditionalLayoutVariation(Solution previousLevelBestSolution, TreeSet<Solution> solutionVariations, Piece bestPieceToCut, int level) throws CloneNotSupportedException, SheetSizeException, CornersOnSidesShareNoCoordinateException, BadCoordinateValueException, NegativePiecePointsException, CornerNotOnSideException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, NotAllCornersFoundException, LayoutFactoryNotInitiatedException, SolutionLayoutVariationLimitExceededException {
        if (
                previousLevelBestSolution.getLayoutVariations().size() >= this.pieces.length ||
                        (
                                previousLevelBestSolution.getLayoutVariations().size() != 0 &&
                                        previousLevelBestSolution.getLayoutVariations().last().getPieceVariations().size() == 0
                        )
        ) return solutionVariations;
        System.out.println(String.format("\t#1 solutionVariations.size(): %d", solutionVariations.size()));
        System.out.print(String.format("\t#1 previousLevelBestSolution.getLayoutVariations().size(): %d", previousLevelBestSolution.getLayoutVariations().size()));
        if (previousLevelBestSolution.getLayoutVariations().size() != 0)
            System.out.println(String.format("previousLevelBestSolution.getLayoutVariations().last().getPieceVariations().size(): %d", previousLevelBestSolution.getLayoutVariations().last().getPieceVariations().size()));
        else System.out.println();
        previousLevelBestSolution = (Solution) previousLevelBestSolution.clone();
        LayoutVariation additionalLayoutVariation = new LayoutVariation(getLayoutFromPreparedLayouts(previousLevelBestSolution));
//        LayoutVariation additionalLayoutVariation = new LayoutVariation();
        System.out.println("\tadditionalLayoutVariation.getLayout().getId(): " + additionalLayoutVariation.getLayout().getId() + " - level: " + level);
        previousLevelBestSolution.add(additionalLayoutVariation);
        System.out.println("\t#2 previousLevelBestSolution.getLayoutVariations().size(): " + previousLevelBestSolution.getLayoutVariations().size());
        TreeSet<Solution> solutionVariationsWithAdditionalLayoutVariation = getSolutionVariations(bestPieceToCut, previousLevelBestSolution);
        System.out.println(String.format("\t#2 solutionVariationsWithAdditionalLayoutVariation.size(): %d", solutionVariationsWithAdditionalLayoutVariation.size()));
        solutionVariations.addAll(solutionVariationsWithAdditionalLayoutVariation);
        System.out.println(String.format("\t#1 + #2 combinedSolutionVariations.size(): %d", solutionVariations.size()));
        return solutionVariations;
    }

    private Layout getLayoutFromPreparedLayouts(Solution previousLevelBestSolution) throws SolutionLayoutVariationLimitExceededException {
        int index = previousLevelBestSolution.getLayoutVariations().size();
        if (index == -1) index = 0;
        if (index >= this.layouts.length) throw new SolutionLayoutVariationLimitExceededException(index, this.layouts);
        return this.layouts[index];
    }

    private Piece selectBestPieceToCutAndRemoveFromPiecesToCut(Solution previousLevelBestSolution, LinkedHashSet<Piece> sortedPiecesToCutFirst, LinkedHashSet<Piece> sortedPiecesToCutLater) {
        LinkedHashSet<Piece> sortedPiecesToCut = selectPiecesToCut(sortedPiecesToCutFirst, sortedPiecesToCutLater);
        if (sortedPiecesToCut == null) return null;
        System.out.println("\t#1 sortedPiecesToCutFirst.size(): " + sortedPiecesToCutFirst.size() + ", sortedPiecesToCutLater.size(): " + sortedPiecesToCutLater.size());
        Iterator sortedSheetPiecesToCutIterator = sortedPiecesToCut.iterator();
        Piece bestPieceToCut = (Piece) sortedSheetPiecesToCutIterator.next();
        if (sortedPiecesToCut.equals(sortedPiecesToCutFirst)) {
            sortedPiecesToCutFirst.remove(bestPieceToCut);
            sortedPiecesToCutLater.add(bestPieceToCut);
        } else {
            LinkedHashSet<Piece> sortedPieceToCutToRemove = new LinkedHashSet<>();
            while (bestPieceToCut != null && previousLevelBestSolution.contains(bestPieceToCut)) {
                sortedPieceToCutToRemove.add(bestPieceToCut);
                if (sortedSheetPiecesToCutIterator.hasNext())
                    bestPieceToCut = (Piece) sortedSheetPiecesToCutIterator.next();
                else bestPieceToCut = null;
            }
            System.out.println("\tbestPieceToCut: " + (bestPieceToCut == null ? null : "bestPieceToCut{ id: " + bestPieceToCut.getId() + " }") + ", sortedPieceToCutToRemove.size(): " + sortedPieceToCutToRemove.size());
            sortedPiecesToCutLater.remove(bestPieceToCut);
            sortedPiecesToCutLater.removeAll(sortedPieceToCutToRemove);
        }
        System.out.println("\t#2 sortedPiecesToCutFirst.size(): " + sortedPiecesToCutFirst.size() + ", sortedPiecesToCutLater.size(): " + sortedPiecesToCutLater.size());
        return bestPieceToCut;
    }

    private LinkedHashSet<Piece> selectPiecesToCut(LinkedHashSet<Piece> sortedPiecesToCutFirst, LinkedHashSet<Piece> sortedPiecesToCutLater) {
        if (sortedPiecesToCutFirst.size() != 0) {
            return sortedPiecesToCutFirst;
        } else if (sortedPiecesToCutLater.size() != 0) {
            return sortedPiecesToCutLater;
        } else return null;
    }

    private Solution getBestSolutionVariationFromNextLevels(LinkedHashSet<Piece> sortedPiecesToCutFirst, LinkedHashSet<Piece> sortedPiecesToCutLater, TreeSet<Solution> solutionVariations, int level) throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException, SolutionLayoutVariationLimitExceededException {
        System.out.println("\t\tgetBestSolutionVariationFromNextLevels(LinkedHashSet<Piece> sortedPiecesToCutFirst, LinkedHashSet<Piece> sortedPiecesToCutLater, TreeSet<Solution> solutionVariations) - level: " + level);
        Solution previousBetterSolutionVariation = null;
        System.out.println("\t\tgetBestSolutionVariationFromNextLevels(LinkedHashSet<Piece> sortedPiecesToCutFirst, LinkedHashSet<Piece> sortedPiecesToCutLater, TreeSet<Solution> solutionVariations) - for ++++++++++++++");
        level++;
        for (Solution solutionVariation : solutionVariations) {
            if (this.curSolutionsChecked >= Cutter.SOLUTION_LIMIT) break;
            Solution currentSolutionVariation = generateBestSolutionIter(sortedPiecesToCutFirst, sortedPiecesToCutLater, solutionVariation, level);
            previousBetterSolutionVariation = pickBetterSolutionVariation(previousBetterSolutionVariation, currentSolutionVariation, level);
            System.out.println("\t\tgetBestSolutionVariationFromNextLevels(LinkedHashSet<Piece> sortedPiecesToCutFirst, TreeSet<Solution> solutionVariations) - pickedBetterSolutionVariation: " + solutionToShortenedString(previousBetterSolutionVariation));
            if (previousBetterSolutionVariation != null) this.solutions.add(previousBetterSolutionVariation);
        }
        System.out.println("\t\tgetBestSolutionVariationFromNextLevels(LinkedHashSet<Piece> sortedPiecesToCutFirst, LinkedHashSet<Piece> sortedPiecesToCutLater, TreeSet<Solution> solutionVariations) - for --------------");
        return previousBetterSolutionVariation;
    }

    private String solutionToShortenedString(Solution solution) {
        if (solution == null) return "Solution: null";
        int amountOfPieceVariations = 0;
        int freeSpaceTotalPoints = 0;
        int pieceVariationsTotalPoints = 0;
        for (LayoutVariation layoutVariation : solution.getLayoutVariations()) {
            amountOfPieceVariations += layoutVariation.getPieceVariations().size();
            freeSpaceTotalPoints += -layoutVariation.getFreeArea();
            for (PieceVariation pieceVariation : layoutVariation.getPieceVariations()) {
                pieceVariationsTotalPoints += pieceVariation.getPiece().getPoints();
            }
        }
        return String.format(
                "pointSum: %d, layoutVariationsSize: %d, freeSpaceTotalPoints: %d, amountOfPieceVariations: %d, pieceVariationsTotalPoints: %d",
                solution.getPointSum(),
                solution.getLayoutVariations().size(),
                freeSpaceTotalPoints,
                amountOfPieceVariations,
                pieceVariationsTotalPoints
        );
    }

    private Solution pickBetterSolutionVariation(Solution previousSolutionVariation, Solution currentSolutionVariation, int level) {
        System.out.println("\t\t\tpickBetterSolutionVariation(Solution previousSolutionVariation, Solution currentSolutionVariation) - level: " + level);
        System.out.println("\t\t\t\t\t\tpreviousSolutionVariation: " + solutionToShortenedString(previousSolutionVariation) + "\n" +
                "\t\t\t\t\t\tvs.\n" +
                "\t\t\t\t\t\tcurrentSolutionVariation: " + solutionToShortenedString(currentSolutionVariation)
        );
        this.curSolutionsChecked++;
        if (previousSolutionVariation == null && currentSolutionVariation == null) {
            System.out.println("\t\t\t\t\t\tpreviousSolutionVariation == null && currentSolutionVariation == null | return null;");
            return null;
        } else if (previousSolutionVariation == null) {
            System.out.println("\t\t\t\t\t\tpreviousSolutionVariation == null | return currentSolutionVariation;");
            return currentSolutionVariation;
        } else if (currentSolutionVariation == null) {
            System.out.println("\t\t\t\t\t\tcurrentSolutionVariation == null | return previousSolutionVariation;");
            return previousSolutionVariation;
        } else if (previousSolutionVariation.getPointSum() > currentSolutionVariation.getPointSum()) {
            System.out.println(String.format("\t\t\t\t\t\tpreviousSolutionVariation.getPointSum(): %d > currentSolutionVariation.getPointSum(): %d | return previousSolutionVariation;", previousSolutionVariation.getPointSum(), currentSolutionVariation.getPointSum()));
            return previousSolutionVariation;
        } else {
            System.out.println(String.format("\t\t\t\t\t\tpreviousSolutionVariation.getPointSum(): %d <= currentSolutionVariation.getPointSum(): %d | return currentSolutionVariation;", previousSolutionVariation.getPointSum(), currentSolutionVariation.getPointSum()));
            return currentSolutionVariation;
        }
    }

    private TreeSet<Solution> getSolutionVariations(Piece bestPieceToCut, Solution previousLevelBestSolution) throws LayoutFactoryNotInitiatedException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, SheetAmountExceededLimitException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        System.out.println("\t\tgetSolutionVariations(Piece bestPieceToCut, Solution previousLevelBestSolution)");
        TreeSet<Solution> solutionVariations = new TreeSet<>();
        solutionVariations.add((Solution) previousLevelBestSolution.clone());
        if (previousLevelBestSolution.getLayoutVariations().size() == 0) return solutionVariations;
        FIFindPieceVariation[] fiFindPieceVariations = new FIFindPieceVariation[3];
        fiFindPieceVariations[0] = LayoutVariation::findSameSizePieceVariation;
        fiFindPieceVariations[1] = LayoutVariation::findSameWidthOrHeightPieceVariation;
        fiFindPieceVariations[2] = LayoutVariation::findSmallerSizePieceVariation;
        for (FIFindPieceVariation fiFindPieceVariation : fiFindPieceVariations) {
            Solution candidateSolution = getSolutionVariationX(bestPieceToCut, previousLevelBestSolution, fiFindPieceVariation);
            if (candidateSolution != null) solutionVariations.add(candidateSolution);
        }
        return solutionVariations;
    }

    private Solution getSolutionVariationX(Piece bestPieceToCut, Solution previousLevelBestSolution, FIFindPieceVariation fiFindPieceVariation) throws CloneNotSupportedException, BadCoordinateValueException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, CutCaseNullArgumentException, SheetSizeException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        System.out.println("\t\tgetSolutionVariationX(Piece bestPieceToCut, Solution previousLevelBestSolution, FIFindPieceVariation fiFindPieceVariation)");
        Solution solutionVariation = (Solution) previousLevelBestSolution.clone();
        PieceVariation bestPieceVariationToCut = new PieceVariation((Piece) bestPieceToCut.clone());
        boolean isAdded = false;
        for (LayoutVariation layoutVariation : solutionVariation.getLayoutVariations()) {
            if (layoutVariation.isEnoughFreeArea(bestPieceVariationToCut)) {
                PieceVariation tmpPieceVariation = fiFindPieceVariation.findPieceVariation(layoutVariation, bestPieceVariationToCut);
                if (tmpPieceVariation != null) {
                    isAdded = layoutVariation.add(tmpPieceVariation);
                    if (isAdded) break;
                }
            }
        }
        return isAdded ? solutionVariation : null;
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public TreeSet<Solution> getSolutions() {
        return this.solutions;
    }

    public Solution getBestSolution() {
        return this.bestSolution;
    }

    public Layout[] getLayouts() { return this.layouts; }

    public Long getCurSolutionsChecked() { return this.curSolutionsChecked; }
}
