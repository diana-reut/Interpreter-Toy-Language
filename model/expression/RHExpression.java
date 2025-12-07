package model.expression;

import model.state.Heap;
import model.state.SymbolTable;
import model.value.RefValue;
import model.value.Value;

public record RHExpression(Expression expression) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws WrongTypeException {
        Value value = expression.evaluate(symbolTable, heap);
        if(!(value instanceof RefValue refValue))
            throw new WrongTypeException("Tried to read a non-ref value!");
        int addr = refValue.getAddress();
        if(addr == 0 || !(heap.isDefined(addr)))
            throw new RuntimeException("Tried to read a non-defined address!");
        return heap.get(addr);
    }

    @Override
    public String toString() {
        return "rH(" + expression + ")";
    }
}
