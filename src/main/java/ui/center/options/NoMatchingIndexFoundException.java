package ui.center.options;

import java.util.Arrays;

public class NoMatchingIndexFoundException extends Exception {
    public NoMatchingIndexFoundException(String[] exercises, String exercise) {
        super(
                "No matching exercise (\"" + exercise + "\") found in EXERCISES: " + Arrays.toString(exercises),
                new Throwable("No matching exercise (\"" + exercise + "\") found in EXERCISES: " + Arrays.toString(exercises))
        );
    }
}
