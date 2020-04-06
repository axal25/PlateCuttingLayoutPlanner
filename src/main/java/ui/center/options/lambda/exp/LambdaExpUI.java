package ui.center.options.lambda.exp;

import javafx.scene.Parent;
import ui.FxmlResourceUrlFetcher;
import ui.center.options.AbstractCenterUI;
import ui.center.options.NoMatchingIndexFoundException;

import java.io.File;
import java.net.URL;

public class LambdaExpUI extends AbstractCenterUI {
    public static final String[] VIEW_FILE_NAMES = {"LambdaExpUI.fxml"};
    public static final String VIEW_FILE_PACKAGE = "ui" + File.separator + "center" + File.separator + "options" + File.separator + "lambda" + File.separator + "exp";
    public static final String[] EXERCISES = {AbstractCenterUI.DEFAULT_EXERCISES_MENU_ITEM_TEXT};

    private Parent parent;
    private LambdaExpUIController controller;

    public LambdaExpUI(String exercise) {
        super(exercise);
    }

    public int getViewFileIndex(String exercise) throws NoMatchingIndexFoundException {
        return getSafelyViewFileIndex(EXERCISES, exercise);
    }

    public URL loadURL(int viewFileIndex) {
        return FxmlResourceUrlFetcher.getViewFileUrl(VIEW_FILE_PACKAGE, VIEW_FILE_NAMES[viewFileIndex]);
    }
}
