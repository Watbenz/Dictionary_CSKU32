package application;

import dictionary.Dictionary;
import dictionary.PartOfSpeech;
import dictionary.Vocabulary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddVocabPopup {
    @FXML private TextField wordTextField;
    @FXML private TextField meanTextField;
    @FXML private MenuButton speechMenuButton;
    @FXML private TextField exampleTextField;
    @FXML private Button addButton;
    @FXML private Label errorLabel;
    private Dictionary dictionary;
    private Vocabulary editVocab;
    private Runnable callbackUpdate;
    private String state;
    @FXML private Stage stage;

    @FXML
    public void initialize() {
        errorLabel.setText("");
        for (MenuItem item: speechMenuButton.getItems()) {
            item.setOnAction(event -> speechMenuButton.setText(item.getText()));
        }

        Platform.runLater(() -> {
            if (state.equals("Edit")) { setTextForEditVocab(); }
        });
    }

    private Vocabulary getVocabFromInput() {
        String word = wordTextField.getText();
        String mean = meanTextField.getText();
        PartOfSpeech speech = findPartOfSpeech(speechMenuButton.getText());
        String example = exampleTextField.getText().equals("") ? "No Example" : exampleTextField.getText();
        return new Vocabulary(word, mean, speech, example);
    }

    private void editVocab() {
        try {
            Vocabulary vocabulary = getVocabFromInput();
            dictionary.editVocab(editVocab.getWord(), vocabulary);
            callbackUpdate.run();
            stage.close();
        } catch (NoSuchFieldException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void addVocab() {
        try {
            Vocabulary vocabulary = getVocabFromInput();
            dictionary.addVocab(vocabulary);
            callbackUpdate.run();
            stage.close();
        } catch (IllegalAccessException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void setTextForEditVocab() {
        wordTextField.setText(editVocab.getWord());
        meanTextField.setText(editVocab.getMean());
        speechMenuButton.setText(editVocab.getPartOfSpeech().getName());
        exampleTextField.setText(editVocab.getExample());
        addButton.setText("แก้ไข");
    }

    @FXML
    private void clickAddButton() {
        switch (state) {
            case "Add":
                addVocab();
                break;
            case "Edit":
                editVocab();
                break;
        }
    }

    private PartOfSpeech findPartOfSpeech(String string) {
        switch (string) {
            case "NOUN":
                return PartOfSpeech.NOUN;
            case "PRONOUN":
                return PartOfSpeech.PRONOUN;
            case "VERB":
                return PartOfSpeech.VERB;
            case "ADVERB":
                return PartOfSpeech.ADVERB;
            case "CONJUNCTION":
                return PartOfSpeech.CONJUNCTION;
            case "PREPOSITION":
                return PartOfSpeech.PREPOSITION;
            case "INTERJECTION":
                return PartOfSpeech.INTERJECTION;
        }
        return PartOfSpeech.UNDEFINED;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDictionaryAndTable(Dictionary dictionary, Runnable callbackUpdate) {
        this.dictionary = dictionary;
        this.callbackUpdate = callbackUpdate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setEditVocab(Vocabulary editVocab) {
        this.editVocab = editVocab;
    }
}
