package ui.center.options;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

@Getter
@Setter
public abstract class AbstractCenterUI {
    public static final String DEFAULT_EXERCISES_MENU_ITEM_TEXT = "The only exercise";

    private AbstractCenterUIController controller = null;

    private AbstractCenterUI() {}

    /** String exercise is
     * final static EXERCISES[index]
     * field of given class extending this one
    **/
    public AbstractCenterUI(String exercise) {
        try {
            loadController(exercise);
        } catch(IOException | NoMatchingIndexFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadController(String exercise) throws IOException, NoMatchingIndexFoundException {
        int viewFileIndex = getViewFileIndex(exercise);
        URL url = loadURL(viewFileIndex);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
    }

    public abstract int getViewFileIndex(String exercise) throws NoMatchingIndexFoundException;
    public int getSafelyViewFileIndex(String[] EXERCISES, String exercise) throws NoMatchingIndexFoundException {
        int viewFileIndex = Arrays.asList(EXERCISES).indexOf(exercise);
        if(viewFileIndex == -1) throw new NoMatchingIndexFoundException(EXERCISES, exercise);
        return viewFileIndex;
    }
    public abstract URL loadURL(int viewFileIndex);

    public AbstractCenterUIController getController() {
        return this.controller;
    }
}
