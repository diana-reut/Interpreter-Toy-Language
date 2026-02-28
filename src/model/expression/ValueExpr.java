package model.expression;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.IHeapDict;
import model.type.Type;
import model.value.Value;


public record ValueExpr(Value value) implements IExpression {
    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeapDict<Integer, Value> heap) throws MyExceptionModel {
        return value;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
