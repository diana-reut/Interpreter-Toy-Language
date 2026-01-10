package gui;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.state.ProgramState;
import model.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RunWindow {
    private IController controller;

    private TextField nrPrgStatesField = new TextField();
    private TableView<Map.Entry<Integer, Value>> heapTable = new TableView<>();
    private ListView<Value> outList = new ListView<>();
    private ListView<String> fileTable = new ListView<>();
    private ListView<Integer> prgIdentifiersList = new ListView<>();
    private TableView<Map.Entry<String, Value>> symTable = new TableView<>();
    private ListView<String> exeStackList = new ListView<>();
    private Button runOneStepBtn = new Button("Run One Step");

    public void display(IController controller) {
        this.controller = controller;
        Stage window = new Stage();
        window.setTitle("Execution Progress");

        setupTables();

        // Layout Organization (Requirement 2)
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Number of PrgStates:"), 0, 0);
        grid.add(nrPrgStatesField, 1, 0);

        grid.add(new Label("Heap Table"), 0, 1);
        grid.add(heapTable, 0, 2);

        grid.add(new Label("Output"), 1, 1);
        grid.add(outList, 1, 2);

        grid.add(new Label("File Table"), 2, 1);
        grid.add(fileTable, 2, 2);

        grid.add(new Label("Program Identifiers"), 0, 3);
        grid.add(prgIdentifiersList, 0, 4);

        grid.add(new Label("Symbol Table"), 1, 3);
        grid.add(symTable, 1, 4);

        grid.add(new Label("Execution Stack"), 2, 3);
        grid.add(exeStackList, 2, 4);

        grid.add(runOneStepBtn, 1, 5);

        // Selection logic for PrgStates (Requirement 2e, f, g)
        prgIdentifiersList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updateSubViews(newVal);
        });

        runOneStepBtn.setOnAction(e -> handleRunOneStep());

        updateUI();

        Scene scene = new Scene(grid, 900, 700);
        window.setScene(scene);
        window.show();
    }

    private void setupTables() {
        // Heap Table Columns
        TableColumn<Map.Entry<Integer, Value>, Integer> addrCol = new TableColumn<>("Address");
        addrCol.setCellValueFactory(p -> new javafx.beans.property.SimpleObjectProperty<>(p.getValue().getKey()));
        TableColumn<Map.Entry<Integer, Value>, Value> valCol = new TableColumn<>("Value");
        valCol.setCellValueFactory(p -> new javafx.beans.property.SimpleObjectProperty<>(p.getValue().getValue()));
        heapTable.getColumns().addAll(addrCol, valCol);

        // Sym Table Columns
        TableColumn<Map.Entry<String, Value>, String> nameCol = new TableColumn<>("Variable");
        nameCol.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getKey()));
        TableColumn<Map.Entry<String, Value>, Value> symValCol = new TableColumn<>("Value");
        symValCol.setCellValueFactory(p -> new javafx.beans.property.SimpleObjectProperty<>(p.getValue().getValue()));
        symTable.getColumns().addAll(nameCol, symValCol);
    }

    private void handleRunOneStep() {
        try {
            List<ProgramState> prgList = controller.getRepository().getProgramStates();

            // 1. If the repository is already empty, the program is definitely done.
            if (prgList.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Execution finished.").showAndWait();
                return;
            }

            // 2. IMPORTANT: Check if the programs are finished BEFORE running the next step.
            // If all available programs have empty stacks, don't try to execute.
            boolean isFinished = prgList.stream().allMatch(p -> p.executionStack().getStatements().isEmpty());

            if (isFinished) {
                updateUI();
                new Alert(Alert.AlertType.INFORMATION, "Execution finished.").showAndWait();
                return;
            }

            // 3. If we reach here, there is at least one statement to execute.
            controller.oneStepForAllPrograms(prgList);

            // 4. Update the UI so the user sees the effect of the statement just run.
            updateUI();

        } catch (Exception e) {
            // This will catch the "Stack is empty" if it somehow slips through,
            // but the logic above should prevent it.
            new Alert(Alert.AlertType.ERROR, "Interpreter Error: " + e.getMessage()).showAndWait();
        }
    }

    private void updateUI() {
        List<ProgramState> prgList = controller.getRepository().getProgramStates();

        // Check if we still have programs to display
        if (prgList.isEmpty()) {
            nrPrgStatesField.setText("0");
            return;
        }

        // 2(a) Update number of PrgStates
        nrPrgStatesField.setText(String.valueOf(prgList.size()));

        // Shared data (taken from the first available PrgState)
        ProgramState firstPrg = prgList.get(0);

        // 2(b) Update Heap Table
        heapTable.setItems(FXCollections.observableArrayList(
                firstPrg.heap().getMap().entrySet()
        ));

        // 2(c) Update Output ListView - FIX: Ensure this is called every step
        outList.setItems(FXCollections.observableArrayList(
                firstPrg.output().getOutputList()
        ));

        // 2(d) Update FileTable
        fileTable.setItems(FXCollections.observableArrayList(
                firstPrg.fileTable().getFileTable().keySet().stream()
                        .map(Object::toString).toList()
        ));

        // 2(e) Update identifiers list
        List<Integer> ids = prgList.stream().map(ProgramState::id).toList();

        // Save current selection to restore it after updating the list
        Integer selectedId = prgIdentifiersList.getSelectionModel().getSelectedItem();
        prgIdentifiersList.setItems(FXCollections.observableArrayList(ids));

        if (selectedId != null && ids.contains(selectedId)) {
            prgIdentifiersList.getSelectionModel().select(selectedId);
        } else if (!ids.isEmpty()) {
            prgIdentifiersList.getSelectionModel().selectFirst();
        }

        // 2(f, g) Update SymTable and ExeStack for the selected ID
        updateSpecificStateViews();

        // Force refresh of all components to be safe
        heapTable.refresh();
        outList.refresh();
        fileTable.refresh();
    }

    private void updateSpecificStateViews() {
        Integer selectedId = prgIdentifiersList.getSelectionModel().getSelectedItem();
        if (selectedId == null) return;

        ProgramState selectedPrg = controller.getRepository().getProgramStates().stream()
                .filter(p -> p.id() == selectedId)
                .findFirst()
                .orElse(null);

        if (selectedPrg != null) {
            // Update SymTable
            symTable.setItems(FXCollections.observableArrayList(
                    selectedPrg.symbolTable().getSymbolTable().entrySet()
            ));

            // Update ExeStack (Requirement 2g)
            List<String> stackContent = selectedPrg.executionStack().getStatements().stream()
                    .map(Object::toString).toList();
            exeStackList.setItems(FXCollections.observableArrayList(stackContent));

            symTable.refresh();
            exeStackList.refresh();
        }
    }

    private void updateSubViews(Integer selectedId) {
        if (selectedId == null) return;
        ProgramState selectedPrg = controller.getRepository().getProgramStates().stream()
                .filter(p -> p.id() == selectedId).findFirst().orElse(null);

        if (selectedPrg != null) {
            symTable.setItems(FXCollections.observableArrayList(selectedPrg.symbolTable().getSymbolTable().entrySet()));
            List<String> stackStrings = selectedPrg.executionStack().getStatements().stream()
                    .map(Object::toString).collect(Collectors.toList());
            exeStackList.setItems(FXCollections.observableArrayList(stackStrings));
        }
    }
}