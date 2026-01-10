package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.expression.BinaryOperatorExpression;
import model.expression.RHExpression;
import model.expression.ValueExpression;
import model.expression.VariableNameExpression;
import model.statement.*;
import model.type.BooleanType;
import model.type.IntegerType;
import model.type.RefType;
import model.type.StringType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Toy Language Interpreter");

        // 1. Create a list of your programs (from your ex1, ex2, etc.)
        ListView<Statement> programList = new ListView<>();
        ObservableList<Statement> examples = FXCollections.observableArrayList(
                getExample1(), getExample2(), getExample3(), getExample4(),
                getExample5(), getExample6(), getExample7(), getExample8(),
                getExample9(), getExample10(), getExample11(), getExample12()
        );
        programList.setItems(examples);

        // 2. Handle selection
        programList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Logic to open a new window and run the controller
                System.out.println("Selected: " + newValue);
            }
        });

        VBox layout = new VBox(programList);
        Scene scene = new Scene(layout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Inside your MainWindow.java -> start() method

        programList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    // 1. Type Check the program first
                    newValue.typeCheck(new model.typecheck.TypeEnvironment());

                    // 2. Setup the Program State and Controller
                    model.state.ProgramState prg = model.state.ProgramState.createNewInstance(
                            new model.state.StackExecutionStack(),
                            new model.state.MapSymbolTable(),
                            new model.state.ListOutput(),
                            new model.state.MapFileTable(),
                            new model.state.MapHeap()
                    );
                    prg.executionStack().push(newValue);

                    repository.IRepository repo = new repository.Repository("log.txt");
                    repo.addProgramState(prg);
                    controller.IController ctrl = new controller.Controller(repo);

                    // 3. Open the Run Window
                    RunWindow runWindow = new RunWindow();
                    runWindow.display(ctrl);

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Selection Error: " + e.getMessage());
                    alert.showAndWait();
                }
            }
        });
    }

    private Statement getExample1() {
        return new CompoundStatement(
                new VariableDeclaration("v", new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement(
                                "v",
                                new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableNameExpression("v"))
                )
        );
    }

    private Statement getExample2() {
        return new CompoundStatement(
                new VariableDeclaration("a", new IntegerType()),
                new CompoundStatement(
                        new VariableDeclaration("b", new IntegerType()),
                        new CompoundStatement(
                                new AssignmentStatement(
                                        "a",
                                        new BinaryOperatorExpression(
                                                new ValueExpression(new IntegerValue(2)),
                                                new BinaryOperatorExpression(
                                                        new ValueExpression(new IntegerValue(3)),
                                                        new ValueExpression(new IntegerValue(5)),
                                                        "*"
                                                ),
                                                "+"
                                        )
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement(
                                                "b",
                                                new BinaryOperatorExpression(
                                                        new VariableNameExpression("a"),
                                                        new ValueExpression(new IntegerValue(1)),
                                                        "+"
                                                )
                                        ),
                                        new PrintStatement(
                                                new VariableNameExpression("b")
                                        )
                                )
                        )
                )

        );
    }

    private Statement getExample3(){
        return new CompoundStatement(
                new VariableDeclaration("a", new BooleanType()),
                new CompoundStatement(
                        new VariableDeclaration("v", new IntegerType()),
                        new CompoundStatement(
                                new AssignmentStatement(
                                        "a",
                                        new ValueExpression(new BooleanValue(true))
                                ),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableNameExpression("a"),
                                                new AssignmentStatement(
                                                        "v",
                                                        new ValueExpression(new IntegerValue(2))
                                                ),
                                                new AssignmentStatement(
                                                        "v",
                                                        new ValueExpression(new IntegerValue(3))
                                                )
                                        ),
                                        new PrintStatement(
                                                new VariableNameExpression("v")
                                        )
                                )
                        )
                )
        );
    }

    private Statement getExample4(){
        return new CompoundStatement(
                new VariableDeclaration("varf", new StringType()),
                new CompoundStatement(
                        new AssignmentStatement(
                                "varf",
                                new ValueExpression(new StringValue("test.in"))
                        ),
                        new CompoundStatement(
                                new OpenRFileStatement(new VariableNameExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclaration("varc", new IntegerType()),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableNameExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableNameExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableNameExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableNameExpression("varc")),
                                                                        new CloseRFileStatement(new VariableNameExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private Statement getExample5(){
       return new CompoundStatement(
                new VariableDeclaration("v", new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement(
                                "v",
                                new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new BinaryOperatorExpression(
                                new VariableNameExpression("v"),
                                new ValueExpression(new IntegerValue(5)),
                                ">"
                        ))
                )
        );
    }

    private Statement getExample6(){
        return new CompoundStatement(
                new VariableDeclaration("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclaration("a", new RefType(new RefType(new IntegerType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableNameExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableNameExpression("v")),
                                                new PrintStatement(new VariableNameExpression("a"))
                                        )
                                )
                        )
                )
        );
    }

    private Statement getExample7(){
        return new CompoundStatement(
                new VariableDeclaration("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclaration("a", new RefType(new RefType(new IntegerType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableNameExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new RHExpression(new VariableNameExpression("v"))),
                                                new PrintStatement(
                                                        new BinaryOperatorExpression(
                                                                new RHExpression(
                                                                        new RHExpression(new VariableNameExpression("a"))
                                                                ),
                                                                new ValueExpression(new IntegerValue(5)),
                                                                "+"
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private Statement getExample8(){
        return new CompoundStatement(
                new VariableDeclaration("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new RHExpression(new VariableNameExpression("v"))),
                                new CompoundStatement(
                                        new WHStatement("v", new ValueExpression(new IntegerValue(30))),
                                        new PrintStatement(
                                                new BinaryOperatorExpression(
                                                        new RHExpression(new VariableNameExpression("v")),
                                                        new ValueExpression(new IntegerValue(5)),
                                                        "+" // Assuming '+' is used for addition, replace with your actual symbol if different
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private Statement getExample9(){
        return new CompoundStatement(
                new VariableDeclaration("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclaration("a", new RefType(new RefType(new IntegerType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableNameExpression("v")),
                                        new CompoundStatement(
                                                // This second allocation for 'v' overwrites the address '1',
                                                // testing the recursive GC logic.
                                                new NewStatement("v", new ValueExpression(new IntegerValue(30))),
                                                new PrintStatement(
                                                        new RHExpression(
                                                                new RHExpression(new VariableNameExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private Statement getExample10(){
        return new CompoundStatement(
                new VariableDeclaration("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntegerValue(10))),
                        new CompoundStatement(
                                new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                                new PrintStatement(new VariableNameExpression("v"))
                        )
                )
        );
    }

    private Statement getExample11(){
        return new CompoundStatement(
                new VariableDeclaration("v", new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        // while (v > 0)
                                        new BinaryOperatorExpression(
                                                new VariableNameExpression("v"),
                                                new ValueExpression(new IntegerValue(0)),
                                                ">"
                                        ),
                                        // body: print(v); v = v - 1
                                        new CompoundStatement(
                                                new PrintStatement(new VariableNameExpression("v")),
                                                new AssignmentStatement(
                                                        "v",
                                                        new BinaryOperatorExpression(
                                                                new VariableNameExpression("v"),
                                                                new ValueExpression(new IntegerValue(1)),
                                                                "-"
                                                        )
                                                )
                                        )
                                ),
                                // final statement: print(v)
                                new PrintStatement(new VariableNameExpression("v"))
                        )
                )
        );
    }

    private Statement getExample12(){
        return new CompoundStatement(
                new VariableDeclaration("v", new IntegerType()),
                new CompoundStatement(new VariableDeclaration("a", new RefType(new IntegerType())),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntegerValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WHStatement("a", new ValueExpression(new IntegerValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableNameExpression("v")),
                                                                                new PrintStatement(
                                                                                        new RHExpression(new VariableNameExpression("a"))
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableNameExpression("v")),
                                                        new PrintStatement(
                                                                new RHExpression(new VariableNameExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
