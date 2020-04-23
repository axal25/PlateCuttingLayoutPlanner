package cutter;

import sheet.LayoutVariation;
import sheet.PieceVariation;

// FunctionalInterface
public interface FIFindPieceVariation {
    // SAM (Single Abstract Method)
    public PieceVariation findPieceVariation(LayoutVariation layoutVariation, PieceVariation pieceVariation) throws CloneNotSupportedException;
}
