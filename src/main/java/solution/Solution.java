package solution;

import coords.exceptions.BadCoordinateValueException;
import sheet.LayoutVariation;
import sheet.Piece;
import sheet.PieceVariation;
import sheet.cutcase.free.piece.exceptions.BadAmountOfCoordinatesFoundException;
import sheet.cutcase.free.piece.exceptions.CornerNotOnSideException;
import sheet.cutcase.free.piece.exceptions.CornersOnSidesShareNoCoordinateException;
import sheet.cutcase.free.piece.exceptions.CutCaseNullArgumentException;
import sheet.exceptions.*;

import java.util.Iterator;
import java.util.TreeSet;

public class Solution implements Comparable<Solution> {
    private TreeSet<LayoutVariation> layoutVariations;

    public Solution() throws SheetSizeException, CornersOnSidesShareNoCoordinateException, BadCoordinateValueException, NegativePiecePointsException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, LayoutFactoryNotInitiatedException, CornerNotOnSideException, NotAllCornersFoundException {
        this.layoutVariations = new TreeSet<>();
//        this.layoutVariations.add(new LayoutVariation());
    }

    public Solution(Solution template) throws CloneNotSupportedException {
        this.layoutVariations = new TreeSet<>();
        for (LayoutVariation layoutVariation : template.layoutVariations)
            this.layoutVariations.add((LayoutVariation) layoutVariation.clone());
    }

    public TreeSet<LayoutVariation> getLayoutVariations() {
        return this.layoutVariations;
    }

    public void add(LayoutVariation layoutVariation) {
        this.layoutVariations.add(layoutVariation);
    }

    public boolean contains(Piece piece) {
        for (LayoutVariation layoutVariation : this.layoutVariations)
            for (PieceVariation pieceVariation : layoutVariation.getPieceVariations())
                if (pieceVariation.getPiece().getId() == piece.getId()) return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("layoutVariations=");
        if (layoutVariations == null) output.append("null");
        else {
            output.append("TreeSet<LayoutVariation>{");
            Iterator iterator = layoutVariations.iterator();
            while (iterator.hasNext()) {
                output.append(iterator.next().toString());
                if (iterator.hasNext()) output.append(", ");
            }
            output.append("}");
        }
        return output.append("}").toString();
    }

    public String toString(int level) {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{\n");
        output = appendByTab(output, level + 1)
                .append("layoutVariations=");
        if (this.layoutVariations == null) output.append("null");
        else {
            output.append("TreeSet<LayoutVariation>{");
            for (LayoutVariation layoutVariation : layoutVariations) {
                output.append("\n");
                output = appendByTab(output, level + 2);
                output.append(layoutVariation.toString(level + 2));
            }
            if (!this.layoutVariations.isEmpty()) {
                output.append("\n");
                output = appendByTab(output, level + 1);
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
        int comparison = other.getPointSum() - this.getPointSum();
        if (comparison == 0) comparison = other.getLayoutVariations().size() - this.getLayoutVariations().size();
        if (comparison == 0) {
            Iterator otherIterator = other.getLayoutVariations().iterator();
            Iterator thisIterator = this.getLayoutVariations().iterator();
            while (otherIterator.hasNext() && thisIterator.hasNext()) {
                LayoutVariation otherLayoutVariation = (LayoutVariation) otherIterator.next();
                LayoutVariation thisLayoutVariation = (LayoutVariation) thisIterator.next();
                comparison = thisLayoutVariation.compareTo(otherLayoutVariation);
                if (comparison != 0) break;
            }
        }
        return comparison;
    }

    public int getPointSum() {
        int pointSum = 0;
        for (LayoutVariation layoutVariation : this.layoutVariations) {
            pointSum += layoutVariation.getPoints();
        }
        return pointSum;
    }
}
