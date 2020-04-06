package ui.center.options.mayeryn.recruit;

import javafx.scene.Parent;
import ui.FxmlResourceUrlFetcher;
import ui.center.options.AbstractCenterUI;
import ui.center.options.NoMatchingIndexFoundException;

import java.io.File;
import java.net.URL;

public class MayerynRecruitUI extends AbstractCenterUI {
    public static final String[] VIEW_FILE_NAMES = {
            "MayerynRecruitUI_Exercise1.fxml",
            "MayerynRecruitUI_Exercise2.fxml",
            "MayerynRecruitUI_Exercise3.fxml",
    };
    public static final String VIEW_FILE_PACKAGE = "ui" + File.separator + "center" + File.separator + "options" + File.separator + "mayeryn" + File.separator + "recruit";
    public static final String[] EXERCISES = {
            "Exercise #1",
            "Exercise #2",
            "Exercise #3",
    };

    private Parent parent;
    private MayerynRecruitUIController controller;

    public MayerynRecruitUI(String exercise) {
        super(exercise);
    }

    public int getViewFileIndex(String exercise) throws NoMatchingIndexFoundException {
        return getSafelyViewFileIndex(EXERCISES, exercise);
    }

    public URL loadURL(int viewFileIndex) {
        return FxmlResourceUrlFetcher.getViewFileUrl(VIEW_FILE_PACKAGE, VIEW_FILE_NAMES[viewFileIndex]);
    }
}
