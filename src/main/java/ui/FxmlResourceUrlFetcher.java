package ui;

import javafx.fxml.FXMLLoader;
import main.AppMain;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class FxmlResourceUrlFetcher {
    public static final String PATH_TO_RESOURCE_FOLDER = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources";

    /**
     * View file (.fxml) needs to be in "src/main/resources/..." location under folder corresponding to Model class
     * (.java) package location from "src/main/java/..." to be able to get URL using:
     * URL url = ClassName.class.getResource(viewFileName);
     *
     * The second option is to get URL using absolute path via:
     * URL url = new File(viewFileFullPath).toURI().toURL();
     **/
    public static URL getViewFileUrl(String packageName, String fileName) {
        URL viewFileUrl = getViewFileUrlNotPackagedRelative(fileName);
        if(!isUrlValid(viewFileUrl)) viewFileUrl = getViewFileUrlNotPackagedAbsolute(packageName, fileName);
        if(!isUrlValid(viewFileUrl)) viewFileUrl = getViewFileUrlPackaged(packageName, fileName);
        if(!isUrlValid(viewFileUrl)) throw new NullPointerException("Could not retrieve resource. Using any method." + "\n\r" +
                "\tpackageName: \"" + packageName + "\", fileName: \"" + fileName + "\".");
        return viewFileUrl;
    }

    private static boolean isUrlValid(URL url) {
        try {
            FXMLLoader.load(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
    * NOT packaged | RELATIVE path - from not packaged resource folder using relative path (not .jar)
    **/
    private static URL getViewFileUrlNotPackagedRelative(String fileName) {
        URL viewFileUrl = MainUI.class.getResource(fileName);
        return viewFileUrl;
    }

    /**
     * NOT packaged | ABSOLUTE path - from not packaged resource folder using absolute path (not .jar)
     **/
    private static URL getViewFileUrlNotPackagedAbsolute(String packageName, String fileName) {
        final String viewFileFullPath = PATH_TO_RESOURCE_FOLDER + File.separator + packageName + File.separator + fileName;
        URL viewFileUrl = null;
        try {
            viewFileUrl = new File(viewFileFullPath).toURI().toURL();
        } catch(MalformedURLException e) {}
        return viewFileUrl;
    }

    /**
     * PACKAGED (RELATIVE path) - from packaged resource folder (not .jar)
     **/
    private static URL getViewFileUrlPackaged(String packageName, String fileName) {
//        final String viewFileRelativeFilePathForJar = File.separator + packageName + File.separator + fileName;
        final URL viewFileRelativeFilePathForJarURL = AppMain.class.getResource(new StringBuilder().append(File.separator).append(packageName)
                .append(File.separator).append(fileName).toString());
        String viewFileRelativeFilePathForJar = URLDecoder.decode(viewFileRelativeFilePathForJarURL.getFile(), StandardCharsets.UTF_8);
        URL viewFileUrl = AppMain.class.getResource(viewFileRelativeFilePathForJar);
        return viewFileUrl;
    }
}
