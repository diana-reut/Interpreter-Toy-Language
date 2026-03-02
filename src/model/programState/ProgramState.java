package model.programState;

import controller.exceptions.MyExceptionController;
import model.exceptions.MyExceptionModel;
import model.statement.IStatement;
import model.value.Value;

import java.io.BufferedReader;
import java.util.List;

public record ProgramState(int id, IDictionary<String, Value> symTable , IList<Value> output, IStack<IStatement> exeStack, IDictionary<String, BufferedReader> fileTable, IHeapDict<Integer, Value> heap, ILatchTable<Integer, Integer> latchTable, ISemaphoreTable<Integer, Integer, List<Integer>> semaphoreTable, IBarrierTable<Integer, Integer, List<Integer>> barrierTable, ILockTable<Integer, Integer> lockTable) {
    private static int NextId = 0;

    public static ProgramState createNewInstance(IDictionary<String, Value> symTable , IList<Value> output, IStack<IStatement> exeStack, IDictionary<String, BufferedReader> fileTable, IHeapDict<Integer, Value> heap, ILatchTable<Integer, Integer> latchTable, ISemaphoreTable<Integer, Integer, List<Integer>> semaphoreTable, IBarrierTable<Integer, Integer, List<Integer>> barrierTable, ILockTable<Integer, Integer> lockTable){
        return new ProgramState(NextId++, symTable, output, exeStack, fileTable, heap, latchTable, semaphoreTable, barrierTable, lockTable);
    }

    private synchronized static int nextId() {
        return NextId++;
    }

    @Override
    public String toString() {
        return id + ":\n"+ exeStack + heap + symTable + fileTable + output + latchTable + semaphoreTable + barrierTable + lockTable;
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
