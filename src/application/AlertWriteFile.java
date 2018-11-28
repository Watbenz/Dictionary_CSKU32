package application;

import application.myFormatter.MyFormatter;
import dictionary.Dictionary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertWriteFile {
    @FXML private Stage stage;
    @FXML private Label dataLabel;
    private Dictionary dictionary;
    private MyFormatter formatter;

    @FXML
    public void initialize() {
        Platform.runLater(() -> dataLabel.setText(dictionary.format(formatter)));
    }

    @FXML
    public void closeWindow() {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void setFormatter(MyFormatter formatter) {
        this.formatter = formatter;
    }
}
