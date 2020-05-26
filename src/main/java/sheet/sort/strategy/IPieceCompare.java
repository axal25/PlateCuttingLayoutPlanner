package sheet.sort.strategy;

import sheet.Piece;

public interface IPieceCompare {
    public int compareTo(Piece thisPiece, Piece otherPiece);
}
