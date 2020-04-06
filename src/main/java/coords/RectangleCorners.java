package coords;

public class RectangleCorners {
    Coordinate northWesternCoord, southEasternCoord;

    public RectangleCorners(Coordinate northWesternCoord, int width, int height) throws BadCoordinateValueException {
        this.northWesternCoord = northWesternCoord;
        this.southEasternCoord = new Coordinate(northWesternCoord.getX() + width, northWesternCoord.getY() + height);
    }

    public Coordinate getNorthWesternCoord() {
        return northWesternCoord;
    }

    public void setNorthWesternCoord(Coordinate northWesternCoord) {
        this.northWesternCoord = northWesternCoord;
    }

    public Coordinate getSouthEasternCoord() {
        return southEasternCoord;
    }

    public void setSouthEasternCoord(Coordinate southEasternCoord) {
        this.southEasternCoord = southEasternCoord;
    }

    public boolean isOverlapping(RectangleCorners other) {
        if(this.isNorthOf(other) || this.isSouthOf(other) || this.isEastOf(other) || this.isWestOf(other)) return false;
        return true;
    }

    boolean isNorthOf(RectangleCorners other) {
        if(other == null) return true;
        return this.southEasternCoord.getY() <= other.northWesternCoord.getY();
    }

    boolean isSouthOf(RectangleCorners other) {
        if(other == null) return true;
        return this.northWesternCoord.getY() >= other.southEasternCoord.getY();
    }

    boolean isWestOf(RectangleCorners other) {
        if(other == null) return true;
        return this.southEasternCoord.getX() <= other.northWesternCoord.getX();
    }

    boolean isEastOf(RectangleCorners other) {
        if(other == null) return true;
        return this.northWesternCoord.getX() >= other.southEasternCoord.getX();
    }
}
