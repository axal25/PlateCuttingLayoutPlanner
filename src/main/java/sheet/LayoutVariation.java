package sheet;

import coords.BadCoordinateValueException;
import coords.RectangleCorners;
import cutter.Solution;
import sheet.exceptions.*;

import java.util.*;

public class LayoutVariation implements Comparable <LayoutVariation> {
    private final Layout layout;
    private LinkedHashSet<PieceVariation> pieceVariations;
    private TreeSet<FreePieceVariation> freePieceVariations;
    private int freeArea;
    private int points;

    public LayoutVariation() throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException {
        this.layout = StaticLayoutFactory.getLayoutFactory().getLayout();
        this.freeArea = this.layout.getHeight() * this.layout.getWidth();
        this.points = - this.freeArea;
        this.pieceVariations = new LinkedHashSet<>();
        this.setFreePieceVariations(null);
    }

    private LayoutVariation(Layout layout, int freeArea, LinkedHashSet<PieceVariation> pieceVariations, TreeSet<FreePieceVariation> freePieceVariations) throws CloneNotSupportedException {
        this.layout = layout;
        this.freeArea = freeArea;
        this.points = - this.freeArea;
        this.pieceVariations = new LinkedHashSet<>();
        for (PieceVariation pieceVariation : pieceVariations) {
            this.pieceVariations.add((PieceVariation) pieceVariation.clone());
        };
        this.freePieceVariations = new TreeSet<>();
        for (FreePieceVariation freePieceVariation : freePieceVariations) {
            this.freePieceVariations.add((FreePieceVariation) freePieceVariation.clone());
        };
    }

    private LayoutVariation(LayoutVariation template) throws CloneNotSupportedException {
        this(template.layout, template.freeArea , template.pieceVariations, template.freePieceVariations);
    }

    public HashSet<PieceVariation> getPieceVariations() {
        return this.pieceVariations;
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
        RectangleCorners candidateRectangleCornersSheetPieceVariation = new RectangleCorners(
                candidatePieceVariation.getNorthWesternCoord(),
                candidatePieceVariation.getPiece().getWidth(),
                candidatePieceVariation.getPiece().getHeight()
        );
        for (PieceVariation memberPieceVariation : this.pieceVariations) {
            RectangleCorners memberRectangleCornersSheetPieceVariation = new RectangleCorners(
                    memberPieceVariation.getNorthWesternCoord(),
                    memberPieceVariation.getPiece().getWidth(),
                    memberPieceVariation.getPiece().getHeight()
            );
            if(memberRectangleCornersSheetPieceVariation.isOverlapping(candidateRectangleCornersSheetPieceVariation)) return false;
        }
        return true;
    }

    public boolean add(PieceVariation pieceVariation) throws BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, SheetSizeException, CloneNotSupportedException, PieceVariationsNotInitiatedException {
        if(isEnoughFreeArea(pieceVariation) && isInsideLayoutVariation(pieceVariation) && isGivenAreaFree(pieceVariation)) {
            this.pieceVariations.add(pieceVariation);
            this.freeArea = this.freeArea - pieceVariation.getArea();
            setFreePieceVariations(pieceVariation);
            return true;
        }
        return false;
    }

