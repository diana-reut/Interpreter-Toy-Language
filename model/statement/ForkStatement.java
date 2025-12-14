package model.statement;

import model.state.*;
import model.value.Value;
import java.util.Map;

public record ForkStatement(Statement statement) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        Map<String, Value> newMapSymTable = state.symbolTable().deepcopy();
        var newSymTable = new MapSymbolTable();
        newSymTable.setMap(newMapSymTable);
        var newStack = new StackExecutionStack();
        newStack.push(statement);
        return ProgramState.createNewInstance(newStack, newSymTable, state.output(), state.fileTable(), state.heap());
    }

    @Override
    public String toString() {
        return "fork(" + statement + ')';
    }
}
