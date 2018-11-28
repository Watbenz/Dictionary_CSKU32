package application;

import application.myFormatter.JSONFormatter;
import application.myFormatter.MyFormat;
import application.myFormatter.MyFormatter;
import application.myFormatter.XMLFormatter;
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

import java.io.*;
import java.net.URISyntaxException;

public class MainPageController {
    @FXML private Stage stage;
    @FXML private Label errorLabel;
    @FXML private TableView<Vocabulary> dictionaryTableView;
    private Dictionary dictionary;
    private File workDir;
    private File dicFile;

    public MainPageController() {
        findPath();
        try {
            dictionaryInit();
        } catch (IllegalAccessException e) {
            errorLabel.setText(e.getMessage());
        }
        updateFile();
    }

    @FXML
    public void initialize()  {
        tableViewInit();
        updateDictionary();
    }

    private void findPath() {
        File jarFile = null;
        try {
            jarFile = new File(MainPageController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String fileSep = System.getProperty("file.separator");

        assert jarFile != null;
        String pathJar = jarFile.getParent() + fileSep;

        workDir = new File(pathJar + "dictionaryData");
        workDir.mkdir();

        dicFile = new File(workDir.getPath() + fileSep + "dictionary.json");
    }

    private void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dicFile));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFile() {
        try {
            MyFormatter formatter = new MyFormat();
            PrintWriter writer = new PrintWriter(new FileWriter(dicFile));

            writer.println(formatter.format(dictionary));
            writer.close();
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void dictionaryInit() throws IllegalAccessException {
        dictionary = new Dictionary();

        dictionary.addVocab(new Vocabulary(
                "void",
                "a large hole or empty space",
                PartOfSpeech.NOUN,
                "She stood at the edge of the chasm and stared into the void. " +
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
                "This type of account offers you instant access to your money. " +
                        "Contrary to expectations, the film was an instant success."
        ));

        dictionary.addVocab(new Vocabulary(
                "constant",
                "staying the same, or not getting less or more",
                PartOfSpeech.ADJECTIVE,
                "The fridge keeps food at a constant temperature."
        ));
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
}
