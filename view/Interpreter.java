package view;

import controller.Controller;
import controller.IController;
import model.expression.BinaryOperatorExpression;
import model.expression.RHExpression;
import model.expression.ValueExpression;
import model.expression.VariableNameExpression;
import model.state.*;
import model.statement.*;
import model.type.*;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.util.Scanner;

class Interpreter {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("What is the name of the log file? ");
        String fileName = scan.nextLine();

        Statement ex1 = new CompoundStatement(
                new VariableDeclaration("v", new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement(
                                "v",
                                new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableNameExpression("v"))
                )
        );
        IRepository repository1 = new Repository(fileName);
        IController controller1 = new Controller(repository1);
        ExecutionStack executionStack1 = new StackExecutionStack();
        Output output1 = new ListOutput();
        SymbolTable symbolTable1 = new MapSymbolTable();
        FileTable fileTable1 = new MapFileTable();
        Heap heap1 = new MapHeap();
        ProgramState programState1 = ProgramState.createNewInstance(executionStack1, symbolTable1, output1, fileTable1, heap1);
        executionStack1.push(ex1);
        controller1.addProgramState(programState1);

        Statement ex2 = new CompoundStatement(
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
        IRepository repository2 = new Repository(fileName);
        IController controller2 = new Controller(repository2);
        ExecutionStack executionStack2 = new StackExecutionStack();
        Output output2 = new ListOutput();
        SymbolTable symbolTable2 = new MapSymbolTable();
        FileTable fileTable2 = new MapFileTable();
        Heap heap2 = new MapHeap();
        ProgramState programState2 = ProgramState.createNewInstance(executionStack2, symbolTable2, output2, fileTable2, heap2);
        executionStack2.push(ex2);
        controller2.addProgramState(programState2);

        Statement ex3 = new CompoundStatement(
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
        IRepository repository3 = new Repository(fileName);
        IController controller3 = new Controller(repository3);
        ExecutionStack executionStack3 = new StackExecutionStack();
        Output output3 = new ListOutput();
        SymbolTable symbolTable3 = new MapSymbolTable();
        FileTable fileTable3 = new MapFileTable();
        Heap heap3 = new MapHeap();
        ProgramState programState3 = ProgramState.createNewInstance(executionStack3, symbolTable3, output3, fileTable3, heap3);
        executionStack3.push(ex3);
        controller3.addProgramState(programState3);

        Statement ex4 = new CompoundStatement(
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
        IRepository repository4 = new Repository(fileName);
        IController controller4 = new Controller(repository4);
        ExecutionStack executionStack4 = new StackExecutionStack();
        Output output4 = new ListOutput();
        SymbolTable symbolTable4 = new MapSymbolTable();
        FileTable fileTable4 = new MapFileTable();
        Heap heap4 = new MapHeap();
        ProgramState programState4 = ProgramState.createNewInstance(executionStack4, symbolTable4, output4, fileTable4, heap4);
        executionStack4.push(ex4);
        controller4.addProgramState(programState4);


        Statement ex5 = new CompoundStatement(
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
        IRepository repository5 = new Repository(fileName);
        IController controller5 = new Controller(repository5);
        ExecutionStack executionStack5 = new StackExecutionStack();
        Output output5 = new ListOutput();
        SymbolTable symbolTable5 = new MapSymbolTable();
        FileTable fileTable5 = new MapFileTable();
        Heap heap5 = new MapHeap();
        ProgramState programState5 = ProgramState.createNewInstance(executionStack5, symbolTable5, output5, fileTable5, heap5);
        executionStack5.push(ex5);
        controller5.addProgramState(programState5);

        Statement ex6 = new CompoundStatement(
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

        IRepository repository6 = new Repository(fileName);
        Controller controller6 = new Controller(repository6);
        ExecutionStack executionStack6 = new StackExecutionStack();
        Output output6 = new ListOutput();
        SymbolTable symbolTable6 = new MapSymbolTable();
        FileTable fileTable6 = new MapFileTable();
        Heap heap6 = new MapHeap();
        ProgramState programState6 = ProgramState.createNewInstance(executionStack6, symbolTable6, output6, fileTable6, heap6);
        executionStack6.push(ex6);
        controller6.addProgramState(programState6);

        Statement ex7 = new CompoundStatement(
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

        IRepository repository7 = new Repository(fileName);
        Controller controller7 = new Controller(repository7);
        ExecutionStack executionStack7 = new StackExecutionStack();
        Output output7 = new ListOutput();
        SymbolTable symbolTable7 = new MapSymbolTable();
        FileTable fileTable7 = new MapFileTable();
        Heap heap7 = new MapHeap();
        ProgramState programState7 = ProgramState.createNewInstance(executionStack7, symbolTable7, output7, fileTable7, heap7);
        executionStack7.push(ex7);
        controller7.addProgramState(programState7);

        Statement ex8 = new CompoundStatement(
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

        IRepository repository8 = new Repository(fileName);
        Controller controller8 = new Controller(repository8);
        ExecutionStack executionStack8 = new StackExecutionStack();
        Output output8 = new ListOutput();
        SymbolTable symbolTable8 = new MapSymbolTable();
        FileTable fileTable8 = new MapFileTable();
        Heap heap8 = new MapHeap();
        ProgramState programState8 = ProgramState.createNewInstance(executionStack8, symbolTable8, output8, fileTable8, heap8);
        executionStack8.push(ex8);
        controller8.addProgramState(programState8);

        Statement ex9 = new CompoundStatement(
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

        IRepository repository9 = new Repository(fileName);
        Controller controller9 = new Controller(repository9);
        ExecutionStack executionStack9 = new StackExecutionStack();
        Output output9 = new ListOutput();
        SymbolTable symbolTable9 = new MapSymbolTable();
        FileTable fileTable9 = new MapFileTable();
        Heap heap9 = new MapHeap();
        ProgramState programState9 = ProgramState.createNewInstance(executionStack9, symbolTable9, output9, fileTable9, heap9);
        executionStack9.push(ex9);
        controller9.addProgramState(programState9);

        Statement ex10 = new CompoundStatement(
                new VariableDeclaration("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntegerValue(10))),
                        new CompoundStatement(
                                new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                                new PrintStatement(new VariableNameExpression("v"))
                        )
                )
        );
        IRepository repository10 = new Repository(fileName);
        Controller controller10 = new Controller(repository10);
        ExecutionStack executionStack10 = new StackExecutionStack();
        Output output10 = new ListOutput();
        SymbolTable symbolTable10 = new MapSymbolTable();
        FileTable fileTable10 = new MapFileTable();
        Heap heap10 = new MapHeap();
        ProgramState programState10 = ProgramState.createNewInstance(executionStack10, symbolTable10, output10, fileTable10, heap10);
        executionStack10.push(ex10);
        controller10.addProgramState(programState10);

        Statement ex11 = new CompoundStatement(
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

        IRepository repository11 = new Repository(fileName);
        Controller controller11 = new Controller(repository11);
        ExecutionStack executionStack11 = new StackExecutionStack();
        Output output11 = new ListOutput();
        SymbolTable symbolTable11 = new MapSymbolTable();
        FileTable fileTable11 = new MapFileTable();
        Heap heap11 = new MapHeap();
        ProgramState programState11 = ProgramState.createNewInstance(executionStack11, symbolTable11, output11, fileTable11, heap11);
        executionStack11.push(ex11);
        controller11.addProgramState(programState11);

        Statement ex12 = new CompoundStatement(
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
        IRepository repository12 = new Repository(fileName);
        IController controller12 = new Controller(repository12);
        ExecutionStack executionStack12 = new StackExecutionStack();
        Output output12 = new ListOutput();
        SymbolTable symbolTable12 = new MapSymbolTable();
        FileTable fileTable12 = new MapFileTable();
        Heap heap12 = new MapHeap();
        ProgramState programState12 = ProgramState.createNewInstance(executionStack12, symbolTable12, output12, fileTable12, heap12);
        executionStack12.push(ex12);
        controller12.addProgramState(programState12);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), controller1));
        menu.addCommand(new RunExample("2", ex2.toString(), controller2));
        menu.addCommand(new RunExample("3", ex3.toString(), controller3));
        menu.addCommand(new RunExample("4", ex4.toString(), controller4));
        menu.addCommand(new RunExample("5", ex5.toString(), controller5));
        menu.addCommand(new RunExample("6", ex6.toString(), controller6));
        menu.addCommand(new RunExample("7", ex7.toString(), controller7));
        menu.addCommand(new RunExample("8", ex8.toString(), controller8));
        menu.addCommand(new RunExample("9", ex9.toString(), controller9));
        menu.addCommand(new RunExample("10", ex10.toString(), controller10));
        menu.addCommand(new RunExample("11", ex11.toString(), controller11));
        menu.addCommand(new RunExample("12", ex12.toString(), controller12));
        menu.show();
    }
}