package model.expression;

import model.state.Heap;
import model.state.SymbolTable;
import model.type.Type;
import model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable symbolTable, Heap heap) throws WrongTypeException;
}
