package application;

import dictionary.Dictionary;
import dictionary.PartOfSpeech;
import dictionary.Vocabulary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {
    @FXML private Stage stage;
    @FXML private TableView<Vocabulary> dictionaryTableView;
    private Dictionary dictionary;

    @FXML
    public void initialize()  {
        try {
            dictionaryInit();
            updateDictionary();
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private void dictionaryInit() throws IllegalAccessException {
        dictionary = new Dictionary();
        dictionary.addVocab(new Vocabulary("1", "2", PartOfSpeech.Noun, "4"));
        dictionary.addVocab(new Vocabulary("2", "4", PartOfSpeech.Adjective, "1"));
        dictionary.addVocab(new Vocabulary("3", "6", PartOfSpeech.Noun, "2"));
        dictionary.addVocab(new Vocabulary("4", "8", PartOfSpeech.Conjunction, "43"));

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
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        updateDictionary();
    }

    @FXML
    public void editVocab() {
        try {
            Vocabulary selectedVocab = dictionaryTableView.getSelectionModel().getSelectedItem();
            if (selectedVocab == null) {
                throw new NoSuchFieldException("No selected vocabulary");
            }
            openPopup("Edit", selectedVocab);
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void addVocab() {
        openPopup("Add");
    }

    private void openPopup(String state) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addVocabPopup.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 400));

            AddVocabPopup controller = loader.getController();
            controller.setStage(stage);
            controller.setDictionaryAndTable(dictionary, this::updateDictionary);
            controller.setState(state);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

    private void openPopup(String state, Vocabulary editVocab) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addVocabPopup.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 400));

            AddVocabPopup controller = loader.getController();
            controller.setStage(stage);
            controller.setDictionaryAndTable(dictionary, this::updateDictionary);
            controller.setState(state);
            controller.setEditVocab(editVocab);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

    @FXML
    private void openFindVocab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("findWordPopup.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 400));

            FindWordPopup controller = loader.getController();
            controller.setStage(stage);
            controller.setDictionary(dictionary);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }
}
