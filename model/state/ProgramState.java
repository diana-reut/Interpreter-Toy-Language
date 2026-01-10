package model.state;

import controller.ControllerException;
import model.statement.Statement;

public record ProgramState
        (int id, ExecutionStack executionStack, SymbolTable symbolTable, Output output, FileTable fileTable, Heap heap) {
    private static int nextId = 0;

    public static ProgramState createNewInstance(ExecutionStack executionStack, SymbolTable symbolTable, Output output, FileTable fileTable, Heap heap){
        return new ProgramState(getNextId(), executionStack, symbolTable, output, fileTable, heap);
    }

    private synchronized static int getNextId() {
        return nextId++;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String toString() {
        return "Program " + id + ":\n" + executionStack.toString() + fileTable.toString() + symbolTable.toString() + heap.toString() + output.toString()
                + "--------------------------------------------------------------------------------------------\n";
    }

    Boolean isNotCompleted(){
        return (!(executionStack.isEmpty()));
    }

    public ProgramState oneStep() throws ControllerException {
        if (isNotCompleted()) {
            Statement statement = executionStack.pop();
            return statement.execute(this);
        }
        throw new ControllerException("The stack is empty");
    }
}
