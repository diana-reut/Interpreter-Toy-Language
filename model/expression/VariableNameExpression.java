package model.expression;

import model.state.Heap;
import model.state.SymbolTable;
import model.value.Value;

public record VariableNameExpression(String variableName) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) {
        if (!symbolTable.isDefined(variableName)) throw new NotDefinedException("Variable not defined");
        return symbolTable.getValue(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
