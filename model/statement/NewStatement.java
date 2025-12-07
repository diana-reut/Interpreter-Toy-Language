package model.statement;

import model.expression.Expression;
import model.state.Heap;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public record NewStatement(String variableName, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTable symbolTable = state.symbolTable();
        if (!symbolTable.isDefined(variableName)) {
            throw new RuntimeException("Variable not defined");
        }
        Type varType = symbolTable.getType(variableName);
        if (!(varType instanceof RefType)) {
            throw new RuntimeException("Type is not a reference");
        }
        Type locationType = ((RefType)varType).getInner();
        Value value = expression.evaluate(symbolTable, state.heap());
        if (!value.getType().equals(locationType)) {
            throw new RuntimeException("Type mismatch");
        }
        Heap heap = state.heap();
        int addr = heap.add(value);
        Value newRefValue = new RefValue(addr, locationType);
        symbolTable.update(variableName, newRefValue);
        return state;
    }

    @Override
    public Statement copyStatement() {
        return new NewStatement(variableName, expression);
    }

    @Override
    public String toString() {
        return "new(" + variableName +", " + expression + ")";
    }
}
