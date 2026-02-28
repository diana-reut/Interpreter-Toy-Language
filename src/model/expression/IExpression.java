package model.expression;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.IHeapDict;
import model.type.Type;
import model.value.Value;

public interface IExpression {
    Value eval(IDictionary<String, Value> symTable, IHeapDict<Integer, Value> heap) throws MyExceptionModel;
    Type typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel;
}
