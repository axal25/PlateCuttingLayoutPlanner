package examples;

import orientation.Orientation;

import java.util.Arrays;
import java.util.stream.Stream;

public class Example {
    public InputData inputData;
    public OutputData outputData;

    public Example(InputData inputData, OutputData outputData) {
        this.inputData = inputData;
        this.outputData = outputData;
    }

    public static class InputData {
        LayoutData layout;
        int amountOfPieces;
        PieceInputData[] pieces;

        public InputData(LayoutData layout, int amountOfPieces, PieceInputData... pieces) {
            this.layout = layout;
            this.amountOfPieces = amountOfPieces;
            this.pieces = pieces;
        }

        @Override
        public String toString() {
            return String.format(
                    "%s{ layout: %s, amountOfPieces: %d, pieces: %s }",
                    this.getClass().getSimpleName(),
                    this.layout.toString(),
                    this.amountOfPieces,
                    this.pieces.toString()
            );
        }

        public String[] toArgs() {
            return Stream.concat(
                    Stream.concat(
                            Arrays.stream(this.layout.toArgs()),
                            Arrays.stream(new String[]{String.format("%d", this.amountOfPieces)})
                    ),
                    Arrays.stream(piecesToArgs())
            ).toArray(String[]::new);
        }

        private String[] piecesToArgs() {
            return Arrays.stream(this.pieces).flatMap(piece -> Arrays.stream(piece.toArgs())).toArray(String[]::new);
        }

        public static class LayoutData {
            int width, height;

            public LayoutData(int width, int height) {
                this.width = width;
                this.height = height;
            }

            @Override
            public String toString() {
                return String.format(
                        "%s{ width: %s, height: %s }",
                        this.getClass().getSimpleName(),
                        this.width,
                        this.height
                );
            }

            public String[] toArgs() {
                return new String[]{
                        String.format("%d", this.width),
                        String.format("%d", this.height)
                };
            }
        }

        public static class PieceInputData {
            int width, height, points;

            public PieceInputData(int width, int height, int points) {
                this.width = width;
                this.height = height;
                this.points = points;
            }

            @Override
            public String toString() {
                return String.format(
                        "%s{ width: %s, height: %s, points: %s }",
                        this.getClass().getSimpleName(),
                        this.width,
                        this.height,
                        this.points
                );
            }

            public String[] toArgs() {
                return new String[]{
                        String.format("%d", this.width),
                        String.format("%d", this.height),
                        String.format("%d", this.points)
                };
            }
        }
    }

    public static class OutputData {
        int amountOfCutPieces;
        PieceOutputData[] pieces;

        public OutputData(int amountOfCutPieces, PieceOutputData... pieces) {
            this.amountOfCutPieces = amountOfCutPieces;
            this.pieces = pieces;
        }

        @Override
        public String toString() {
            return String.format(
                    "%s{ amountOfPieces: %d, pieces: %s }",
                    this.getClass().getSimpleName(),
                    this.amountOfCutPieces,
                    this.pieces.toString()
            );
        }

        public String toOutputString() {
            return String.format(
                    "%d\r\n%s\n",
                    this.amountOfCutPieces,
                    piecesToOutputString()
            );
        }

        private String piecesToOutputString() {
            StringBuilder piecesInputString = new StringBuilder();
            Arrays.asList(this.pieces).forEach(piece -> {
                if (piecesInputString.length() != 0) piecesInputString.append("\r\n");
                piecesInputString.append(piece.toOutputString());
            });
            return piecesInputString.toString();
        }

        public static class PieceOutputData {
            int id, x, y;
            Orientation orientation;

            public PieceOutputData(int id, Orientation orientation, int x, int y) {
                this.id = id;
                this.orientation = orientation;
                this.x = x;
                this.y = y;
            }

            @Override
            public String toString() {
                return String.format(
                        "%s{ id: %d, orientation: %s, this.x: %d, this.y: %d }",
                        this.getClass().getSimpleName(),
                        this.id,
                        this.orientation,
                        this.x,
                        this.y
                );
            }

            public String toOutputString() {
                return String.format(
                        "%d %s %d %d",
                        this.id,
                        this.orientation,
                        this.x,
                        this.y
                );
            }
        }
    }
}
