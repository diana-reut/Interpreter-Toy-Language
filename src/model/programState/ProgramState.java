package model.programState;

import controller.exceptions.MyExceptionController;
import model.exceptions.MyExceptionModel;
import model.statement.IStatement;
import model.value.Value;

import java.io.BufferedReader;
import java.util.List;
import java.util.Stack;

public record ProgramState(int id, Stack<IDictionary<String, Value>> symTableStack , IList<Value> output, IStack<IStatement> exeStack, IDictionary<String, BufferedReader> fileTable, IHeapDict<Integer, Value> heap, ILatchTable<Integer, Integer> latchTable, ISemaphoreTable<Integer, Integer, List<Integer>> semaphoreTable, IBarrierTable<Integer, Integer, List<Integer>> barrierTable, ILockTable<Integer, Integer> lockTable, IProcTable<String, List<String>, IStatement> procTable) {
    private static int NextId = 0;

    public static ProgramState createNewInstance(Stack<IDictionary<String, Value>> symTableStack , IList<Value> output, IStack<IStatement> exeStack, IDictionary<String, BufferedReader> fileTable, IHeapDict<Integer, Value> heap, ILatchTable<Integer, Integer> latchTable, ISemaphoreTable<Integer, Integer, List<Integer>> semaphoreTable, IBarrierTable<Integer, Integer, List<Integer>> barrierTable, ILockTable<Integer, Integer> lockTable, IProcTable<String, List<String>, IStatement> procTable){
        return new ProgramState(nextId(), symTableStack, output, exeStack, fileTable, heap, latchTable, semaphoreTable, barrierTable, lockTable, procTable);
    }

    private synchronized static int nextId() {
        return NextId++;
    }

    public Stack<IDictionary<String, Value>> symTableStackClone() {
        Stack<IDictionary<String, Value>> newSymTableStack = new Stack<>();
        for (IDictionary<String, Value> symTable : symTableStack) {
            newSymTableStack.push(symTable.deepcopy());
        }
        return newSymTableStack;
    }

    public IDictionary<String, Value> symTable(){
        return symTableStack.peek();
    }

    @Override
    public String toString() {
        return id + ":\n"+ exeStack + heap + symTableStack + fileTable + output + latchTable + semaphoreTable + barrierTable + lockTable;
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
