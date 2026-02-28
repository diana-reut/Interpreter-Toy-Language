package view;

import controller.Controller;
import controller.IController;
import model.exceptions.MyExceptionModel;
import model.expression.*;
import model.programState.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.command.ExitCommand;
import view.command.RunExample;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Interpreter {

    public static void clearFile()
    {
        try {
            var logFile = new PrintWriter(new BufferedWriter(new FileWriter("execution.txt", false)));
            logFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addExample(IStatement statement, String key, TextMenu textMenu) {
        var typeChecker = new TypeChecker();
        try {
            statement.typecheck(typeChecker);

            IRepository repository = new Repository();
            IController controller = new Controller(repository);
            var output = new Output();
            var exeStack = new ExeStack();
            var symTable = new SymTable();
            var fileTable = new FileTable();
            var heap = new Heap();
            ProgramState programState = ProgramState.createNewInstance(symTable, output, exeStack, fileTable, heap);

            exeStack.push(statement);
            controller.addProgramState(programState);

            textMenu.addCommand(new RunExample(key, statement.toString(),controller));
        } catch (MyExceptionModel e) {
            System.out.println("Example "+ key + " did not pass the typechecker: " + e.getMessage());
        }
    }

    public static IStatement getStatement1(){
        //int v; v = 2; print(v);
        return new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExpr(new IntValue(2))),
                        new PrintStmt(new VariableNameExpr("v"))
                )
        );
    }

    public static IStatement getStatement2(){
        //int a; int b; a = 2+3*5; b = a+1; print(b)
        return new CompStmt(
                new VarDeclStmt(new IntType(), "a"),
                new CompStmt(
                        new VarDeclStmt(new IntType(), "b"),
                        new CompStmt(
                                new AssignStmt("a", new ArithmeticExpr(new ValueExpr(new IntValue(2)), new ArithmeticExpr(new ValueExpr(new IntValue(3)), new ValueExpr(new IntValue(5)), "*"), "+")),
                                new CompStmt(
                                        new AssignStmt("b", new ArithmeticExpr(new VariableNameExpr("a"), new ValueExpr(new IntValue(1)), "+")),
                                        new PrintStmt(new VariableNameExpr("b"))
                                )
                        )
                )
        );
    }

    public static IStatement getStatement3(){
        //bool a; int v; a = true; if a then v=2 else v=3; print(v);
        return new CompStmt(
                new VarDeclStmt(new BoolType(), "a"),
                new CompStmt(
                        new VarDeclStmt(new IntType(), "v"),
                        new CompStmt(
                                new AssignStmt("a", new ValueExpr(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VariableNameExpr("a"),
                                                new AssignStmt("v", new ValueExpr(new IntValue(2))),
                                                new AssignStmt("v", new ValueExpr(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VariableNameExpr("v"))
                                )
                        )
                )
        );
    }

    public static IStatement getStatement4(){
        //string varf; varf = "test.in"; openRFile(varf); int varc; readFile(varf, varc);
        // print(varc); readFile(varf, varc); print(varc); closeRFile(varf);
        return new CompStmt(
                new VarDeclStmt(new StringType(), "varf"),
                new CompStmt(
                        new AssignStmt("varf", new ValueExpr(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFile(new VariableNameExpr("varf")),
                                new CompStmt(
                                        new VarDeclStmt(new IntType(), "varc"),
                                        new CompStmt(
                                                new ReadFile(new VariableNameExpr("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VariableNameExpr("varc")),
                                                        new CompStmt(
                                                                new ReadFile(new VariableNameExpr("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VariableNameExpr("varc")),
                                                                        new CloseRFile(new VariableNameExpr("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement getStatement5(){
        //int x; x = 2; print(x == 2); print(x > 2); print(x < 2);print(x >= 2); print(x <= 2); print(x!=2)
        return new CompStmt(
                new VarDeclStmt(new IntType(), "x"),
                new CompStmt(
                        new AssignStmt("x", new ValueExpr(new IntValue(2))),
                        new CompStmt(
                                new PrintStmt(new RelationalExpr(new VariableNameExpr("x"), new ValueExpr(new IntValue(2)), "==")),
                                new CompStmt(
                                        new PrintStmt(new RelationalExpr(new VariableNameExpr("x"), new ValueExpr(new IntValue(2)), ">")),
                                        new CompStmt(
                                                new PrintStmt(new RelationalExpr(new VariableNameExpr("x"), new ValueExpr(new IntValue(2)), "<")),
                                                new CompStmt(
                                                        new PrintStmt(new RelationalExpr(new VariableNameExpr("x"), new ValueExpr(new IntValue(2)), ">=")),
                                                        new CompStmt(
                                                                new PrintStmt(new RelationalExpr(new VariableNameExpr("x"), new ValueExpr(new IntValue(2)), "<=")),
                                                                new PrintStmt(new RelationalExpr(new VariableNameExpr("x"), new ValueExpr(new IntValue(2)), "!="))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement getStatement6(){
        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new NewStatement("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(
                                    new NewStatement("a", new VariableNameExpr("v")),
                                        new CompStmt(
                                                new PrintStmt(new VariableNameExpr("v")),
                                                new PrintStmt(new VariableNameExpr("a"))
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement getStatement7(){
        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new NewStatement("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(
                                        new NewStatement("a", new VariableNameExpr("v")),
                                        new CompStmt(
                                                new PrintStmt(new rHExpr(new VariableNameExpr("v"))),
                                                new PrintStmt(new ArithmeticExpr(new rHExpr(new rHExpr(new VariableNameExpr("a"))), new ValueExpr(new IntValue(5)), "+"))
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement getStatement8(){
        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new NewStatement("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new rHExpr(new VariableNameExpr("v"))),
                                new CompStmt(
                                        new wHStatement("v", new ValueExpr(new IntValue(30))),
                                        new PrintStmt(new ArithmeticExpr(new rHExpr(new VariableNameExpr("v")), new ValueExpr(new IntValue(5)), "+"))
                                )
                        )
                )
        );
    }

    public static IStatement getStatement9(){
        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new NewStatement("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(
                                        new NewStatement("a", new VariableNameExpr("v")),
                                        new CompStmt(
                                                new NewStatement("v", new ValueExpr(new IntValue(30))),
                                                new PrintStmt(new rHExpr(new rHExpr(new VariableNameExpr("a"))))
                                        )
                                )
                        )
                )
        );
    }

    public static  IStatement getStatement10(){
        //Ref int v;new(v,20); new(v,30), print(rH(v))
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new NewStatement("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new NewStatement("v", new ValueExpr(new IntValue(30))),
                                new PrintStmt(new rHExpr(new VariableNameExpr("v")))
                        )
                )
        );
    }

    public static IStatement getStatement11(){
        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        return new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExpr(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(0)), ">"),
                                        new CompStmt(
                                                new PrintStmt(new VariableNameExpr("v")),
                                                new AssignStmt("v", new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(1)), "-"))
                                        )
                                ),
                                new PrintStmt(new VariableNameExpr("v"))
                        )
                )
        );
    }

    public static IStatement getStatement12(){
        //   int v; Ref int a; v=10;new(a,22);
        //   fork(wH(a,30);v=32;print(v);print(rH(a)));
        //   print(v);print(rH(a))
        return new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new  CompStmt(
                        new VarDeclStmt(new RefType(new IntType()), "a"),
                        new CompStmt(
                                new AssignStmt("v", new ValueExpr(new IntValue(10))),
                                new CompStmt(
                                        new NewStatement("a", new ValueExpr(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new wHStatement("a", new ValueExpr(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExpr(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VariableNameExpr("v")),
                                                                                new PrintStmt(new rHExpr(new VariableNameExpr("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VariableNameExpr("v")),
                                                        new PrintStmt(new rHExpr(new VariableNameExpr("a")))
                                                )

                                        )
                                )
                        )
                )
        );
    }

    public static IStatement getStatement13(){
        //this will not pass the typechecker
        //bool v; v = 2; print(v);
        return new CompStmt(
                new VarDeclStmt(new BoolType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExpr(new IntValue(2))),
                        new PrintStmt(new VariableNameExpr("v"))
                )
        );
    }

    public static IStatement getStatement14(){
        //Ref int a; Ref int b; int v;
        //new(a,0); new(b,0);
        //wh(a,1); wh(b,2);
        //v=(rh(a)<rh(b))?100:200;
        //print(v);
        //v= ((rh(b)-2)>rh(a))?100:200;
        //print(v);
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "a"),
                new CompStmt(
                        new VarDeclStmt(new RefType(new IntType()), "b"),
                        new CompStmt(
                                new VarDeclStmt(new IntType(), "v"),
                                new CompStmt(
                                        new NewStatement("a", new ValueExpr(new IntValue(0))),
                                        new  CompStmt(
                                                new NewStatement("b", new ValueExpr(new IntValue(0))),
                                                new CompStmt(
                                                        new wHStatement("a", new ValueExpr(new IntValue(1))),
                                                        new CompStmt(
                                                                new wHStatement("b", new ValueExpr(new IntValue(2))),
                                                                new CompStmt(
                                                                        new CondAssignmentStmt(
                                                                                "v",
                                                                                new RelationalExpr(
                                                                                        new rHExpr(new VariableNameExpr("a")),
                                                                                        new rHExpr(new VariableNameExpr("b")),
                                                                                        "<"
                                                                                ),
                                                                                new ValueExpr(new IntValue(100)),
                                                                                new ValueExpr(new IntValue(200))
                                                                        ),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VariableNameExpr("v")),
                                                                                new CompStmt(
                                                                                        new CondAssignmentStmt(
                                                                                                "v",
                                                                                                new RelationalExpr(
                                                                                                        new ArithmeticExpr(new rHExpr(new VariableNameExpr("b")), new ValueExpr(new IntValue(2)), "-"),
                                                                                                        new rHExpr(new VariableNameExpr("a")),
                                                                                                        ">"
                                                                                                ),
                                                                                                new ValueExpr(new IntValue(100)),
                                                                                                new ValueExpr(new IntValue(200))
                                                                                        ),
                                                                                        new PrintStmt(new VariableNameExpr("v"))
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement getStatement15(){
        //int v; v=20; wait(10);print(v*10)
        return new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new WaitStmt(new ValueExpr(new IntValue(10))),
                                new PrintStmt(new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(10)), "*"))
                        )
                )
        );
    }

    public  static IStatement getStatement16(){
        //int a; int b; int c;
        //a=1;b=2;c=5;
        //(switch(a*10)
        //(case (b*c) : print(a);print(b))
        //(case (10) : print(100);print(200))
        //(default : print(300)));
        //print(300)
        var switchStatement = new SwitchStmt(
                new ArithmeticExpr(new VariableNameExpr("a"), new ValueExpr(new IntValue(10)), "*"),
                new ArithmeticExpr(new VariableNameExpr("b"), new VariableNameExpr("c"), "*"),
                new CompStmt(
                        new PrintStmt(new VariableNameExpr("a")),
                        new PrintStmt(new VariableNameExpr("b"))
                ),
                new ValueExpr(new IntValue(10)),
                new CompStmt(
                        new PrintStmt(new ValueExpr(new IntValue(100))),
                        new PrintStmt(new ValueExpr(new IntValue(200)))
                ),
                new PrintStmt(new ValueExpr(new IntValue(300)))
        );
        return new CompStmt(
                new VarDeclStmt(new IntType(), "a"),
                new CompStmt(
                        new VarDeclStmt(new IntType(), "b"),
                        new CompStmt(
                                new VarDeclStmt(new IntType(), "c"),
                                new CompStmt(
                                        new AssignStmt("a", new ValueExpr(new IntValue(1))),
                                        new CompStmt(
                                                new AssignStmt("b", new ValueExpr(new IntValue(2))),
                                                new CompStmt(
                                                        new AssignStmt("c", new ValueExpr(new IntValue(5))),
                                                        new CompStmt(
                                                                switchStatement,
                                                                new PrintStmt(new ValueExpr(new IntValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement getStatement17(){
        //int v; v=0;
        //(while(v<3) (fork(print(v);v=v+1);v=v+1);
        //sleep(5);
        //print(v*10)
        return new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExpr(new IntValue(0))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(3)), "<"),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new PrintStmt(new VariableNameExpr("v")),
                                                                new AssignStmt("v", new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(1)), "+"))
                                                        )
                                                ),
                                                new AssignStmt("v", new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(1)), "+"))
                                        )
                                ),
                                new CompStmt(
                                        new SleepStmt(new ValueExpr(new IntValue(5))),
                                        new PrintStmt(new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(10)), "*"))
                                )
                        )
                )
        );
    }

    public static IStatement getStatement18(){
        //int v; v=0;
        //(repeat (fork(print(v);v=v-1);v=v+1) until v==3);
        //x=1;y=2;z=3;w=4;
        //print(v*10)
        return new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExpr(new IntValue(0))),
                        new CompStmt(
                                new RepeatUntilStmt(
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new PrintStmt(new VariableNameExpr("v")),
                                                                new AssignStmt("v", new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(1)), "-"))
                                                        )
                                                ),
                                                new AssignStmt("v", new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(1)), "+"))
                                        ),
                                        new RelationalExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(3)), "==")
                                ),
                                new CompStmt(
                                        new VarDeclStmt(new IntType(), "x"),
                                        new CompStmt(
                                                new VarDeclStmt(new IntType(), "y"),
                                                new CompStmt(
                                                        new VarDeclStmt(new IntType(), "z"),
                                                        new CompStmt(
                                                                new VarDeclStmt(new IntType(), "w"),
                                                                new CompStmt(
                                                                        new AssignStmt(
                                                                                "x",
                                                                                new ValueExpr(new IntValue(1))
                                                                        ),
                                                                        new CompStmt(
                                                                                new AssignStmt(
                                                                                        "y",
                                                                                        new ValueExpr(new IntValue(2))
                                                                                ),
                                                                                new CompStmt(
                                                                                        new AssignStmt(
                                                                                                "z",
                                                                                                new ValueExpr(new IntValue(3))
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new AssignStmt(
                                                                                                        "w",
                                                                                                        new ValueExpr(new IntValue(4))
                                                                                                ),
                                                                                                new PrintStmt(
                                                                                                        new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(10)), "*")
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement getStatement19(){
        //int v; v=20;
        //(for(v=0;v<3;v=v+1) fork(print(v);v=v+1) );
        //print(v*10)
        var forStatement = new ForStmt(
                new AssignStmt("v", new ValueExpr(new IntValue(0))),
                new RelationalExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(3)), "<"),
                new AssignStmt("v", new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(1)), "+")),
                new ForkStmt(new CompStmt(
                        new PrintStmt(new VariableNameExpr("v")),
                        new AssignStmt("v", new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(1)), "+"))
                ))
        );
        return new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new  CompStmt(
                        new AssignStmt("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                forStatement,
                                new PrintStmt(new ArithmeticExpr(new VariableNameExpr("v"), new ValueExpr(new IntValue(10)), "*"))
                        )
                )
        );
    }

    static void main() {
        clearFile();
        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "Exit"));

        addExample(getStatement1(), "1", textMenu);
        addExample(getStatement2(), "2", textMenu);
        addExample(getStatement3(), "3", textMenu);
        addExample(getStatement4(), "4", textMenu);
        addExample(getStatement5(), "5", textMenu);
        addExample(getStatement6(), "6", textMenu);
        addExample(getStatement7(), "7", textMenu);
        addExample(getStatement8(), "8", textMenu);
        addExample(getStatement9(), "9", textMenu);
        addExample(getStatement10(), "10", textMenu);
        addExample(getStatement11(), "11", textMenu);
        addExample(getStatement12(), "12", textMenu);
        addExample(getStatement13(), "13", textMenu);
        addExample(getStatement14(), "14", textMenu);
        addExample(getStatement15(), "15", textMenu);
        addExample(getStatement16(), "16", textMenu);
        addExample(getStatement17(), "17", textMenu);
        addExample(getStatement18(), "18", textMenu);
        addExample(getStatement19(), "19", textMenu);

        textMenu.show();
    }
}
