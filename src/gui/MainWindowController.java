package gui;

import controller.IController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.exceptions.MyExceptionModel;
import model.programState.*;
import model.statement.IStatement;
import model.value.Value;
import view.Interpreter;
import view.TypeChecker;

import java.util.Map;

public class MainWindowController {
    @FXML private ListView<IStatement> ProgramsListView;

    @FXML
    public void initialize() {
        ObservableList<IStatement> examples = FXCollections.observableArrayList(
                Interpreter.getStatement1(), Interpreter.getStatement2(),
                Interpreter.getStatement3(), Interpreter.getStatement4(),
                Interpreter.getStatement5(), Interpreter.getStatement6(),
                Interpreter.getStatement7(), Interpreter.getStatement8(),
                Interpreter.getStatement9(), Interpreter.getStatement10(),
                Interpreter.getStatement11(), Interpreter.getStatement12(),
                Interpreter.getStatement13(), Interpreter.getStatement14(),
                Interpreter.getStatement15(), Interpreter.getStatement16(),
                Interpreter.getStatement17(), Interpreter.getStatement18(),
                Interpreter.getStatement19()
        );
        ProgramsListView.setItems(examples);

        ProgramsListView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        System.out.println("User selected program with id: " + newVal);
                        try {
                            newVal.typecheck(new TypeChecker());
                        } catch (MyExceptionModel e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Program did not pass the type checker: " + e.getMessage());
                            alert.showAndWait();
                            return;
                        }
                        try {
                            openSelectedProgramWindow(newVal);
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Selection Error: " + e.getMessage());
                            alert.showAndWait();
                        }
                    }
                });
    }

    private void openSelectedProgramWindow(IStatement program) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/gui/SelectedProgram.fxml")
        );

        Parent root = loader.load();

        SelectedProgramController controller =
                loader.getController();
        controller.setProgram(program);

        Stage stage = new Stage();
        stage.setTitle("Selected Program");
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

}