package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Interpreter;

public class GUImain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Interpreter.clearFile();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/gui/MainWindow.fxml")
        );

        Parent root = loader.load();

        stage.setTitle("Toy Language Interpreter");
        stage.setScene(new Scene(root, 950, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
