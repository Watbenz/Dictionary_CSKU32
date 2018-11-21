package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);

        MainPageController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.show();
    }
}
