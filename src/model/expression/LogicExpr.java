package model.expression;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.IHeapDict;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public record LogicExpr(IExpression ex1, IExpression ex2, String operand) implements IExpression {
    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeapDict<Integer, Value> heap) throws MyExceptionModel {
        Value v1 = ex1.eval(symTable, heap);
        if(!(v1.getType() instanceof BoolType)){
            throw new MyExceptionModel("Logic expression requires a boolean value");
        }
        Value v2 = ex2.eval(symTable, heap);
        if(!(v2.getType() instanceof BoolType)){
            throw new MyExceptionModel("Logic expression requires a boolean value");
        }
        BoolValue bv1 = (BoolValue)v1;
        BoolValue bv2 = (BoolValue)v2;
        return switch (operand) {
            case "&&" -> new BoolValue(bv1.getValue() && bv2.getValue());
            case "||" -> new BoolValue(bv1.getValue() || bv2.getValue());
            default -> throw new MyExceptionModel("Operation not supported");
        };
    }

    @Override
    public String toString() {
        return ex1 + " " + operand + " " + ex2 + "\n";
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type t1, t2;
        t1 = ex1.typecheck(typeEnv);
        t2 = ex2.typecheck(typeEnv);
        if(t1.equals(new BoolType())) {
            if (t2.equals(new BoolType())) {
                return new BoolType();
            }
            throw new MyExceptionModel("Second operand is not bool");
        }
        throw new MyExceptionModel("First operand is not bool");
    }
}
