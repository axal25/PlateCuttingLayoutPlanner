package ui.example;

import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExampleMainController implements Initializable {
    public Button button1, button2;
    public Label labelILoveMushedPotatoes, labelILoveMushedPotatoes2, labelILoveMushedPotatoes3;
    public JFXProgressBar jfxProgressBar1;

    public void handleButton1Click() {
        labelILoveMushedPotatoes.setText("You have clicked button1, now click button2.");
    }

    public void handleButton2Click() {
        labelILoveMushedPotatoes.setText("You have clicked button2, now click button1.");
    }

    public void handleJfxButton1() {
        showProgressBar1();
        hideProgressBar1AfterDelay((long) 2500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideProgressBar1AfterDelay((long) 2500, TimeUnit.MILLISECONDS);
    }

    private void hideProgressBar1AfterDelay(Long timeDelay, TimeUnit timeUnit) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(this::hideProgressBar1, timeDelay, timeUnit);
    }

    private void hideProgressBar1() {
        try {
//            this.jfxProgressBar1.getStyleClass().clear();
//            this.jfxProgressBar1.getStyleClass().add("jfx-progress-bar");
            this.jfxProgressBar1.getStyleClass().removeAll("jfx-progress-bar-visible");
            this.jfxProgressBar1.getStyleClass().add("jfx-progress-bar-hidden");
        } catch(Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void showProgressBar1() {
        try {
//            this.jfxProgressBar1.getStyleClass().clear();
//            this.jfxProgressBar1.getStyleClass().add("jfx-progress-bar");
            this.jfxProgressBar1.getStyleClass().removeAll("jfx-progress-bar-hidden");
            this.jfxProgressBar1.getStyleClass().add("jfx-progress-bar-visible");
        } catch(Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
