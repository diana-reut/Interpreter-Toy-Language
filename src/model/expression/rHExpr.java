package model.expression;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.IHeapDict;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public record rHExpr(IExpression expr) implements IExpression {

    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeapDict<Integer, Value> heap) throws MyExceptionModel {
        var value = expr.eval(symTable, heap);
        if(!(value instanceof RefValue refValue))
        {
            throw new MyExceptionModel("Expression is not a reference");
        }
        int address = refValue.getAddress();
        if(!(heap.containsKey(address)))
        {
            throw new MyExceptionModel("Address not found in the heap!");
        }
        return heap.getValue(address);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type type = expr.typecheck(typeEnv);
        if(type instanceof RefType refType){
            return refType.getInner();
        }
        throw new MyExceptionModel("Expression is not a reference");
    }

    @Override
    public String toString() {
        return "rH("+ expr.toString() +")";
    }

}
