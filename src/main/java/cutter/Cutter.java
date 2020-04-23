package cutter;

import coords.exceptions.BadCoordinateValueException;
import parser.InputParser;
import parser.OutputComposer;
import parser.exceptions.BadAmountOfInputArgsException;
import parser.exceptions.NullSolutionException;
import sheet.LayoutVariation;
import sheet.Piece;
import sheet.PieceVariation;
import sheet.cutcase.free.piece.exceptions.BadAmountOfCoordinatesFoundException;
import sheet.cutcase.free.piece.exceptions.CornerNotOnSideException;
import sheet.cutcase.free.piece.exceptions.CornersOnSidesShareNoCoordinateException;
import sheet.cutcase.free.piece.exceptions.CutCaseNullArgumentException;
import sheet.exceptions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class Cutter {
    private Piece[] pieces;
    private TreeSet<Solution> solutions;
    private Solution bestSolution;

    public Cutter(String[] args) throws LayoutFactoryAlreadyInitiatedException, BadAmountOfInputArgsException, SheetSizeException, NegativePiecePointsException, CalculatedAndInputAmountOfPiecesNotMatchException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        InputParser.initSheetLayoutFactory(args);
        this.pieces = InputParser.getSheetPieces(args);
        Arrays.sort(this.pieces);
        this.solutions = new TreeSet<>();
        Arrays.asList(this.pieces).forEach(piece -> {
            System.out.println("piece: " + piece);
        });
    }

    public String cut() throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NullSolutionException {
        this.bestSolution = generateBestSolution();
        return OutputComposer.getOutputString(this.bestSolution);
    }

    private Solution generateBestSolution() throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        System.out.println("generateBestSolution()");
        LinkedHashSet<Piece> sortedPiecesToCut = new LinkedHashSet<>();
        sortedPiecesToCut.addAll(Arrays.asList(this.pieces));
        Solution previousLevelBestSolution = new Solution();
        return generateBestSolution(sortedPiecesToCut, previousLevelBestSolution);
    }

    private Solution generateBestSolution(LinkedHashSet<Piece> sortedPiecesToCut, Solution previousLevelBestSolution) throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        System.out.println("\tgenerateBestSolution(LinkedHashSet<Piece> sortedPiecesToCut, Solution previousLevelBestSolution)");
        Iterator sortedSheetPiecesToCutIterator = sortedPiecesToCut.iterator();
        if (!sortedSheetPiecesToCutIterator.hasNext()) return previousLevelBestSolution;
        Piece largestPieceToCut = (Piece) sortedSheetPiecesToCutIterator.next();
        sortedPiecesToCut.remove(largestPieceToCut);
        TreeSet<Solution> solutionVariations = getSolutionVariations(largestPieceToCut, previousLevelBestSolution);
        previousLevelBestSolution.add(new LayoutVariation());
        solutionVariations.addAll(getSolutionVariations(largestPieceToCut, previousLevelBestSolution));
        return getBestSolutionVariationFromNextLevels(sortedPiecesToCut, solutionVariations);
    }

    private Solution getBestSolutionVariationFromNextLevels(LinkedHashSet<Piece> sortedPiecesToCut, TreeSet<Solution> solutionVariations) throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        System.out.println("\t\tgetBestSolutionVariationFromNextLevels(LinkedHashSet<Piece> sortedPiecesToCut, TreeSet<Solution> solutionVariations)");
        Solution previousBetterSolutionVariation = null;
        System.out.println("\t\tgetBestSolutionVariationFromNextLevels(LinkedHashSet<Piece> sortedPiecesToCut, TreeSet<Solution> solutionVariations) - for ++++++++++++++");
        for (Solution solutionVariation : solutionVariations) {
            Solution currentSolutionVariation = generateBestSolution(sortedPiecesToCut, solutionVariation);
            previousBetterSolutionVariation = pickBetterSolutionVariation(previousBetterSolutionVariation, currentSolutionVariation);
            System.out.println("\t\tgetBestSolutionVariationFromNextLevels(LinkedHashSet<Piece> sortedPiecesToCut, TreeSet<Solution> solutionVariations) - pickedBetterSolutionVariation: " + solutionToShortenedString(previousBetterSolutionVariation));
            if (previousBetterSolutionVariation != null) this.solutions.add(previousBetterSolutionVariation);
        }
        System.out.println("\t\tgetBestSolutionVariationFromNextLevels(LinkedHashSet<Piece> sortedPiecesToCut, TreeSet<Solution> solutionVariations) - for --------------");
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

    private Solution pickBetterSolutionVariation(Solution previousSolutionVariation, Solution currentSolutionVariation) {
        System.out.println("\t\t\tpickBetterSolutionVariation(Solution previousSolutionVariation, Solution currentSolutionVariation)");
        System.out.println("\t\t\t\t\t\tpreviousSolutionVariation: " + solutionToShortenedString(previousSolutionVariation) + "\n" +
                "\t\t\t\t\t\tvs.\n" +
                "\t\t\t\t\t\tcurrentSolutionVariation: " + solutionToShortenedString(currentSolutionVariation)
        );
        if (previousSolutionVariation == null && currentSolutionVariation == null) {
            System.out.println("\t\t\t\t\t\tpreviousSolutionVariation == null && currentSolutionVariation == null | return null;");
            return null;
        }
        if (previousSolutionVariation == null) {
            System.out.println("\t\t\t\t\t\tpreviousSolutionVariation == null | return currentSolutionVariation;");
            return currentSolutionVariation;
        }
        if (currentSolutionVariation == null) {
            System.out.println("\t\t\t\t\t\tcurrentSolutionVariation == null | return previousSolutionVariation;");
            return previousSolutionVariation;
        }
        if (previousSolutionVariation.getPointSum() > currentSolutionVariation.getPointSum()) {
            System.out.println("\t\t\t\t\t\tpreviousSolutionVariation.getPointSum() > currentSolutionVariation.getPointSum() | return previousSolutionVariation;");
            return previousSolutionVariation;
        } else {
            System.out.println("\t\t\t\t\t\tpreviousSolutionVariation.getPointSum() <= currentSolutionVariation.getPointSum() | return currentSolutionVariation;");
            return currentSolutionVariation;
        }
    }

    private TreeSet<Solution> getSolutionVariations(Piece largestPieceToCut, Solution previousLevelBestSolution) throws LayoutFactoryNotInitiatedException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, SheetAmountExceededLimitException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        System.out.println("\t\tgetSolutionVariations(Piece largestPieceToCut, Solution previousLevelBestSolution)");
        TreeSet<Solution> solutionVariations = new TreeSet<>();
        FIFindPieceVariation[] fiFindPieceVariations = new FIFindPieceVariation[3];
        fiFindPieceVariations[0] = LayoutVariation::findSameSizePieceVariation;
        fiFindPieceVariations[1] = LayoutVariation::findSameWidthOrHeightPieceVariation;
        fiFindPieceVariations[2] = LayoutVariation::findSmallerSizePieceVariation;
        for (FIFindPieceVariation fiFindPieceVariation : fiFindPieceVariations) {
            Solution candidateSolution = getSolutionVariationX(largestPieceToCut, previousLevelBestSolution, fiFindPieceVariation);
            if (candidateSolution != null) solutionVariations.add(candidateSolution);
        }
        solutionVariations.add((Solution) previousLevelBestSolution.clone());
        return solutionVariations;
    }

    private Solution getSolutionVariationX(Piece largestPieceToCut, Solution previousLevelBestSolution, FIFindPieceVariation fiFindPieceVariation) throws CloneNotSupportedException, BadCoordinateValueException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, CutCaseNullArgumentException, SheetSizeException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        Solution solutionVariation = (Solution) previousLevelBestSolution.clone();
        PieceVariation largestPieceVariationToCut = new PieceVariation((Piece) largestPieceToCut.clone());
        boolean wasAdded = false;
        for (LayoutVariation layoutVariation : solutionVariation.getLayoutVariations()) {
            if (layoutVariation.isEnoughFreeArea(largestPieceVariationToCut)) {
                PieceVariation tmpPieceVariation = fiFindPieceVariation.findPieceVariation(layoutVariation, largestPieceVariationToCut);
                if (tmpPieceVariation != null) {
                    wasAdded = layoutVariation.add(tmpPieceVariation);
                    if (wasAdded) break;
                }
            }
        }
        return wasAdded ? solutionVariation : null;
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
}
