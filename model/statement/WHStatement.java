package model.statement;

import model.expression.Expression;
import model.state.Heap;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.RefType;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
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
        return null;
    }

    @Override
    public ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException {
        Type typeVar = env.getType(variableName);
        Type typeExp = expression.typeCheck(env);
        if(!(typeVar instanceof RefType)){
            throw new TypeCheckException("Type " + variableName + " is not a ref type");
        }
        Type locationType = ((RefType)typeVar).getInner();
        if(!(typeExp.equals(locationType))){
            throw new TypeCheckException("Type mismatch");
        }
        return env;
    }

    @Override
    public String toString() {
        return "wH(" + variableName + ", " + expression + ")";
    }
}
