package labs.lab1;

import main.AppMain;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import utils.lab1.exceptions.FileOpenException;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CSVFilesTest {
    public static final String NOT_PACKAGED_TARGET_RESOURCE_PATH = new StringBuilder()
            .append(System.getProperty("user.dir")).append(File.separator)
            .append("target").append(File.separator).append("classes").toString();
    public static final String PACKAGED_RESOURCE_PATH = File.separator;
    public static final String PACKAGE = CSVFiles.PACKAGE;

    @Test
    @Order(1)
    void isNotPackaged1() {
        assertTrue(isNotPackagedPrototype());
    }

    boolean isNotPackagedPrototype() {
        URL url = AppMain.class.getResource(new StringBuilder().append(PACKAGED_RESOURCE_PATH).append(PACKAGE).toString());
        String decodedUrlString = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
        return new StringBuilder().append(NOT_PACKAGED_TARGET_RESOURCE_PATH).append(File.separator).append(PACKAGE).toString().compareTo(decodedUrlString) == 0;
    }

    @Test
    @Order(2)
    void isNotPackaged2() throws FileOpenException {
        assertTrue(CSVFiles.isNotPackaged());
    }
}
