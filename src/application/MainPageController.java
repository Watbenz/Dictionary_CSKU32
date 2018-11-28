package application;

import application.myFormatter.JSONFormatter;
import application.myFormatter.XMLFormatter;
import dictionary.Dictionary;
import dictionary.DictionaryIO;
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

import java.io.*;

public class MainPageController {
    @FXML private Stage stage;
    @FXML private Label errorLabel;
    @FXML private TableView<Vocabulary> dictionaryTableView;
    private Dictionary dictionary;
    private DictionaryIO dictionaryIO;

    public MainPageController() {
        this.dictionary = new Dictionary();
        this.dictionaryIO = new DictionaryIO(dictionary);
    }

    @FXML
    public void initialize()  {
        tableViewInit();
        updateTableView();
    }

    private void tableViewInit() {
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

    private void updateTableView() {
        ObservableList<Vocabulary> list = FXCollections.observableArrayList(dictionary.getVocabularies());
        dictionaryTableView.setItems(list);
        dictionaryIO.updateFile();
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
            updateTableView();
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
                controller.setFormatter(new XMLFormatter());
            }
            if (state.equals("Json")) {
                controller.setFormatter(new JSONFormatter());
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
            controller.setDictionaryAndTable(dictionary, this::updateTableView);
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
            controller.setDictionaryAndTable(dictionary, this::updateTableView);
            controller.setState("Edit");
            controller.setEditVocab(editVocab);
            stage.show();
        } catch (IOException e) {
            errorLabel.setText(e.getMessage());
        }
    }
}
