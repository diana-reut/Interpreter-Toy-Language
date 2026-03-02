package gui;

import controller.Controller;
import controller.IController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.programState.*;
import model.statement.IStatement;
import model.value.Value;
import repository.IRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SelectedProgramController {
    private IController controller;

    @FXML private TableView<Map.Entry<Integer, Value>> heapTableView;
    @FXML private ListView<Value> outputListView;
    @FXML private ListView<String> fileTableListView;
    @FXML private ListView<Integer> prgStateIdentifiers;
    @FXML private ListView<String> exeStackListView;
    @FXML private TableView<Map.Entry<String, Value>> symTableView;

    @FXML private TableColumn<Map.Entry<Integer, Value>, Integer> heapAddressColumn;
    @FXML private TableColumn<Map.Entry<Integer, Value>, Value> heapValueColumn;

    @FXML private TableColumn<Map.Entry<String, Value>, String> symTableVariableNameColumn;
    @FXML private TableColumn<Map.Entry<String, Value>, Value> symTableValueColumn;

    @FXML private TableView<Map.Entry<Integer, Integer>> latchTable;
    @FXML private TableColumn<Map.Entry<Integer, Integer>, Integer> latchLocation;
    @FXML private TableColumn<Map.Entry<Integer, Integer>, Integer> latchValue;

    @FXML private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semaphoreTable;
    @FXML private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> semaphoreIndex;
    @FXML private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> semaphoreValue;
    @FXML private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, List<Integer>> semaphoreList;

    @FXML private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> barrierTable;
    @FXML private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> barrierIndex;
    @FXML private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> barrierValue;
    @FXML private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, List<Integer>> barrierList;

    @FXML private TableView<Map.Entry<Integer, Integer>> lockTable;
    @FXML private TableColumn<Map.Entry<Integer, Integer>, Integer> lockAddress;
    @FXML private TableColumn<Map.Entry<Integer, Integer>, Integer> lockValue;

    @FXML private TextField numberOfProgramStates;
    private Integer selectedId = null; // stores the ID of the thread the user clicked on

    @FXML private Button runButton;

    @FXML
    public void initialize() {
        heapAddressColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        heapValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        symTableVariableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        symTableValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        latchLocation.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        latchValue.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        semaphoreIndex.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        semaphoreValue.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().getKey()));
        semaphoreList.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().getValue()));

        barrierIndex.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        barrierValue.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().getKey()));
        barrierList.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().getValue()));

        lockAddress.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        lockValue.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        prgStateIdentifiers.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals(selectedId)) {
                selectedId = newVal;
                update();
            }
        });
    }

    public void setProgram(IStatement program) {
        ProgramState programState = ProgramState.createNewInstance(
                new SymTable(),
                new Output(),
                new ExeStack(),
                new FileTable(),
                new Heap(),
                new LatchTable(),
                new SemaphoreTable(),
                new BarrierTable(),
                new LockTable()
        );
        programState.exeStack().push(program);
        IRepository repo = new Repository();
        repo.addProgramState(programState);
        this.controller = new Controller(repo);

        update();
    }

    private void update() {
        List<ProgramState> programStates = controller.getProgramStates();
        numberOfProgramStates.setText(Integer.toString(programStates.size()));
        List<Integer> ids = programStates.stream().map(ProgramState::id).toList();
        prgStateIdentifiers.setItems(FXCollections.observableArrayList(ids));

        if (programStates.isEmpty()) {
            runButton.setDisable(true);
            return;
        }

        ProgramState currentState;
        if (selectedId != null && ids.contains(selectedId)) {
            currentState = programStates.stream().filter(p -> p.id() == selectedId).findFirst().get();
        } else {
            currentState = programStates.get(0);
            selectedId = currentState.id();
            prgStateIdentifiers.getSelectionModel().select(selectedId);
        }

        outputListView.setItems(FXCollections.observableArrayList(currentState.output().getList()));
        fileTableListView.setItems(FXCollections.observableArrayList(new ArrayList<>(currentState.fileTable().getDictionary().keySet())));

        List<IStatement> stackList = new ArrayList<>(currentState.exeStack().getStack());
        Collections.reverse(stackList);
        exeStackListView.setItems(FXCollections.observableArrayList(stackList.stream().map(Object::toString).toList()));

        symTableView.setItems(FXCollections.observableArrayList(new ArrayList<>(currentState.symTable().getDictionary().entrySet())));
        heapTableView.setItems(FXCollections.observableArrayList(new ArrayList<>(currentState.heap().getDictionary().entrySet())));
        latchTable.setItems(FXCollections.observableArrayList(new ArrayList<>(currentState.latchTable().getDictionary().entrySet())));
        semaphoreTable.setItems(FXCollections.observableArrayList(new ArrayList<>(currentState.semaphoreTable().getDictionary().entrySet())));
        barrierTable.setItems(FXCollections.observableArrayList(new ArrayList<>(currentState.barrierTable().getDictionary().entrySet())));
        lockTable.setItems(FXCollections.observableArrayList(new ArrayList<>(currentState.lockTable().getDictionary().entrySet())));
        lockTable.refresh();
        barrierTable.refresh();
        symTableView.refresh();
        semaphoreTable.refresh();
        heapTableView.refresh();
        latchTable.refresh();
    }

    @FXML
    public void handleRunOneStep() {
        if (controller == null) {return;}
        try {
            List<ProgramState> programStates = controller.getProgramStates();
            if((programStates.size() == 1) && (programStates.getLast().exeStack().isEmpty())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Program has finished its execution.");
                alert.showAndWait();
                return;
            }
            if (!programStates.isEmpty()) {
                controller.oneStepForAllPrg(programStates);
                update();
            } else {
                runButton.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Look at your IDE console to see the real error!
            Alert alert = new Alert(Alert.AlertType.ERROR, "Execution Error: " + (e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage()));
            alert.showAndWait();
        }
    }
}
