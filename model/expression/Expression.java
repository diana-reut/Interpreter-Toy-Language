package model.expression;

import model.state.Heap;
import model.state.SymbolTable;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
import model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable symbolTable, Heap heap) throws WrongTypeException;
    Type typeCheck(ITypeEnvironment env) throws TypeCheckException;
}
