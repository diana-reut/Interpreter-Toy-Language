package model.statement;

import model.expression.Expression;
import model.state.Heap;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public record WHStatement(String variableName, Expression expression) implements Statement{
    @Override
    public ProgramState execute(ProgramState state) {
        Heap heap = state.heap();
        SymbolTable symbolTable = state.symbolTable();
        if(!(symbolTable.isDefined(variableName)))
            throw new RuntimeException("Variable " + variableName + " is not defined");
        if(!(symbolTable.getType(variableName) instanceof RefType))
            throw new RuntimeException("Type " + variableName + " is not a ref type");
        Value varValue = symbolTable.getValue(variableName);
        if(!(varValue instanceof RefValue))
            throw new RuntimeException("Variable " + variableName + " is not a ref value");
        int addr = ((RefValue)symbolTable.getValue(variableName)).getAddress();
        if(addr == 0 || !(heap.isDefined(addr)))
            throw new RuntimeException("Address " + addr + " is not defined");
        Value value = expression.evaluate(symbolTable, state.heap());
        Type locationType = ((RefType)symbolTable.getType(variableName)).getInner();
        if(!(value.getType().equals(locationType)))
            throw new RuntimeException("Type mismatch");
        heap.update(addr, value);
        return state;
    }

    @Override
    public String toString() {
        return "wH(" + variableName + ", " + expression + ")";
    }
}
