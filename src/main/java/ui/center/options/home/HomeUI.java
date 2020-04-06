package ui.center.options.home;

import javafx.scene.Parent;
import ui.FxmlResourceUrlFetcher;
import ui.center.options.AbstractCenterUI;
import ui.center.options.NoMatchingIndexFoundException;

import java.io.File;
import java.net.URL;

public class HomeUI extends AbstractCenterUI {
    public static final String[] VIEW_FILE_NAMES = {"HomeUI.fxml"};
    public static final String VIEW_FILE_PACKAGE = "ui" + File.separator + "center" + File.separator + "options" + File.separator + "home";
    public static final String[] EXERCISES = {"The only welcome page available"};

    private Parent parent;
    private HomeUIController controller;

    public HomeUI(String exercise) {
        super(exercise);
    }

    public int getViewFileIndex(String exercise) throws NoMatchingIndexFoundException {
        return getSafelyViewFileIndex(EXERCISES, exercise);
    }

    public URL loadURL(int viewFileIndex) {
        return FxmlResourceUrlFetcher.getViewFileUrl(VIEW_FILE_PACKAGE, VIEW_FILE_NAMES[viewFileIndex]);
    }
}
