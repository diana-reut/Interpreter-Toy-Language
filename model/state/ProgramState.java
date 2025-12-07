package model.state;

public record ProgramState
        (ExecutionStack executionStack, SymbolTable symbolTable, Output output, FileTable fileTable, Heap heap) {
    @Override
    public String toString() {
        return executionStack.toString() + fileTable.toString() + symbolTable.toString() + heap.toString() + output.toString()
                + "--------------------------------------------------------------------------------------------\n";
    }
}
