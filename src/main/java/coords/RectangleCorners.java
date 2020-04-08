package coords;

import coords.exceptions.BadCoordinateValueException;
import sheet.PieceVariation;

public class RectangleCorners {
    Coordinate northWesternCoord, southEasternCoord;

    public RectangleCorners(PieceVariation pieceVariation) throws BadCoordinateValueException {
        this.northWesternCoord = pieceVariation.getNorthWesternCoord();
        this.southEasternCoord = new Coordinate(
                this.northWesternCoord.getX() + pieceVariation.getPiece().getWidth(),
                this.northWesternCoord.getY() + pieceVariation.getPiece().getHeight()
        );
    }

    public Coordinate getNECoord() throws BadCoordinateValueException {
        return new Coordinate(
                getSECoord().getX(),
                getNWCoord().getY()
        );
    }

    public Coordinate getSWCoord() throws BadCoordinateValueException {
        return new Coordinate(
                getNWCoord().getX(),
                getSECoord().getY()
        );
    }

    public Coordinate getNWCoord() {
        return northWesternCoord;
    }

    public void setNWCoord(Coordinate northWesternCoord) {
        this.northWesternCoord = northWesternCoord;
    }

    public Coordinate getSECoord() {
        return southEasternCoord;
    }

    public void setSECoord(Coordinate southEasternCoord) {
        this.southEasternCoord = southEasternCoord;
    }

    public boolean isRectangleOverlapping(RectangleCorners other) {
        if(this.isRectangleNorthOf(other) || this.isRectangleSouthOf(other) || this.isRectangleEastOf(other) || this.isRectangleWestOf(other)) return false;
        return true;
    }

    boolean isRectangleNorthOf(RectangleCorners other) {
        if(other == null) return true;
        return this.southEasternCoord.getY() <= other.northWesternCoord.getY();
    }

    boolean isRectangleSouthOf(RectangleCorners other) {
        if(other == null) return true;
        return this.northWesternCoord.getY() >= other.southEasternCoord.getY();
    }

    boolean isRectangleWestOf(RectangleCorners other) {
        if(other == null) return true;
        return this.southEasternCoord.getX() <= other.northWesternCoord.getX();
    }

    boolean isRectangleEastOf(RectangleCorners other) {
        if(other == null) return true;
        return this.northWesternCoord.getX() >= other.southEasternCoord.getX();
    }

    public boolean isRectangleInsideOther(RectangleCorners outside) {
        return outside.getNWCoord().getX() <= this.getNWCoord().getX() &&
                outside.getNWCoord().getY() <= this.getNWCoord().getY() &&
                outside.getSECoord().getX() >= this.getSECoord().getX() &&
                outside.getSECoord().getY() >= this.getSECoord().getY();
    }

    public boolean isInside(Coordinate coordinate) {
        return this.getNWCoord().getX() <= coordinate.getX() &&
                this.getNWCoord().getY() <= coordinate.getY() &&
                this.getSECoord().getX() >= coordinate.getX() &&
                this.getSECoord().getY() >= coordinate.getY();
    }

    @Override
    public boolean equals(Object unKnownTypeOther) {
        if(this == unKnownTypeOther) return true;
        if(unKnownTypeOther == null || this.getClass() != unKnownTypeOther.getClass()) return false;
        RectangleCorners other = (RectangleCorners) unKnownTypeOther;
        if(this.getNWCoord().equals(other.getNWCoord()) && this.getSECoord().equals(other.getSECoord())) return true;
        return false;
    }

    @Override
    public int hashCode() {
        final int primeSeed =  89;
        int hashCode = primeSeed + this.getNWCoord().hashCode();
        hashCode = primeSeed * hashCode + this.getSECoord().hashCode();
        return hashCode;
    }
}
