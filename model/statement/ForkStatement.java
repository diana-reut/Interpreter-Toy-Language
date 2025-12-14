package model.statement;

import model.state.*;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
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
    public ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException {
        statement.typeCheck(env.deepCopy());
        return env;
    }

    @Override
    public String toString() {
        return "fork(" + statement + ')';
    }
}
