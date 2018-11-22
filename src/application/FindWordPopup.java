package application;

import dictionary.Dictionary;
import dictionary.Vocabulary;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class FindWordPopup {
    public TextField wordInputTextField;
    public Label wordLabel;
    public Label meaningLabel;
    public Label speechLabel;
    public Label exampleLabel;
    @FXML private Stage stage;
    private Dictionary dictionary;

    @FXML
    public void initialize() {
        Platform.runLater(this::setUpText);
    }

    @FXML
    private void confirmWordOnEnter() {
        wordInputTextField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                clickToFind();
            }
        });
    }

    @FXML
    public void clickToFind() {
        Vocabulary vocabulary = null;
        String input = wordInputTextField.getText();
        try {
            vocabulary = dictionary.findVocab(input);
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }

        if (vocabulary != null) {
            setUpText(vocabulary);
        }
        wordInputTextField.setText("");
    }

    private void setUpText() {
        wordLabel.setText("");
        meaningLabel.setText("");
        speechLabel.setText("");
        exampleLabel.setText("");
    }

    private void setUpText(Vocabulary vocabulary) {
        wordLabel.setText(vocabulary.getWord());
        meaningLabel.setText(vocabulary.getMean());
        speechLabel.setText(vocabulary.getPartOfSpeech().getName());
        exampleLabel.setText(vocabulary.getExample());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}
