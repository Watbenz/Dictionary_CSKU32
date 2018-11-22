package application;

import dictionary.Dictionary;
import dictionary.PartOfSpeech;
import dictionary.Vocabulary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {
    @FXML private Stage stage;
    @FXML private Label errorLabel;
    @FXML private TableView<Vocabulary> dictionaryTableView;
    private Dictionary dictionary;

    @FXML
    public void initialize()  {
        try {
            dictionaryInit();
            updateDictionary();
        } catch (IllegalAccessException e) {
            errorLabel.setText(e.getMessage());        }
    }

    private void dictionaryInit() throws IllegalAccessException {
        dictionary = new Dictionary();

        dictionary.addVocab(new Vocabulary(
                "void",
                "a large hole or empty space",
                PartOfSpeech.NOUN,
                "She stood at the edge of the chasm and stared into the void.\n" +
                "Before Einstein, space was regarded as a formless void."
        ));

        dictionary.addVocab(new Vocabulary(
                "avoid",
                "to prevent something from happening or to not allow yourself to do something",
                PartOfSpeech.VERB,
                "The report studiously avoided any mention of the controversial plan."
        ));

        dictionary.addVocab(new Vocabulary(
                "null",
                "having a value of zero",
                PartOfSpeech.ADJECTIVE,
                "Browser performance was improved by analysing failed searches which return null set results."
        ));

        dictionary.addVocab(new Vocabulary(
                "instant",
                "happening immediately, without any delay",
                PartOfSpeech.ADJECTIVE,
                "This type of account offers you instant access to your money.\n" +
                        "Contrary to expectations, the film was an instant success."
        ));

        dictionary.addVocab(new Vocabulary(
                "constant",
                "staying the same, or not getting less or more",
                PartOfSpeech.ADJECTIVE,
                "The fridge keeps food at a constant temperature."
        ));

        TableColumn<Vocabulary, String> word = new TableColumn<>("word");
        TableColumn<Vocabulary, String> mean = new TableColumn<>("mean");
        TableColumn<Vocabulary, PartOfSpeech> speech = new TableColumn<>("partOfSpeech");
        TableColumn<Vocabulary, String> example = new TableColumn<>("example");

        word.setCellValueFactory(new PropertyValueFactory<>("word"));
        mean.setCellValueFactory(new PropertyValueFactory<>("mean"));
        speech.setCellValueFactory(new PropertyValueFactory<>("partOfSpeech"));
        example.setCellValueFactory(new PropertyValueFactory<>("example"));

        dictionaryTableView.getColumns().add(word);
        dictionaryTableView.getColumns().add(mean);
        dictionaryTableView.getColumns().add(speech);
        dictionaryTableView.getColumns().add(example);
    }

    private void updateDictionary() {
        ObservableList<Vocabulary> list = FXCollections.observableArrayList(dictionary.getVocabularies());
        dictionaryTableView.setItems(list);
        errorLabel.setText("");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void deleteVocab() {
        try {
            Vocabulary selectedVocab = dictionaryTableView.getSelectionModel().getSelectedItem();
            if (selectedVocab == null) {
                throw new NoSuchFieldException("No selected vocabulary");
            }
            dictionary.deleteVocab(selectedVocab.getWord());
            updateDictionary();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void editVocab() {
        try {
            Vocabulary selectedVocab = dictionaryTableView.getSelectionModel().getSelectedItem();
            if (selectedVocab == null) {
                throw new NoSuchFieldException("No selected vocabulary");
            }
            openPopup(selectedVocab);
        } catch (NoSuchFieldException e) {
            errorLabel.setText(e.getMessage());        }
    }

    @FXML
    public void addVocab() {
        openPopup();
    }

    @FXML
    private void writeXML() {
        openAlertBox("XML");
    }

    @FXML
    private void writeJson() {
        openAlertBox("Json");
    }

    @FXML
    private void openFindVocab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("findWordPopup.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 400));
            stage.setTitle("Find vocabulary");

            FindWordPopup controller = loader.getController();
            controller.setStage(stage);
            controller.setDictionary(dictionary);
            stage.show();
        } catch (IOException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void openAlertBox(String state) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("alertWritFile.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 400));
            stage.setTitle(state + " file");

            AlertWriteFile controller = loader.getController();
            controller.setStage(stage);
            controller.setDictionary(dictionary);

            if (state.equals("XML")) {
                controller.setFormatter(obj -> xmlFormat((Dictionary) obj));
            }
            if (state.equals("Json")) {
                controller.setFormatter(obj -> jsonFormat((Dictionary) obj));
            }

            stage.show();
        } catch (IOException e) {
            errorLabel.setText(e.getMessage());        }
    }

    private void openPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addVocabPopup.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 400));
            stage.setTitle("Add vocabulary");

            AddVocabPopup controller = loader.getController();
            controller.setStage(stage);
            controller.setDictionaryAndTable(dictionary, this::updateDictionary);
            controller.setState("Add");
            stage.show();
        } catch (IOException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void openPopup(Vocabulary editVocab) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addVocabPopup.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 400));
            stage.setTitle("Edit vocabulary");

            AddVocabPopup controller = loader.getController();
            controller.setStage(stage);
            controller.setDictionaryAndTable(dictionary, this::updateDictionary);
            controller.setState("Edit");
            controller.setEditVocab(editVocab);
            stage.show();
        } catch (IOException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private String xmlFormat(Dictionary dictionary) {
        StringBuilder output = new StringBuilder();

        output.append("<Dictionary>\n");                                                            // <Dictionary>

        for (Vocabulary vocab: dictionary.getVocabularies()) {
            output.append("\t<Vocab word=\"").append(vocab.getWord()).append("\">\n");              // <Vocab word="word">
            output.append("\t\t<PartOfSpeech>").append(vocab.getPartOfSpeech().getName()).append("</PartOfSpeech>\n");  // <PartOfSpeech>NOUN</PartOfSpeech>
            output.append("\t\t<Meaning>").append(vocab.getMean()).append("</Meaning>\n");          // <Meaning>To say, or do again; repeat</Meaning>
            output.append("\t\t<Example>").append(vocab.getExample()).append("</Example>\n");       // <Example>She kept reiterating her request.</Example>
            output.append("\t</Vocab>\n");                                                          // </Vocab>
        }

        output.append("</Dictionary>\n");                                                           // </Dictionary>
        return output.toString();
    }

    private String jsonFormat(Dictionary dictionary) {
        StringBuilder output = new StringBuilder();
        output.append("]\n");

        for (Vocabulary vocab: dictionary.getVocabularies()) {
            output.append("\t{\n");
            output.append("\t\tvocab: \"").append(vocab.getWord()).append("\",\n");             // vocab: "iterator",
            output.append("\t\tpartOfSpeech: \"").append(vocab.getPartOfSpeech().getName()).append("\",\n");       // partOfSpeech: "VERB",
            output.append("\t\tmeaning: \"").append(vocab.getMean()).append("\",\n");           // meaning: "To say, or do again; repeat",
            output.append("\t\texample: \"").append(vocab.getExample()).append("\",\n");        // example: "She kept reiterating her request."

            output.append("\t},\n");
        }

        if (!dictionary.getVocabularies().isEmpty())                                    // If dictionary is not empty
            output.delete(output.length()-2, output.length()-1);                        // Delete last ,

        output.append("[\n");
        return output.toString();
    }
}
