package sheet;

import coords.Coordinate;
import cutter.Solution;
import orientation.Orientation;

public class PieceVariation {
    private final Piece piece;
    private Orientation orientation;
    private Coordinate northWesternCoord;

    public PieceVariation(Piece piece) throws CloneNotSupportedException {
        this.piece = (Piece) piece.clone();
        calculateOrientation();
        this.northWesternCoord = null;
    }

    private PieceVariation(PieceVariation template) throws CloneNotSupportedException {
        this.piece = (Piece) template.piece.clone();
        this.orientation = template.getOrientation();
        if(template.northWesternCoord != null) this.northWesternCoord = (Coordinate) template.northWesternCoord.clone();
        else this.northWesternCoord = null;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getArea() {
        return this.piece.getHeight() * this.piece.getWidth();
    }

    public Coordinate getNorthWesternCoord() {
        return northWesternCoord;
    }

    public void setNorthWesternCoord(Coordinate northWesternCoord) {
        this.northWesternCoord = northWesternCoord;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    private void calculateOrientation() {
        if(this.piece.getHeight() > this.piece.getWidth()) this.orientation = Orientation.V;
        else this.orientation = Orientation.H;
    }

    public void rotate() {
        if(this.piece.getWidth() == this.piece.getHeight()) {
            swapOrientation();
        } else {
            this.piece.rotate();
            calculateOrientation();
        }
    }

    private void swapOrientation() {
        if(this.orientation == Orientation.H) this.orientation = Orientation.V;
        else this.orientation = Orientation.H;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new PieceVariation(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("piece=")
                .append(this.piece)
                .append(", orientation=")
                .append(this.orientation)
                .append(", northEasternCoord=")
                .append(this.northWesternCoord)
                .append("}");
        return output.toString();
    }

    public String toString(int level) {
        StringBuilder output = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{");
        Solution.appendByTab(output, level+1)
                .append("piece=")
                .append(this.piece)
                .append(",\n");
        Solution.appendByTab(output, level+1)
                .append("orientation=")
                .append(this.orientation)
                .append(",\n");
        Solution.appendByTab(output, level+1)
                .append("northEasternCoord=")
                .append(this.northWesternCoord)
                .append("\n");
        Solution.appendByTab(output, level)
                .append("}");
        return output.toString();
    }
}
