package cutter;

import coords.exceptions.BadCoordinateValueException;
import cutter.exceptions.CutCaseNullArgumentException;
import parser.exceptions.BadAmountOfInputArgsException;
import parser.InputParser;
import parser.OutputComposer;
import sheet.*;
import sheet.exceptions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class Cutter {
    private Piece[] pieces;
    private TreeSet<Solution> solutions;
    private Solution bestSolution = null;

    public Cutter(String[] args) throws LayoutFactoryAlreadyInitiatedException, BadAmountOfInputArgsException, SheetSizeException, NegativePiecePointsException, CalculatedAndInputAmountOfPiecesNotMatchException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        InputParser.initSheetLayoutFactory(args);
        this.pieces = InputParser.getSheetPieces(args);
        Arrays.sort(this.pieces);
        this.solutions = new TreeSet<>();
    }

    public String cut() throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException {
        this.bestSolution = generateBestSolution();
        return OutputComposer.getOutputString(bestSolution);
    }

    private Solution generateBestSolution() throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException {
        LinkedHashSet<Piece> sortedPiecesToCut = new LinkedHashSet<>();
        sortedPiecesToCut.addAll(Arrays.asList(this.pieces));
        Solution previousLevelBestSolution = new Solution();
        solutions.add(previousLevelBestSolution);
        //System.out.println("generateBestSolution()");
        return generateBestSolution(sortedPiecesToCut, previousLevelBestSolution);
    }

    private Solution generateBestSolution(LinkedHashSet<Piece> sortedPiecesToCut, Solution previousLevelBestSolution) throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException {
        //System.out.println("\tgenerateBestSolution(LinkedHashSet<Piece> sortedPiecesToCut, Solution previousLevelBestSolution)");
        Iterator sortedSheetPiecesToCutIterator = sortedPiecesToCut.iterator();
        if(sortedSheetPiecesToCutIterator.hasNext()) {
            Piece largestPieceToCut = (Piece) sortedSheetPiecesToCutIterator.next();
            sortedPiecesToCut.remove(largestPieceToCut);
            TreeSet<Solution> solutionVariations = getSolutionVariations(largestPieceToCut, previousLevelBestSolution);
            return sendSolutionVariationsToNextLevel(sortedPiecesToCut, solutionVariations);
        } else return previousLevelBestSolution;
    }

    private TreeSet<Solution> getSolutionVariations(Piece largestPieceToCut, Solution previousLevelBestSolution) throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException {
        //System.out.println("\t\tgetSolutionVariations(Piece largestPieceToCut, Solution previousLevelBestSolution)");
        TreeSet<LayoutVariation> layoutVariations = previousLevelBestSolution.getLayoutVariations();
        TreeSet<Solution> solutionVariations = new TreeSet<>();
        int amountOfSolutionVariations = layoutVariations.size() + 1; // always i think, maybe // if(layoutVariations.size() == this.pieces.length) amountOfSolutionVariations = layoutVariations.size();
        for (int i = 0; i < amountOfSolutionVariations; i++) {
            Solution solutionVariation = (Solution) previousLevelBestSolution.clone();
            if(i == layoutVariations.size()) solutionVariation.add(new LayoutVariation());
            solutionVariations.add(solutionVariation);
        }

//        LayoutVariation layoutVariationWithEnoughFreeSpace = previousLevelBestSolution.getSheetLayoutVariationWithEnoughFreeSpace(largestPieceToCut);
//        largestPieceVariation = layoutVariationWithEnoughFreeSpace.getFittedSheetPieceVariationIntoFreeArea(largestPieceVariation);
//        boolean wasAddingSuccessful = layoutVariationWithEnoughFreeSpace.add(largestPieceVariation);
//            if(!wasAddingSuccessful)

        return solutionVariations;
    }

    private Solution sendSolutionVariationsToNextLevel(LinkedHashSet<Piece> sortedPiecesToCut, TreeSet<Solution> solutionVariations) throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException {
        //System.out.println("\t\tsendSolutionVariationsToNextLevel(LinkedHashSet<Piece> sortedPiecesToCut, TreeSet<Solution> solutionVariations)");
        Solution betterSolutionVariation = null;
        for (Solution solutionVariation : solutionVariations) {
            betterSolutionVariation = pickBetterSolutionVariation(betterSolutionVariation, generateBestSolution(sortedPiecesToCut, solutionVariation));
            this.solutions.add(betterSolutionVariation);
        }
        return betterSolutionVariation;
    }

    private Solution pickBetterSolutionVariation(Solution previousSolutionVariation, Solution currentSolutionVariation) {
        //System.out.println("\t\t\tpickBetterSolutionVariation(Solution previousSolutionVariation, Solution currentSolutionVariation)");
        //System.out.println("\t\t\t\t\t\tpreviousSolutionVariation: " + (previousSolutionVariation != null ? previousSolutionVariation.toString(6) : "null")+ "\n" +
//                "\t\t\t\t\t\tvs.\n" +
//                "\t\t\t\t\t\tcurrentSolutionVariation: " + (currentSolutionVariation != null ? currentSolutionVariation.toString(6) : "null"));
        if(previousSolutionVariation == null && currentSolutionVariation == null) {
            //System.out.println("\t\t\t\t\t\tpreviousSolutionVariation == null && currentSolutionVariation == null | return null;");
            return null;
        }
        if(previousSolutionVariation == null) {
            //System.out.println("\t\t\t\t\t\tpreviousSolutionVariation == null | return currentSolutionVariation;");
            return currentSolutionVariation;
        }
        if(currentSolutionVariation == null) {
            //System.out.println("\t\t\t\t\t\tcurrentSolutionVariation == null | return previousSolutionVariation;");
            return previousSolutionVariation;
        }
        if(previousSolutionVariation.getPointSum() > currentSolutionVariation.getPointSum()) {
            //System.out.println("\t\t\t\t\t\tpreviousSolutionVariation.getPointSum() > currentSolutionVariation.getPointSum() | return previousSolutionVariation;");
            return previousSolutionVariation;
        } else {
            //System.out.println("\t\t\t\t\t\tpreviousSolutionVariation.getPointSum() <= currentSolutionVariation.getPointSum() | return currentSolutionVariation;");
            return currentSolutionVariation;
        }
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public TreeSet<Solution> getSolutions() {
        return this.solutions;
    }

    public Solution getBestSolution() {
        return bestSolution;
    }
}
