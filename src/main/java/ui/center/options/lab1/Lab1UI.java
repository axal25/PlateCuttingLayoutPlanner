package ui.center.options.lab1;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ui.FxmlResourceUrlFetcher;
import ui.center.options.AbstractCenterUI;
import ui.center.options.NoMatchingIndexFoundException;

import java.io.File;
import java.net.URL;

public class Lab1UI extends AbstractCenterUI {
    public static final String[] VIEW_FILE_NAMES = {
            "Lab1UI_Overview_Load.fxml",
            "Lab1UI_Intro_Load.fxml",
            "Lab1UI_FileShort_Load.fxml",
            "Lab1UI_Ex1_Load.fxml",
            "Lab1UI_Ex2_Load.fxml",
            "Lab1UI_Ex3_Load.fxml",
    };
    public static final String[] COMPLEX_VIEW_FILE_NAMES = {
            "Lab1UI_Overview_Comp.fxml",
            "Lab1UI_Intro_Comp.fxml",
            "Lab1UI_FileShort_Comp.fxml",
            "Lab1UI_Ex1_Comp.fxml",
            "Lab1UI_Ex2_Comp.fxml",
            "Lab1UI_Ex3_Comp.fxml",
    };
    public static final String VIEW_FILE_PACKAGE = "ui" + File.separator + "center" + File.separator + "options" + File.separator + "lab1";
    public static final String[] EXERCISES = {
            "Overview",
            "Introduction",
            "File Shortening",
            "Exercise #1",
            "Exercise #2",
            "Exercise #3",
    };
    public static final Long FAKE_LOADING_DELAY = 500L;

    private Parent parent;
    private Lab1UIController controller;

    public Lab1UI(String exercise) {
        super(exercise);
        loadComplexScene(exercise);
    }

    public int getViewFileIndex(String exercise) throws NoMatchingIndexFoundException {
        return getSafelyViewFileIndex(EXERCISES, exercise);
    }

    public URL loadURL(int viewFileIndex) {
        return FxmlResourceUrlFetcher.getViewFileUrl(VIEW_FILE_PACKAGE, VIEW_FILE_NAMES[viewFileIndex]);
    }

    private void loadComplexScene(String exercise) {
        try {
            startTask(getSafelyViewFileIndex(EXERCISES, exercise));
        } catch (NoMatchingIndexFoundException e) {
            e.printStackTrace();
        }
    }

    private void startTask(int complexFileIndex) {
        Task<Parent> loadTask = new Task<Parent>() {
            public int complexFileIndexInnerCopy = complexFileIndex;

            @Override
            protected Parent call() throws Exception {
                Thread.sleep(FAKE_LOADING_DELAY);
                URL url = FxmlResourceUrlFetcher.getViewFileUrl(VIEW_FILE_PACKAGE, COMPLEX_VIEW_FILE_NAMES[complexFileIndexInnerCopy]);
                FXMLLoader fxmlLoader = new FXMLLoader(url);
                Parent parent = fxmlLoader.load();
                return parent;
            }
        };

        loadTask.setOnSucceeded(workerStateEvent -> {
            super.getController().getCenterNodeProperty().set(loadTask.getValue());
        });

        loadTask.setOnFailed(workerStateEvent -> {
            loadTask.getException().printStackTrace();
        });

        Thread thread = new Thread(loadTask);
        thread.start();
    }
}
