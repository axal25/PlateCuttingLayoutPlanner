package sheet.sort.strategy;

import sheet.Piece;

public enum PieceSortStrategy implements IPieceCompare {
    HEIGHT_DESC{
        @Override
        public int compareTo(Piece thisPiece, Piece otherPiece) {
            return otherPiece.getHeight() - thisPiece.getHeight();
        }
    },
    WIDTH_DESC{
        @Override
        public int compareTo(Piece thisPiece, Piece otherPiece) {
            return otherPiece.getWidth() - thisPiece.getWidth();
        }
    },
    LONGEST_SIDE_DESC{
        @Override
        public int compareTo(Piece thisPiece, Piece otherPiece) {
            final int thisLongestSide = Math.max(thisPiece.getHeight(), thisPiece.getWidth());
            final int otherLongestSide = Math.max(otherPiece.getHeight(), otherPiece.getWidth());
            return otherLongestSide - thisLongestSide;
        }
    },
    AREA_DESC{
        @Override
        public int compareTo(Piece thisPiece, Piece otherPiece) {
            final int thisArea = thisPiece.getHeight() * thisPiece.getWidth();
            final int otherArea = otherPiece.getHeight() * otherPiece.getWidth();
            return otherArea - thisArea;
        }
    },
    POINTS_TO_LONGEST_SIDE_RATIO_DESC{
        @Override
        public int compareTo(Piece thisPiece, Piece otherPiece) {
            return getPointsToLongestSideRatioDescDifference(thisPiece, otherPiece);
        }

        private int getPointsToLongestSideRatioDescDifference(Piece piece1, Piece piece2) {
            final double answerLongestSideDouble = getPointsToLongestSideRatioDesc(piece2) - getPointsToLongestSideRatioDesc(piece1);
            if (answerLongestSideDouble >= 0) return (int) Math.ceil(answerLongestSideDouble);
            else return (int) Math.floor(answerLongestSideDouble);
        }

        private double getPointsToLongestSideRatioDesc(Piece piece) {
            final int pieceLongestSide = Math.max(piece.getHeight(), piece.getWidth());
            return ((double) piece.getPoints()) / ((double) pieceLongestSide);
        }
    },
    POINTS_TO_AREA_RATIO_DESC{
        @Override
        public int compareTo(Piece thisPiece, Piece otherPiece) {
            return getPointsToAreaRatioDescDifference(thisPiece, otherPiece);
        }

        private int getPointsToAreaRatioDescDifference(Piece piece1, Piece piece2) {
            final double answerAreaDouble = getPointsToAreaRatioDesc(piece2) - getPointsToAreaRatioDesc(piece1);
            if (answerAreaDouble >= 0) return (int) Math.ceil(answerAreaDouble);
            else return (int) Math.floor(answerAreaDouble);
        }

        private double getPointsToAreaRatioDesc(Piece piece) {
            final int pieceArea = piece.getHeight() * piece.getWidth();
            return ((double) piece.getPoints()) / ((double) pieceArea);
        }
    };
}
