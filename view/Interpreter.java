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
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
import model.typecheck.TypeEnvironment;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.util.Scanner;

class Interpreter {

    private static void addExample(String fileName, Statement program, String key, TextMenu menu) {
        try {
            ITypeEnvironment typeEnv = new TypeEnvironment();
            program.typeCheck(typeEnv);

            IRepository repository = new Repository(fileName);
            IController controller = new Controller(repository);
            ExecutionStack executionStack = new StackExecutionStack();
            Output output = new ListOutput();
            SymbolTable symbolTable = new MapSymbolTable();
            FileTable fileTable = new MapFileTable();
            Heap heap = new MapHeap();

            ProgramState programState = ProgramState.createNewInstance(
                    executionStack, symbolTable, output, fileTable, heap
            );

            executionStack.push(program);
            controller.addProgramState(programState);

            menu.addCommand(new RunExample(key, program.toString(), controller));
        } catch (TypeCheckException e) {
            System.err.println("Example " + key + ":\n" + program.toString() + "\nfailed type check: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Example " + key + " failed setup due to a runtime error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

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
        addExample(fileName, ex1, "1", menu);

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
        addExample(fileName, ex2, "2", menu);

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
        addExample(fileName, ex3, "3", menu);

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
        addExample(fileName, ex4, "4", menu);

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
        addExample(fileName, ex5, "5", menu);

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
        addExample(fileName, ex6, "6", menu);

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
        addExample(fileName, ex7, "7", menu);

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
        addExample(fileName, ex8, "8", menu);

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
        addExample(fileName, ex9, "9", menu);

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
        addExample(fileName, ex10, "10", menu);

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
        addExample(fileName, ex11, "11", menu);

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
        addExample(fileName, ex12, "12", menu);

        Statement ex13 = new CompoundStatement(
                new VariableDeclaration("v", new IntegerType()),
                new PrintStatement(new VariableNameExpression("a"))
        );
        addExample(fileName, ex13, "13", menu);

        menu.show();
    }
}