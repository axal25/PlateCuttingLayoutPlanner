package cutter;

import coords.exceptions.BadCoordinateValueException;
import cutter.exceptions.CutCaseNullArgumentException;
import sheet.LayoutVariation;
import sheet.PieceVariation;
import sheet.exceptions.*;

import java.util.Iterator;
import java.util.TreeSet;

public class Solution implements Comparable<Solution> {
    private TreeSet<LayoutVariation> layoutVariations;

    public Solution() {
        this.layoutVariations = new TreeSet<>();
    }

    public Solution(Solution template) throws CloneNotSupportedException {
        this();
        for (LayoutVariation layoutVariation : template.layoutVariations) {
            this.layoutVariations.add((LayoutVariation) layoutVariation.clone());
        }
    }

    public TreeSet<LayoutVariation> getLayoutVariations() {
        return this.layoutVariations;
    }

    public void add(LayoutVariation layoutVariation) {
        this.layoutVariations.add(layoutVariation);
    }

//    public LayoutVariation getSheetLayoutVariationWith

    public LayoutVariation getSheetLayoutVariationWithEnoughFreeSpace(PieceVariation largestPieceVariation) throws SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException {
        LayoutVariation layoutVariation = getSheetLayoutVariationWithEnoughFreeSpaceOrLast(largestPieceVariation);
        if(!layoutVariation.isEnoughFreeArea(largestPieceVariation)) {
            this.layoutVariations.add(new LayoutVariation());
            layoutVariation = getSheetLayoutVariationWithEnoughFreeSpaceOrLast(largestPieceVariation);
        }
        return layoutVariation;
    }

    private LayoutVariation getSheetLayoutVariationWithEnoughFreeSpaceOrLast(PieceVariation largestPieceVariation) throws SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException {
        Iterator sheetLayoutVariationsIterator = this.layoutVariations.iterator();
        if(!sheetLayoutVariationsIterator.hasNext()) this.layoutVariations.add(new LayoutVariation());
        LayoutVariation layoutVariation = (LayoutVariation) sheetLayoutVariationsIterator.next();
        while (sheetLayoutVariationsIterator.hasNext() && !layoutVariation.isEnoughFreeArea(largestPieceVariation)) {
            layoutVariation = (LayoutVariation) sheetLayoutVariationsIterator.next();
        }
        return layoutVariation;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("layoutVariations=");
        if(layoutVariations == null) output.append("null");
        else {
            output.append("TreeSet<LayoutVariation>{");
            for (LayoutVariation layoutVariation : layoutVariations) {
                output.append(layoutVariation.toString());
            }
            output.append("}");
        }
        return output.append("}").toString();
    }

    public String toString(int level) {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{\n");
        output = appendByTab(output, level+1)
                .append("layoutVariations=");
        if(this.layoutVariations == null) output.append("null");
        else {
            output.append("TreeSet<LayoutVariation>{");
            for (LayoutVariation layoutVariation : layoutVariations) {
                output.append("\n");
                output = appendByTab(output, level+2);
                output.append(layoutVariation.toString(level+2));
            }
            if(!this.layoutVariations.isEmpty()) {
                output.append("\n");
                output = appendByTab(output, level+1);
            }
            output.append("}\n");
        }
        return output.append("}").toString();
    }

    public static StringBuilder appendByTab(StringBuilder stringBuilder, int level) {
        for (int i = 0; i < level; i++) stringBuilder.append("\t");
        return stringBuilder;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new Solution(this);
        }
    }

    @Override
    public int compareTo(Solution other) {
        return other.getPointSum() - this.getPointSum();
    }

    public int getPointSum() {
        int pointSum = 0;
        for (LayoutVariation layoutVariation : this.layoutVariations) {
            pointSum += layoutVariation.getPoints();
        }
        return pointSum;
    }
}
