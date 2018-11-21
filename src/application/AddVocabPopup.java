package application;

import dictionary.Dictionary;
import dictionary.PartOfSpeech;
import dictionary.Vocabulary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddVocabPopup {
    @FXML private TextField wordTextField;
    @FXML private TextField meanTextField;
    @FXML private MenuButton speechMenuButton;
    @FXML private TextField exampleTextField;
    @FXML private Button addButton;
    private Dictionary dictionary;
    private Vocabulary editVocab;
    private Runnable callbackUpdate;
    private String state;
    @FXML private Stage stage;

    @FXML
    public void initialize() {
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
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());;
        }
        callbackUpdate.run();
        stage.close();
    }

    private void addVocab() {
        try {
            Vocabulary vocabulary = getVocabFromInput();
            dictionary.addVocab(vocabulary);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        callbackUpdate.run();
        stage.close();
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

    public void setState(String state) {
        this.state = state;
    }

    public void setEditVocab(Vocabulary editVocab) {
        this.editVocab = editVocab;
    }
}
