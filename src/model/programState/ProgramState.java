package model.programState;

import controller.exceptions.MyExceptionController;
import model.exceptions.MyExceptionModel;
import model.statement.IStatement;
import model.value.Value;

import java.io.BufferedReader;

public record ProgramState(int id, IDictionary<String, Value> symTable , IList<Value> output, IStack<IStatement> exeStack, IDictionary<String, BufferedReader> fileTable, IHeapDict<Integer, Value> heap) {
    private static int NextId = 0;

    public static ProgramState createNewInstance(IDictionary<String, Value> symTable , IList<Value> output, IStack<IStatement> exeStack, IDictionary<String, BufferedReader> fileTable, IHeapDict<Integer, Value> heap){
        return new ProgramState(NextId++, symTable, output, exeStack, fileTable, heap);
    }

    private synchronized static int nextId() {
        return NextId++;
    }

    @Override
    public String toString() {
        return id + ":\n"+ exeStack + heap + symTable + fileTable + output;
    }

    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public ProgramState oneStep() throws MyExceptionController {
        if(!isNotCompleted()){
            throw new MyExceptionController("Execution stack is empty");
        }
        IStatement statement = exeStack.pop();
        try {
            return statement.execute(this);
        } catch (MyExceptionModel e) {
            throw new MyExceptionController(e.getMessage());
        }
    }

    @Override
    public int id() {
        return id;
    }
}
