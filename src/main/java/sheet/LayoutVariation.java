package sheet;

import coords.RectangleCorners;
import coords.exceptions.BadCoordinateValueException;
import solution.Solution;
import sheet.cutcase.free.piece.exceptions.BadAmountOfCoordinatesFoundException;
import sheet.cutcase.free.piece.exceptions.CornerNotOnSideException;
import sheet.cutcase.free.piece.exceptions.CornersOnSidesShareNoCoordinateException;
import sheet.cutcase.free.piece.exceptions.CutCaseNullArgumentException;
import sheet.exceptions.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class LayoutVariation implements Comparable<LayoutVariation> {
    private final Layout layout;
    private LinkedHashSet<PieceVariation> pieceVariations;
    private TreeSet<FreePieceVariation> freePieceVariations;
    private int freeArea;
    private int points;

    public LayoutVariation() throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        this(StaticLayoutFactory.getLayoutFactory().getLayout());
    }

    public LayoutVariation(Layout layout) throws SheetSizeException, CornersOnSidesShareNoCoordinateException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, CornerNotOnSideException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, NotAllCornersFoundException, BadCoordinateValueException {
        this.layout = layout;
        this.freeArea = this.layout.getHeight() * this.layout.getWidth();
        this.points = -this.freeArea;
        this.pieceVariations = new LinkedHashSet<>();
        this.updateFreePieceVariations(null);
    }

    private LayoutVariation(Layout layout, int freeArea, int points, LinkedHashSet<PieceVariation> pieceVariations, TreeSet<FreePieceVariation> freePieceVariations) throws CloneNotSupportedException {
        this.layout = layout;
        this.freeArea = freeArea;
        this.points = points;
        this.pieceVariations = new LinkedHashSet<>();
        for (PieceVariation pieceVariation : pieceVariations) {
            this.pieceVariations.add((PieceVariation) pieceVariation.clone());
        }
        this.freePieceVariations = new TreeSet<FreePieceVariation>();
        for (FreePieceVariation freePieceVariation : freePieceVariations) {
            this.freePieceVariations.add((FreePieceVariation) freePieceVariation.clone());
        }
    }

    private LayoutVariation(LayoutVariation template) throws CloneNotSupportedException {
        this(template.layout, template.freeArea, template.points, template.pieceVariations, template.freePieceVariations);
    }

    public Layout getLayout() {
        return this.layout;
    }

    public HashSet<PieceVariation> getPieceVariations() {
        return this.pieceVariations;
    }

    public TreeSet<FreePieceVariation> getFreePieceVariations() {
        return this.freePieceVariations;
    }

    public int getFreeArea() {
        return this.freeArea;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean isEnoughFreeArea(PieceVariation candidatePieceVariation) {
        return 0 <= this.freeArea - candidatePieceVariation.getArea();
    }

    public boolean isInsideLayoutVariation(PieceVariation candidatePieceVariation) {
        return candidatePieceVariation.getNorthWesternCoord().getX() >= 0 &&
                candidatePieceVariation.getNorthWesternCoord().getY() >= 0 &&
                candidatePieceVariation.getNorthWesternCoord().getX() + candidatePieceVariation.getPiece().getWidth() <= this.layout.getWidth() &&
                candidatePieceVariation.getNorthWesternCoord().getY() + candidatePieceVariation.getPiece().getHeight() <= this.layout.getHeight();
    }

    public boolean isGivenAreaFree(PieceVariation candidatePieceVariation) throws BadCoordinateValueException {
        RectangleCorners candidatePVRectangleCorners = new RectangleCorners(candidatePieceVariation);
        for (PieceVariation memberPieceVariation : this.pieceVariations) {
            RectangleCorners memberPVRectangleCorners = new RectangleCorners(memberPieceVariation);
            if (memberPVRectangleCorners.isRectangleOverlapping(candidatePVRectangleCorners)) return false;
        }
        return true;
    }

    public boolean add(PieceVariation pieceVariation) throws BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        if (isEnoughFreeArea(pieceVariation) && isInsideLayoutVariation(pieceVariation) && isGivenAreaFree(pieceVariation)) {
            this.pieceVariations.add(pieceVariation);
            this.freeArea -= pieceVariation.getArea();
            updateFreePieceVariations(pieceVariation);
            this.points += pieceVariation.getPiece().getPoints() + (pieceVariation.getPiece().getWidth() * pieceVariation.getPiece().getHeight());
            return true;
        }
        return false;
    }

    /**
     * returned cutUpFreePieceVariations are always biggest,
     * and every pieceVariation overlaps one of previous freePieceVariations,
     * so if PieceVariation is overlapping one of freePieceVariations but doesn't fit inside
     * it fits wholly inside another, but only partially inside this one
     */
    private void updateFreePieceVariations(PieceVariation pieceVariation) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        if (pieceVariation == null) {
            if (pieceVariations != null && !pieceVariations.isEmpty()) throw new PieceVariationsNotInitiatedException(
                    this.getClass().getSimpleName(),
                    "setFreePieceVariations(PieceVariation pieceVariation)",
                    "PieceVariation must be given or pieceVariations inside LayoutVariation must be initiated."
            );
            this.freePieceVariations = new TreeSet<FreePieceVariation>();
            this.freePieceVariations.add(FreePieceVariation.getNewFreePieceVariation(0, this.layout.getWidth(), this.layout.getHeight(), this.points, 0, 0));
        } else {
            TreeSet<FreePieceVariation> fpvsToRemove = new TreeSet<>();
            TreeSet<FreePieceVariation> fpvsToAdd = new TreeSet<>();
            for (FreePieceVariation freePieceVariation : this.freePieceVariations) {
                if (pieceVariation.isOverlapping(freePieceVariation)) {
                    TreeSet<FreePieceVariation> cutUpFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
                    fpvsToRemove.add(freePieceVariation);
                    fpvsToAdd.addAll(cutUpFreePieceVariations);
                }
            }
            fpvsToRemove.forEach(fpvToRemove -> this.freePieceVariations.remove(fpvToRemove));
            this.freePieceVariations.addAll(fpvsToAdd);
        }
    }

    public PieceVariation findSameSizePieceVariation(PieceVariation candidatePieceVariation) throws CloneNotSupportedException {
        if (isEnoughFreeArea(candidatePieceVariation))
            for (FreePieceVariation freePieceVariation : this.freePieceVariations) {
                if (freePieceVariation.getArea() == candidatePieceVariation.getArea() &&
                        (
                                (
                                        freePieceVariation.getPiece().getWidth() == candidatePieceVariation.getPiece().getWidth() &&
                                                freePieceVariation.getPiece().getHeight() == candidatePieceVariation.getPiece().getHeight()
                                ) || (
                                        freePieceVariation.getPiece().getWidth() == candidatePieceVariation.getPiece().getHeight() &&
                                                freePieceVariation.getPiece().getHeight() == candidatePieceVariation.getPiece().getWidth()
                                )
                        )
                ) {
                    PieceVariation perfectlyFittingPieceVariation = (PieceVariation) candidatePieceVariation.clone();
                    if (freePieceVariation.getPiece().getWidth() != perfectlyFittingPieceVariation.getPiece().getWidth())
                        perfectlyFittingPieceVariation.rotate();
                    perfectlyFittingPieceVariation.setNorthWesternCoord(freePieceVariation.getNorthWesternCoord());
                    return perfectlyFittingPieceVariation;
                }
            }
        return null;
    }

    public PieceVariation findSameWidthOrHeightPieceVariation(PieceVariation candidatePieceVariation) throws CloneNotSupportedException {
        if (isEnoughFreeArea(candidatePieceVariation))
            for (FreePieceVariation freePieceVariation : this.freePieceVariations) {
                if (freePieceVariation.getArea() >= candidatePieceVariation.getArea() &&
                        (
                                freePieceVariation.getPiece().getWidth() == candidatePieceVariation.getPiece().getWidth() ||
                                        freePieceVariation.getPiece().getHeight() == candidatePieceVariation.getPiece().getHeight() ||
                                        freePieceVariation.getPiece().getWidth() == candidatePieceVariation.getPiece().getHeight() ||
                                        freePieceVariation.getPiece().getHeight() == candidatePieceVariation.getPiece().getWidth()
                        )
                ) {
                    PieceVariation pieceVariationWithSameWidthOrHeight = (PieceVariation) candidatePieceVariation.clone();
                    if (
                            freePieceVariation.getPiece().getWidth() != candidatePieceVariation.getPiece().getWidth() &&
                                    freePieceVariation.getPiece().getHeight() != candidatePieceVariation.getPiece().getHeight()
                    ) pieceVariationWithSameWidthOrHeight.rotate();
                    pieceVariationWithSameWidthOrHeight.setNorthWesternCoord(freePieceVariation.getNorthWesternCoord());
                    return pieceVariationWithSameWidthOrHeight;
                }
            }
        return null;
    }

    public PieceVariation findSmallerSizePieceVariation(PieceVariation candidatePieceVariation) throws CloneNotSupportedException {
        if (isEnoughFreeArea(candidatePieceVariation))
            for (FreePieceVariation freePieceVariation : this.freePieceVariations) {
                if (freePieceVariation.getArea() >= candidatePieceVariation.getArea() &&
                        (
                                (
                                        freePieceVariation.getPiece().getWidth() > candidatePieceVariation.getPiece().getWidth() &&
                                                freePieceVariation.getPiece().getHeight() > candidatePieceVariation.getPiece().getHeight()
                                ) || (
                                        freePieceVariation.getPiece().getWidth() > candidatePieceVariation.getPiece().getHeight() &&
                                                freePieceVariation.getPiece().getHeight() > candidatePieceVariation.getPiece().getWidth()
                                )
                        )
                ) {
                    PieceVariation smallerSizePieceVariation = (PieceVariation) candidatePieceVariation.clone();
                    if (
                            (
                                    freePieceVariation.getPiece().getWidth() < candidatePieceVariation.getPiece().getWidth() &&
                                    freePieceVariation.getPiece().getWidth() > candidatePieceVariation.getPiece().getHeight() &&
                                    freePieceVariation.getPiece().getHeight() > candidatePieceVariation.getPiece().getWidth()
                            ) || (
                                    freePieceVariation.getPiece().getHeight() < candidatePieceVariation.getPiece().getHeight() &&
                                    freePieceVariation.getPiece().getHeight() > candidatePieceVariation.getPiece().getWidth() &&
                                    freePieceVariation.getPiece().getWidth() > candidatePieceVariation.getPiece().getHeight()
                            )
                    ) smallerSizePieceVariation.rotate();
                    smallerSizePieceVariation.setNorthWesternCoord(freePieceVariation.getNorthWesternCoord());
                    return smallerSizePieceVariation;
                }
            }
        return null;
    }

    @Override
    public int compareTo(LayoutVariation other) {
        int comparison = other.points - this.points;
        if (comparison == 0) comparison = other.freeArea - this.freeArea;
        if (comparison == 0) comparison = other.pieceVariations.size() - this.pieceVariations.size();
        if (comparison == 0) comparison = other.freePieceVariations.size() - this.freePieceVariations.size();
        if (comparison == 0) {
            Iterator otherPvIterator = other.pieceVariations.iterator();
            Iterator thisPvIterator = this.pieceVariations.iterator();
            while (otherPvIterator.hasNext() && thisPvIterator.hasNext()) {
                PieceVariation otherPv = (PieceVariation) otherPvIterator.next();
                PieceVariation thisPv = (PieceVariation) thisPvIterator.next();
                comparison = thisPv.compareTo(otherPv);
                if (comparison != 0) break;
            }
        }
        if (comparison == 0) {
            Iterator otherFpvIterator = other.freePieceVariations.iterator();
            Iterator thisFpvIterator = this.freePieceVariations.iterator();
            while (otherFpvIterator.hasNext() && thisFpvIterator.hasNext()) {
                FreePieceVariation otherPv = (FreePieceVariation) otherFpvIterator.next();
                FreePieceVariation thisPv = (FreePieceVariation) thisFpvIterator.next();
                comparison = thisPv.compareTo(otherPv);
                if (comparison != 0) break;
            }
        }
        return comparison;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return (LayoutVariation) super.clone();
        } catch (CloneNotSupportedException e) {
            return new LayoutVariation(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("layout=")
                .append(this.layout.toString())
                .append(", this.freeArea=")
                .append(this.freeArea)
                .append(", points=")
                .append(this.points)
                .append(", pieceVariations=");
        if (this.pieceVariations == null) output.append("null");
        else {
            output.append("LinkedHashSet<PieceVariation>{");
            for (PieceVariation pieceVariation : this.pieceVariations) {
                output.append(pieceVariation.toString());
            }
            output.append("}");
        }
        return output.append("}").toString();
    }

    public String toString(int level) {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{\n");
        Solution.appendByTab(output, level + 1)
                .append("layout=")
                .append(this.layout.toString())
                .append(",\n");
        Solution.appendByTab(output, level + 1)
                .append("this.freeArea=")
                .append(this.freeArea)
                .append(",\n");
        Solution.appendByTab(output, level + 1)
                .append("points=")
                .append(this.points)
                .append(",\n");
        Solution.appendByTab(output, level + 1)
                .append("pieceVariations=");
        if (this.pieceVariations == null) output.append("null");
        else {
            output.append("LinkedHashSet<PieceVariation>{");
            for (PieceVariation pieceVariation : this.pieceVariations) {
                output.append("\n");
                Solution.appendByTab(output, level + 2)
                        .append(pieceVariation.toString(level + 2));
            }
            if (!this.pieceVariations.isEmpty()) {
                output.append("\n");
                Solution.appendByTab(output, level + 1);
            }
            output.append("}");
        }
        output.append(",\n");
        Solution.appendByTab(output, level + 1)
                .append("freePieceVariations=");
        if (this.freePieceVariations == null) output.append("null");
        else {
            output.append("TreeSet<FreePieceVariation>{");
            for (FreePieceVariation freePieceVariation : this.freePieceVariations) {
                output.append("\n");
                Solution.appendByTab(output, level + 2)
                        .append(freePieceVariation.toString(level + 2));
            }
            if (!this.freePieceVariations.isEmpty()) {
                output.append("\n");
                Solution.appendByTab(output, level + 1);
            }
            output.append("}");
        }
        output.append("\n");
        return Solution.appendByTab(output, level).append("}").toString();
    }
}