    private void setFreePieceVariations(PieceVariation pieceVariation) throws CloneNotSupportedException, BadCoordinateValueException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, PieceVariationsNotInitiatedException {
        if(pieceVariation == null) {
            if(pieceVariations != null && !pieceVariations.isEmpty()) throw new PieceVariationsNotInitiatedException(
                    this.getClass().getSimpleName(),
                    "setFreePieceVariations(PieceVariation pieceVariation)",
                    "PieceVariation must be given or pieceVariations inside LayoutVariation must be initiated."
            );
            this.freePieceVariations = new TreeSet<>();
            this.freePieceVariations.add(FreePieceVariation.getNewFreePieceVariation(0, this.layout.getWidth(), this.layout.getHeight(), this.points, 0, 0));
        } else {
            for (FreePieceVariation freePieceVariation : this.freePieceVariations) {
                if(freePieceVariation.isOverlapping(pieceVariation)) {
                    TreeSet<FreePieceVariation> cutUpFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
                    this.freePieceVariations.remove(freePieceVariation);
                    this.freePieceVariations.addAll(cutUpFreePieceVariations);
                }
            }
        }
    }

//    public PieceVariation getFittedSheetPieceVariationIntoFreeArea(PieceVariation candidatePieceVariation) throws BadCoordinateValueException, CandidatePieceVariationPositionAlreadySetException, CandidatePieceVariationCouldNotBeFitIntoEmptyLayoutVariation {
//        final String className = this.getClass().getSimpleName();
//        final String functionName = "fitIntoFreeArea(PieceVariation candidatePieceVariation)";
//        if(candidatePieceVariation.getNorthWesternCoord() != null) throw new CandidatePieceVariationPositionAlreadySetException(
//                className,
//                functionName,
//                "Candidate PieceVariation has already been set Coordinate (northEasternCoord != null)."
//        );
//        if(this.pieceVariations.isEmpty()) {
////            candidatePieceVariation = getFittedSheetPieceVariationIntoFreeArea(new Coordinate(0, 0), candidatePieceVariation);
//            if(candidatePieceVariation.getNorthWesternCoord() == null) throw new CandidatePieceVariationCouldNotBeFitIntoEmptyLayoutVariation(
//                    className,
//                    functionName,
//                    new StringBuilder()
//                            .append("Candidate PieceVariation could not be fit into empty LayoutVariation (pieceVariations.isEmpty == true). Possible causes: \n\r")
//                            .append("LayoutVariation width: ")
//                            .append(layout.getWidth())
//                            .append(", height: ")
//                            .append(layout.getHeight())
//                            .append(" \n\rPieceVariation width: ")
//                            .append(candidatePieceVariation.getPiece().getWidth())
//                            .append(", height: ")
//                            .append(candidatePieceVariation.getPiece().getHeight())
//                            .toString()
//            );
//        }
//        Iterator sheetPieceVariationsIterator = this.pieceVariations.iterator();
//        while(sheetPieceVariationsIterator.hasNext() && candidatePieceVariation.getNorthWesternCoord() == null) {
//            PieceVariation memberPieceVariation = (PieceVariation) sheetPieceVariationsIterator.next();
////            candidatePieceVariation = getFittedSheetPieceVariationIntoFreeArea(memberPieceVariation, candidatePieceVariation);
//        }
//        return candidatePieceVariation;
//    }

//    private PieceVariation getFittedSheetPieceVariationIntoFreeArea(PieceVariation memberSheetPieceVariation, PieceVariation candidateSheetPieceVariation) {
//        return getFittedSheetPieceVariationIntoFreeArea(, candidateSheetPieceVariation);
//    }

//    private PieceVariation getFittedSheetPieceVariationIntoFreeArea(Coordinate candidateNorthEasternCoord, PieceVariation candidateSheetPieceVariation) {
//        return null;
//    }

    @Override
    public int compareTo(LayoutVariation other) {
        return other.points - this.points;
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
        if(this.pieceVariations == null) output.append("null");
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
        Solution.appendByTab(output, level+1)
                .append("layout=")
                .append(this.layout.toString())
                .append(",\n");
        Solution.appendByTab(output, level+1)
                .append("this.freeArea=")
                .append(this.freeArea)
                .append(",\n");
        Solution.appendByTab(output, level+1)
                .append("points=")
                .append(this.points)
                .append(",\n");
        Solution.appendByTab(output, level+1)
                .append("pieceVariations=");
        if(this.pieceVariations == null) output.append("null");
        else {
            output.append("LinkedHashSet<PieceVariation>{");
            for (PieceVariation pieceVariation : this.pieceVariations) {
                output.append("\n");
                Solution.appendByTab(output, level+2)
                        .append(pieceVariation.toString(level+2));
            }
            if(!this.pieceVariations.isEmpty()) {
                output.append("\n");
                Solution.appendByTab(output, level+1);
            }
            output.append("}");
        }
        output.append(",\n");
        Solution.appendByTab(output, level+1)
                .append("freePieceVariations=");
        if(this.freePieceVariations == null) output.append("null");
        else {
            output.append("TreeSet<FreePieceVariation>{");
            for (FreePieceVariation freePieceVariation : this.freePieceVariations) {
                output.append("\n");
                Solution.appendByTab(output, level+2)
                        .append(freePieceVariation.toString(level+2));
            }
            if(!this.freePieceVariations.isEmpty()) {
                output.append("\n");
                Solution.appendByTab(output, level+1);
            }
            output.append("}");
        }
        output.append("\n");
        return Solution.appendByTab(output, level).append("}").toString();
    }
}
