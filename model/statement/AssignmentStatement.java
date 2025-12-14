package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.value.Value;

public record AssignmentStatement(String variableName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTable symbolTable = state.symbolTable();
        if (!symbolTable.isDefined(variableName)) {
            throw new RuntimeException("Variable not defined");
        }
        Value value = expression.evaluate(symbolTable, state.heap());
        if (!value.getType().equals(symbolTable.getType(variableName))) {
            throw new RuntimeException("Type mismatch");
        }
        symbolTable.update(variableName, value);
        return null;
    }

    @Override
    public Statement copyStatement() {
        return new AssignmentStatement(variableName, expression);
    }

    @Override
    public String toString() {
        return variableName + "=" + expression.toString();
    }
}
