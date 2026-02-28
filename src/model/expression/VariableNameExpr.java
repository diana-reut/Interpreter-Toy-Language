package model.expression;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.IHeapDict;
import model.type.Type;
import model.value.Value;

public record VariableNameExpr(String variableName) implements IExpression {
    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeapDict<Integer, Value> heap) throws MyExceptionModel {
        return symTable.getValue(variableName);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        return typeEnv.getValue(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
