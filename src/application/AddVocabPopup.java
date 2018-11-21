package application;

import dictionary.Dictionary;
import dictionary.PartOfSpeech;
import dictionary.Vocabulary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddVocabPopup {
    public TextField wordTextField;
    public TextField meanTextField;
    public MenuButton speechMenuButton;
    public TextField exampleTextField;
    public Button addButton;
    private Dictionary dictionary;
    private Runnable callbackUpdate;
    @FXML private Stage stage;

    @FXML
    public void initialize() {
        for (MenuItem item: speechMenuButton.getItems()) {
            item.setOnAction(event -> speechMenuButton.setText(item.getText()));
        }
    }

    @FXML
    private void clickAddButton() {
        String word = wordTextField.getText();
        String mean = meanTextField.getText();
        PartOfSpeech speech = findPartOfSpeech(speechMenuButton.getText());
        String example = exampleTextField.getText();

        try {
            Vocabulary vocabulary = new Vocabulary(word, mean, speech, example);
            dictionary.addVocab(vocabulary);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        wordTextField.setText("");
        meanTextField.setText("");
        exampleTextField.setText("");
        speechMenuButton.setText("ประเภทของคำ");
        callbackUpdate.run();
    }

    private PartOfSpeech findPartOfSpeech(String string) {
        switch (string) {
            case "Noun":
                return PartOfSpeech.Noun;
            case "Pronoun":
                return PartOfSpeech.Pronoun;
            case "Verb":
                return PartOfSpeech.Verb;
            case "Adverb":
                return PartOfSpeech.Adverb;
            case "Conjunction":
                return PartOfSpeech.Conjunction;
            case "Preposition":
                return PartOfSpeech.Preposition;
            case "Interjection":
                return PartOfSpeech.Interjection;
        }
        return PartOfSpeech.Undefined;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDictionaryAndTable(Dictionary dictionary, Runnable callbackUpdate) {
        this.dictionary = dictionary;
        this.callbackUpdate = callbackUpdate;
    }

}
