//import controller.Controller;
//import controller.IController;
//import model.expression.BinaryOperatorExpression;
//import model.expression.ValueExpression;
//import model.expression.VariableNameExpression;
//import model.state.*;
//import model.statement.*;
//import model.type.BooleanType;
//import model.type.IntegerType;
//import model.type.StringType;
//import model.value.BooleanValue;
//import model.value.IntegerValue;
//import model.value.StringValue;
//import repository.IRepository;
//import repository.Repository;
//
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String args[]){
//
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Do you want to display the Program State? [y/n]");
//        boolean display;
//        display = scan.nextLine().equals("y");
//
//        while (true) {
//            System.out.println("Enter the number of the exercise you want to run:");
//            System.out.println("1) int v; v = 2; print(v)");
//            System.out.println("2) int a; int b; a=2+3*5; b=a+1; Print(b)");
//            System.out.println("3) bool a; int v; a=true; (If a Then v=2 Else v=3);Print(v)");
//            System.out.println("4) string varf; " +
//                    "varf=\"test.in\"; " +
//                    "openRFile(varf); " +
//                    "int varc; " +
//                    "readFile(varf,varc);print(varc); " +
//                    "readFile(varf,varc);print(varc); " +
//                    "closeRFile(varf)");
//            System.out.println("0) Exit");
//            int n = scan.nextInt();
//
//            IRepository repository = new Repository("logFile.txt");
//            IController controller = new Controller(repository);
//            ExecutionStack executionStack = new StackExecutionStack();
//            Output output = new ListOutput();
//            SymbolTable symbolTable = new MapSymbolTable();
//            FileTable fileTable = new MapFileTable();
//            Heap heap = new MapHeap();
//            ProgramState programState = new ProgramState(executionStack, symbolTable, output,  fileTable, heap);
//            //int v; v = 2; print(v)
//            if (n == 1) {
//                Statement ex1 = new CompoundStatement(
//                        new VariableDeclaration("v", new IntegerType()),
//                        new CompoundStatement(
//                                new AssignmentStatement(
//                                        "v",
//                                        new ValueExpression(new IntegerValue(2))),
//                                new PrintStatement(new VariableNameExpression("v"))
//                        )
//                );
//                executionStack.push(ex1);
//
//                controller.addProgramState(programState);
//                controller.allStep(display);
//            }
//
//            //int a; int b; a=2+3*5; b=a+1; Print(b)
//            if(n == 2) {
//                Statement ex2 = new CompoundStatement(
//                        new VariableDeclaration("a", new IntegerType()),
//                        new CompoundStatement(
//                                new VariableDeclaration("b", new IntegerType()),
//                                new CompoundStatement(
//                                        new AssignmentStatement(
//                                                "a",
//                                                new BinaryOperatorExpression(
//                                                        new ValueExpression(new IntegerValue(2)),
//                                                        new BinaryOperatorExpression(
//                                                                new ValueExpression(new IntegerValue(3)),
//                                                                new ValueExpression(new IntegerValue(5)),
//                                                                "*"
//                                                        ),
//                                                        "+"
//                                                )
//                                        ),
//                                        new CompoundStatement(
//                                                new AssignmentStatement(
//                                                        "b",
//                                                        new BinaryOperatorExpression(
//                                                                new VariableNameExpression("a"),
//                                                                new ValueExpression(new IntegerValue(1)),
//                                                                "+"
//                                                        )
//                                                ),
//                                                new PrintStatement(
//                                                        new VariableNameExpression("b")
//                                                )
//                                        )
//                                )
//                        )
//
//                );
//                executionStack.push(ex2);
//
//                controller.addProgramState(programState);
//                controller.allStep(display);
//            }
//
//            //bool a; int v; a=true; (If a Then v=2 Else v=3);Print(v)
//            if (n == 3) {
//                Statement ex3 = new CompoundStatement(
//                        new VariableDeclaration("a", new BooleanType()),
//                        new CompoundStatement(
//                                new VariableDeclaration("v", new IntegerType()),
//                                new CompoundStatement(
//                                        new AssignmentStatement(
//                                                "a",
//                                                new ValueExpression(new BooleanValue(true))
//                                        ),
//                                        new CompoundStatement(
//                                                new IfStatement(
//                                                        new VariableNameExpression("a"),
//                                                        new AssignmentStatement(
//                                                                "v",
//                                                                new ValueExpression(new IntegerValue(2))
//                                                        ),
//                                                        new AssignmentStatement(
//                                                                "v",
//                                                                new ValueExpression(new IntegerValue(3))
//                                                        )
//                                                ),
//                                                new PrintStatement(
//                                                        new VariableNameExpression("v")
//                                                )
//                                        )
//                                )
//                        )
//                );
//                executionStack.push(ex3);
//
//                controller.addProgramState(programState);
//                controller.allStep(display);
//            }
//            //string varf;
//            //varf="test.in";
//            //openRFile(varf);
//            //int varc;
//            //readFile(varf,varc);print(varc);
//            //readFile(varf,varc);print(varc)
//            //closeRFile(varf)
//            if(n == 4){
//                Statement ex4 = new CompoundStatement(
//                        new VariableDeclaration("varf", new StringType()),
//                        new CompoundStatement(
//                                new AssignmentStatement(
//                                        "varf",
//                                        new ValueExpression(new StringValue("test.in"))
//                                ),
//                                new CompoundStatement(
//                                        new OpenRFileStatement(new VariableNameExpression("varf")),
//                                        new CompoundStatement(
//                                                new VariableDeclaration("varc", new IntegerType()),
//                                                new CompoundStatement(
//                                                        new ReadFileStatement(new VariableNameExpression("varf"), "varc"),
//                                                        new CompoundStatement(
//                                                                new PrintStatement(new VariableNameExpression("varc")),
//                                                                new CompoundStatement(
//                                                                        new ReadFileStatement(new VariableNameExpression("varf"), "varc"),
//                                                                        new CompoundStatement(
//                                                                                new PrintStatement(new VariableNameExpression("varc")),
//                                                                                new CloseRFileStatement(new VariableNameExpression("varf"))
//                                                                        )
//                                                                )
//                                                        )
//                                                )
//                                        )
//                                )
//                        )
//                );
//                executionStack.push(ex4);
//
//                controller.addProgramState(programState);
//                controller.allStep(display);
//            }
//            if(n == 0)
//                break;
//        }
//    }
//}