package model.expression;

import model.state.Heap;
import model.state.SymbolTable;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
import model.value.Value;

public record ValueExpression(Value value) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) {
        return value;
    }

    @Override
    public Type typeCheck(ITypeEnvironment env) throws TypeCheckException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
